package me.nopox.utils.storage.repository.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import me.nopox.utils.Utils;
import me.nopox.utils.storage.MongoConnection;
import me.nopox.utils.storage.repository.Repository;
import org.bson.Document;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class MongoRepository<K extends String, T extends Class<T>> implements Repository<K, T> {

    private final MongoCollection<Document> collection = MongoConnection.getInstance().getDatabase().getCollection(((Class<K>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]).getSimpleName());

    @Override
    public void save(K key, T value) {
        CompletableFuture.runAsync(() -> {
            Document doc = Document.parse(Utils.getInstance().getGSON().toJson(value));

            collection.updateOne(Filters.eq("_id", key), new Document("$set", doc), new UpdateOptions().upsert(true));


        });
    }

    @Override
    public CompletableFuture<T> byId(K id) {
        return CompletableFuture.supplyAsync(() -> {
            Document doc = collection.find(Filters.eq("_id", id)).first();

            if (doc == null) return null;

            return Utils.getInstance().getGSON().fromJson(doc.toJson(), (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
        });
    }

    @Override
    public boolean exists(K id) {
        return collection.find(Filters.eq("_id", id)).first() != null;
    }



}
