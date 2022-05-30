package me.nopox.utils.storage.repository.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import me.nopox.utils.Utils;
import me.nopox.utils.storage.MongoConnection;
import me.nopox.utils.storage.repository.Repository;
import org.bson.Document;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.CompletableFuture;

/**
 * @author 98ping
 * @param <K> The name of the collection that the data should be stored in.
 * @param <T> The type of the data that should be stored.
 */
public abstract class MongoRepository<K extends String, T> implements Repository<K, T> {

    private String collectionName;

    /**
     * This needs to be called to initialize the repository
     *
     * @param collectionName The name of the collection that this repository will use
     */
    public MongoRepository(String collectionName) {
        this.collectionName = collectionName;

    }

    private final MongoCollection<Document> collection = MongoConnection.getInstance().getDatabase().getCollection(collectionName);

    /**
     * This method saves the object to the database
     *
     * @param key The key of the object (Ex. A player's UUID)
     * @param value The object to save
     */
    @Override
    public void save(K key, T value) {
        CompletableFuture.runAsync(() -> {
            Document doc = Document.parse(Utils.getInstance().getGSON().toJson(value));

            collection.updateOne(Filters.eq("_id", key), new Document("$set", doc), new UpdateOptions().upsert(true));


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
        return CompletableFuture.supplyAsync(() -> {
            Document doc = collection.find(Filters.eq("_id", id)).first();

            if (doc == null) return null;

            return Utils.getInstance().getGSON().fromJson(doc.toJson(), (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
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



}
