package dev.panda.combofly.providers;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.profile.Profile;
import dev.panda.combofly.utilities.Utils;
import dev.panda.combofly.utilities.tablist.v1_8.adapter.TabAdapter;
import dev.panda.combofly.utilities.tablist.v1_8.entry.TabEntry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TablistProvider_v1_8 implements TabAdapter {

    @Override
    public String getHeader(Player player) {
        return ComboFly.get().getTablistConfig().getString("TABLIST-HEADER");
    }

    @Override
    public String getFooter(Player player) {
        return ComboFly.get().getTablistConfig().getString("TABLIST-FOOTER");
    }

    @Override
    public List<TabEntry> getLines(Player player) {
        List<TabEntry> lines = new ArrayList<>();
        for (int i = 0; i <= 19; i++) {
            lines.add(new TabEntry(0, i, this.replace(ComboFly.get().getTablistConfig().getStringList("TABLIST.LEFT").get(i), player)));
            lines.add(new TabEntry(1, i, this.replace(ComboFly.get().getTablistConfig().getStringList("TABLIST.CENTER").get(i), player)));
            lines.add(new TabEntry(2, i, this.replace(ComboFly.get().getTablistConfig().getStringList("TABLIST.RIGHT").get(i), player)));
            lines.add(new TabEntry(3, i, this.replace(ComboFly.get().getTablistConfig().getStringList("TABLIST.EXTERNAL-RIGHT").get(i), player)));
        }
        return lines;
    }

    public String replace(String string, Player player) {
        string = string
                .replace("{name}", player.getName())
                .replace("{ping}", String.valueOf(Utils.getPlayerPing(player)))
                .replace("{rank}", ComboFly.get().getRankManager().getRank().getName(player))
                .replace("{rank-prefix}", ComboFly.get().getRankManager().getRank().getPrefix(player))
                .replace("{rank-suffix}", ComboFly.get().getRankManager().getRank().getSuffix(player))
                .replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .replace("{slots}", String.valueOf(Bukkit.getMaxPlayers()))
                .replace("{kills}", String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getKd().getKills()))
                .replace("{deaths}", String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getKd().getDeaths()))
                .replace("{balance}", String.valueOf(ComboFly.get().getBalanceType().getBalance(player)))
                .replace("{discord}", ComboFly.get().getMainConfig().getString("DISCORD"))
                .replace("{store}", ComboFly.get().getMainConfig().getString("STORE"))
                .replace("{teamspeak}", ComboFly.get().getMainConfig().getString("TEAMSPEAK"))
                .replace("{twitter}", ComboFly.get().getMainConfig().getString("TWITTER"))
                .replace("{website}", ComboFly.get().getMainConfig().getString("WEBSITE"));
        return string;
    }
}
