package p.theDannJK_Worlds.Command;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p.theDannJK_Worlds.TheDannJK_Worlds;

public class CreateWorldCommand implements CommandExecutor {

    private final TheDannJK_Worlds plugin;
    private final String PERMISSION = "thedannjk.worlds.createworld";

    public CreateWorldCommand(TheDannJK_Worlds plugin) {
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

        if (args.length < 1) {
            sender.sendMessage(getMessage("missing_world_name"));
            return false;
        }

        String worldName = args[0];
        WorldCreator creator = new WorldCreator(worldName);
        creator.type(WorldType.NORMAL);

        World world = plugin.getServer().createWorld(creator);
        sender.sendMessage(getMessage("world_created")
                .replace("%world%", worldName));

        plugin.getConfig().set("worlds." + worldName + ".type", "NORMAL");
        plugin.getConfig().set("worlds." + worldName + ".spawn.x", world.getSpawnLocation().getX());
        plugin.getConfig().set("worlds." + worldName + ".spawn.y", world.getSpawnLocation().getY());
        plugin.getConfig().set("worlds." + worldName + ".spawn.z", world.getSpawnLocation().getZ());

        plugin.saveConfig();
        return true;
    }

    private String getMessage(String key) {
        return plugin.getConfig().getString( "messages.prefix" + "messages." + key, "Mensaje no definido en la configuración.");
    }
}
