package org.hegglandtech.mccontrol.utils;

import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

import java.util.List;

public class PlayerTest {

    private final MemoryStorage memoryStorage;

    public PlayerTest() {
        this.memoryStorage = Mccontrol.getInstance().getMemoryStorage();
    }

    public boolean validate(org.bukkit.entity.Player player) {
        List<String> memory = memoryStorage.getMemory();

        if (memory == null || memory.isEmpty()) {
            return true;
        }

        String playerEntry = memory.stream()
                .filter(line -> line.contains(player.getUniqueId().toString()))
                .findFirst()
                .orElse(null);

        if (playerEntry != null) {
            return Player.canInteractWithBlocks(playerEntry);
        } else {
            return false;
        }
    }
}
