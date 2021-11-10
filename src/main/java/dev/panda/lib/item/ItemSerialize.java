package dev.panda.lib.item;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Risas
 * Project: PandaLib
 * Date: 31-10-2021 - 14:18
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@UtilityClass
public class ItemSerialize {

    public String serializeItemStack(ItemStack item) {
        StringBuilder builder = new StringBuilder();

        if (item == null) {
            return "null";
        }

        String isType = String.valueOf(item.getType().getId());
        builder.append("t@").append(isType);

        if (item.getDurability() != 0) {
            String isDurability = String.valueOf(item.getDurability());
            builder.append(":d@").append(isDurability);
        }

        if (item.getAmount() != 1) {
            String isAmount = String.valueOf(item.getAmount());
            builder.append(":a@").append(isAmount);
        }

        Map<Enchantment, Integer> isEnchantment = item.getEnchantments();

        if (isEnchantment.size() > 0) {
            for (Map.Entry<Enchantment, Integer> entry : isEnchantment.entrySet()) {
                builder.append(":e@").append(entry.getKey().getId()).append("@").append(entry.getValue());
            }
        }

        if (item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();

            if (itemMeta.hasDisplayName()) {
                builder.append(":dn@").append(itemMeta.getDisplayName());
            }

            if (itemMeta.hasLore()) {
                builder.append(":l@").append(itemMeta.getLore());
            }
        }

        return builder.toString();
    }

    public static ItemStack deserializeItemStack(String item) {
        ItemStack itemStack = null;
        ItemMeta itemMeta = null;

        if (item.equals("null")) {
            return new ItemStack(Material.AIR);
        }

        String[] split = item.split(":");

        for (String itemInfo : split) {
            String[] itemAttribute = itemInfo.split("@");
            String s2 = itemAttribute[0];

            switch (s2) {
                case "t": {
                    itemStack = new ItemStack(Material.getMaterial(Integer.parseInt(itemAttribute[1])));
                    itemMeta = itemStack.getItemMeta();
                    break;
                }
                case "d": {
                    if (itemStack != null) {
                        itemStack.setDurability(Short.parseShort(itemAttribute[1]));
                        break;
                    }
                    break;
                }
                case "a": {
                    if (itemStack != null) {
                        itemStack.setAmount(Integer.parseInt(itemAttribute[1]));
                        break;
                    }
                    break;
                }
                case "e": {
                    if (itemStack != null) {
                        itemStack.addEnchantment(Enchantment.getById(Integer.parseInt(itemAttribute[1])), Integer.parseInt(itemAttribute[2]));
                        break;
                    }
                    break;
                }
                case "dn": {
                    if (itemMeta != null) {
                        itemMeta.setDisplayName(itemAttribute[1]);
                        break;
                    }
                    break;
                }
                case "l": {
                    itemAttribute[1] = itemAttribute[1].replace("[", "");
                    itemAttribute[1] = itemAttribute[1].replace("]", "");

                    List<String> lore = Arrays.asList(itemAttribute[1].split(","));

                    for (int i = 0; i < lore.size(); ++i) {
                        String index = lore.get(i);

                        if (index != null) {
                            if (index.toCharArray().length != 0) {
                                if (index.charAt(0) == ' ') {
                                    index = index.replaceFirst(" ", "");
                                }

                                lore.set(i, index);
                            }
                        }
                    }

                    if (itemMeta != null) {
                        itemMeta.setLore(lore);
                        break;
                    }

                    break;
                }
            }
        }

        if (itemMeta != null && (itemMeta.hasDisplayName() || itemMeta.hasLore())) {
            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }
}
