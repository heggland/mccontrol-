package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.PluginManager;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.enums.Animal_Protected;
import org.hegglandtech.mccontrol.utils.PlayerCheckPermission;
import org.hegglandtech.mccontrol.utils.Player_Permission;

public class onEntityInteractListener implements Listener {

    @EventHandler
    public void handleEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {

        if (event.getEntity().getType() == EntityType.PLAYER && event.getDamager() instanceof Player player) {

            boolean hasPermission = new PlayerCheckPermission(player).validate(Player_Permission.canPvp);

            if (!hasPermission) {
                player.sendMessage("You are not allowed to attack other players!");
                event.setCancelled(true);
            }
        }

        if (!Animal_Protected.isProtected(event.getEntity().getType())) {
            Player player = (Player) event.getDamager();

            boolean hasPermission = new PlayerCheckPermission(player).validate(Player_Permission.canPva);

            if (!hasPermission) {
                player.sendMessage("You are not allowed to attack innocent animals!");
                event.setCancelled(true);
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
