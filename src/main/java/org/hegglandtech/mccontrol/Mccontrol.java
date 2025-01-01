package org.hegglandtech.mccontrol;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.hegglandtech.mccontrol.listeners.listeners;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

public final class Mccontrol extends JavaPlugin {

    private static Mccontrol instance;
    private MemoryStorage memoryStorage;
    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        instance = this;
        memoryStorage = new MemoryStorage();
        memoryStorage.loadMemory();
        pluginManager = getServer().getPluginManager();

        new listeners();

        getLogger().info("mc-control loaded");
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static Mccontrol getInstance() {
        return instance;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public MemoryStorage getMemoryStorage() {
        return memoryStorage;
    }
}
