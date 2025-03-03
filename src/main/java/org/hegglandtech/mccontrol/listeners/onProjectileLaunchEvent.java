package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.PluginManager;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.enums.Arrow_Types;
import org.hegglandtech.mccontrol.utils.PlayerCheckPermission;
import org.hegglandtech.mccontrol.utils.Player_Permission;

public class onProjectileLaunchEvent implements Listener {

    @EventHandler
    public void handleProjectileLaunchEvent(ProjectileLaunchEvent event) {

        if (!Arrow_Types.isProtected(event.getEntity().getType())) {
            return;
        }

        Player player = (Player) event.getEntity().getShooter();

        if (player == null) {
            return;
        }

        boolean hasPermission = new PlayerCheckPermission(player).validate(Player_Permission.canUseArrows);

        if (!hasPermission) {
            player.sendMessage("You are not allowed to shoot arrows!");
            event.setCancelled(true);
        }

    }

    public boolean load() {
        try {
            PluginManager pluginManager = Mccontrol.getInstance().getPluginManager();
            pluginManager.registerEvents(this, Mccontrol.getInstance());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

