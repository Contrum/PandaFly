package dev.panda.combofly.commands.staff;

import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import dev.panda.combofly.utilities.staff.StaffItems;
import org.bukkit.entity.Player;

public class VanishCommand extends BaseCommand {

    @Command(name = "vanish", permission = "pandafly.vanish", aliases = "v")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        
        if (!ComboFly.get().getStaffManager().isStaff(player)) {
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("VANISH.NOT-STAFF")));
            return;
        }

        if (ComboFly.get().getVanishManager().isVanish(player)) {
            ComboFly.get().getVanishManager().removeVanish(player);
            if (ComboFly.get().getStaffManager().isStaffMode(player)) {
                player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("VANISH-OFF.SLOT"), StaffItems.getVanishOff());
            }
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("VANISH.DISABLE")));
        }
        else {
            ComboFly.get().getVanishManager().setVanish(player);
            if (ComboFly.get().getStaffManager().isStaffMode(player)) {
                player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("VANISH-ON.SLOT"), StaffItems.getVanishOn());
            }
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("VANISH.ENABLE")));
        }
    }
}
