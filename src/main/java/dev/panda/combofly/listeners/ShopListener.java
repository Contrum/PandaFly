package dev.panda.combofly.listeners;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.profile.Profile;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.combofly.utilities.Ints;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    public ShopListener() {
        Bukkit.getPluginManager().registerEvents(this, ComboFly.get());
    }

    @EventHandler
    @Deprecated
    public void SignBuy(PlayerInteractEvent event){
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        BlockState state = block.getState();
        if (state instanceof Sign){
            Sign sign = (Sign) state;
            String line1 = sign.getLine(0);
            String line2 = sign.getLine(1);
            String line3 = sign.getLine(2);
            String line4 = sign.getLine(3);
            if (line1.contains("[Buy]")) {
                if (line2.contains(":")) {
                    Material material = Material.matchMaterial(line2.replace("_", "").split(":")[0]);
                    Integer amount = Ints.tryParse(line3);
                    if (amount != null && material != null && line4.contains("$")) {
                        int money = Integer.parseInt(line4.replace("$", ""));

                        if (money > Profile.getProfiles().get(player.getUniqueId()).getBalance()) {
                            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("SHOP.ENOUGH-MONEY")));
                            return;
                        }

                        int data = Integer.parseInt(line2.split(":")[1]);
                        int playerBalance = (int) ComboFly.get().getBalanceType().getBalance(player);
                        ComboFly.get().getBalanceType().removeBalance(player, playerBalance - money);
                        player.getInventory().addItem(this.getItemData(material.getId(), amount, data));
                    }
                }
                Material material = Material.matchMaterial(line2.replace("_", ""));
                Integer amount = Ints.tryParse(line3);
                if (amount != null && material != null && line4.contains("$")) {
                    int money = Integer.parseInt(line4.replace("$", ""));

                    if (money > Profile.getProfiles().get(player.getUniqueId()).getBalance()) {
                        player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("SHOP.ENOUGH-MONEY")));
                        return;
                    }

                    int playerBalance = (int) ComboFly.get().getBalanceType().getBalance(player);
                    ComboFly.get().getBalanceType().removeBalance(player, playerBalance - money);
                    player.getInventory().addItem(this.getItem(material.getId(), amount));
                    }
                }
            }
        }
    }

    private ItemStack getItemData(int id, int amount, int data) {
        return new ItemStack(id, amount, (short) data);
    }
    
    private ItemStack getItem(int id, int amount) {
        return new ItemStack(id, amount);
    }
}
