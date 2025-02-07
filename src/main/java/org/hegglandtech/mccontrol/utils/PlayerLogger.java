package org.hegglandtech.mccontrol.utils;

import org.hegglandtech.mccontrol.Mccontrol;

public class PlayerLogger extends Player {

    private static final Mccontrol instance = Mccontrol.getInstance();

    public PlayerLogger(org.bukkit.entity.Player player) {
        super(player);
    }

    public void print(String text) {
        instance.getLogger().info(this.name + ": " + text);
    }
}
