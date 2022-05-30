package me.nopox.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import lombok.Getter;

@Getter
public class Utils {

    @Getter private static Utils instance;

    private final Gson GSON = new GsonBuilder()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .serializeNulls().setPrettyPrinting().create();

    public Utils() {
        instance = this;
    }
}
