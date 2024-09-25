package p.theDannJK_Worlds.Command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p.theDannJK_Worlds.TheDannJK_Worlds;

public class EditPortalCommand implements CommandExecutor {

    private final TheDannJK_Worlds plugin;
    private final String PERMISSION = "thedannjk.worlds.editportal";

    public EditPortalCommand(TheDannJK_Worlds plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getMessage("only_players_command"));
            return false;
        }

        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage(getMessage("no_permission"));
            return true;
        }

        if (args.length != 3) {
            sender.sendMessage(getMessage("correct_usage2"));
            return false;
        }

        String portalName = args[0];
        String regionName = args[1];
        String worldName = args[2];

        if (!plugin.getConfig().isConfigurationSection("portals." + portalName)) {
            sender.sendMessage(getMessage("portal_not_found").replace("%portal%", portalName));
            return true;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            sender.sendMessage(getMessage("world_not_found").replace("%world%", worldName));
            return true;
        }

        plugin.getConfig().set("portals." + portalName + ".region", regionName);
        plugin.getConfig().set("portals." + portalName + ".world_to", worldName);
        plugin.saveConfig();

        sender.sendMessage(getMessage("portal_updated")
                .replace("%portal%", portalName)
                .replace("%region%", regionName)
                .replace("%world%", worldName));

        plugin.getPortalManager().loadPortalsFromConfig();

        return true;
    }

    private String getMessage(String key) {
        String prefix = plugin.getConfig().getString("messages.prefix", "");
        String message = plugin.getConfig().getString("messages." + key, "Mensaje no definido en la configuración.");

        return prefix + message;
    }
}
