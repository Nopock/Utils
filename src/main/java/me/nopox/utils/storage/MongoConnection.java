package me.nopox.utils.storage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

public class MongoConnection {

    @Getter private static MongoConnection instance;

    private MongoClient mongoClient;

    private MongoDatabase database;


    /**
     * Initiates a connection to the MongoDB server.
     *
     * @param uri The URI of the MongoDB server.
     */
    public MongoConnection(String uri) {
        instance = this;
        this.mongoClient = MongoClients.create(uri);

    }

    /**
     * Sets the database to use.
     *
     * @param database Whichever database you would like to use.
     */
    public MongoConnection setDatabase(String database) {
        this.database = mongoClient.getDatabase(database);
        return this;
    }

    /**
     * Gets the database
     */
    public MongoDatabase getDatabase() {
        return database;
    }




}
