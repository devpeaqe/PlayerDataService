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
 *  Class: CoinsManagerCommand
 *
 *  Information's:
 *  Type: Java-Class | Bukkit Command
 *  Created: 03.07.2023 / 11:16
 *
 */

public class CoinsManagerCommand implements CommandExecutor, TabExecutor {

    SimpleCoins main = SimpleCoins.getInstance();
    CoinsFormatter coinsFormatter = new CoinsFormatter();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(main.prefix + "Du musst online sein, um diesen Befehl ausführen zu können!");
            return true;
        }

        if (!(player.hasPermission("simplecoins.coins.manage"))) {
            player.sendMessage(main.prefix + "Dazu hast du keine Rechte! §8(§bsimplecoins.coins.manage§8)");
            return true;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {

            var targetName = args[1];
            var target = Bukkit.getOfflinePlayer(targetName);

            if (!target.hasPlayedBefore()) {
                player.sendMessage(main.prefix + "Der Spieler §b" + targetName + " §7konnte nicht gefunden werden!");
                return true;
            }

            var coins = 0;

            try {
                coins = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage(main.prefix + "Bitte gebe eine gültige Anzahl an!");
                return true;
            }

            if (coins <= 0) {
                coins = 0;
            }

            var coinsapi = new Coins(target.getUniqueId());
            coinsapi.updateCoins(coins);

            player.sendMessage(main.prefix + "Der Spieler §b" + target.getName() + " §7besitzt nun §b" + coinsFormatter.formatCoins(coinsapi.getCoins()) + "$§7.");
            return true;

        } else if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {

            var targetName = args[1];
            var target = Bukkit.getOfflinePlayer(targetName);

            if (!target.hasPlayedBefore()) {
                player.sendMessage(main.prefix + "Der Spieler §b" + targetName + " §7konnte nicht gefunden werden!");
                return true;
            }

            var coins = 0;

            try {
                coins = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage(main.prefix + "Bitte gebe eine gültige Anzahl an!");
                return true;
            }

            if (coins <= 0) {
                coins = 0;
            }

            var coinsapi = new Coins(target.getUniqueId());
            coinsapi.removeCoins(coins);

            player.sendMessage(main.prefix + "Der Spieler §b" + target.getName() + " §7besitzt nun §b" + coinsFormatter.formatCoins(coinsapi.getCoins()) + "$§7.");
            return true;

        } else if (args.length == 2 && args[0].equalsIgnoreCase("clear")) {

            var targetName = args[1];
            var target = Bukkit.getOfflinePlayer(targetName);

            if (!target.hasPlayedBefore()) {
                player.sendMessage(main.prefix + "Der Spieler §b" + targetName + " §7konnte nicht gefunden werden!");
                return true;
            }

            var coinsapi = new Coins(target.getUniqueId());

            if (coinsapi.getCoins() <= 0) {
                player.sendMessage(main.prefix + "Der Spieler §b" + target.getName() + " §7besitzt derzeit kein Guthaben!");
                return true;
            }

            coinsapi.clearCoins();
            player.sendMessage(main.prefix + "Der Spieler §b" + target.getName() + " §7besitzt nun keine §bCoins §7mehr.");

            return true;

        } else if (args.length == 3 && args[0].equalsIgnoreCase("add")) {

            var targetName = args[1];
            var target = Bukkit.getOfflinePlayer(targetName);

            if (!target.hasPlayedBefore()) {
                player.sendMessage(main.prefix + "Der Spieler §b" + targetName + " §7konnte nicht gefunden werden!");
                return true;
            }

            var coins = 0;

            try {
                coins = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage(main.prefix + "Bitte gebe eine gültige Anzahl an!");
                return true;
            }

            if (coins <= 0) {
                coins = 0;
            }

            var coinsapi = new Coins(target.getUniqueId());
            coinsapi.addCoins(coins);

            player.sendMessage(main.prefix + "Der Spieler §b" + target.getName() + " §7besitzt nun §b" + coinsFormatter.formatCoins(coinsapi.getCoins()) + "$§7.");
            return true;

        }

        this.sendUsage(player);

        return false;
    }

    private void sendUsage(Player player) {
        player.sendMessage(main.prefix + "§8§m                    §r §bSIMPLE§8-§bCOINS §8§m                    ");
        player.sendMessage(main.prefix + "/coinmanager set (Spieler) (Coins)");
        player.sendMessage(main.prefix + "/coinmanager add (Spieler) (Coins)");
        player.sendMessage(main.prefix + "/coinmanager remove (Spieler) (Coins)");
        player.sendMessage(main.prefix + "/coinmanager clear (Spieler)");
        player.sendMessage(main.prefix + "§8§m                    §r §bSIMPLE§8-§bCOINS §8§m                    ");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> matches = new ArrayList<>();

        if (args.length == 1 && sender.hasPermission("simplecoins.coins.manage")) {

            matches.add("set");
            matches.add("add");
            matches.add("remove");
            matches.add("clear");

        }

        if (args.length == 2 && sender.hasPermission("simplecoins.coins.manage")) {

            for (OfflinePlayer target : Bukkit.getOfflinePlayers()) {

                if (!target.hasPlayedBefore()) return matches;
                if (Objects.requireNonNull(target.getName()).equalsIgnoreCase(sender.getName())) return matches;

                matches.add(target.getName());

            }

        }

        return matches;
    }
}