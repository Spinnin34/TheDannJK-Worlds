package p.theDannJK_Worlds.Utils;

import org.bukkit.Location;
import org.bukkit.World;

public class Portal {
    private final String regionName;
    private final World destinationWorld;
    private final Location destinationLocation;

    public Portal(String regionName, World destinationWorld, Location destinationLocation) {
        this.regionName = regionName;
        this.destinationWorld = destinationWorld;
        this.destinationLocation = destinationLocation;
    }

    public String getRegionName() {
        return regionName;
    }

    public World getDestinationWorld() {
        return destinationWorld;
    }

    public Location getDestinationLocation() {
        return destinationLocation;
    }
}
