package de.peaqe.simplecoins.listener;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: PlayerJoinListener
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 03.07.2023 / 11:31
 *
 */

import de.peaqe.coinapi.api.Coins;
import de.peaqe.coinapi.api.CoinsManager;
import de.peaqe.simplecoins.SimpleCoins;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@SuppressWarnings(value = "unused")
public class PlayerJoinListener implements Listener {

    SimpleCoins main = SimpleCoins.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        var player = event.getPlayer();
        var coinsapi = new Coins(player.getUniqueId());

        if (player.hasPlayedBefore()) return;

        coinsapi.updateCoins(1000);

        player.sendMessage(main.prefix + "Herzlich willkommen auf unserem Netzwerk.");
        player.sendMessage(main.prefix + "Du hast §b1000$ §7gut geschrieben bekommen.");
        return;

    }

}
