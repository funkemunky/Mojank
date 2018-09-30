package cc.funkemunky.fixer.api.command;

import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class CommandArgument {
    private String[] argument;
    private int argLength;
    private boolean fixes;
    private String display;
    private String description;
    private String[] permission;
    private Map<String, String> messages = new HashMap<>();

    public CommandArgument(String argument, String display, String description, int argLength, boolean fixes) {
        this.display = display;
        this.description = description;
        this.argument = new String[] {argument};
        this.argLength = argLength;
        this.fixes = fixes;
    }

    public CommandArgument(String argument, String display, String description, int argLength, boolean fixes, String... permission) {
        this.display = display;
        this.description = description;
        this.argument = new String[] {argument};
        this.argLength = argLength;
        this.fixes = fixes;
        this.permission = permission;
    }

    public CommandArgument(String display, String description, int argLength, boolean fixes, String permission, String...aliases) {
        this.display = display;
        this.description = description;
        this.argument = aliases;
        this.argLength = argLength;
        this.fixes = fixes;
        this.permission = new String[] {permission};
    }

    public void addMessage(String id, String msg) {
        messages.put(id, msg);
    }

    public abstract void runArgument(CommandSender sender, String command, String[] args);


}
