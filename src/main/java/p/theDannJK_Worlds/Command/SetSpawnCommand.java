package p.theDannJK_Worlds.Command;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser usado por jugadores.");
            return false;
        }

        Player player = (Player) sender;
        World world = player.getWorld();
        Location location = player.getLocation();
        world.setSpawnLocation(location);
        sender.sendMessage("Punto de spawn establecido en " + world.getName());
        return true;
    }
}
