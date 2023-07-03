package de.peaqe.coinapi.api;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: CoinsManager
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 03.07.2023 / 10:38
 *
 */

import com.google.common.io.CountingOutputStream;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.UUID;

@SuppressWarnings(value = "unused")
public class CoinsManager {

    MongoClient mongoClient;
    private final MongoCollection<Document> collection;

    private final UUID uuid;

    public CoinsManager(UUID uuid) {

        this.uuid = uuid;

        String databaseName = "admin";
        String databaseUrl = "mongodb://localhost:27017";

        mongoClient = new MongoClient(new MongoClientURI(databaseUrl));
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        collection = database.getCollection("coins");

    }

    protected void updateCoins(int coins) {

        Document document = new Document("uuid", this.uuid.toString()).append("coins", coins);

        if (collection.find(new Document("uuid", uuid.toString())).first() != null) {
            collection.findOneAndDelete(new Document("uuid", uuid.toString()));
        }

        collection.insertOne(document);

    }

    protected int getCoins() {

        Document document = collection.find(new Document("uuid", this.uuid.toString())).first();
        if (document == null) return 0;

        return document.getInteger("coins");

    }

    public void closeConnection() {
        mongoClient.close();
    }

}
