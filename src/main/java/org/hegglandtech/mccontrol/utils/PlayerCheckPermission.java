package org.hegglandtech.mccontrol.utils;

public class PlayerCheckPermission extends Player {

    private final org.bukkit.entity.Player player;

    public PlayerCheckPermission(org.bukkit.entity.Player player) {
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
