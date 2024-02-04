package me.lordierclaw.todoserver.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;

public class ConfigManager {

    private static ConfigManager instance;
    private final String secretDir;
    private final HashMap<String, Dotenv> configMap = new HashMap<>();

    private ConfigManager() {
        secretDir = System.getenv("todo.secret");
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public Dotenv load(String config) {
        if (!configMap.containsKey(config)) {
            configMap.put(config, Dotenv.configure().directory(secretDir).filename(config).load());
        }
        return configMap.get(config);
    }
}
