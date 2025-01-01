package org.hegglandtech.mccontrol.listeners;

import org.bukkit.plugin.PluginManager;
import org.hegglandtech.mccontrol.Mccontrol;

public class listeners {

    private PluginManager pluginManager;

    public listeners() {
        this.pluginManager = Mccontrol.getInstance().getPluginManager();

        pluginManager.registerEvents(new PlayerCommands(), Mccontrol.getInstance());
        pluginManager.registerEvents(new BlockPlaceListener(), Mccontrol.getInstance());
        pluginManager.registerEvents(new BlockBreakListener(), Mccontrol.getInstance());
    }

}
