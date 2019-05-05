package cc.funkemunky.fixer.impl.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.data.PlayerData;
import cc.funkemunky.fixer.api.fixes.Fix;
import cc.funkemunky.fixer.api.utils.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BlockGlitching extends Fix {
    public BlockGlitching() {
        super("BlockGlitching", true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player
                && event.getEntity() instanceof LivingEntity
                && MiscUtil.entityDimensions.containsKey(event.getEntity().getType())) {
            Player player = (Player) event.getDamager();
            PlayerData data = Mojank.getInstance().getDataManager().getPlayerData(player);

            if (data == null) {
                return;
            }

            RayTrace trace = new RayTrace(player.getEyeLocation().toVector(), player.getEyeLocation().getDirection());
            BoundingBox entityBox = MiscUtil.getEntityBoundingBox((LivingEntity) event.getEntity());
            if (trace.intersects(entityBox, 3.25, 0.25)
                    && trace.getBlocks(player.getWorld(), MathUtil.getHorizontalDistance(event.getDamager().getLocation(), event.getEntity().getLocation()), 0.25).stream().allMatch(block -> BlockUtil.isSolid(block) && ReflectionsUtil.getBlockBoundingBox(block).getMaximum().subtract(block.getLocation().toVector()).lengthSquared() == 3 && !BlockUtil.isStair(block))) {
                event.setCancelled(cancel(data, "Block glitched " + event.getEntity().getName()));
                //event.getDamager().sendMessage(ChatColor.GRAY + "Fix: Block Glitch");
            }
        }
    }

    @Override
    public void protocolLibListeners() {

    }
}
