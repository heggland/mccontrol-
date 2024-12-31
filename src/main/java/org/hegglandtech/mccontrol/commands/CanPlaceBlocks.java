package org.hegglandtech.mccontrol.commands;

import org.bukkit.entity.Player;
import org.hegglandtech.mccontrol.storage.Storage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CanPlaceBlocks {

    public static void canPlaceBlock(String command, Player player) {
        // Check if the command contains "false" to block the player from placing blocks
        if (command.contains("false")) {
            blockPlayerFromPlacingBlocks(player);
            player.sendMessage("You have been blocked from placing blocks.");
            return;
        }

        // Check if player is admin before proceeding
        if (!player.isOp() && !player.hasPermission("mccontrol.isadmin")) {
            player.sendMessage("You are not an admin.");
            return;
        }

        // If not "false", grant block placement permission
        String uuid = player.getUniqueId().toString();
        String name = player.getName();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); // Current date and time

        // Prepare the data to be saved
        String dataToStore = "UUID: " + uuid + ", Name: " + name + ", Start Date: " + date + ", Blocked: false";

        // Store the data in the mccontrol-config.txt file
        Storage storage = new Storage();
        storage.writeToFile(dataToStore + "\n"); // Append the new entry to the file

        player.sendMessage("You have been added to the block placement list.");
    }

    private static void blockPlayerFromPlacingBlocks(Player player) {
        Storage storage = new Storage();
        String fileContent = storage.readFromFile();

        // If the file content is empty, there's nothing to update
        if (fileContent.isEmpty()) {
            player.sendMessage("No players to block.");
            return;
        }

        // Get the UUID to search for in the file
        String uuidToBlock = player.getUniqueId().toString();

        // Debugging: Print the file content for inspection
        System.out.println("File content before update:\n" + fileContent);

        // Update the line containing the player's UUID to set Blocked to true
        List<String> updatedLines = fileContent.lines()
                .map(line -> {
                    if (line.contains("UUID: " + uuidToBlock)) {
                        // Debugging: Print the line being updated
                        System.out.println("Found player: " + line);
                        // Replace the Blocked status with "true"
                        return line.replace("Blocked: false", "Blocked: true");
                    }
                    return line;
                })
                .collect(Collectors.toList());

        // If the file content has changed, write the updated content back to the file
        String updatedContent = String.join("\n", updatedLines);
        if (!updatedContent.equals(fileContent)) {
            storage.writeToFile(updatedContent);  // Write the updated file content with blocked status
            player.sendMessage("Player's block placement permission updated.");
        } else {
            player.sendMessage("Player not found in the block placement list.");
        }

        // Debugging: Print the updated file content
        System.out.println("File content after update:\n" + updatedContent);
    }
}
