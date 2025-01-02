package org.hegglandtech.mccontrol.utils;

import org.bukkit.entity.Player;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

import java.util.List;

public class CanPlaceBlocks {

    private static MemoryStorage memoryStorage = null;

    public CanPlaceBlocks() {
        memoryStorage = Mccontrol.getInstance().getMemoryStorage();
    }

    public void update(String playerName, boolean canInteract) {
        Player player = Mccontrol.getInstance().getServer().getPlayer(playerName);

        if (player == null) {
            Mccontrol.getInstance().getLogger().warning("Player " + playerName + " not found.");
            return;
        }

        McPlayer playerData = new McPlayer(player);

        if (!canInteract) {
            setCanPlaceBlocks(playerData, false);
            player.sendMessage(player.getName() + " has been blocked from placing blocks.");
            return;
        }

        setCanPlaceBlocks(playerData, true);
        player.sendMessage(player.getName() + " has been allowed to place blocks.");
    }


    private static void setCanPlaceBlocks(McPlayer playerData, boolean value) {
        playerData.setCanInteractWithBlocks(value);

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
