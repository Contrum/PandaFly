package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class SpeedCommand extends BaseCommand {

    @Command(name = "speed", permission = "pandafly.speed")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();
        
        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /" + label + " <walk|fly> <amount|restore>"));
            return;
        }

        if (args[0].equalsIgnoreCase("walk")){
            if (args.length < 2) {
                player.sendMessage(CC.translate("&cUsage: /" + label + " walk <amount|restore>"));
                return;
            }
            if (args[1].equalsIgnoreCase("restore")){
                player.setWalkSpeed(0.2F);
                player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("SPEED.WALK.RESTORE")));
                return;
            }
            ComboFly.get().getSpeedManager().getWalkSpeed(args[1], player);
        }
        else if (args[0].equalsIgnoreCase("fly")) {
            if (args.length < 2) {
                player.sendMessage(CC.translate("&cUsage: /" + label + " fly <amount|restore>"));
                return;
            }
            if (args[1].equalsIgnoreCase("restore")){
                player.setFlySpeed(0.1F);
                player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("SPEED.FLY.RESTORE")));
                return;
            }
            ComboFly.get().getSpeedManager().getFlySpeed(args[1], player);
        }
    }
}
