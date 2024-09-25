package p.theDannJK_Worlds.Utils;

import org.bukkit.Location;

public class Utils {

    public static boolean isWithinRegion(Location loc, Location corner1, Location corner2) {
        double xMin = Math.min(corner1.getX(), corner2.getX());
        double xMax = Math.max(corner1.getX(), corner2.getX());
        double yMin = Math.min(corner1.getY(), corner2.getY());
        double yMax = Math.max(corner1.getY(), corner2.getY());
        double zMin = Math.min(corner1.getZ(), corner2.getZ());
        double zMax = Math.max(corner1.getZ(), corner2.getZ());

        return loc.getX() >= xMin && loc.getX() <= xMax &&
                loc.getY() >= yMin && loc.getY() <= yMax &&
                loc.getZ() >= zMin && loc.getZ() <= zMax;
    }
}
