package org.hegglandtech.mccontrol.utils;

import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class McPlayer {

    private final String uuid;
    private final String name;
    private final String modifiedDate;
    private boolean canInteractWithBlocks;

    public McPlayer(Player player) {
        this.uuid = player.getUniqueId().toString();
        this.name = player.getName();
        this.modifiedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.canInteractWithBlocks = true;
    }

    public McPlayer(String uuid, String name, String startDate, boolean canInteractWithBlocks) {
        this.uuid = uuid;
        this.name = name;
        this.modifiedDate = startDate;
        this.canInteractWithBlocks = canInteractWithBlocks;
    }

    public String getUuid() {
        return uuid;
    }

    public void setCanInteractWithBlocks(boolean canInteractWithBlocks) {
        this.canInteractWithBlocks = canInteractWithBlocks;
    }

    public static boolean canInteractWithBlocks(String playerstring) {
        return playerstring.contains("canInteractWithBlocks=true");
    }

    @Override
    public String toString() {
        return "Player{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", modifiedDate='" + modifiedDate + '\'' +
                ", canInteractWithBlocks=" + canInteractWithBlocks +
                '}';
    }

}
