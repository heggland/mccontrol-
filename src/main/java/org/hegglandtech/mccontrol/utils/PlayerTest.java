package org.hegglandtech.mccontrol.utils;

import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

import java.util.List;

public class PlayerTest extends Player {

    private final org.bukkit.entity.Player player;

    public PlayerTest(org.bukkit.entity.Player player) {
        super(player);
        this.player = player;
    }

    public boolean validate() {

        MemoryStorage memoryStorage = Mccontrol.getInstance().getMemoryStorage();

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
