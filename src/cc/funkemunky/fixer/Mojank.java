package cc.funkemunky.fixer;

import cc.funkemunky.fixer.api.command.CommandManager;
import cc.funkemunky.fixer.api.data.DataManager;
import cc.funkemunky.fixer.api.event.ListenerManager;
import cc.funkemunky.fixer.api.fixes.FixManager;
import cc.funkemunky.fixer.api.utils.BlockUtil;
import cc.funkemunky.fixer.api.utils.MiscUtil;
import cc.funkemunky.fixer.api.utils.ReflectionsUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Mojank extends JavaPlugin {

    private static Mojank instance;
    private DataManager dataManager;
    private FixManager fixManager;
    private CommandManager commandManager;
    public String serverVersion;

    public void onEnable() {
        instance = this;

        serverVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);

        new ListenerManager();
        new MiscUtil();
        new BlockUtil();
        new ReflectionsUtil();
        dataManager = new DataManager();
        fixManager = new FixManager();
        commandManager = new CommandManager();
    }

    public static Mojank getInstance() {
        return instance;
    }
}
