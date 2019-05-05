package cc.funkemunky.fixer.impl.listeners;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.data.PlayerData;
import cc.funkemunky.fixer.api.event.MListener;
import cc.funkemunky.fixer.api.utils.BlockUtil;
import cc.funkemunky.fixer.api.utils.MiscUtil;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MoveEvent extends MListener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        PlayerData data = Mojank.getInstance().getDataManager().getPlayerData(event.getPlayer());

        if (data == null) {
            return;
        }

        /* Setting the hitbox of the player */

        data.setBoundingBox(MiscUtil.getEntityBoundingBox(event.getPlayer()));

        /* Calculating if the player is onGround */
        List<Block> blocks = data.getBoundingBox().subtract(0, 0.1f, 0, 0, 0, 0).getCollidingBlocks(event.getPlayer());

        AtomicBoolean onGround = new AtomicBoolean(false), inLiquid = new AtomicBoolean(false);
        blocks.stream().forEach(block -> {
            if (BlockUtil.isSolid(block)) {
                onGround.set(true);
            } else {
                if (BlockUtil.isLiquid(block)) {
                    inLiquid.set(true);
                }
            }
        });

        data.setOnGroundBefore(data.isOnGround());
        data.setOnGround(onGround.get());
        data.setInLiquid(inLiquid.get());


        /* Utility for noFall Fix */

        double deltaY = event.getTo().getY() - event.getFrom().getY();
        if (deltaY < 0) {
            data.fallDistance += Math.abs(deltaY);
        } else {
            data.fallDistance = 0;
        }
        //Bukkit.broadcastMessage(deltaY + "");
    }
}
