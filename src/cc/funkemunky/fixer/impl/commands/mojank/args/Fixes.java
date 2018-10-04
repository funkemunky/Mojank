package cc.funkemunky.fixer.impl.commands.mojank.args;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.command.CommandArgument;
import cc.funkemunky.fixer.api.fixes.Fix;
import cc.funkemunky.fixer.api.utils.Color;
import cc.funkemunky.fixer.api.utils.MiscUtil;
import org.bukkit.command.CommandSender;

public class Fixes extends CommandArgument {


    public Fixes() {
        super("fixes", "fixes", "View the fixes", 1, false, "mojank.fixes");
    }

    @Override
    public void runArgument(CommandSender sender, String command, String[] args) {
        StringBuilder options = new StringBuilder();

        for (int i = 0; i < Mojank.getInstance().getFixManager().getFixes().size(); i++) {
            Fix option = Mojank.getInstance().getFixManager().getFixes().get(i);
            options.append(option.isEnabled() ? Color.Green : Color.Red).append(option.getName()).append(i + 1 < Mojank.getInstance().getFixManager().getFixes().size() ? Color.Gray + ", " : "");
        }
        MiscUtil.sendPlayerMessage(sender, "&7Fixes: " + options.toString() + "&7.");
    }
}
