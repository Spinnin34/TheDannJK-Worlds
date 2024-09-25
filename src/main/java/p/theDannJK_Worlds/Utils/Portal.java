package p.theDannJK_Worlds.Utils;

import org.bukkit.Location;
import org.bukkit.World;

public class Portal {

    private final String worldFromName;
    private final World worldTo;
    private final Location loc1;
    private final Location loc2;

    public Portal(String worldFromName, World worldTo, Location loc1, Location loc2) {
        this.worldFromName = worldFromName;
        this.worldTo = worldTo;
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    public String getWorldFromName() {
        return worldFromName;
    }

    public World getDestinationWorld() {
        return worldTo;
    }

    public boolean isInPortalRegion(Location playerLocation) {
        // Asegurarse de que el jugador esté en el mismo mundo que el portal
        if (!playerLocation.getWorld().getName().equals(loc1.getWorld().getName())) {
            return false;
        }

        // Verificar si la ubicación del jugador está dentro de los límites del portal
        double xMin = Math.min(loc1.getX(), loc2.getX());
        double xMax = Math.max(loc1.getX(), loc2.getX());
        double yMin = Math.min(loc1.getY(), loc2.getY());
        double yMax = Math.max(loc1.getY(), loc2.getY());
        double zMin = Math.min(loc1.getZ(), loc2.getZ());
        double zMax = Math.max(loc1.getZ(), loc2.getZ());

        double playerX = playerLocation.getX();
        double playerY = playerLocation.getY();
        double playerZ = playerLocation.getZ();

        return (playerX >= xMin && playerX <= xMax) &&
                (playerY >= yMin && playerY <= yMax) &&
                (playerZ >= zMin && playerZ <= zMax);
    }
}
