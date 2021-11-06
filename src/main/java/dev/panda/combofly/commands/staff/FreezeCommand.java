package dev.panda.combofly.commands.staff;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand extends BaseCommand {

    @Command(name = "freeze", permission = "pandafly.freeze", aliases = "ss", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();
        String label = command.getLabel();
        
        if (args.length < 1) {
            sender.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <player>"));
            return;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(ChatUtil.translate("&cPlayer '" + args[0] + "' not found."));
            return;
        }

        if (sender.equals(player)) {
            sender.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("FREEZE.FROZEN-SELF")));
            return;
        }

        if (ComboFly.get().getStaffManager().isStaff(player)) {
            sender.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("FREEZE.FROZEN-STAFF")));
            return;
        }

        if (ComboFly.get().getFreezeManager().isFrozen(player)) {
            ComboFly.get().getFreezeManager().removeFreeze(player);
            ComboFly.get().getStaffManager().sendMessageAllStaffs(ComboFly.get().getMessageConfig().getString("FREEZE.UNFROZEN-PLAYER-STAFF")
                    .replace("{staff}", sender.getName())
                    .replace("{player}", player.getName()));
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("FREEZE.UNFROZEN-PLAYER")
                    .replace("{staff}", sender.getName())));
        }
        else {
            ComboFly.get().getFreezeManager().setFreeze(player);
            ComboFly.get().getStaffManager().sendMessageAllStaffs(ComboFly.get().getMessageConfig().getString("FREEZE.FROZEN-PLAYER-STAFF")
                    .replace("{staff}", sender.getName())
                    .replace("{player}", player.getName()));
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("FREEZE.FROZEN-PLAYER")
                    .replace("{staff}", sender.getName())));
        }
    }
}
