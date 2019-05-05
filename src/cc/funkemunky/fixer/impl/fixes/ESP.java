package cc.funkemunky.fixer.impl.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.fixes.Fix;
import cc.funkemunky.fixer.api.utils.BoundingBox;
import cc.funkemunky.fixer.api.utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ESP extends Fix {

    public ESP() {
        super("ESP", true);

        new BukkitRunnable() {
            public void run() {
                if (isEnabled()) {
                    Bukkit.getOnlinePlayers().forEach(online -> {
                        online.getWorld().getEntities().parallelStream().filter(entity -> entity instanceof Player && ((Player) entity).isSneaking() && entity.getLocation().distance(online.getLocation()) < 50 && MathUtil.getOffsetFromEntity(online, (LivingEntity) entity)[0] < 150).forEach(entity -> {
                            BoundingBox box = new BoundingBox(online.getEyeLocation().toVector(), entity.getLocation().toVector());

                            if(!online.hasLineOfSight(entity)) {
                                List<BoundingBox> collidingBlocks = box.getCollidingBlockBoxes(online);
                                if(collidingBlocks.stream().filter(block ->
                                        block.getMaximum().subtract(block.getMinimum()).lengthSquared() == 3
                                                && !new Location(online.getWorld(), block.getMinimum().getX(), block.getMinimum().getY(), block.getMinimum().getZ()).getBlock().getType().toString().toLowerCase().contains("glass")).count() >
                                        collidingBlocks.stream().filter(block ->
                                                block.getMaximum().lengthSquared() < 3
                                                        || new Location(online.getWorld(), block.getMinimum().getX(), block.getMinimum().getY(), block.getMinimum().getZ()).getBlock().getType().toString().toLowerCase().contains("glass")).count()) {
                                    new BukkitRunnable() {
                                        public void run() {
                                            online.hidePlayer((Player) entity);
                                        }
                                    }.runTask(Mojank.getInstance());
                                } else {
                                    new BukkitRunnable() {
                                        public void run() {
                                            online.showPlayer((Player) entity);
                                        }
                                    }.runTask(Mojank.getInstance());
                                }
                                collidingBlocks.clear();
                            } else {
                                new BukkitRunnable() {
                                    public void run() {
                                        online.showPlayer((Player) entity);
                                    }
                                }.runTask(Mojank.getInstance());
                                //Bukkit.broadcastMessage("Showing " + entity.getName());
                            }
                        });
                    });
                }
            }
        }.runTaskTimer(Mojank.getInstance(), 0L, 10L);
    }

    @Override
    public void protocolLibListeners() {

    }
}
