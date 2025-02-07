package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.PluginManager;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.utils.PermissionTabCompleter;
import org.hegglandtech.mccontrol.utils.PlayerUpdatePermission;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

import java.util.Objects;

public class onCommandPlayerListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (!isPlayerAllowed(player, event)) return;
        if (!event.getMessage().startsWith("/mccontrol:")) return;

        String command = event.getMessage().replace("/mccontrol:", "");

        if (command.startsWith("permission")) {
            handlePermissionCommand(player, command);
        } else if (command.equals("getmemory")) {
            handleGetMemoryCommand(player);
        }
    }

    private boolean isPlayerAllowed(Player player, PlayerCommandPreprocessEvent event) {
        if (!player.isOp()) {
            player.sendMessage("You are not allowed to use commands");
            event.setCancelled(true);
            return false;
        }
        return true;
    }

    private void handlePermissionCommand(Player player, String command) {
        String[] args = command.split(" ");
        if (args.length != 4) {
            player.sendMessage("Usage: /mccontrol:permission <permission> grant|revoke playername");
            return;
        }

        String permission = args[1];
        String action = args[2];
        String playerName = args[3];

        PlayerUpdatePermission playerPermission = new PlayerUpdatePermission();
        playerPermission.update(permission, action, playerName);
    }

    private void handleGetMemoryCommand(Player player) {
        MemoryStorage memoryStorage = Mccontrol.getInstance().getMemoryStorage();
        player.sendMessage(memoryStorage.getMemory(true));
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
