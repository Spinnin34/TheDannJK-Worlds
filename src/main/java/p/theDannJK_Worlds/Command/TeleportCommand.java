package p.theDannJK_Worlds.Command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import p.theDannJK_Worlds.TheDannJK_Worlds;

public class TeleportCommand implements CommandExecutor {

    private final TheDannJK_Worlds plugin;
    private final String PERMISSION = "thedannjk.worlds.teleport";

    public TeleportCommand(TheDannJK_Worlds plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage(getMessage("no_permission"));
            return true;
        }

        if (sender instanceof Player && args.length == 4) {
            Player player = (Player) sender;
            String worldName = args[0];
            double x, y, z;

            try {
                x = Double.parseDouble(args[1]);
                y = Double.parseDouble(args[2]);
                z = Double.parseDouble(args[3]);
            } catch (NumberFormatException e) {
                player.sendMessage(getMessage("invalid_coordinates"));
                return true;
            }

            if (Bukkit.getWorld(worldName) != null) {
                Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
                player.teleport(location);
                player.sendMessage(getMessage("teleported")
                        .replace("%world%", worldName)
                        .replace("%x%", String.valueOf(x))
                        .replace("%y%", String.valueOf(y))
                        .replace("%z%", String.valueOf(z)));
            } else {
                player.sendMessage(getMessage("world_not_found")
                        .replace("%world%", worldName));
            }
        } else {
            sender.sendMessage(getMessage("correct_usage"));
        }
        return true;
    }

    private String getMessage(String key) {
        return plugin.getConfig().getString( "messages.prefix" + "messages." + key, "Mensaje no definido en la configuración.");
    }
}
