package de.peaqe.simplecoins;

import de.peaqe.coinapi.api.Coins;
import de.peaqe.simplecoins.commands.CoinsCommand;
import de.peaqe.simplecoins.commands.CoinsManagerCommand;
import de.peaqe.simplecoins.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SimpleCoins extends JavaPlugin {

    private static SimpleCoins instance;

    public final String prefix = "§9§lCOINS §8• §7";

    @Override
    public void onEnable() {

        // Instancing...
        instance = this;

        // Register Command's
        this.registerCommand("coins", new CoinsCommand());
        this.registerCommand("coinmanager", new CoinsManagerCommand());

        // Register Event's
        this.registerEvent(new PlayerJoinListener());

    }

    private final void registerCommand(String commandName, CommandExecutor command) {
        Objects.requireNonNull(Bukkit.getPluginCommand(commandName)).setTabCompleter((TabCompleter) command);
        Objects.requireNonNull(Bukkit.getPluginCommand(commandName)).setExecutor(command);
    }

    private final void registerEvent(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public static SimpleCoins getInstance() {
        return instance;
    }

}
