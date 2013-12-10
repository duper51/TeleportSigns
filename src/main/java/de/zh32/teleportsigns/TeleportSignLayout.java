package de.zh32.teleportsigns;

import de.zh32.teleportsigns.ping.ServerInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.bukkit.ChatColor;

/**
 *
 * @author zh32
 */
@Data
class TeleportSignLayout implements SignLayout {
    private final String name;    
    private final String online;
    private final String offline;
    private final List<String> layout;
    private final boolean teleport;
    private final String offlineInteger;

    @Override
    public List<String> parseLayout(ServerInfo sinfo) {
        List<String> laa = new ArrayList<>();
        int motdCount = 0;
        String[] splitMotd = sinfo.getMotd().split("(?<=\\G.{15})");
        for (String line : layout) {
            line = line.replace("%displayname%", sinfo.getDisplayname());
            if (sinfo.isOnline()) {
                line = line.replace("%isonline%", online);
                line = line.replace("%numpl%", String.valueOf(sinfo.getPlayersOnline()));
                line = line.replace("%maxpl%", String.valueOf(sinfo.getMaxPlayers()));
                if (line.contains("%motd%")) {
                    if (motdCount < splitMotd.length) {
                        String motd = splitMotd[motdCount];
                        if (motd != null) {
                            line = line.replace("%motd%", motd);
                        }
                        motdCount++;
                    } else {
                        line = line.replace("%motd%", "");
                    }
                }
            }
            else {
                line = line.replace("%isonline%", offline);
                line = line.replace("%numpl%", offlineInteger);
                line = line.replace("%maxpl%", offlineInteger);
                line = line.replace("%motd%", "");
            }
            laa.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return laa;
    }
}
