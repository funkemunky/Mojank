package cc.funkemunky.fixer.impl.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.data.PlayerData;
import cc.funkemunky.fixer.api.fixes.Fix;
import cc.funkemunky.fixer.api.utils.Color;
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
        addConfigValue("kickMessage", "%prefix% &7You have been kicked for: &e%method%");
        addConfigValue("cancelInsteadOfKick", false);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().distance(event.getTo()) > 20) {
            if ((boolean) getConfigValues().get("cancelInsteadOfKick")) {
                event.setCancelled(true);
            } else {
                event.getPlayer().kickPlayer(Color.translate(((String) getConfigValues().get("kickMessage")).replaceAll("%method%", "Move")));
            }
        }
    }


    @Override
    public void protocolLibListeners() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Mojank.getInstance(), PacketType.Play.Client.ARM_ANIMATION, PacketType.Play.Client.HELD_ITEM_SLOT) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PlayerData data = Mojank.getInstance().getDataManager().getPlayerData(event.getPlayer());

                if (data != null) {
                    if (event.getPacketType() == PacketType.Play.Client.HELD_ITEM_SLOT) {
                        if (MathUtil.elapsed(data.heldItemsInSecond, 1000L)) {
                            data.heldItems = 0;
                        } else {
                            if (data.heldItems++ > 40) {
                                if ((boolean) getConfigValues().get("cancelInsteadOfKick")) {
                                    event.setCancelled(true);
                                } else {
                                    event.getPlayer().kickPlayer(Color.translate(((String) getConfigValues().get("kickMessage")).replaceAll("%method%", "Item")));
                                }
                            }
                        }
                    } else if (event.getPacketType() == PacketType.Play.Client.ARM_ANIMATION) {
                        if (MathUtil.elapsed(data.armSwingsInSecond, 1000L)) {
                            data.swings = 0;
                        } else {
                            if (data.swings++ > 100) {
                                if ((boolean) getConfigValues().get("cancelInsteadOfKick")) {
                                    event.setCancelled(true);
                                } else {
                                    event.getPlayer().kickPlayer(Color.translate(((String) getConfigValues().get("kickMessage")).replaceAll("%method%", "Swing")));
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
