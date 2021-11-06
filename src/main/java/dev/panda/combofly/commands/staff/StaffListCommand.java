package dev.panda.combofly.commands.staff;

import dev.panda.combofly.ComboFly;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class StaffListCommand extends BaseCommand {

    @Command(name = "stafflist", permission = "pandafly.stafflist", aliases = {"staffl", "lstaff", "sl"}, inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        ComboFly.get().getStaffManager().getOnlineStaff(sender);
    }
}
