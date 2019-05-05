package cc.funkemunky.fixer.api.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.data.PlayerData;
import cc.funkemunky.fixer.api.event.MListener;
import cc.funkemunky.fixer.api.utils.MiscUtil;
import lombok.Getter;
import lombok.Setter;
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

        if(this.requiresProtocolLib) {
            protocolLibListeners();
        } else {
            MiscUtil.sendConsoleMessage("&cCould not load fix \"" + name + "\" because ProtocolLib is not enabled.");
        }
    }

    public boolean cancel(PlayerData data, String log) {
        Mojank.getInstance().getDataManager().addLog(data, log);
        return true;
    }

    public void addConfigValue(String name, Object value) {
        configValues.put(name, value);
    }

    public abstract void protocolLibListeners();

    public void setEnabled(boolean enabled) {
        if (enabled) {
            Mojank.getInstance().getServer().getPluginManager().registerEvents(this, Mojank.getInstance());
        } else {
            HandlerList.unregisterAll(this);
        }
        this.enabled = (requiresProtocolLib && Mojank.getInstance().getServer().getPluginManager().isPluginEnabled("ProtocolLib") && enabled) || enabled;
    }
}
