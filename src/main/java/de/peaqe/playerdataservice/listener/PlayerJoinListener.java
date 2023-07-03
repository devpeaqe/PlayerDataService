package de.peaqe.playerdataservice.listener;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: PlayerJoinListener
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 03.07.2023 / 09:01
 *
 */

import de.peaqe.playerdataservice.PlayerDataService;
import de.peaqe.playerdataservice.utils.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@SuppressWarnings(value = "unused")
public class PlayerJoinListener implements Listener {

    PlayerDataService main = PlayerDataService.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        PlayerDataManager playerDataManager = new PlayerDataManager();

        playerDataManager.savePlayerData(player);

    }

}
