package de.peaqe.playerdataservice.utils;

import java.util.UUID;

public record PlayerData(UUID uuid, String name, String ipAddress) {
}

