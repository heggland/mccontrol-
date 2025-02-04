package org.hegglandtech.mccontrol.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.PluginManager;
import org.hegglandtech.mccontrol.Mccontrol;
import org.hegglandtech.mccontrol.utils.PlayerTest;

import java.util.EnumSet;

public class onEntityInteractListener implements Listener {

    private static final EnumSet<EntityType> ANIMAL_TYPES = EnumSet.of(
            EntityType.COW,
            EntityType.PIG,
            EntityType.SHEEP,
            EntityType.CHICKEN,
            EntityType.RABBIT,
            EntityType.HORSE,
            EntityType.DONKEY,
            EntityType.MULE,
            EntityType.LLAMA,
            EntityType.FOX,
            EntityType.WOLF,
            EntityType.CAT,
            EntityType.OCELOT,
            EntityType.PARROT,
            EntityType.PANDA,
            EntityType.POLAR_BEAR,
            EntityType.TURTLE,
            EntityType.STRIDER,
            EntityType.GOAT,
            EntityType.CAMEL,
            EntityType.FROG,
            EntityType.SNIFFER,
            EntityType.PLAYER
    );

    @EventHandler
    public void onAnimalEntityDeath(EntityDeathEvent event) {
        if (ANIMAL_TYPES.contains(event.getEntity().getType())) {
            Player killer = event.getEntity().getKiller();
            if (killer != null) {
                killer.sendMessage("You killed an innocent animal!");
            }
        }
    }

    @EventHandler
    public void onEntityAttackAnimal(EntityDamageByEntityEvent event) {
        if (ANIMAL_TYPES.contains(event.getEntity().getType()) && event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            PlayerTest playerTest = new PlayerTest(player);

            if (!playerTest.validate()) {
                player.sendMessage("You are not allowed to attack innocent animals!");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onArrowShoot(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Arrow arrow && arrow.getShooter() instanceof Player player) {
            player.sendMessage("You are not allowed to shoot arrows!");
            event.setCancelled(true);
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
