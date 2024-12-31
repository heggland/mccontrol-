package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.hegglandtech.mccontrol.storage.Storage;

import java.util.List;

public class BlockBreakListener implements Listener {

    private final Storage storage;

    public BlockBreakListener() {
        // Initialize storage with the default file (mccontrol-config.txt)
        this.storage = new Storage();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        System.out.println("Block break event triggered!");

        Player player = event.getPlayer();

        // If the player is an operator or has the "mccontrol.isadmin" permission, allow block breaking
        if (player.isOp() || player.hasPermission("mccontrol.isadmin")) {
            System.out.println("Player is admin or op");
            return;
        }

        // Read the file content to get the list of players who are allowed to break blocks
        String fileContent = storage.readFromFile();

        // If the file content is empty, no players are allowed yet
        if (fileContent.isEmpty()) {
            event.setCancelled(true);
            player.sendMessage("§cYou don't have permission to break blocks!");
            return;
        }

        // Process each line in the file and check if the player's UUID is present and if they're blocked
        List<String> allowedPlayers = fileContent.lines()
                .filter(line -> line.contains("UUID: " + player.getUniqueId().toString()))  // Filter based on UUID
                .filter(line -> !line.contains("Blocked: true")) // Check if the player is blocked
                .toList();

        // If the player is not in the allowed list or is blocked, cancel the event
        if (allowedPlayers.isEmpty()) {
            if (!player.hasPermission("mccontrol.breakblock")) {
                player.sendMessage("§cYou don't have permission to break blocks!");
                event.setCancelled(true);
            }
        }
    }
}
