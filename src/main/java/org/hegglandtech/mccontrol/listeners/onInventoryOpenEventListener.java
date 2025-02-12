package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.utils.PlayerCheckPermission;
import org.hegglandtech.mccontrol.utils.Player_Permission;

public class onInventoryOpenEventListener implements Listener {

    @EventHandler
    public void onChestOpen(InventoryOpenEvent event) {


        Player player = (Player) event.getPlayer();

        Inventory inventory = event.getInventory();
        InventoryType type = inventory.getType();

        for (InventoryType validType : InventoryType.values()) {
            if (type == validType) {

                PlayerCheckPermission playerTest = new PlayerCheckPermission(player);

                if (!playerTest.validate(Player_Permission.canBuild)) {
                    player.sendMessage("You are not allowed to open " + type + "!");
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    public boolean load() {
        try {
            PluginManager pluginManager = Mccontrol.getInstance().getPluginManager();
            pluginManager.registerEvents(this, Mccontrol.getInstance());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}