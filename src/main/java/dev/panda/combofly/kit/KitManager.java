package dev.panda.combofly.kit;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.combofly.utilities.Time;
import dev.panda.lib.file.FileConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.*;

@Getter
@Setter
public class KitManager {

    private Map<UUID, String> name;
    private int time;
    private Table<String, UUID, Long> cooldown;
    public List<String> kits;
    private FileConfig config = ComboFly.get().getKitConfig();

    public KitManager() {
        this.name = new HashMap<>();
        this.time = 0;
        this.cooldown = HashBasedTable.create();
        this.kits = new ArrayList<>();
    }

    public void createKit(String kit) {
        config.getConfiguration().set("KITS." + kit + ".DELAY", 0);
        config.getConfiguration().set("KITS." + kit + ".STATE", true);
        config.getConfiguration().set("KITS." + kit + ".ARMOR.1.ITEM", new ItemStack(Material.DIAMOND_BOOTS));
        config.getConfiguration().set("KITS." + kit + ".CONTENTS.1.ITEM", new ItemStack(Material.DIAMOND_SWORD));
        config.save();
        config.reload();
    }

    public void deleteKit(String kit) {
        config.getConfiguration().set("KITS." + kit, null);
        config.save();
        config.reload();
    }

    public void setName(Player player, String kit) {
        this.name.put(player.getUniqueId(), kit);
    }

    public String getName(Player player) {
        return this.name.get(player.getUniqueId());
    }

    public boolean hasCooldown(String kit, Player player) {
        return cooldown.contains(kit, player.getUniqueId()) && cooldown.get(kit, player.getUniqueId()) > System.currentTimeMillis();
    }

    public void setCooldown(String kit, Player player, int time) {
        cooldown.put(kit, player.getUniqueId(), System.currentTimeMillis() + (time * 1000L));
    }

    public long getCooldown(String kit, Player player) {
        return cooldown.get(kit, player.getUniqueId()) - System.currentTimeMillis();
    }

    public void removeCooldown(String kit, Player player) {
        cooldown.remove(kit, player.getUniqueId());
    }

    public void setItems(String kit, Player p) {
        int armorCount = 0;
        int contentsCount = 0;

        config.getConfiguration().set("KITS." + kit + ".ARMOR", null);
        for (ItemStack armorItems : p.getInventory().getArmorContents()) {
            armorCount++;
            if (armorItems == null || armorItems.getType() == Material.AIR) continue;
                config.getConfiguration().set("KITS." + kit + ".ARMOR." + armorCount + ".ITEM", armorItems);
        }

        config.getConfiguration().set("KITS." + kit + ".CONTENTS", null);
        for (ItemStack contentsItems : p.getInventory().getContents()) {
            contentsCount++;
            if (contentsItems == null || contentsItems.getType() == Material.AIR) continue;
            config.getConfiguration().set("KITS." + kit + ".CONTENTS." + contentsCount + ".ITEM", contentsItems);
        }
        config.save();
        config.reload();
    }

    public void equipKit(String kit, Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        List<ItemStack> armorList = new ArrayList<>();

        for (String armorItems : config.getConfiguration().getConfigurationSection("KITS." + kit + ".ARMOR").getKeys(false)) {
            ItemStack item = config.getConfiguration().getItemStack("KITS." + kit + ".ARMOR." + armorItems + ".ITEM").clone();

            if (item != null) armorList.add(item);

            ItemStack[] armor = armorList.toArray(new ItemStack[0]);

            p.getInventory().setArmorContents(armor);
        }

//        int contentsCount = 0;
        for (String contentsItems : config.getConfiguration().getConfigurationSection("KITS." + kit + ".CONTENTS").getKeys(false)) {
//            contentsCount++;
            ItemStack item = config.getConfiguration().getItemStack("KITS." + kit + ".CONTENTS." + contentsItems + ".ITEM").clone();

            if (item == null || item.getType().equals(Material.AIR)) continue;

            p.getInventory().setItem(Integer.parseInt(contentsItems) - 1 , item);
        }
        p.updateInventory();
    }

    public int getDelayKitInt(String kit) {
        return config.getInt("KITS." + kit + ".DELAY");
    }

    public long getDelayKitLong(String kit) {
        return this.getDelayKitInt(kit) * 1000L;
    }

    public String getDelayKitString(String kit) {
        return Time.formatDuration(this.getDelayKitLong(kit));
    }
    
    public void setDelayKit(String kit, int delay) {
        config.getConfiguration().set("KITS." + kit + ".DELAY", delay);
        config.save();
        config.reload();
    }

    public void renameKit(String oldName, String newName) {
        int armorCount = 0;
        int contentsCount = 0;

        for (String armorItems : config.getConfiguration().getConfigurationSection("KITS." + oldName + ".ARMOR").getKeys(false)) {
            armorCount++;
            ItemStack item = config.getConfiguration().getItemStack("KITS." + oldName + ".ARMOR." + armorItems + ".ITEM");
            config.getConfiguration().set("KITS." + newName + ".ARMOR." + armorCount + ".ITEM", item);
        }

        for (String contentsItems : config.getConfiguration().getConfigurationSection("KITS." + oldName + ".CONTENTS").getKeys(false)) {
            contentsCount++;
            ItemStack item = config.getConfiguration().getItemStack("KITS." + oldName + ".CONTENTS." + contentsItems + ".ITEM");
            config.getConfiguration().set("KITS." + newName + ".CONTENTS." + contentsCount + ".ITEM", item);
        }

        config.getConfiguration().set("KITS." + oldName, null);
        config.save();
        config.reload();
    }

    public Set<String> getKits(){
        return config.getConfiguration().getConfigurationSection("KITS.").getKeys(false);
    }

    public void giveKit(Player player, String kitName) {
        for (String kit : config.getConfiguration().getConfigurationSection("KITS").getKeys(false)) {
            if (kitName.equals(kit)) {
                if (!player.hasPermission("pandafly.kit." + kitName.toLowerCase())) {
                    player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("KIT.PERMISSION-KIT")));
                    return;
                }
                if (!this.isState(kit)) {
                    return;
                }

                if (this.hasCooldown(kitName, player)) {
                    player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("KIT.COOLDOWN")
                            .replace("{kit}", kitName)
                            .replace("{cooldown}", Time.formatDuration(this.getCooldown(kitName, player)))));
                    return;
                }

                if (this.getDelayKitInt(kitName) > 0) this.setCooldown(kitName, player, this.getDelayKitInt(kitName));

                this.equipKit(kitName, player);
                player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("KIT.EQUIP-KIT")
                        .replace("{kit}", kitName)));
            }
        }
    }

    public boolean isState(String kit) {
        return config.getBoolean("KITS." + kit + ".STATE");
    }

    public void setState(String kit, boolean state) {
        config.getConfiguration().set("KITS." + kit + ".STATE", state);
        config.save();
        config.reload();
    }

    public void setDefaultKit(String kit) {
        ComboFly.get().getMainConfig().getConfiguration().set("KIT-DEFAULT", kit);
        ComboFly.get().getMainConfig().save();
        ComboFly.get().getMainConfig().reload();
    }

    public String getDefaultKit() {
        return ComboFly.get().getMainConfig().getString("KIT-DEFAULT");
    }
}
