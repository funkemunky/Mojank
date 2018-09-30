package cc.funkemunky.fixer.api.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.utils.MiscUtil;
import cc.funkemunky.fixer.impl.fixes.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class FixManager {
    private List<Fix> fixes;
    private boolean isProtocolLibLoaded;

    public FixManager() {
        fixes = new ArrayList<>();

        isProtocolLibLoaded = Mojank.getInstance().getServer().getPluginManager().isPluginEnabled("ProtocolLib");

        addFix(new BlockGlitching());
        addFix(new Falling());
        addFix(new Phase());
        addFix(new Crash());
        addFix(new DamageIndicators());
        addFix(new MorePackets());
        addFix(new AntiVPN());
        addFix(new BookExploits());
    }

    public void addFix(Fix fix) {
        if(!fix.isRequiresProtocolLib() || isProtocolLibLoaded) {
            fixes.add(fix);
        } else {
            MiscUtil.sendConsoleMessage("&cCould not load fix \"" + fix.getName() + "\" because ProtocolLib is not enabled.");
        }
    }

    public Fix getFix(String name) {
        Optional<Fix> fixOp = fixes.stream().filter(fix -> fix.getName().equalsIgnoreCase(name)).findFirst();

        return fixOp.orElse(null);
    }
}
