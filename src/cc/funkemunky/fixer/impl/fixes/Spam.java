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

public class Spam extends Fix {
    public Spam() {
        super("Spam", true, true);

        addConfigValue("timeAllowed", 250);
        addConfigValue("threshold", 5);
        addConfigValue("kickMessage", "&6Mojank&7: Kicked for spam.");
    }

    @Override
    public void protocolLibListeners() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Mojank.getInstance(), PacketType.Play.Client.CLIENT_COMMAND, PacketType.Play.Client.CHAT) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PlayerData data = Mojank.getInstance().getDataManager().getPlayerData(event.getPlayer());

                if (data != null) {
                    if (!MathUtil.elapsed(data.lastChat, (int) getConfigValues().get("timeAllowed"))) {
                        if (data.chatVerbose++ > (int) getConfigValues().get("threshold")) {
                            event.getPlayer().kickPlayer(Color.translate((String) getConfigValues().get("kickMessage")));
                        }
                    } else {
                        data.chatVerbose = Math.max(0, data.chatVerbose - 1);
                    }
                    data.lastChat = System.currentTimeMillis();
                }
            }
        });
    }
}
