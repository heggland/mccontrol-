package org.hegglandtech.mccontrol.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Player_Permission {
    canPvp,
    canPva,
    canUseArrows,
    canBuildBlock,
    canBreakBlock,
    canUseBucket,
    canOpenInventory;

    // Public static list containing all enum values as strings
    public static final List<String> Permission_list = Arrays.stream(Player_Permission.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    public static String Permission_list_string = String.join(" ", Permission_list);
}