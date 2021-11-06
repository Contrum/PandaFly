package dev.panda.combofly.utilities.menu.pagination;

import dev.panda.combofly.utilities.menu.Button;
import dev.panda.item.ItemBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PageButton extends Button {

    private final int mod;
    private final PaginatedMenu menu;

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.CARPET);

        if (mod > 0) {
            itemBuilder.data((short) 13);
        }
        else {
            itemBuilder.data((short) 14);
        }

        if (this.hasNext(player)) {
            itemBuilder.name(this.mod > 0 ? "&aNext page" : "&cPrevious page");
        }
        else {
            itemBuilder.name("&7" + (this.mod > 0 ? "Last page" : "First page"));
        }

        return itemBuilder.build();
    }

    @Override
    public void clicked(Player player, int i, ClickType clickType, int hb) {
        if (hasNext(player)) {
            this.menu.modPage(player, this.mod);
            Button.playNeutral(player);
        } else {
            Button.playFail(player);
        }
    }

    private boolean hasNext(Player player) {
        int pg = this.menu.getPage() + this.mod;
        return pg > 0 && this.menu.getPages(player) >= pg;
    }
}
