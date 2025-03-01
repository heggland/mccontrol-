package org.hegglandtech.mccontrol.utils;

import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.storage.LoadPlayerFromMemory;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

import java.util.Arrays;
import java.util.List;

public class PlayerUpdatePermission {

    private static MemoryStorage memoryStorage = null;
    private static LoadPlayerFromMemory loadPlayerFromMemory;
    private static org.bukkit.entity.Player player;

    public PlayerUpdatePermission(org.bukkit.entity.Player playerSender) {
        memoryStorage = Mccontrol.getInstance().getMemoryStorage();
        player = playerSender;
        if (player == null) {
            loadPlayerFromMemory = new LoadPlayerFromMemory();
        } else {
            loadPlayerFromMemory = new LoadPlayerFromMemory(player);
        }
    }

    public void update(String playerName, String action, String permission) {
        org.bukkit.entity.Player player = Mccontrol.getInstance().getServer().getPlayer(playerName);

        if (player == null) {
            ServerLogger.print("Online player " + playerName + " not found.");
            ServerLogger.print("If UUID is used, trying to find player by UUID.");
            updatePlayerUsingUuid(permission, action, playerName);
            return;
        }

        Player playerData = loadPlayerFromMemory.getPlayer(player);

        if (playerData == null) {
            playerData = new Player(player);
        }

        if (action.equals("grant")) {
            List<String> permissions = Arrays.asList(permission.split(" "));
            playerData.setPermission(permissions);
            player.sendMessage(player.getName() + " has been granted the permission " + permission);
        } else if (action.equals("revoke")) {
            List<String> permissions = Arrays.asList(permission.split(" "));
            playerData.removePermission(permissions);
            player.sendMessage(player.getName() + " has been revoked the permission " + permission);
        }

        save(playerData);

        ServerLogger.print(playerData.name + " has been " + action + "ed the permission " + permission + ". Command issued by " + this.player.getName());

    }

    public void updatePlayerUsingUuid(String permission, String action, String uuid) {
        Player playerData = loadPlayerFromMemory.getPlayerByPlayerUuid(uuid);

        if (playerData == null) {
            ServerLogger.print("Player with UUID " + uuid + " not found.");
            return;
        }

        if (action.equals("grant")) {
            playerData.setPermission(Arrays.asList(permission.split(" ")));
        } else if (action.equals("revoke")) {
            playerData.removePermission(Arrays.asList(permission.split(" ")));
        }

        save(playerData);

        ServerLogger.print(playerData.name + " has been " + action + "ed the permission " + permission + ". Command issued by " + this.player.getName());
    }

    public void createEmptyPlayer(String token, String permissions) {
        try {
            Player playerData = new Player(token, permissions);
            memoryStorage.updateMemory(playerData.toString());
        } catch (Exception e) {
            ServerLogger.print("Error creating player");
            player.sendMessage("Error creating player");
        }
    }

    public void updatePlayerUsingToken(String token) {
        if (player == null) return;

        Player playerData = loadPlayerFromMemory.getPlayerByToken(token);

        if (playerData == null) {
            return;
        }

        playerData.setUuid(player.getUniqueId().toString());
        playerData.setName(player.getName());

        save(playerData, token);
    }

    private static void save(Player playerData, String token) {
        if (!playerData.getToken().equals(token)) return;

        playerData.setToken("");

        List<String> memory = memoryStorage.getMemory();

        for (String line : memory) {
            if (line.contains(token)) {
                memory.remove(line);
                break;
            }
        }

        memoryStorage.updateMemory(playerData.toString());

        memoryStorage.writeToFile(memoryStorage.getMemory(true));
    }

    private static void save(Player playerData) {
        List<String> memory = memoryStorage.getMemory();

        for (String line : memory) {
            if (line.contains(playerData.getUuid())) {
                memory.remove(line);
                break;
            }
        }

        memoryStorage.updateMemory(playerData.toString());
        memoryStorage.writeToFile(memoryStorage.getMemory(true));
    }
}
