package org.hegglandtech.mccontrol.enums;

import org.bukkit.entity.EntityType;

public enum Animal_Protected {
    COW(EntityType.COW),
    PIG(EntityType.PIG),
    SHEEP(EntityType.SHEEP),
    CHICKEN(EntityType.CHICKEN),
    RABBIT(EntityType.RABBIT),
    HORSE(EntityType.HORSE),
    DONKEY(EntityType.DONKEY),
    MULE(EntityType.MULE),
    LLAMA(EntityType.LLAMA),
    FOX(EntityType.FOX),
    WOLF(EntityType.WOLF),
    CAT(EntityType.CAT),
    OCELOT(EntityType.OCELOT),
    PARROT(EntityType.PARROT),
    PANDA(EntityType.PANDA),
    POLAR_BEAR(EntityType.POLAR_BEAR),
    TURTLE(EntityType.TURTLE),
    STRIDER(EntityType.STRIDER),
    GOAT(EntityType.GOAT),
    CAMEL(EntityType.CAMEL),
    FROG(EntityType.FROG),
    SNIFFER(EntityType.SNIFFER);

    private final EntityType entityType;

    Animal_Protected(EntityType entityType) {
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public static boolean isProtected(EntityType type) {
        for (Animal_Protected animal : values()) {
            if (animal.getEntityType() == type) {
                return true;
            }
        }
        return false;
    }
}