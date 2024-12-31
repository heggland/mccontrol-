package org.hegglandtech.mccontrol;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.hegglandtech.mccontrol.listeners.BlockPlaceListener;
import org.hegglandtech.mccontrol.listeners.BlockBreakListener;
import org.hegglandtech.mccontrol.listeners.PlayerCommands; // Import PlayerCommands
import org.hegglandtech.mccontrol.storage.Storage;

public final class Mccontrol extends JavaPlugin {

    private static PluginManager plugin = null;

    @Override
    public void onEnable() {
        // Initialize and create the storage file
        Storage storage = new Storage();

        // Register the event listeners
        plugin = getServer().getPluginManager();
        plugin.registerEvents(new BlockPlaceListener(), this);
        plugin.registerEvents(new BlockBreakListener(), this);
        plugin.registerEvents(new PlayerCommands(), this); // Register PlayerCommands listener

        getLogger().info("mc-control plugin has started.");
    }

    @Override
    public void onDisable() {
        plugin = null;
        getLogger().info("mc-control plugin stopped.");
    }
}
