package me.nopox.utils.storage.repository.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import me.nopox.utils.Utils;
import me.nopox.utils.storage.MongoConnection;
import me.nopox.utils.storage.repository.Repository;
import org.bson.BsonDocument;
import org.bson.Document;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.CompletableFuture;

/**
 * @author 98ping
 * @param <K> The name of the collection that the data should be stored in.
 * @param <T> The type of the data that should be stored.
 */
public abstract class MongoRepository<K extends String, T> implements Repository<K, T> {

    private final MongoCollection<Document> collection;
    private final Class<T> type;

    /**
     * This needs to be called to initialize the repository
     *
     * @param collectionName The name of the collection that this repository will use
     * @param clazz The class of the object that this repository will store
     */
    public MongoRepository(String collectionName, Class<T> clazz) {
        System.out.println("[MongoRepository] Initializing repository for collection " + collectionName);
        this.collection = MongoConnection.getInstance().getDatabase().getCollection(collectionName);
        this.type = clazz;
    }


    /**
     * This method saves the object to the database
     *
     * @param key The key of the object (Ex. A player's UUID)
     * @param value The object to save
     */
    @Override
    public void save(K key, T value) {
        long firstTime = System.currentTimeMillis();
        CompletableFuture.runAsync(() -> {
            Document doc = Document.parse(Utils.getInstance().getGSON().toJson(value));

            collection.updateOne(Filters.eq("_id", key), new Document("$set", doc), new UpdateOptions().upsert(true));

            System.out.println("[MongoDB] Saved object to mongo: " + key + " in " + (System.currentTimeMillis() - firstTime) + "ms.");

        });
    }

    /**
     * This method retrieves an object from the database
     *
     * @param id The key of the object (Ex. A player's UUID)
     * @return CompletableFuture<T> The object that was retrieved
     */
    @Override
    public CompletableFuture<T> byId(K id) {
        long firstTime = System.currentTimeMillis();
        return CompletableFuture.supplyAsync(() -> {
            Document doc = collection.find(Filters.eq("_id", id)).first();

            if (doc == null) return null;

            System.out.println("[MongoDB] Fetched object from mongo: " + id + " in " + (System.currentTimeMillis() - firstTime) + "ms.");

            return Utils.getInstance().getGSON().fromJson(doc.toJson(), type);
        });
    }

    /**
     * This method retrives an object from the database
     *
     * @param key The key of the object (Ex. _id)
     * @param value The value of the object (Ex. A player's UUID)
     */
    @Override
    public CompletableFuture<T> byKey(String key, K value) {
        long firstTime = System.currentTimeMillis();
        return CompletableFuture.supplyAsync(() -> {
            Document doc = collection.find(Filters.eq(key, value)).first();

            if (doc == null) return null;

            System.out.println("[MongoDB] Fetched object from mongo: " + key + " in " + (System.currentTimeMillis() - firstTime) + "ms.");

            return Utils.getInstance().getGSON().fromJson(doc.toJson(), type);
        });
    }

    /**
     * This method checks if an object exists in the database
     *
     * @param id The key of the object (Ex. A player's UUID)
     * @return True if the object exists, false if it doesn't
     */
    @Override
    public CompletableFuture<Boolean> exists(K id) {
        return CompletableFuture.supplyAsync(() -> collection.find(Filters.eq("_id", id)).first() != null);

    }

    /**
     * This method deletes an object from the database
     */
    @Override
    public void delete(K id) {
        CompletableFuture.runAsync(() -> collection.deleteOne(Filters.eq("_id", id)));
    }

    /**
     * This method deletes all objects from the database
     */
    @Override
    public void deleteAll() {
        CompletableFuture.runAsync(() -> collection.deleteMany(new BsonDocument()));
    }
}

