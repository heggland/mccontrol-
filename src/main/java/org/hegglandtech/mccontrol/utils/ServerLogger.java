package org.hegglandtech.mccontrol.utils;

import org.hegglandtech.mccontrol.Mccontrol;

public class ServerLogger {

    private static final Mccontrol instance = Mccontrol.getInstance();

    public static void print(String text) {
        instance.getLogger().info(text);
    }
}
