package org.hegglandtech.mccontrol.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Player {

    private String uuid;
    private String name;
    private String modifiedDate;
    private boolean canInteractWithBlocks;

    public Player(org.bukkit.entity.Player player) {
        this.uuid = player.getUniqueId().toString();
        this.name = player.getName();
        this.modifiedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.canInteractWithBlocks = true;
    }

    public Player(String uuid, String name, String startDate, boolean canInteractWithBlocks) {
        this.uuid = uuid;
        this.name = name;
        this.modifiedDate = startDate;
        this.canInteractWithBlocks = canInteractWithBlocks;
    }

    public Player(String playerEntry) {
        try {
            // Validate and strip the outer "Player{ }"
            if (!playerEntry.startsWith("Player{") || !playerEntry.endsWith("}")) {
                throw new IllegalArgumentException("Invalid playerEntry format: " + playerEntry);
            }

            // Remove "Player{" and "}" to get the content
            String content = playerEntry.substring(7, playerEntry.length() - 1);

            // Split by commas to get each key-value pair
            String[] fields = content.split(", ");

            // Loop through fields and assign to the respective variables
            for (String field : fields) {
                String[] keyValue = field.split("=");
                String key = keyValue[0];
                String value = keyValue[1].replace("'", ""); // Remove any single quotes

                switch (key) {
                    case "uuid":
                        this.uuid = value;
                        break;
                    case "name":
                        this.name = value;
                        break;
                    case "modifiedDate":
                        this.modifiedDate = value;
                        break;
                    case "canInteractWithBlocks":
                        this.canInteractWithBlocks = Boolean.parseBoolean(value);
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected field: " + key);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing playerEntry: " + playerEntry, e);
        }
    }


    public String getUuid() {
        return uuid;
    }

    public void setCanInteractWithBlocks(boolean canInteractWithBlocks) {
        this.canInteractWithBlocks = canInteractWithBlocks;
    }

    public boolean canInteractWithBlocks() {
        return this.canInteractWithBlocks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Player{");
        try {
            for (var field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true); // Access private fields
                sb.append(field.getName())
                        .append("='")
                        .append(field.get(this))
                        .append("', ");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error generating toString", e);
        }
        // Remove trailing comma and space, add closing brace
        sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

}
