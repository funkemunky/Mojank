package cc.funkemunky.fixer.impl.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.data.PlayerData;
import cc.funkemunky.fixer.api.fixes.Fix;
import cc.funkemunky.fixer.api.utils.BlockUtil;
import cc.funkemunky.fixer.api.utils.MathUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Falling extends Fix {

    public Falling() {
        super("Falling", true);
    }

    @EventHandler
    public void onFall(PlayerMoveEvent event) {
        PlayerData data = Mojank.getInstance().getDataManager().getPlayerData(event.getPlayer());

        if (data == null) {
            return;
        }

        boolean onGround = !data.getBoundingBox().subtract(0, MathUtil.getVerticalDistance(event.getFrom(), event.getTo()) == 0 ? 0.5f : (float) MathUtil.getVerticalDistance(event.getFrom(), event.getTo()) * 1.5f, 0, 0, 0, 0).getCollidingBlocks(event.getPlayer()).isEmpty();

        if ((onGround || data.fallDistance % 1 == 0) && data.fallDistance > 3 && !BlockUtil.isLiquid(data.player.getLocation().getBlock())) {
            event.getPlayer().damage(data.fallDistance - 4D);
            // Bukkit.broadcastMessage("damaged");
        }
        // Bukkit.broadcastMessage(data.fallDistance + ", " + data.lastFallDistance + ", " + onGround);
        data.lastFallDistance = data.getFallDistance();
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player
                && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
            //Bukkit.broadcastMessage("canceled" + ", " + event.getDamage());
        }
    }

    @Override
    public void protocolLibListeners() {

    }
}
