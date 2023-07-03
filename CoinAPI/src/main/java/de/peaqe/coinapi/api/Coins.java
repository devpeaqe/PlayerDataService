package de.peaqe.coinapi.api;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: Coins
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 03.07.2023 / 10:37
 *
 */

import java.util.UUID;

@SuppressWarnings(value = "unused")
public class Coins {

        final UUID uuid;

        public Coins(UUID uuid) {
                this.uuid = uuid;
        }

        public int getCoins() {
                return new CoinsManager(this.uuid).getCoins();
        }

        public void updateCoins(int coins) {
                new CoinsManager(this.uuid).updateCoins(coins);
        }

        public void clearCoins() {
                new CoinsManager(this.uuid).updateCoins(0);
        }

        public void addCoins(int coins) {

                CoinsManager manager = new CoinsManager(this.uuid);

                final var playerCoins = coins + manager.getCoins();
                manager.updateCoins(playerCoins);

        }

        public void removeCoins(int coins) {

                CoinsManager manager = new CoinsManager(this.uuid);

                var playerCoins = (manager.getCoins() - coins);

                if (playerCoins < 0) {
                        playerCoins = 0;
                }

                manager.updateCoins(playerCoins);

        }



}
