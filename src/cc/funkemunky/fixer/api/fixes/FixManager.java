package cc.funkemunky.fixer.api.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.impl.fixes.BlockGlitching;
import cc.funkemunky.fixer.impl.fixes.Falling;
import cc.funkemunky.fixer.impl.fixes.Phase;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class FixManager {
    private List<Fix> fixes;

    public FixManager() {
        fixes = new ArrayList<>();

        new BlockGlitching();
        new Falling();
        new Phase();
    }

    public void addFix(Fix fix) {
        fixes.add(fix);
    }

    public Fix getFix(String name) {
        Optional<Fix> fixOp = fixes.stream().filter(fix -> fix.getName().equalsIgnoreCase(name)).findFirst();

        return fixOp.get();
    }
}
