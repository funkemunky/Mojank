package cc.funkemunky.fixer.api.fixes;

import cc.funkemunky.fixer.impl.fixes.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class FixManager {
    private List<Fix> fixes;

    public FixManager() {
        fixes = new ArrayList<>();

        addFix(new BlockGlitching());
        addFix(new Falling());
        addFix(new Phase());
        addFix(new Crash());
        addFix(new DamageIndicators());
    }

    public void addFix(Fix fix) {
        fixes.add(fix);
    }

    public Fix getFix(String name) {
        Optional<Fix> fixOp = fixes.stream().filter(fix -> fix.getName().equalsIgnoreCase(name)).findFirst();

        return fixOp.get();
    }
}
