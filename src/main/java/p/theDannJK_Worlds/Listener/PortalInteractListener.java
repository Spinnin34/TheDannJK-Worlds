package p.theDannJK_Worlds.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import p.theDannJK_Worlds.TheDannJK_Worlds;

public class PortalInteractListener implements Listener {

    private final TheDannJK_Worlds plugin;

    public PortalInteractListener(TheDannJK_Worlds plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
    }
}
