package org.hegglandtech.mccontrol.utils;

import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

import java.util.List;

public class PlayerUpdatePermission {

    private static MemoryStorage memoryStorage = null;
    private static LoadPlayerFromMemory loadPlayerFromMemory;
    private static org.bukkit.entity.Player player;

    public PlayerUpdatePermission(org.bukkit.entity.Player playerSender) {
        memoryStorage = Mccontrol.getInstance().getMemoryStorage();
        loadPlayerFromMemory = new LoadPlayerFromMemory();
        player = playerSender;
    }

    public void update(String permission, String action, String playerName) {
        org.bukkit.entity.Player player = Mccontrol.getInstance().getServer().getPlayer(playerName);

        if (player == null) {
            Mccontrol.getInstance().getLogger().warning("Player " + playerName + " not found.");
            return;
        }

        Player playerData = loadPlayerFromMemory.getPlayer(player);

        if (playerData == null) {
            playerData = new Player(player);
        }

        if (action.equals("grant")) {
            playerData.setPermission(Player_Permission.valueOf(permission));
            player.sendMessage(player.getName() + " has been granted the permission " + permission);
        } else if (action.equals("revoke")) {
            playerData.removePermission(Player_Permission.valueOf(permission));
            player.sendMessage(player.getName() + " has been revoked the permission " + permission);
        }

        save(playerData);

        ServerLogger.print(playerData.name + " has been " + action + "ed the permission " + permission + ". Command issued by " + this.player.getName());

    }

    public void updatePlayerUsingUuid(String permission, String action, String uuid) {
        Player playerData = loadPlayerFromMemory.getPlayerByPlayerUuid(uuid);

        if (playerData == null) {
            return;
        }

        if (action.equals("grant")) {
            playerData.setPermission(Player_Permission.valueOf(permission));
        } else if (action.equals("revoke")) {
            playerData.removePermission(Player_Permission.valueOf(permission));
        }

        save(playerData);
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
