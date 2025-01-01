package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.hegglandtech.mccontrol.utils.BlockPlaceBreak;


public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        BlockPlaceBreak blockPlaceBreak = new BlockPlaceBreak();
        Player player = event.getPlayer();
        blockPlaceBreak.validate(player);

        if (!blockPlaceBreak.validate(player)) {
            player.sendMessage("You are not allowed to break blocks");
            event.setCancelled(true);
        }
    }
}
