package cc.funkemunky.fixer.api.data;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.event.MListener;
import cc.funkemunky.fixer.api.utils.FunkeFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataManager extends MListener {

    /**
     * Player Object Stufff
     **/
    private final List<PlayerData> dataObjects;

    public DataManager() {
        dataObjects = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            createDataObject(player);
        }

        new BukkitRunnable() {
            public void run() {
                dataObjects.forEach(data -> data.log.write());
            }
        }.runTaskTimerAsynchronously(Mojank.getInstance(), 0L, 2400);
    }

    public void createDataObject(Player player) {
        dataObjects.add(new PlayerData(player));
    }

    public void removeDataObject(PlayerData dataObject) {
        dataObjects.remove(dataObject);
    }

    public PlayerData getPlayerData(Player player) {
        for (PlayerData data : dataObjects) {
            if (data.player == player) return data;
        }
        return null;
    }

    public void addLog(PlayerData data, String log) {
        LocalDateTime now = LocalDateTime.now();

        if(data.log == null) data.log = new FunkeFile(Mojank.getInstance(), "logs", data.player.getName());
        data.log.addLine("<" + now + "> " + log);
    }

    public List<PlayerData> getDataObjects() {
        return dataObjects;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        createDataObject(event.getPlayer());

        //event.getPlayer().sendMessage("test");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerData data = getPlayerData(event.getPlayer());

        if (data != null) {
            removeDataObject(data);
        }
    }

}
