package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.PluginManager;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.utils.PlayerCheckPermission;
import org.hegglandtech.mccontrol.utils.Player_Permission;

public class onBlockPlaceListener implements Listener {

    @EventHandler
    public void handleBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        boolean hasPermission = new PlayerCheckPermission(player).validate(Player_Permission.canBuild);

        if (!hasPermission) {
            player.sendMessage("You are not allowed to place blocks");
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
