package cc.funkemunky.fixer.impl.commands.mojank.args;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.command.CommandArgument;
import cc.funkemunky.fixer.api.utils.MiscUtil;
import org.bukkit.command.CommandSender;

public class Reload extends CommandArgument {
    public Reload() {
        super("reload", "reload", "Reload the config.", 1, false, "mojank.reload");
        addMessage("reloaded", "&aSuccessfully reloaded the configuration file.");
    }

    @Override
    public void runArgument(CommandSender sender, String command, String[] args) {
        Mojank.getInstance().reloadConfig();
        MiscUtil.sendPlayerMessage(sender, getMessages().get("reloaded"));
    }
}
