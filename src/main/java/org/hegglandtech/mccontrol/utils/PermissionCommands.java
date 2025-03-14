package org.hegglandtech.mccontrol.utils;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

import java.util.Arrays;

import static org.hegglandtech.mccontrol.enums.Player_Permission.Permission_list_string;

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
        this.args = command.split(" ");
    }

    public void updatePermission() {

        if (!command.startsWith("permission")) return;

        if (!isAdmin()) return;
        if (!argsValid()) return;

        if (command.split(" ").length < 4) return;

        this.playerIdentifier = args[1]; // player name or player UUID
        this.action = args[2];

        if (args[3].equals("-all")) {
            this.permission = Permission_list_string;
        } else {
            this.permission = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
        }

        updatePlayerPermission();
    }

    private void updatePlayerPermission() {
        PlayerUpdatePermission playerPermission = new PlayerUpdatePermission(player);
        playerPermission.update(playerIdentifier, action, permission);
    }

    public void printMemory() {

        if (!isAdmin()) return;

        MemoryStorage memoryStorage = Mccontrol.getInstance().getMemoryStorage();
        ServerLogger.print(memoryStorage.getMemory(true));
        player.sendMessage("See the console for memory");
    }

    public void reloadMemory() {
        if (!isAdmin()) return;

        MemoryStorage memoryStorage = Mccontrol.getInstance().getMemoryStorage();
        memoryStorage.clearMemory();
        memoryStorage.loadMemory();
        player.sendMessage("Memory reloaded");
    }

    public boolean isAdmin() {
        if (!player.isOp()) {
            player.sendMessage("You are not allowed to use commands");
            event.setCancelled(true);
            return false;
        }

        return true;
    }


    public void generateToken() {
        if (!isAdmin()) return;
        if (!argsValid()) return;

        if (this.command.replace("generatetoken", "").isEmpty()) {
            player.sendMessage("No permissions specified");
            ServerLogger.print("No permissions specified");
            return;
        }

        if (command.startsWith("generatetoken")) {
            PlayerUpdatePermission playerPermission = new PlayerUpdatePermission(this.player);
            Token token = new Token();
            String tokenString = token.generate(16);

            String permissions = this.command.replace("generatetoken ", "");

            if (permissions.equals("-all")) {
                permissions = Permission_list_string;
            }

            playerPermission.createEmptyPlayer(tokenString, permissions);
        }
    }

    public void validateToken() {

        if (command.replace("token", "").isEmpty()) {
            player.sendMessage("No token specified");
            ServerLogger.print("No token specified");
            return;
        }

        PlayerUpdatePermission playerPermission = new PlayerUpdatePermission(this.player);
        playerPermission.updatePlayerUsingToken(this.args[1]);
    }

    private boolean argsValid() {
        return args != null && args.length != 0;
    }

    private String getPermissions() {
        return Permission_list_string;
    }

}
