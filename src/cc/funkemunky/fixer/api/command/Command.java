package cc.funkemunky.fixer.api.command;

import cc.funkemunky.fixer.Mojank;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
public abstract class Command {
    private String name;
    private boolean hasArgs;
    private String[] permissions;

    public Command(String name, boolean hasArgs) {
        this.name = name;
        this.hasArgs = hasArgs;
    }

    public Command(String name, boolean hasArgs, String... permissions) {
        this.name = name;
        this.hasArgs = hasArgs;
        this.permissions = permissions;
    }

    public abstract void onCommand(CommandSender sender, String[] args);
}
