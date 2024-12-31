package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.hegglandtech.mccontrol.commands.CanPlaceBlocks;

public class PlayerCommands implements Listener {

        @EventHandler
        public void onCommand(PlayerCommandPreprocessEvent event) {
            Player player = event.getPlayer();

            String command = event.getMessage();

            if (!command.startsWith("/")) return;

            command = command.substring(1);

            if (command.startsWith("canplaceblock")) {
                CanPlaceBlocks.canPlaceBlock(command, player);
            }
        }


}

