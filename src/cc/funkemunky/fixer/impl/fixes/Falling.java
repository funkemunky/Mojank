package cc.funkemunky.fixer.impl.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.data.PlayerData;
import cc.funkemunky.fixer.api.fixes.Fix;
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

        if(data.isOnGround() && !data.isOnGroundBefore() && data.getFallDistance() > 3) {
            event.getPlayer().damage(data.getFallDistance() - 3D);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player
                && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }
}
