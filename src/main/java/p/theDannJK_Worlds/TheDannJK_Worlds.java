package p.theDannJK_Worlds;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;
import p.theDannJK_Worlds.Command.CreateWorldCommand;
import p.theDannJK_Worlds.Command.SetSpawnCommand;
import p.theDannJK_Worlds.Command.TabCompleter.TeleportTabCompleter;
import p.theDannJK_Worlds.Command.TeleportCommand;
import p.theDannJK_Worlds.Manager.PortalManager;
import p.theDannJK_Worlds.Utils.Portal;

import javax.sound.sampled.Port;


public final class TheDannJK_Worlds extends JavaPlugin {
    private PortalManager portalManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadWorldsFromConfig();

        portalManager = new PortalManager(this);
        portalManager.loadPortalsFromConfig();

        this.getCommand("createworld").setExecutor(new CreateWorldCommand(this));
        this.getCommand("setspawn").setExecutor(new SetSpawnCommand());
        this.getCommand("tpworld").setExecutor(new TeleportCommand());
        this.getCommand("tpworld").setTabCompleter(new TeleportTabCompleter());
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadWorldsFromConfig() {
        if (getConfig().isConfigurationSection("worlds")) {
            for (String worldName : getConfig().getConfigurationSection("worlds").getKeys(false)) {
                String type = getConfig().getString("worlds." + worldName + ".type", "NORMAL");

                // Si el mundo ya existe, no lo recreamos
                if (getServer().getWorld(worldName) == null) {
                    getServer().createWorld(new WorldCreator(worldName).type(WorldType.valueOf(type)));
                }

                double x = getConfig().getDouble("worlds." + worldName + ".spawn.x");
                double y = getConfig().getDouble("worlds." + worldName + ".spawn.y");
                double z = getConfig().getDouble("worlds." + worldName + ".spawn.z");
                World world = getServer().getWorld(worldName);
                if (world != null) {
                    world.setSpawnLocation(new Location(world, x, y, z));
                }
            }
        }
    }

    public PortalManager getPortalManager() {
        return portalManager;
    }
}
