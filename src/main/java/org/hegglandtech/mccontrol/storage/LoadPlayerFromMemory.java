package org.hegglandtech.mccontrol.storage;

import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.utils.Player;
import org.hegglandtech.mccontrol.utils.ServerLogger;

import java.util.List;
import java.util.Objects;

public class LoadPlayerFromMemory {

    private final org.bukkit.entity.Player player;
    MemoryStorage memoryStorage = Mccontrol.getInstance().getMemoryStorage();
    List<String> memory = memoryStorage.getMemory();

    public LoadPlayerFromMemory() {
        this.player = null;
    }

    public LoadPlayerFromMemory(org.bukkit.entity.Player player) {
        this.player = player;
    }


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

        // check if online player uuid is in memory
        if (player != null) {
            String playerEntry = memory.stream()
                    .filter(line -> line.contains(player.getUniqueId().toString()))
                    .findFirst()
                    .orElse(null);

            if (playerEntry != null) {
                ServerLogger.print("Player " + player.getName() + " is found by UUID, ignoring token");
                return null;
            }
        }

        String playerEntry = memory.stream()
                .filter(line -> line.contains(token))
                .findFirst()
                .orElse(null);

        if (playerEntry == null) return null;

        return new Player(playerEntry);
    }
}
