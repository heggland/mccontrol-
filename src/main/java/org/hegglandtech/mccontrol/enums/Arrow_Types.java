package org.hegglandtech.mccontrol.enums;

import org.bukkit.entity.EntityType;

public enum Arrow_Types {
    ARROW(EntityType.ARROW),
    SPECTRAL_ARROW(EntityType.SPECTRAL_ARROW);

    private final EntityType entityType;

    Arrow_Types(EntityType entityType) {
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public static boolean isProtected(EntityType type) {
        for (Arrow_Types arrow : values()) {
            if (arrow.getEntityType() == type) {
                return true;
            }
        }
        return false;
    }
}