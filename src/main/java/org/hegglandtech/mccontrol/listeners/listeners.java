package org.hegglandtech.mccontrol.listeners;

public class listeners {

    public listeners() {

        new onCommandPlayerListener().load();
        new onBlockPlaceListener().load();
        new onBlockBreakListener().load();
        new onBucketEmptyListener().load();
        new onEntityInteractListener().load();
        new onInventoryOpenEventListener().load();
        new onProjectileLaunchEvent().load();

    }

}
