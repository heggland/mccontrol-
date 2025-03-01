package org.hegglandtech.mccontrol.utils.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.hegglandtech.mccontrol.utils.Player_Permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateTokenTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (label.contains("generatetoken")) {

            while (args.length > 0) {
                StringUtil.copyPartialMatches(args[0], Player_Permission.Permission_list, suggestions);
                args = Arrays.copyOfRange(args, 1, args.length);
            }

            return suggestions;
        }

        return null;
    }
}