package org.hegglandtech.mccontrol.utils;

import org.hegglandtech.mccontrol.storage.LoadPlayerFromMemory;

public class PlayerCheckPermission extends Player {

    private final org.bukkit.entity.Player player;

    public PlayerCheckPermission(org.bukkit.entity.Player player) {
        super(player);
        this.player = player;
    }

    public boolean validate(Player_Permission permission) {

        try {
            LoadPlayerFromMemory loadPlayerFromMemory = new LoadPlayerFromMemory();
            Player playerData = loadPlayerFromMemory.getPlayer(player);

            if (playerData == null) return false;
            return playerData.checkPermission(permission);
        } catch (Exception e) {
            return false;
        }

    }
}
