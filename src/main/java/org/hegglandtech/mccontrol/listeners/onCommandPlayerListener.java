package org.hegglandtech.mccontrol.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.PluginManager;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.utils.*;

import java.util.Objects;

public class onCommandPlayerListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {

        if (!event.getMessage().startsWith("/mccontrol:")) return;

        PermissionCommands permissionCommands = new PermissionCommands(event);

        String command = event.getMessage().replace("/mccontrol:", "");

        if (command.startsWith("permission")) {
            permissionCommands.addPermission();
            return;
        }

        if (command.startsWith("modify")) {
            permissionCommands.modifyPermission();
            return;
        }

        if (command.equals("getmemory")) {
            permissionCommands.printMemory();
            return;
        }

    }

    public boolean load() {
        try {
            PluginManager pluginManager = Mccontrol.getInstance().getPluginManager();
            pluginManager.registerEvents(this, Mccontrol.getInstance());

            Objects.requireNonNull(Mccontrol.getInstance().getCommand("mccontrol:permission"))
                    .setTabCompleter(new PermissionTabCompleter());

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
