package p.theDannJK_Worlds.Command.TabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TeleportTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            for (String worldName : Bukkit.getWorlds().stream().map(world -> world.getName()).toList()) {
                completions.add(worldName);
            }
        }
        return completions;
    }
}

