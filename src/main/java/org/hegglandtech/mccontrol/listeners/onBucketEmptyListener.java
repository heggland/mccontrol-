package org.hegglandtech.mccontrol.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.plugin.PluginManager;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.utils.PlayerTest;

import java.util.EnumSet;

public class onBucketEmptyListener implements Listener {

    private static final EnumSet<Material> BUCKET_TYPES = EnumSet.of(
            Material.WATER_BUCKET,
            Material.LAVA_BUCKET
    );

    @EventHandler
    public void onBucketPour(PlayerBucketEmptyEvent event) {

        if (!BUCKET_TYPES.contains(event.getBucket())) {
            return;
        }

        Player player = event.getPlayer();

        PlayerTest playerTest = new PlayerTest(player);

        boolean canPlace = playerTest.validate();

        if (!canPlace) {
            player.sendMessage("You are not allowed to this bucket fluid.");
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
