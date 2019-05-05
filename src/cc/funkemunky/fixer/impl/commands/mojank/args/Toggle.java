package cc.funkemunky.fixer.impl.commands.mojank.args;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.command.CommandArgument;
import cc.funkemunky.fixer.api.fixes.Fix;
import cc.funkemunky.fixer.api.utils.Color;
import cc.funkemunky.fixer.api.utils.MiscUtil;
import org.bukkit.command.CommandSender;

public class Toggle extends CommandArgument {

    public Toggle() {
        super("toggle", "toggle <fix> [status]", "To enable/disable a fix.", 1, false, "mojank.toggle");
        addMessage("toggled", "&7Set &e%fix%&7's enabled state to: &e%state%&7.");
        addMessage("fixDoesntExist", "&cThe argument entered %arg% is not the name of any fix!");
        addMessage("fixOptions", "&7Options: %options%&7.");
    }

    @Override
    public void runArgument(CommandSender sender, String command, String[] args) {
        if (args.length == 2) {
            Fix fix = Mojank.getInstance().getFixManager().getFix(args[1]);

            if (fix != null) {
                fix.setEnabled(!fix.isEnabled());
                MiscUtil.sendPlayerMessage(sender, getMessages().get("toggled").replaceAll("%fix%", fix.getName()).replaceAll("%state%", String.valueOf(fix.isEnabled())));
                return;
            }
            MiscUtil.sendPlayerMessage(sender, getMessages().get("fixDoesntExist").replaceAll("%arg%", args[1]));

            StringBuilder options = new StringBuilder();

            for (int i = 0; i < Mojank.getInstance().getFixManager().getFixes().size(); i++) {
                Fix option = Mojank.getInstance().getFixManager().getFixes().get(i);
                options.append(Color.Yellow).append(option.getName()).append(i + 1 < Mojank.getInstance().getFixManager().getFixes().size() ? Color.Gray + ", " : "");
            }
            MiscUtil.sendPlayerMessage(sender, getMessages().get("fixOptions").replaceAll("%options%", options.toString()));
        } else if (args.length == 3) {
            Fix fix = Mojank.getInstance().getFixManager().getFix(args[1]);
            boolean enabled = Boolean.parseBoolean(args[2]);

            if (fix != null) {
                fix.setEnabled(enabled);
                MiscUtil.sendPlayerMessage(sender, getMessages().get("toggled").replaceAll("%fix%", fix.getName()).replaceAll("%state%", String.valueOf(fix.isEnabled())));
                return;
            }

            MiscUtil.sendPlayerMessage(sender, getMessages().get("fixDoesntExist").replaceAll("%arg%", args[1]));

            StringBuilder options = new StringBuilder();

            for (int i = 0; i < Mojank.getInstance().getFixManager().getFixes().size(); i++) {
                Fix option = Mojank.getInstance().getFixManager().getFixes().get(i);
                options.append(Color.Yellow).append(option.getName()).append(i + 1 < Mojank.getInstance().getFixManager().getFixes().size() ? Color.Gray + ", " : "");
            }
            MiscUtil.sendPlayerMessage(sender, getMessages().get("fixOptions").replaceAll("%options%", options.toString()));
        } else {
            MiscUtil.sendPlayerMessage(sender, "&cInvalid arguments!");
        }
    }


}
