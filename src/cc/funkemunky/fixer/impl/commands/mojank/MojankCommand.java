package cc.funkemunky.fixer.impl.commands.mojank;

import cc.funkemunky.fixer.api.command.Command;
import cc.funkemunky.fixer.impl.commands.mojank.args.Fixes;
import cc.funkemunky.fixer.impl.commands.mojank.args.Reload;
import cc.funkemunky.fixer.impl.commands.mojank.args.Toggle;

public class MojankCommand extends Command {
    public MojankCommand() {
        super("mojank", "Mojank", true, "mojank.main");

        addArgument(new Toggle());
        addArgument(new Reload());
        addArgument(new Fixes());
    }


}
