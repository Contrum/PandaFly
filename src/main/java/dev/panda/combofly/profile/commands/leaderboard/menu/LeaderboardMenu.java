package dev.panda.combofly.profile.commands.leaderboard.menu;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.combofly.profile.Profile;
import dev.panda.combofly.utilities.menu.Button;
import dev.panda.combofly.utilities.menu.Menu;
import dev.panda.lib.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LeaderboardMenu extends Menu {

    @Override
    public String getTitle(Player player) {
        return "&6&lLeaderboard";
    }

    @Override
    public int getSize() {
        return 9*3;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(12, new KillsButton());
        buttons.put(14, new DeathsButton());
        return buttons;
    }

    private static class KillsButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            List<String> lore = Lists.newArrayList();
            lore.add(ChatUtil.NORMAL_LINE);
            AtomicInteger pos = new AtomicInteger(1);
            Profile.getLeaderboards().get("kills").stream().limit(10).forEach(profile ->
                    lore.add(ChatUtil.translate("&7" + pos.getAndIncrement() + ") " + "&a" + profile.getName() + " &7(" + profile.getKd().getKills() + ")")));
            lore.add(ChatUtil.NORMAL_LINE);
            return new ItemBuilder(Material.EMERALD)
                    .name("&2Top Kills")
                    .lore(lore)
                    .build();
        }
    }

    private static class DeathsButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            List<String> lore = Lists.newArrayList();
            lore.add(ChatUtil.NORMAL_LINE);
            AtomicInteger pos = new AtomicInteger(1);
            Profile.getLeaderboards().get("deaths").stream().limit(10).forEach(profile ->
                    lore.add(ChatUtil.translate("&7" + pos.getAndIncrement() + ") " + "&a" + profile.getName() + " &7(" + profile.getKd().getDeaths() + ")")));
            lore.add(ChatUtil.NORMAL_LINE);
            return new ItemBuilder(Material.EMERALD)
                    .name("&2Top Deaths")
                    .lore(lore)
                    .build();
        }
    }
}
