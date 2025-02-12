package org.hegglandtech.mccontrol.utils;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

public class PermissionCommands {

    org.bukkit.entity.Player player;
    PlayerCommandPreprocessEvent event;
    String command;

    String[] args;

    String permission;
    String action;
    String playerIdentifier;


    public PermissionCommands(PlayerCommandPreprocessEvent event) {
        this.event = event;
        this.player = event.getPlayer();
        this.command = event.getMessage().replace("/mccontrol:", "");

        if (command.split(" ").length != 4) return;

        this.args = command.split(" ");
        this.permission = args[1];
        this.action = args[2];
        this.playerIdentifier = args[3]; // player name or player UUID

    }

    public void updatePermission() {

        if (!isAdmin()) return;
        if (!argsValid()) return;

        if (command.startsWith("permission")) {
            updatePlayerPermission();
        }
    }

    private void updatePlayerPermission() {
        PlayerUpdatePermission playerPermission = new PlayerUpdatePermission(player);
        playerPermission.update(permission, action, playerIdentifier);
    }

    public void printMemory() {

        if (!isAdmin()) return;

        MemoryStorage memoryStorage = Mccontrol.getInstance().getMemoryStorage();
        ServerLogger.print(memoryStorage.getMemory(true));
        player.sendMessage("See the console for memory");
    }

    public boolean isAdmin() {
        if (!player.isOp()) {
            player.sendMessage("You are not allowed to use commands");
            event.setCancelled(true);
            return false;
        }

        return true;
    }

    private boolean argsValid() {
        return args != null && args.length != 0;
    }

}
