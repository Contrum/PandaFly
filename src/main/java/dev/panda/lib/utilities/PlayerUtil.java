package dev.panda.lib.utilities;

import dev.panda.lib.chat.ChatUtil;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

@UtilityClass
public class PlayerUtil {

    public int getPing(Player player) {
        try {
            String a = Bukkit.getServer().getClass().getPackage().getName().substring(23);
            Class<?> b = Class.forName("org.bukkit.craftbukkit." + a + ".entity.CraftPlayer");
            Object c = b.getMethod("getHandle").invoke(player);
            return (int) c.getClass().getDeclaredField("ping").get(c);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public String getCountry(String IP) {
        URL url;
        BufferedReader in;
        String country = "";

        try {
            url = new URL("http://ip-api.com/json/" + IP + "?fields=country");
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            country = in.readLine().trim();
            if (country.length() <= 0)
                try {
                    InetAddress ip = InetAddress.getLocalHost();
                    System.out.println(ip.getHostAddress().trim());
                    country = ip.getHostAddress().trim();
                } catch (Exception exp) {
                    country = "Not Found";
                }
        }
        catch (Exception ex) {
            ChatUtil.log("Error in check country!");
        }
        return country
                .replace("{", "")
                .replace("}", "")
                .replace("\"\"", "")
                .replace(":", "");
    }

    public boolean isInventoryFull(Player player) {
        return player.getInventory().firstEmpty() < 0;
    }

    public void decrement(Player player, ItemStack itemStack, boolean sound, boolean cursor) {
        if (sound) player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1F, 1F);

        if (itemStack.getAmount() <= 1) {
            if (cursor) player.setItemOnCursor(null);
            else player.setItemInHand(null);
        }
        else itemStack.setAmount(itemStack.getAmount() - 1);

        player.updateInventory();
    }
}
