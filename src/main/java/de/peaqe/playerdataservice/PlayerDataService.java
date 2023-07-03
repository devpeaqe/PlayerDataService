package de.peaqe.playerdataservice;

import de.peaqe.playerdataservice.commands.DataCommand;
import de.peaqe.playerdataservice.listener.PlayerJoinListener;
import de.peaqe.playerdataservice.utils.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerDataService extends JavaPlugin {

    private static PlayerDataService instance;

    public final String prefix = "§9§lNOVIAMC §8• §7";

    @Override
    public void onEnable() {

        // Instancing...
        instance = this;

        // Registering Command
        Bukkit.getPluginCommand("data").setExecutor(new DataCommand());
        Bukkit.getPluginCommand("data").setTabCompleter(new DataCommand());

        // Registering Event
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

    }

    public static PlayerDataService getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        PlayerDataManager manager = new PlayerDataManager();
        manager.closeConnection();
    }
}
