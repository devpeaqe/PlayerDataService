package de.peaqe.simplecoins.commands;

import de.peaqe.coinapi.api.Coins;
import de.peaqe.coinapi.utils.CoinsFormatter;
import de.peaqe.simplecoins.SimpleCoins;
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
import java.util.Objects;

/*
 *
 *  Class by peaqe created in 2023
 *  Class: CoinsCommand
 *
 *  Information's:
 *  Type: Java-Class | Bukkit Command
 *  Created: 03.07.2023 / 11:04
 *
 */

public class CoinsCommand implements CommandExecutor, TabExecutor {

    SimpleCoins main = SimpleCoins.getInstance();
    CoinsFormatter coinsFormatter = new CoinsFormatter();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(main.prefix + "Du musst online sein, um diesen Befehl ausführen zu können!");
            return true;
        }

        if (args.length == 1 && player.hasPermission("simplecoins.coins.view.other")) {

            var playerName = args[0];

            if (!(Bukkit.getOfflinePlayer(playerName).hasPlayedBefore())) {
                player.sendMessage(main.prefix + "Der Spieler §b" + playerName + "§7 konnte nicht gefunden werden!");
                return true;
            }

            var target = Bukkit.getOfflinePlayer(playerName);
            var targetUUID = target.getUniqueId();

            var coins = new Coins(targetUUID);

            if (coins.getCoins() <= 0) {
                player.sendMessage(main.prefix + "Der Spieler §b" + target.getName() + " §7besitzt derzeit kein Guthaben!");
                return true;
            }

            player.sendMessage(main.prefix + "Der Spieler §b" + target.getName() + " §7besitzt derzeit §b" + coinsFormatter.formatCoins(coins.getCoins()) + "$§7.");
            return true;

        } else if (args.length == 0) {

            var coinapi = new Coins(player.getUniqueId());
            var coins = coinapi.getCoins();

            if (coins <= 0) {
                player.sendMessage(main.prefix + "Du besitzt derzeit kein Guthaben!");
                return true;
            }

            player.sendMessage(main.prefix + "Du besitzt derzeit §b" + coinsFormatter.formatCoins(coins) + "$§7.");
            return true;
        }

        // Get the Usage
        if (player.hasPermission("simplecoins.coins.view.other")) {
            player.sendMessage(main.prefix + "Bitte verwende: /coins (Spieler).");
        } else {
            player.sendMessage(main.prefix + "Bitte verwende: /coins.");
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> matches = new ArrayList<>();

        if (args.length == 1 && sender.hasPermission("simplecoins.coins.view.other")) {

            for (OfflinePlayer target : Bukkit.getOfflinePlayers()) {

                if (!target.hasPlayedBefore()) return matches;
                if (Objects.requireNonNull(target.getName()).equalsIgnoreCase(sender.getName())) return matches;
                matches.add(target.getName());

            }

        }

        return matches;
    }
}