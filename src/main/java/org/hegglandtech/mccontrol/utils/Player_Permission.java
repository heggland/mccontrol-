package org.hegglandtech.mccontrol.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Player_Permission {
    canBuild,
    canPvp,
    canUseBucket;

    // Public static list containing all enum values as strings
    public static final List<String> Permission_list = Arrays.stream(Player_Permission.values())
            .map(Enum::name)
            .collect(Collectors.toList());
}