package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.plugin.PluginManager;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.enums.Bucket_Types;
import org.hegglandtech.mccontrol.utils.PlayerCheckPermission;
import org.hegglandtech.mccontrol.utils.Player_Permission;

public class onBucketEmptyListener implements Listener {

    @EventHandler
    public void handleBucketEmpty(PlayerBucketEmptyEvent event) {

        if (!Bucket_Types.isProtected(event.getBucket())) {
            return;
        }

        Player player = event.getPlayer();

        boolean hasPermission = new PlayerCheckPermission(player).validate(Player_Permission.canUseBucket);

        if (!hasPermission) {
            player.sendMessage("You are not allowed to use this bucket fluid.");
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
