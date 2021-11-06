package dev.panda.combofly.commands.staff;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class StaffModeCommand extends BaseCommand {

    @Command(name = "staffmode", permission = "pandafly.staffmode", aliases = {"smode", "sm", "mod"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        
        if (!ComboFly.get().getStaffManager().isStaff(player)) {
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("STAFFMODE.NOT-STAFF")));
            return;
        }

        if (ComboFly.get().getStaffManager().isStaffMode(player)) {
            ComboFly.get().getStaffManager().removeStaffMode(player);
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("STAFFMODE.DISABLE")));
        }
        else {
            ComboFly.get().getStaffManager().setStaffMode(player);
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("STAFFMODE.ENABLE")));
        }
    }
}
