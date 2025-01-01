package org.hegglandtech.mccontrol.listeners;

public class listeners {

    public listeners() {

        new PlayerCommands().load();
        new BlockPlaceListener().load();
        new BlockBreakListener().load();

    }

}
