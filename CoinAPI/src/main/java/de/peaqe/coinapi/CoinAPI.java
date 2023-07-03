package de.peaqe.coinapi;

import de.peaqe.coinapi.api.Coins;
import de.peaqe.coinapi.api.CoinsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class CoinAPI extends JavaPlugin {

    private static CoinAPI instance;

    public final String prefix = "§9§lCOINAPI §8• §7";

    @Override
    public void onEnable() {

        // Instancing...
        instance = this;

        // Logging activation
        Bukkit.getConsoleSender().sendMessage(this.prefix + "was activated");

    }

    @Override
    public void onDisable() {
        new CoinsManager(UUID.randomUUID()).closeConnection();
    }

    public static CoinAPI getInstance() {
        return instance;
    }
}
