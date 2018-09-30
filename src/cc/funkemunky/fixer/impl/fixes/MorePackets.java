package cc.funkemunky.fixer.impl.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.data.PlayerData;
import cc.funkemunky.fixer.api.fixes.Fix;
import cc.funkemunky.fixer.api.utils.MathUtil;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

public class MorePackets extends Fix {
    public MorePackets() {
        super("MorePackets", true, true);

        addConfigValue("kickMessage", "&6Mojank&7: Too many packets.");
    }

    @Override
    public void protocolLibListeners() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Mojank.getInstance(),
                PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.POSITION, PacketType.Play.Client.LOOK, PacketType.Play.Client.FLYING) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PlayerData data = Mojank.getInstance().getDataManager().getPlayerData(event.getPlayer());

                if(data != null) {
                    if(MathUtil.elapsed(data.flyingPacketsInSecond, 1000L)) {
                        if(data.flyingPacketsInSecond > 150) {
                            event.getPlayer().kickPlayer((String) getConfigValues().get("kickMessage"));
                        }
                        data.flyingPacketsInSecond = 0;
                    } else {
                        data.flyingPacketsInSecond++;
                    }
                }
            }
        });
    }
}
