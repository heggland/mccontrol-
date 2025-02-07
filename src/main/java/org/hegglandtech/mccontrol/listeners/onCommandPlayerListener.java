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
import org.hegglandtech.mccontrol.utils.ServerLogger;

import java.util.Objects;

public class onCommandPlayerListener implements Listener {

    org.bukkit.entity.Player player;

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        player = event.getPlayer();

        if (!player.isOp()) {
            player.sendMessage("You are not allowed to use commands");
            event.setCancelled(true);
            return;
        }

        if (!event.getMessage().startsWith("/mccontrol:")) return;

        String command = event.getMessage().replace("/mccontrol:", "");

        ServerLogger.print(command);

        if (command.startsWith("permission")) {
            command = command.replace("permission ", "");
            giveOnlinePlayerPermission(command);
        }

        if (command.startsWith("modify")) {
            command = command.replace("modify ", "");
            modifyOfflinePlayerPermission(command);
        }

        if (command.equals("getmemory")) {
            handleGetMemoryCommand(player);
        }

    }

    private void giveOnlinePlayerPermission(String command) {
        String[] args = command.split(" ");

        if (args.length != 3) {
            return;
        }

        String permission = args[0];
        String action = args[1];
        String playerName = args[2];

        PlayerUpdatePermission playerPermission = new PlayerUpdatePermission(player);
        playerPermission.update(permission, action, playerName);
    }

    private void modifyOfflinePlayerPermission(String command) {
        String[] args = command.split(" ");

        if (args.length != 3) {
            return;
        }

        String permission = args[0];
        String action = args[1];
        String uuid = args[2];

        PlayerUpdatePermission playerPermission = new PlayerUpdatePermission(player);
        playerPermission.updatePlayerUsingUuid(permission, action, uuid);

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
