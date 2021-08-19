package dev.panda.combofly.commands.staff;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportHereCommand extends BaseCommand {

    @Command(name = "teleporthere", permission = "pandafly.teleporthere", aliases = {"tphere", "s"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();
        
        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /" + label + " <player>"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (player.equals(target)){
            player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("TPHERE.NOT-SELF")));
            return;
        }

        if (target == null) {
            player.sendMessage(CC.getPlayerNotFound(args[0]));
            return;
        }
        Location location = player.getLocation();
        target.teleport(location);
        player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("TPHERE.PLAYER").replace("{target}", target.getName())));
        target.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("TPHERE.TARGET").replace("{player}", player.getName())));
    }
}
