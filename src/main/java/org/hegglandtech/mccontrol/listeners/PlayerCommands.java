package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.PluginManager;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.utils.CanPlaceBlocks;
import org.hegglandtech.mccontrol.storage.MemoryStorage;

public class PlayerCommands implements Listener {

    @EventHandler
        public void onCommand(PlayerCommandPreprocessEvent event) {
            Player player = event.getPlayer();

            if (!player.isOp()) {
                player.sendMessage("You are not allowed to use commands");
                event.setCancelled(true);
            }

            String command = event.getMessage();

            if (!command.startsWith("/mccontrol:")) return;

            command = command.replace("/mccontrol:", "");

            if (command.startsWith("caninteractwithblocks")) {
                event.setCancelled(true);
                CanPlaceBlocks canPlaceBlocks = new CanPlaceBlocks();
                canPlaceBlocks.check(command, player);
            }

            if (command.equals("getmemory")) {
                MemoryStorage memoryStorage = Mccontrol.getInstance().getMemoryStorage();
                player.sendMessage(memoryStorage.getMemory(true));
            }
        }

    public boolean load() {
        try {
            PluginManager pluginManager = Mccontrol.getInstance().getPluginManager();
            pluginManager.registerEvents(new PlayerCommands(), Mccontrol.getInstance());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

