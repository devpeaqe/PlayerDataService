package de.peaqe.playerdataservice.commands;

import de.peaqe.playerdataservice.PlayerDataService;
import de.peaqe.playerdataservice.utils.PlayerData;
import de.peaqe.playerdataservice.utils.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/*
 *
 *  Class by peaqe created in 2023
 *  Class: PlayerDataCommand
 *
 *  Information's:
 *  Type: Java-Class | Bukkit Command
 *  Created: 03.07.2023 / 09:14
 *
 */

public class DataCommand implements CommandExecutor, TabExecutor {

    PlayerDataService main = PlayerDataService.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return true;

        if (!(player.hasPermission("noviamc.command.data"))) {
            player.sendMessage(main.prefix + "Dazu hast du keine Rechte! §8(§bnoviamc.command.data§8)");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(main.prefix + "Bitte nutze: /data (Spieler)");
            return true;
        }

        String targetName = args[0];
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        if (!(target.hasPlayedBefore())) {
            player.sendMessage(main.prefix + "Der Spieler §b" + targetName + " §7konnte nicht gefunden werden!");
            return true;
        }

        var UUID = target.getUniqueId();

        PlayerData playerData = new PlayerDataManager().getPlayerData(UUID);

        if(playerData.name() == null || playerData.uuid() == null || playerData.ipAddress() == null) {
            player.sendMessage(main.prefix + "Der Spieler §b" + targetName + " §7konnte nicht gefunden werden!");
            return true;
        }

        player.sendMessage(main.prefix + "Hier siehst du nun alle gesammelten §bDaten§7:");
        player.sendMessage(main.prefix + "Benutzername: §b" + playerData.name() + "§7,");
        player.sendMessage(main.prefix + "UUID: §b" + playerData.uuid() + "§7,");
        player.sendMessage(main.prefix + "IP-Adresse: §b" + playerData.ipAddress() + "§7.");

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> matches = new ArrayList<>();

        if (args.length == 1) {

            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {

                if (player.getName().equalsIgnoreCase(sender.getName())) return matches;
                if (new PlayerDataManager().getPlayerData(player.getUniqueId()) == null) return matches;
                matches.add(player.getName());

            }

        }

        return matches;
    }
}