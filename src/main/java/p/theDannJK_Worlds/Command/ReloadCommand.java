package p.theDannJK_Worlds.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import p.theDannJK_Worlds.TheDannJK_Worlds;

public class ReloadCommand implements CommandExecutor {

    private final TheDannJK_Worlds plugin;
    private final String PERMISSION = "thedannjk.worlds.reload";

    public ReloadCommand(TheDannJK_Worlds plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(PERMISSION)) {
            plugin.reloadConfig();
            sender.sendMessage(getMessage("config_reloaded"));
        } else {
            sender.sendMessage(getMessage("no_permission"));
        }
        return true;
    }

    private String getMessage(String key) {
        return plugin.getConfig().getString( "messages.prefix" + "messages." + key, "Mensaje no definido en la configuración.");
    }
}

