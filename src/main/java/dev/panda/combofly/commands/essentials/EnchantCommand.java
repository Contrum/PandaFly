package dev.panda.combofly.commands.essentials;

import dev.panda.lib.chat.ChatUtil;
import dev.panda.combofly.utilities.Ints;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantCommand extends BaseCommand {
    
    @Command(name = "enchant", permission = "pandafly.enchant")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();
        
        if (args.length < 2) {
            player.sendMessage(ChatUtil.translate("&cUsage: /" +  label + " <enchantment> <level>"));
            return;
        }

        ItemStack item = player.getItemInHand();

        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(ChatUtil.translate("&cYou need hold an item."));
            return;
        }

        Enchantment enchantment = Enchantment.getByName(enchantments(args[0]));
        if (enchantment == null) {
            player.sendMessage(ChatUtil.translate("&cEnchantment no valid."));
            return;
        }

        Integer level = Ints.tryParse(args[1]);

        if (level == null) {
            player.sendMessage(ChatUtil.translate("&cAmount must be a number."));
            return;
        }

        if (level < 0) {
            player.sendMessage(ChatUtil.translate("&cAmount must be positive."));
            return;
        }

        if(level == 0) {

            if(item.containsEnchantment(enchantment)) {
                item.removeEnchantment(enchantment);
                player.sendMessage(ChatUtil.translate("&aYou have removed the enchantment " + enchantment + "."));
            }
            else {
                player.sendMessage(ChatUtil.translate("&cThis item does not have this enchantment."));
            }
        } else {
            item.addUnsafeEnchantment(enchantment, level);
            player.sendMessage(ChatUtil.translate("&aEnchanted item with " + enchantment.getName() + " " + level + "."));
        }
    }

    public String enchantments(String name) {
        String enchant = name;

        if (name.equalsIgnoreCase("sharp") || name.equalsIgnoreCase("sharpness")) {
            enchant = "DAMAGE_ALL";
        }
        if (name.equalsIgnoreCase("fire") || name.equalsIgnoreCase("fireaspect")) {
            enchant = "FIRE_ASPECT";
        }
        if (name.equalsIgnoreCase("kb") || name.equalsIgnoreCase("knock")) {
            enchant = "KNOCKBACK";
        }
        if (name.equalsIgnoreCase("smi") || name.equalsIgnoreCase("smite")) {
            enchant = "DAMAGE_UNDEAD";
        }
        if (name.equalsIgnoreCase("bane") || name.equalsIgnoreCase("baneof") || name.equalsIgnoreCase("baneofarthropods")) {
            enchant = "DAMAGE_ARTHROPODS";
        }
        if (name.equalsIgnoreCase("prote") || name.equalsIgnoreCase("protection")) {
            enchant = "PROTECTION_ENVIRONMENTAL";
        }
        if (name.equalsIgnoreCase("fire") || name.equalsIgnoreCase("fireprot") || name.equalsIgnoreCase("fireprotection")) {
            enchant = "PROTECTION_FIRE";
        }
        if (name.equalsIgnoreCase("blast") || name.equalsIgnoreCase("blastprot") || name.equalsIgnoreCase("blastprotection")) {
            enchant = "PROTECTION_EXPLOSIONS";
        }
        if (name.equalsIgnoreCase("proj") || name.equalsIgnoreCase("projprot") || name.equalsIgnoreCase("projectileprotection")) {
            enchant = "PROTECTION_PROJECTILE";
        }
        if (name.equalsIgnoreCase("loot") || name.equalsIgnoreCase("looting")) {
            enchant = "LOOT_BONUS_MOBS";
        }
        if (name.equalsIgnoreCase("fea") || name.equalsIgnoreCase("falling") || name.equalsIgnoreCase("featherfalling")) {
            enchant = "PROTECTION_FALL";
        }
        if (name.equalsIgnoreCase("fort") || name.equalsIgnoreCase("fortune")) {
            enchant = "LOOT_BONUS_BLOCKS";
        }
        if (name.equalsIgnoreCase("silk") || name.equalsIgnoreCase("silktouch")) {
            enchant = "SILK_TOUCH";
        }
        if (name.equalsIgnoreCase("pow") || name.equalsIgnoreCase("power")) {
            enchant = "ARROW_DAMAGE";
        }
        if (name.equalsIgnoreCase("pun") || name.equalsIgnoreCase("punch")) {
            enchant = "ARROW_KNOCKBACK";
        }
        if (name.equalsIgnoreCase("fla") || name.equalsIgnoreCase("flame")) {
            enchant = "ARROW_FIRE";
        }
        if (name.equalsIgnoreCase("inf") || name.equalsIgnoreCase("infinity")) {
            enchant = "ARROW_INFINITE";
        }
        if(name.equalsIgnoreCase("unb") || name.equalsIgnoreCase("unbreaking")) {
            enchant = "DURABILITY";
        }
        if(name.equalsIgnoreCase("eff") || name.equalsIgnoreCase("efficiency")) {
            enchant = "DIG_SPEED";
        }
        return enchant.toUpperCase();
    }
}
