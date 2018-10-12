package cc.funkemunky.fixer.api.command;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.utils.Color;
import cc.funkemunky.fixer.api.utils.MiscUtil;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.*;

@Getter
public abstract class Command {
    private String name;
    private String display;
    private boolean hasCustomArgs;
    private String[] permissions;
    private List<CommandArgument> arguments = new ArrayList<>();
    private Map<String, String> messages = new HashMap<>();

    public Command(String name, String display, boolean hasArgs) {
        this.name = name;
        this.display = display;
        this.hasCustomArgs = hasArgs;
    }

    public Command(String name, String display, boolean hasArgs, String... permissions) {
        this.name = name;
        this.display = display;
        this.hasCustomArgs = hasArgs;
        this.permissions = permissions;
    }

    public void addArgument(CommandArgument argument) {
        arguments.add(argument);

        String path = "messages." + name + ".args." + argument.getArgument()[0];
        for (String id : argument.getMessages().keySet()) {
            if (Mojank.getInstance().getConfig().get(path + "." + id) == null) {
                Mojank.getInstance().getConfig().set(path + "." + id, argument.getMessages().get(id));
            }
            Mojank.getInstance().saveConfig();
            argument.getMessages().put(id, Mojank.getInstance().getConfig().getString(path + "." + id));
        }
    }

    public void onCommand(CommandSender sender, String[] args) {
        if (hasCustomArgs) {
            if(sender.hasPermission("mojank.main") || sender.hasPermission("mojank.admin")) {
                if (args.length == 0) {
                    sender.sendMessage(MiscUtil.line(Color.Dark_Gray));
                    sender.sendMessage(Color.Gold + Color.Bold + display + Color.Yellow + " Command Help " + Color.White + "Page (1 / " + ((arguments.size() / 6) + (arguments.size() - ((arguments.size() / 6) * 6) > 0 ? 1 : 0)) + ")");
                    sender.sendMessage("");
                    sender.sendMessage(Color.translate("&b<> &7= required. &b[] &7= optional."));
                    sender.sendMessage("");

                    int max = Math.min(6, arguments.size());

                    for (int i = 0; i < max; i++) {
                        CommandArgument argument = arguments.get(i);
                        sender.sendMessage(Color.Gray + "/" + name.toLowerCase() + Color.White + " " + argument.getDisplay() + Color.Gray + " to " + argument.getDescription());
                    }
                    sender.sendMessage(MiscUtil.line(Color.Dark_Gray));
                } else {
                    try {
                        int page = Integer.parseInt(args[0]);
                        sender.sendMessage(MiscUtil.line(Color.Dark_Gray));
                        sender.sendMessage(Color.Gold + Color.Bold + name + Color.Yellow + " Command Help " + Color.White + "Page (" + page + " / " + ((arguments.size() / 6) + (arguments.size() - ((arguments.size() / 6) * 6) > 0 ? 1 : 0)) + ")");
                        sender.sendMessage("");
                        sender.sendMessage(Color.translate("&b<> &7= required. &b[] &7= optional."));
                        sender.sendMessage("");
                        int max = Math.min(6 * page, arguments.size());

                        for (int i = (6 * page - 5); i < max; i++) {
                            CommandArgument argument = arguments.get(i);
                            sender.sendMessage(Color.Gray + "/" + name.toLowerCase() + Color.White + " " + argument.getDisplay() + Color.Gray + " to " + argument.getDescription());
                        }
                        sender.sendMessage(MiscUtil.line(Color.Dark_Gray));
                    } catch (Exception e) {
                        for (CommandArgument argument : this.arguments) {
                            for (String argumentName : argument.getArgument()) {
                                if (!args[0].equalsIgnoreCase(argumentName))
                                    continue;

                                if ((argument.getPermission() == null || sender.hasPermission("mojank.admin")
                                        || Arrays.stream(argument.getPermission()).anyMatch(sender::hasPermission))) {
                                    argument.runArgument(sender, name, args);
                                    break;
                                }
                                sender.sendMessage(Color.Red + "No permission.");
                                break;
                            }
                        }
                    }
                }
            } else {
                sender.sendMessage(Color.Red + "No permission.");
            }
        }
    }

    public void addMessage(String id, String msg) {
        messages.put(id, msg);
    }
}
