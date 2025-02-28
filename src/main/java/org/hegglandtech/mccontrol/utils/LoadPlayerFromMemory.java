package org.hegglandtech.mccontrol.utils;

import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

import java.util.List;
import java.util.Objects;

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

    public Player getPlayerByPlayerUuid(String uuid) {
        if (memory == null || memory.isEmpty()) return null;

        String playerEntry = memory.stream()
                .filter(line -> line.contains(uuid))
                .findFirst()
                .orElse(null);
        if (playerEntry == null) return null;

        return new Player(playerEntry);
    }

    public Player getPlayerByToken(String token) {
        if (memory == null || memory.isEmpty()) return null;

        String playerEntry = memory.stream()
                .filter(line -> line.contains(token))
                .findFirst()
                .orElse(null);

        if (playerEntry == null) return null;

        Player player = new Player(playerEntry);

        if (Objects.equals(player.getToken(), "")) return null;

        return player;
    }
}
