package cc.funkemunky.plugins;

import org.bukkit.plugin.java.JavaPlugin;

public class Mojank extends JavaPlugin {

    public static Mojank INSTANCE;

    public void onEnable() {
        INSTANCE = this;
    }
}
