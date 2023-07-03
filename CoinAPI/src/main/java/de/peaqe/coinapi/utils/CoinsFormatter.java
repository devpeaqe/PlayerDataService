package de.peaqe.coinapi.utils;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: DecimalFormatter
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 15.06.2023 / 00:43
 *
 */

@SuppressWarnings(value = "unused")
public class CoinsFormatter {

    public final String formatCoins(int coins) {
        String cash = "§b" + String.format("%,d", (int) coins);
        cash = cash.replace(".", "§7.§b");
        cash = cash.replace(",", "§7.§b");

        return cash;
    }

}
