package cc.funkemunky.fixer.impl.commands;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.command.Command;
import cc.funkemunky.fixer.api.fixes.Fix;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MojankCommand extends Command {
    public MojankCommand() {
        super("mojank", true, "mojank.admin", "mojank.main");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(args.length > 0) {
            if(Mojank.getInstance().getFixManager().getFixes().stream().anyMatch(fix -> fix.getName().equalsIgnoreCase(args[0]))) {
                Fix fix = Mojank.getInstance().getFixManager().getFix(args[0]);
                fix.setEnabled(!fix.isEnabled());
                sender.sendMessage(ChatColor.GREEN + "Toggled " + fix.getName() + " " + fix.isEnabled());
                return;
            }
            sender.sendMessage(ChatColor.RED + "Usage: /mojank <fixName>");
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /mojank <fixName>");
        }
    }
}
