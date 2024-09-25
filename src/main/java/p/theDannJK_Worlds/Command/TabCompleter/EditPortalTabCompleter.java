package p.theDannJK_Worlds.Command.TabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import p.theDannJK_Worlds.TheDannJK_Worlds;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EditPortalTabCompleter implements TabCompleter {

    private final TheDannJK_Worlds plugin;

    public EditPortalTabCompleter(TheDannJK_Worlds plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions = new ArrayList<>(plugin.getConfig().getConfigurationSection("portals").getKeys(false));
        } else if (args.length == 2) {
        } else if (args.length == 3) {
            suggestions = Bukkit.getWorlds().stream()
                    .map(World::getName)
                    .collect(Collectors.toList());
        }

        return suggestions.stream()
                .filter(s -> s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .collect(Collectors.toList());
    }
}
