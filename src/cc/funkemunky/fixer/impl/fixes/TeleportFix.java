package cc.funkemunky.fixer.impl.fixes;

import cc.funkemunky.fixer.api.fixes.Fix;
import cc.funkemunky.fixer.api.utils.BoundingBox;
import cc.funkemunky.fixer.api.utils.MiscUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

public class TeleportFix extends Fix {

    public TeleportFix() {
        super("Teleport", true);

        addConfigValue("cancelMessage", "%prefix%&7Your &denderpearl &7was cancelled because you would have been stuck.");
    }

    @Override
    public void protocolLibListeners() {

    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            BoundingBox box = new BoundingBox(event.getTo().toVector().subtract(new Vector(0.3, 0, 0.3)), event.getTo().toVector().add(new Vector(0.3, 1.8, 0.3)));

            if(box.getCollidingBlocks(event.getPlayer()).size() > 0) {
                MiscUtil.sendPlayerMessage(event.getPlayer(), (String) getConfigValues().get("cancelMessage"));
                event.setCancelled(true);
            }
        }
    }
}
