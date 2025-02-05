package org.hegglandtech.mccontrol.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class Player {

    private String uuid;
    public String name;
    private String createdDate;
    private String modifiedDate;
    private List<String> permissions = new ArrayList<>();

    // Constructor for creating Player from Bukkit player
    public Player(org.bukkit.entity.Player player) {
        this.uuid = player.getUniqueId().toString();
        this.name = player.getName();
        this.createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.modifiedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.permissions.add(String.valueOf(Player_Permission.canBuild));
        this.permissions.add(String.valueOf(Player_Permission.canPvp));
    }

    // Constructor for parsing a string representation of Player
    public Player(String playerEntry) {
        try {
            // Validate and strip the outer "Player{ }"
            if (!playerEntry.startsWith("Player{") || !playerEntry.endsWith("}")) {
                throw new IllegalArgumentException("Invalid playerEntry format: " + playerEntry);
            }

            // Remove "Player{" and "}" to get the content
            String content = playerEntry.substring(7, playerEntry.length() - 1);

            // Split by ", " while handling potential lists inside brackets
            String[] fields = content.split(", (?=[a-zA-Z]+='[^']*')");

            for (String field : fields) {
                String[] keyValue = field.split("=", 2);
                if (keyValue.length != 2) continue; // Skip invalid entries

                String key = keyValue[0].trim();
                String value = keyValue[1].trim().replaceAll("^'|'$", ""); // Remove surrounding quotes

                switch (key) {
                    case "uuid":
                        this.uuid = value;
                        break;
                    case "name":
                        this.name = value;
                        break;
                    case "createdDate":
                        this.createdDate = value;
                        break;
                    case "modifiedDate":
                        this.modifiedDate = value;
                        break;
                    case "permissions":
                        // Extract permissions list from the format "[perm1, perm2]"
                        value = value.replaceAll("^\\[|]$", ""); // Remove square brackets
                        this.permissions = value.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(value.split(", ")));
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

    // Check if the player has the specific permission
    public boolean checkPermission(Player_Permission permission) {
        return this.permissions.contains(permission.toString());
    }

    public void setPermission(Player_Permission permission) {
            if (this.permissions.contains(String.valueOf(permission))) return;
            this.permissions.add(String.valueOf(permission));
            this.modifiedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public void removePermission(Player_Permission permission) {
        if (!this.permissions.contains(String.valueOf(permission))) return;
        this.permissions.remove(String.valueOf(permission));
        this.modifiedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
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
