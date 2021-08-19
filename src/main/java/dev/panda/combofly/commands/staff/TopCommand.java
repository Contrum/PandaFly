package dev.panda.combofly.commands.staff;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.panda.combofly.utilities.Utils;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TopCommand extends BaseCommand {

    @Command(name = "top", permission = "pandafly.teleporttop")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        Location origin = player.getLocation().clone();
        Location highestLocation = Utils.getHighestLocation(origin.clone());
        if (highestLocation != null && !Objects.equals(highestLocation, origin)) {
            Block originBlock = origin.getBlock();
            if ((highestLocation.getBlockY() - originBlock.getY() != 1 || originBlock.getType() != Material.WATER) && originBlock.getType() != Material.STATIONARY_WATER) {
                player.teleport(highestLocation.add(0.0, 1.0, 0.0));
                player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("TOP.MESSAGE")));
                return;
            }
        }
        player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("TOP.NO-LOCATION")));
    }
}
