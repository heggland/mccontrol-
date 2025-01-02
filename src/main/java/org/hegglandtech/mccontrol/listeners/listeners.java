package org.hegglandtech.mccontrol.listeners;

public class listeners {

    public listeners() {

        new onCommandPlayerListener().load();
        new onBlockPlaceListener().load();
        new onBlockBreakListener().load();
        new onEntityInteractListener().load();
        new onPlayerInteractListener().load();

    }

}
