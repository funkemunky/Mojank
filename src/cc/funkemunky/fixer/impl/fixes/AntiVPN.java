package cc.funkemunky.fixer.impl.fixes;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.fixes.Fix;
import cc.funkemunky.fixer.api.utils.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class AntiVPN extends Fix {
    public AntiVPN() {
        super("AntiVPN", true);

        addConfigValue("kickMessage", "&6Mojank AntiVPN\n&7You have been detected using a VPN on the IP &c%ip% &7in &c%country%&7.");
        addConfigValue("allowPermBypass", true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!(boolean) getConfigValues().get("allowPermBypass") || (!event.getPlayer().hasPermission("mojank.antivpn.bypass") && !event.getPlayer().hasPermission("mojank.admin"))) {
            new BukkitRunnable() {
                public void run() {
                    Map<String, String> result = getResponse(event.getPlayer());

                    if (result.get("status").equalsIgnoreCase("success")) {
                        if (result.get("hostIP").equalsIgnoreCase("true")) {
                            new BukkitRunnable() {
                                public void run() {
                                    event.getPlayer().kickPlayer(Color.translate(((String) getConfigValues().get("kickMessage")).replaceAll("%ip%", result.get("ip")).replaceAll("%country%", result.get("countryName"))));
                                }
                            }.runTask(Mojank.getInstance());
                        }
                    } else {

                    }
                }
            }.runTaskAsynchronously(Mojank.getInstance());
        }
    }

    public Map<String, String> getResponse(Player player) {
        Map<String, String> toReturn = new HashMap<>();
        try {
            StringBuilder response = new StringBuilder();
            String url = "http://api.vpnblocker.net/v2/json/" + player.getAddress().getAddress().getHostAddress() + "/gklH7N4NU5S8gpO6Tdk6UF68hrBTZ1";
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mojank v" + Mojank.getInstance().getDescription().getVersion());
            connection.setConnectTimeout(10000);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Throwable localThrowable3 = null;
            try {
                while ((url = in.readLine()) != null) {
                    response.append(url);
                }
                in.close();
            } catch (Throwable localThrowable1) {
                localThrowable3 = localThrowable1;
                throw localThrowable1;
            } finally {
                if (localThrowable3 != null) {
                    try {
                        in.close();
                    } catch (Throwable localThrowable2) {
                        localThrowable3.addSuppressed(localThrowable2);
                    }
                } else {
                    in.close();
                }
            }
            String result = response.toString();
            JSONObject object = new JSONObject(result);
            String status = object.getString("status");

            toReturn.put("status", status);
            if (status.equalsIgnoreCase("success")) {
                String ip = object.getString("ipaddress");
                String hostIP = object.getBoolean("host-ip") ? "true" : "false";
                JSONObject country = object.getJSONObject("country");
                String countryName = country.getString("name");
                String countryCode = country.getString("code");

                toReturn.put("ip", ip);
                toReturn.put("hostIP", hostIP);
                toReturn.put("countryName", countryName);
                toReturn.put("countryCode", countryCode);
            }

            return toReturn;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        toReturn.put("status", "failure");
        return toReturn;
    }

    @Override
    public void protocolLibListeners() {

    }
}
