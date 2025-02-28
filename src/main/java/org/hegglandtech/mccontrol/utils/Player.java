package org.hegglandtech.mccontrol.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class Player {

    private String uuid;
    public String name;
    private String createdDate;
    private String modifiedDate;
    private List<String> permissions = new ArrayList<>();
    private String token;

    // Constructor for creating empty player from token and permissions
    public Player(String Token, String permissions) {
        this.token = Token;
        this.permissions = Arrays.asList(permissions.split(" "));
    }

    // Constructor for creating Player from Bukkit player
    public Player(org.bukkit.entity.Player player) {
        this.uuid = player.getUniqueId().toString();
        this.name = player.getName();
        this.createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.modifiedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
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
                    case "token":
                        this.token = value;
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
        try {
            return this.permissions.contains(String.valueOf(permission));
        } catch (Exception e) {
            return false;
        }
    }

    public void setPermission(List<String> permission) {

        for (String perm : permission) {
            if (!Player_Permission.Permission_list.contains(String.valueOf(perm))) {
                continue;
            }
            this.permissions.add(String.valueOf(perm));
        }

        this.modifiedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public void removePermission(List<String> permission) {

        for (String perm : permission) {
            if (!Player_Permission.Permission_list.contains(String.valueOf(perm))) {
                continue;
            }
            this.permissions.remove(String.valueOf(perm));
        }

        this.modifiedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getToken() {
        return token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setToken(String token) {
        this.token = token;
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
