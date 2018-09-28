package cc.funkemunky.fixer.impl.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.fixes.Fix;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class DamageIndicators extends Fix {
    public DamageIndicators() {
        super("DamageIndicators", true, true);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Mojank.getInstance(), PacketType.Play.Server.SPAWN_ENTITY_LIVING,
                PacketType.Play.Server.NAMED_ENTITY_SPAWN, PacketType.Play.Server.ENTITY_METADATA) {

            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Entity e = (Entity) packet.getEntityModifier(event).read(0);
                if (e instanceof LivingEntity && e.getType().equals(EntityType.PLAYER)
                        && packet.getWatchableCollectionModifier().read(0) != null
                        && e.getUniqueId() != event.getPlayer().getUniqueId()) {
                    packet = packet.deepClone();
                    event.setPacket(packet);
                    if (event.getPacket().getType() == PacketType.Play.Server.ENTITY_METADATA) {
                        WrappedDataWatcher watcher = new WrappedDataWatcher(
                                packet.getWatchableCollectionModifier().read(0));
                        this.processDataWatcher(watcher);
                        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
                    }
                }
            }

            private void processDataWatcher(WrappedDataWatcher watcher) {
                if (watcher != null && watcher.getObject(6) != null && watcher.getFloat(6) != 0.0F) {
                    watcher.setObject(6, Float.NaN);
                }
            }
        });
    }
}
