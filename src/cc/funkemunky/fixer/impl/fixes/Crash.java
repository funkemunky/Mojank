package cc.funkemunky.fixer.impl.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.data.PlayerData;
import cc.funkemunky.fixer.api.fixes.Fix;
import cc.funkemunky.fixer.api.utils.MathUtil;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Crash extends Fix {
    public Crash() {
        super("Crash", true, true);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Mojank.getInstance(), PacketType.Play.Client.FLYING, PacketType.Play.Client.POSITION, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.LOOK, PacketType.Play.Client.ARM_ANIMATION, PacketType.Play.Client.HELD_ITEM_SLOT) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PlayerData data = Mojank.getInstance().getDataManager().getPlayerData(event.getPlayer());

                if(data != null) {
                    if(event.getPacketType() == PacketType.Play.Client.HELD_ITEM_SLOT) {
                        if(MathUtil.elapsed(data.heldItemsInSecond, 1000L)) {
                            if(data.heldItems > 40) {
                                event.getPlayer().kickPlayer("Mojank: Boxer");
                            }
                        } else {
                            data.heldItems++;
                        }
                    }
                    else if(event.getPacketType() == PacketType.Play.Client.ARM_ANIMATION) {
                        if(MathUtil.elapsed(data.armSwingsInSecond, 1000L)) {
                            if(data.swings > 100) {
                                event.getPlayer().kickPlayer("Mojank: Swing crasher");
                            }
                        } else {
                            data.swings++;
                        }
                    } else {
                        if(MathUtil.elapsed(data.flyingPacketsInSecond, 1000L)) {
                            if(data.flyingPacketsInSecond > 150) {
                                event.getPlayer().kickPlayer("Mojank: Too many packets. Lag?");
                            }
                            data.flyingPacketsInSecond = 0;
                        } else {
                            data.flyingPacketsInSecond++;
                        }
                    }
                }
            }
        });
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(event.getFrom().distance(event.getTo()) > 20) {
            event.getPlayer().kickPlayer("Mojank: Moved too far (" + event.getFrom().distance(event.getTo()) + ")");
        }
    }


}
