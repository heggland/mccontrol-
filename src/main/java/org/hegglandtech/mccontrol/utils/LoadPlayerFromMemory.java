package org.hegglandtech.mccontrol.utils;

import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

import java.util.List;

public class LoadPlayerFromMemory {

    MemoryStorage memoryStorage = Mccontrol.getInstance().getMemoryStorage();
    List<String> memory = memoryStorage.getMemory();

    public Player getPlayer(org.bukkit.entity.Player player) {
        if (memory == null || memory.isEmpty()) return null;

        String playerEntry = memory.stream()
                .filter(line -> line.contains(player.getUniqueId().toString()))
                .findFirst()
                .orElse(null);
        if (playerEntry == null) return null;

        return new Player(playerEntry);
    }
}
