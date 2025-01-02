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
            return true;
        }

        String playerEntry = memory.stream()
                .filter(line -> line.contains(player.getUniqueId().toString()))
                .findFirst()
                .orElse(null);

        if (playerEntry != null) {
            return McPlayer.canInteractWithBlocks(playerEntry);
        } else {
            return false;
        }
    }
}
