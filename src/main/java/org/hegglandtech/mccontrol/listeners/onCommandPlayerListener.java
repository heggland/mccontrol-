package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.PluginManager;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.utils.PermissionTabCompleter;
import org.hegglandtech.mccontrol.utils.PlayerPermission;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

import java.util.Objects;

public class onCommandPlayerListener implements Listener {

    @EventHandler
        public void onCommand(PlayerCommandPreprocessEvent event) {
            Player player = event.getPlayer();

            if (!player.isOp()) {
                player.sendMessage("You are not allowed to use commands");
                event.setCancelled(true);
            }

            String command = event.getMessage();

            if (!command.startsWith("/mccontrol:")) return;
            command = command.replace("/mccontrol:", "");

            if (command.startsWith("permission")) {

                if (command.split(" ").length != 4) {
                    player.sendMessage("Usage: /mccontrol:permission <permission> grant|revoke playername");
                    return;
                }

                System.out.println(command);

                String permission = command.split(" ")[1];
                String action = command.split(" ")[2];
                String playerName = command.split(" ")[3];

                PlayerPermission playerPermission = new PlayerPermission();
                playerPermission.update(permission, action, playerName);

            }

            if (command.equals("getmemory")) {
                MemoryStorage memoryStorage = Mccontrol.getInstance().getMemoryStorage();
                player.sendMessage(memoryStorage.getMemory(true));
            }
        }


    public boolean load() {
        try {
            PluginManager pluginManager = Mccontrol.getInstance().getPluginManager();
            pluginManager.registerEvents(this, Mccontrol.getInstance());

            Objects.requireNonNull(Mccontrol.getInstance().getCommand("mccontrol:permission")).setTabCompleter(new PermissionTabCompleter());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

