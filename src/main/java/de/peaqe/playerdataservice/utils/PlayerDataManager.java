package de.peaqe.playerdataservice.utils;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: PlayerDataManager
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 03.07.2023 / 08:58
 *
 */

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerDataManager {
    private final MongoClient mongoClient;
    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> mongoService;
    private final Gson gson = new Gson();

    public PlayerDataManager() {

        String databaseName = "admin";
        String databaseUrl = "mongodb://localhost:27017";

        mongoClient = new MongoClient(new MongoClientURI(databaseUrl));
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        collection = database.getCollection("playerData");

        this.mongoService = database.getCollection("playerData");

    }

    public void savePlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        String ipAddress = player.getAddress().getAddress().getHostAddress();

        Document document = new Document("uuid", uuid.toString())
                .append("name", name)
                .append("ipAddress", ipAddress);

        if (collection.find(new Document("uuid", uuid.toString())).first() != null) {
            collection.findOneAndDelete(new Document("uuid", uuid.toString()));
        }

        collection.insertOne(document);

    }

    public PlayerData getPlayerData(UUID uniqueId) {
        Document document = mongoService.find(Filters.eq("uniqueId", uniqueId)).first();
        return gson.fromJson(gson.toJson(document), PlayerData.class);
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
