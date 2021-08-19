package dev.panda.combofly.koth.commands;

import com.google.common.collect.Maps;
import dev.panda.combofly.ComboFly;
import dev.panda.combofly.koth.KoTHMenu;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import dev.risas.panda.item.ItemBuilder;
import dev.risas.panda.menu.Button;
import dev.risas.panda.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class HostCommand extends BaseCommand {

    @Command(name = "host", permission = "pandafly.host")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        new HostMenu(ComboFly.get()).openMenu(player);
    }

    private class HostMenu extends Menu {

        public HostMenu(Plugin plugin) {
            super(plugin);
        }

        @Override
        public String getTitle(Player player) {
            return "&cHost a Event";
        }

        @Override
        public int getSize() {
            return 9*3;
        }

        @Override
        public Map<Integer, Button> getButtons(Player player) {
            Map<Integer, Button> buttons = Maps.newHashMap();
            buttons.put(13, new KoTHButton());
            return buttons;
        }
    }

    private class KoTHButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.NAME_TAG)
                    .name("&9KoTH´s event")
                    .lore("&7Click for open menu of all KoTH´s events")
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            super.clicked(player, slot, clickType, hotbarButton);
            new KoTHMenu(ComboFly.get()).openMenu(player);
        }
    }
}
