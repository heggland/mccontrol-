package org.hegglandtech.mccontrol.listeners;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.hegglandtech.mccontrol.Mccontrol;

public class CustomListener implements Listener {
    private final Plugin plugin;
    private ListenerMode mode;
    private BukkitRunnable scheduledTask;

    public CustomListener(ListenerMode mode) {
        this.plugin = Mccontrol.getInstance();
        setMode(mode);
    }

    public void setMode(ListenerMode mode) {
        unregisterListener(); // Ensure previous mode is cleared
        this.mode = mode;
        switch (mode) {
            case ALWAYS_ACTIVE:
                registerListener();
                break;
            case SCHEDULED:
                startScheduledTask();
                break;
            case DYNAMIC:
                break;
        }
    }

    private void registerListener() {
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(this, plugin);
    }

    private void unregisterListener() {
        HandlerList.unregisterAll(this);
    }

    private void startScheduledTask() {
        scheduledTask = new BukkitRunnable() {
            @Override
            public void run() {
                registerListener();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        unregisterListener();
                    }
                }.runTaskLater(plugin, 100L);
            }
        };
        scheduledTask.runTaskTimer(plugin, 0L, 200L);
    }

    public void stopScheduledTask() {
        if (scheduledTask != null) {
            scheduledTask.cancel();
        }
    }

    public boolean load() {
        try {
            PluginManager pluginManager = Mccontrol.getInstance().getPluginManager();
            pluginManager.registerEvents(this, Mccontrol.getInstance());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
