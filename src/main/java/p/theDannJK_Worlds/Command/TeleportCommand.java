package p.theDannJK_Worlds.Command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;

public class TeleportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && args.length == 4) {
            Player player = (Player) sender;
            String worldName = args[0];
            double x, y, z;

            try {
                x = Double.parseDouble(args[1]);
                y = Double.parseDouble(args[2]);
                z = Double.parseDouble(args[3]);
            } catch (NumberFormatException e) {
                player.sendMessage("Las coordenadas deben ser números.");
                return true;
            }

            if (Bukkit.getWorld(worldName) != null) {
                Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
                player.teleport(location);
                player.sendMessage("Teletransportado a " + worldName + " en " + location.toString());
            } else {
                player.sendMessage("El mundo " + worldName + " no existe.");
            }
        } else {
            sender.sendMessage("Uso correcto: /tpworld <nombre_mundo> <x> <y> <z>");
        }
        return true;
    }
}

