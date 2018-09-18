package cc.funkemunky.fixer.api.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.event.MListener;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fix extends MListener {

    private String name;
    private boolean enabled;

    public Fix(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;

        Mojank.getInstance().getFixManager().addFix(this);
    }
}
