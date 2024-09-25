package p.theDannJK_Worlds.Manager;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import p.theDannJK_Worlds.TheDannJK_Worlds;
import p.theDannJK_Worlds.Utils.Portal;

import java.util.HashMap;
import java.util.Map;

public class PortalManager {
    private final TheDannJK_Worlds plugin;
    private final Map<String, Portal> portals = new HashMap<>();
    private final Map<Player, Long> lastTeleportTime = new HashMap<>();
    private final WorldGuardPlugin wgPlugin;
    private final FileConfiguration config;

    public PortalManager(TheDannJK_Worlds plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        Plugin wg = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (wg instanceof WorldGuardPlugin) {
            this.wgPlugin = (WorldGuardPlugin) wg;
        } else {
            throw new IllegalStateException("WorldGuard plugin not found!");
        }
    }

    public void loadPortalsFromConfig() {
        Map<String, Location> worldSpawns = new HashMap<>();
        if (config.isConfigurationSection("worlds")) {
            for (String worldName : config.getConfigurationSection("worlds").getKeys(false)) {
                double x = config.getDouble("worlds." + worldName + ".spawn.x");
                double y = config.getDouble("worlds." + worldName + ".spawn.y");
                double z = config.getDouble("worlds." + worldName + ".spawn.z");

                World world = Bukkit.getWorld(worldName);
                if (world != null) {
                    worldSpawns.put(worldName, new Location(world, x, y, z));
                } else {
                    plugin.getLogger().warning("El mundo no existe: " + worldName);
                }
            }
        }

        // Cargar portales
        if (config.isConfigurationSection("portals")) {
            for (String portalName : config.getConfigurationSection("portals").getKeys(false)) {
                String regionName = config.getString("portals." + portalName + ".region");
                String worldToName = config.getString("portals." + portalName + ".world_to");

                if (!worldSpawns.containsKey(worldToName)) {
                    plugin.getLogger().warning("El mundo de destino no existe en la configuración: " + worldToName);
                    continue;
                }

                Location destinationLocation = worldSpawns.get(worldToName);

                Portal portal = new Portal(regionName, Bukkit.getWorld(worldToName), destinationLocation);
                portals.put(portalName, portal);
            }

            startPortalTask();
        }
    }

    private void startPortalTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Location playerLocation = player.getLocation();
                for (Portal portal : portals.values()) {
                    if (isInWorldGuardRegion(playerLocation, portal.getRegionName())) {
                        long currentTime = System.currentTimeMillis();
                        long lastTime = lastTeleportTime.getOrDefault(player, 0L);

                        if (currentTime - lastTime > 5000) {
                            Location spawnLocation = portal.getDestinationLocation();

                            player.teleport(spawnLocation);
                            String teleportMessage = getMessage("teleport_success")
                                    .replace("%world%", spawnLocation.getWorld().getName())
                                    .replace("%x%", String.valueOf(spawnLocation.getBlockX()))
                                    .replace("%y%", String.valueOf(spawnLocation.getBlockY()))
                                    .replace("%z%", String.valueOf(spawnLocation.getBlockZ()));
                            player.sendMessage(teleportMessage);
                            plugin.getLogger().info("El jugador " + player.getName() + " ha sido teletransportado.");

                            lastTeleportTime.put(player, currentTime);
                        } else {
                            player.sendMessage(getMessage("teleport_cooldown"));
                        }
                    }
                }
            }
        }, 0L, 10L);
    }

    private boolean isInWorldGuardRegion(Location location, String regionName) {
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(location.getWorld());

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = container.get(world);

        if (regionManager == null) {
            return false;
        }

        if (!regionManager.hasRegion(regionName)) {
            return false;
        }

        ProtectedRegion region = regionManager.getRegion(regionName);
        boolean isInside = region.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        return isInside;
    }

    private String getMessage(String key) {
        return config.getString( "messages.prefix" + "messages." + key, "Mensaje no definido en la configuración.");
    }

    public Map<String, Portal> getPortals() {
        return portals;
    }
}
