package p.theDannJK_Worlds.Command;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p.theDannJK_Worlds.TheDannJK_Worlds;

public class SetSpawnCommand implements CommandExecutor {

    private final TheDannJK_Worlds plugin;
    private final String PERMISSION = "thedannjk.worlds.setspawn";

    public SetSpawnCommand(TheDannJK_Worlds plugin) {
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

        Player player = (Player) sender;
        World world = player.getWorld();
        Location location = player.getLocation();
        world.setSpawnLocation(location);

        sender.sendMessage(getMessage("spawn_set")
                .replace("%world%", world.getName())
                .replace("%x%", String.valueOf(location.getBlockX()))
                .replace("%y%", String.valueOf(location.getBlockY()))
                .replace("%z%", String.valueOf(location.getBlockZ())));

        return true;
    }

    private String getMessage(String key) {
        String prefix = plugin.getConfig().getString("messages.prefix", "");
        String message = plugin.getConfig().getString("messages." + key, "Mensaje no definido en la configuración.");

        return prefix + message;
    }
}
