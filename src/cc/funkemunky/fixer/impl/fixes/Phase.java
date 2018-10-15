package cc.funkemunky.fixer.impl.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.data.PlayerData;
import cc.funkemunky.fixer.api.fixes.Fix;
import cc.funkemunky.fixer.api.utils.*;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;

public class Phase extends Fix {
    private Map<Player, Long> lastDoorSwing;

    public Phase() {
        super("Phase", true);

        lastDoorSwing = new WeakHashMap<>();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPhase(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        PlayerData data = Mojank.getInstance().getDataManager().getPlayerData(player);

        if (player.getAllowFlight()
                || player.getVehicle() != null
                || MathUtil.elapsed(lastDoorSwing.getOrDefault(player, 0L)) < 500) {
            return;
        }

        if (e.getFrom().distance(e.getTo()) > 10) {
            e.setTo(e.getFrom());
            return;
        }

        float minX = (float) Math.min(e.getFrom().getX(), e.getTo().getX()), minY = (float) Math.min(e.getFrom().getY(), e.getTo().getY()), minZ = (float) Math.min(e.getFrom().getZ(), e.getTo().getZ()),
                maxX = (float) Math.max(e.getFrom().getX(), e.getTo().getX()), maxY = (float) Math.max(e.getFrom().getY(), e.getTo().getY()), maxZ = (float) Math.max(e.getFrom().getZ(), e.getTo().getZ());

        Object box = new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ).add(0f, 0f, 0f, 0f, 1.8f, 0f).toAxisAlignedBB();

        if (ReflectionsUtil.getCollidingBlocks(e.getPlayer(), box).size() > 0) {
            Location setback = findSetback(data);

            if(setback != null) {
                setback.setPitch(e.getTo().getPitch());
                setback.setYaw(e.getTo().getYaw());
            }
            e.setTo(setback != null ? setback : e.getFrom());
            //e.getPlayer().sendMessage(ChatColor.GRAY + "Fix: Phase");
        }

        data.locations.addLocation(MiscUtil.getEntityBoundingBox(player), data);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if ((BlockUtil.isDoor(event.getClickedBlock())
                    || BlockUtil.isFenceGate(event.getClickedBlock())
                    || BlockUtil.isTrapDoor(event.getClickedBlock()))
                    && !event.isCancelled()) {
                lastDoorSwing.put(event.getPlayer(), System.currentTimeMillis());
            }
        }
    }

    @Override
    public void protocolLibListeners() {

    }

    public Location findSetback(PlayerData data) {
        List<BoundingBox> boxes = new ArrayList<>(data.locations.getBoundingBoxes());

        for(BoundingBox box : boxes) {
            if(box.getCollidingBlocks(data.player).size() == 0) {
                return box.getMinimum().toLocation(data.player.getWorld());
            }
        }
        return null;
    }
}
