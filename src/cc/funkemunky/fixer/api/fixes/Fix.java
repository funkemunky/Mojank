package cc.funkemunky.fixer.api.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.event.MListener;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class Fix extends MListener {

    private String name;
    private boolean enabled;
    private boolean requiresProtocolLib;
    private Map<String, Object> configValues = new HashMap<>();

    public Fix(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;

        requiresProtocolLib = false;
    }

    public Fix(String name, boolean enabled, boolean requiresProtocolLib) {
        this.name = name;
        this.enabled = enabled;
        this.requiresProtocolLib = requiresProtocolLib;

        if(requiresProtocolLib) {
            protocolLibListeners();
        }
    }

    public void addConfigValue(String name, Object value) {
        configValues.put(name, value);
    }

    public abstract void protocolLibListeners();

    public void setEnabled(boolean enabled) {
        if(enabled) {
            Mojank.getInstance().getServer().getPluginManager().registerEvents(this, Mojank.getInstance());
        } else {
            HandlerList.unregisterAll(this);
        }
        this.enabled = (requiresProtocolLib && Mojank.getInstance().getServer().getPluginManager().isPluginEnabled("ProtocolLib") && enabled) || enabled;
    }
}
