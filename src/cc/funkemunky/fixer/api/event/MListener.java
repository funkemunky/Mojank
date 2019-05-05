package cc.funkemunky.fixer.api.event;

import cc.funkemunky.fixer.Mojank;
import org.bukkit.event.Listener;

public abstract class MListener implements Listener {
    public MListener() {
        Mojank.getInstance().getServer().getPluginManager().registerEvents(this, Mojank.getInstance());
    }
}
