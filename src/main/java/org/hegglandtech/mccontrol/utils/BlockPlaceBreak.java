package org.hegglandtech.mccontrol.utils;

import org.bukkit.entity.Player;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

import java.util.List;

public class BlockPlaceBreak {

    private final MemoryStorage memoryStorage;


    public BlockPlaceBreak() {
        this.memoryStorage = Mccontrol.getInstance().getMemoryStorage();
    }

    public boolean validate(Player player) {

        List<String> memory = memoryStorage.getMemory();

        if (memory == null || memory.isEmpty()) {
            return false;
        }

        for (String line : memory) {
            if (line.contains(player.getUniqueId().toString())) {

                if (!McPlayer.canInteractWithBlocks(line)) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }
}
