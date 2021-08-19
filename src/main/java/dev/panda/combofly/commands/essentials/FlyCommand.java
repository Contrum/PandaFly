package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FlyCommand extends BaseCommand {

    @Command(name = "fly", permission = "pandafly.fly")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        
        if (args.length < 1) {
            if (!player.hasPermission("pandafly.fly")) {
                player.sendMessage(CC.getNoPermission());
                return;
            }

            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("FLY.DISABLE")));
            }
            else {
                player.setAllowFlight(true);
                player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("FLY.ENABLE")));
            }
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (!player.hasPermission("pandafly.fly.others")) {
            player.sendMessage(CC.getNoPermission());
            return;
        }

        if (target == null) {
            player.sendMessage(CC.getPlayerNotFound(args[0]));
            return;
        }

        if (target.getAllowFlight()) {
            target.setAllowFlight(false);
            player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("FLY.DISABLE-OTHERS")
                    .replace("{target}", target.getName())));
        }
        else {
            target.setAllowFlight(true);
            player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("FLY.ENABLE-OTHERS")
                    .replace("{target}", target.getName())));
        }
    }
}
