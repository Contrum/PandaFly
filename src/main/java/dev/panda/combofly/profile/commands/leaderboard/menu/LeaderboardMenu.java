package dev.panda.combofly.profile.commands.leaderboard.menu;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.panda.combofly.profile.Profile;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.item.ItemBuilder;
import dev.risas.panda.menu.Button;
import dev.risas.panda.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LeaderboardMenu extends Menu {

    public LeaderboardMenu(Plugin plugin) {
        super(plugin);
    }

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
            lore.add(CC.MENU_BAR);
            AtomicInteger pos = new AtomicInteger(1);
            Profile.getLeaderboards().get("kills").stream().limit(10).forEach(profile ->
                    lore.add(CC.translate("&7" + pos.getAndIncrement() + ") " + "&a" + profile.getName() + " &7(" + profile.getKd().getKills() + ")")));
            lore.add(CC.MENU_BAR);
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
            lore.add(CC.MENU_BAR);
            AtomicInteger pos = new AtomicInteger(1);
            Profile.getLeaderboards().get("deaths").stream().limit(10).forEach(profile ->
                    lore.add(CC.translate("&7" + pos.getAndIncrement() + ") " + "&a" + profile.getName() + " &7(" + profile.getKd().getDeaths() + ")")));
            lore.add(CC.MENU_BAR);
            return new ItemBuilder(Material.EMERALD)
                    .name("&2Top Deaths")
                    .lore(lore)
                    .build();
        }
    }
}
