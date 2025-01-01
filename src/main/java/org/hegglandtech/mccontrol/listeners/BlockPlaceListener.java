package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.hegglandtech.mccontrol.utils.BlockPlaceBreak;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        BlockPlaceBreak blockPlaceBreak = new BlockPlaceBreak();
        Player player = event.getPlayer();
        boolean test = blockPlaceBreak.validate(player);

        if (!test) {
            player.sendMessage("You are not allowed to place blocks");
            event.setCancelled(true);
        }
    }
}
