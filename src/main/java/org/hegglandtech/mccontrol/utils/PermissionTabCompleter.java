package org.hegglandtech.mccontrol.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class PermissionTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], List.of("canBuild", "canPvp"), suggestions);
        } else if (args.length == 2) {
            StringUtil.copyPartialMatches(args[1], List.of("grant", "revoke"), suggestions);
        } else if (args.length == 3) {
            for (org.bukkit.entity.Player player : sender.getServer().getOnlinePlayers()) {
                StringUtil.copyPartialMatches(args[2], List.of(player.getName()), suggestions);
            }
        }

        return suggestions;
    }
}