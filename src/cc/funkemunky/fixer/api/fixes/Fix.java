package cc.funkemunky.fixer.api.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.event.MListener;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class Fix extends MListener {

    private String name;
    private boolean enabled;
    private boolean requiresProtocolLib;

    public Fix(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;

        requiresProtocolLib = false;
        Mojank.getInstance().getServer().getPluginManager().registerEvents(this, Mojank.getInstance());
    }

    public Fix(String name, boolean enabled, boolean requiresProtocolLib) {
        this.name = name;
        this.enabled = enabled;
        this.requiresProtocolLib = requiresProtocolLib;
    }

    public void setEnabled(boolean enabled) {
        if(enabled) {
            Mojank.getInstance().getServer().getPluginManager().registerEvents(this, Mojank.getInstance());
        } else {
            HandlerList.unregisterAll(this);
        }
        this.enabled = (requiresProtocolLib && Mojank.getInstance().getServer().getPluginManager().isPluginEnabled("ProtocolLib") && enabled) || enabled;
    }
}
