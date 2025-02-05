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

    public boolean validate(Player_Permission permission) {

        LoadPlayerFromMemory loadPlayerFromMemory = new LoadPlayerFromMemory();
        Player playerData = loadPlayerFromMemory.getPlayer(player);

        if (playerData == null) return false;
        return playerData.checkPermission(permission);
    }
}
