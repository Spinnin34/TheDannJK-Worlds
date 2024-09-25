package p.theDannJK_Worlds.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import p.theDannJK_Worlds.TheDannJK_Worlds;
import p.theDannJK_Worlds.Utils.Portal;

import java.util.HashMap;
import java.util.Map;

public class PortalManager {

    private final TheDannJK_Worlds plugin;
    private final Map<String, Portal> portals = new HashMap<>();

    public PortalManager(TheDannJK_Worlds plugin) {
        this.plugin = plugin;
    }

    public void loadPortalsFromConfig() {
        FileConfiguration config = plugin.getConfig();
        if (config.isConfigurationSection("portals")) {
            for (String portalName : config.getConfigurationSection("portals").getKeys(false)) {
                String worldFromName = config.getString("portals." + portalName + ".world_from");
                String worldToName = config.getString("portals." + portalName + ".world_to");

                World worldTo = Bukkit.getWorld(worldToName);
                if (worldTo == null) {
                    plugin.getLogger().warning("El mundo de destino no existe: " + worldToName);
                    continue;
                }

                double x1 = config.getDouble("portals." + portalName + ".location_from.x1");
                double y1 = config.getDouble("portals." + portalName + ".location_from.y1");
                double z1 = config.getDouble("portals." + portalName + ".location_from.z1");
                double x2 = config.getDouble("portals." + portalName + ".location_from.x2");
                double y2 = config.getDouble("portals." + portalName + ".location_from.y2");
                double z2 = config.getDouble("portals." + portalName + ".location_from.z2");

                Location loc1 = new Location(Bukkit.getWorld(worldFromName), x1, y1, z1);
                Location loc2 = new Location(Bukkit.getWorld(worldFromName), x2, y2, z2);

                Portal portal = new Portal(worldFromName, worldTo, loc1, loc2);
                portals.put(portalName, portal);
            }

            startPortalTask();
        }
    }

    private void startPortalTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Location playerLocation = player.getLocation();
                plugin.getLogger().info("El jugador " + player.getName() + " está en " + playerLocation); // Depuración

                for (Portal portal : portals.values()) {
                    plugin.getLogger().info("Verificando portal: " + portal.getWorldFromName()); // Depuración

                    if (portal.isInPortalRegion(playerLocation)) {
                        plugin.getLogger().info("El jugador está dentro de la región del portal"); // Depuración

                        World targetWorld = portal.getDestinationWorld();
                        Location spawnLocation = targetWorld.getSpawnLocation();

                        player.teleport(spawnLocation);
                        player.sendMessage("Has sido teletransportado a " + targetWorld.getName());
                        plugin.getLogger().info("El jugador ha sido teletransportado a " + targetWorld.getName()); // Depuración
                    }
                }
            }
        }, 0L, 10L);
    }

    public Map<String, Portal> getPortals() {
        return portals;
    }
}
