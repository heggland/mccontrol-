package org.hegglandtech.mccontrol.utils;

import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

import java.util.List;

public class PlayerPermission {

    private static MemoryStorage memoryStorage = null;

    public PlayerPermission() {
        memoryStorage = Mccontrol.getInstance().getMemoryStorage();
    }

    public void update(String permission, String action, String playerName) {
        org.bukkit.entity.Player player = Mccontrol.getInstance().getServer().getPlayer(playerName);

        if (player == null) {
            Mccontrol.getInstance().getLogger().warning("Player " + playerName + " not found.");
            return;
        }

        LoadPlayerFromMemory loadPlayerFromMemory = new LoadPlayerFromMemory();
        Player playerData = loadPlayerFromMemory.getPlayer(player);

        if (action.equals("grant")) {
            playerData.setPermission(Player_Permission.valueOf(permission));
            player.sendMessage(player.getName() + " has been granted the permission " + permission);
        } else if (action.equals("revoke")) {
            playerData.removePermission(Player_Permission.valueOf(permission));
            player.sendMessage(player.getName() + " has been revoked the permission " + permission);
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
