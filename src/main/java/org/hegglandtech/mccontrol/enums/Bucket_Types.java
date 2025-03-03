package org.hegglandtech.mccontrol.enums;

import org.bukkit.Material;

public enum Bucket_Types {
    WATER_BUCKET(Material.WATER_BUCKET),
    LAVA_BUCKET(Material.LAVA_BUCKET);

    private final Material material;

    Bucket_Types(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public static boolean isProtected(Material material) {
        for (Bucket_Types bucket : values()) {
            if (bucket.getMaterial() == material) {
                return true;
            }
        }
        return false;
    }
}