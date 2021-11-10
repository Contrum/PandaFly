package dev.panda.combofly.commands.staff;

import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportPositionCommand extends BaseCommand {

    @Command(name = "teleportposition", permission = "pandafly.teleportposition", aliases = {"tpp", "teleportp", "tpposition", "ttppos"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();
        
        if (args.length < 3) {
            player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <x> <y> <z>"));
            return;
        }

        double getX = Double.parseDouble(args[0]);
        double getY = Double.parseDouble(args[1]);
        double getZ = Double.parseDouble(args[2]);
        Location location = new Location(player.getLocation().getWorld(), getX, getY, getZ);
        player.teleport(location);
        player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("TPPOSITION.MESSAGE")));
    }
}
