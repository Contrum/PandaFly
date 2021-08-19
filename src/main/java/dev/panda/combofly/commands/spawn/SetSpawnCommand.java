package dev.panda.combofly.commands.spawn;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends BaseCommand {

    @Command(name = "setspawn", permission = "pandafly.setspawn")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        ComboFly.get().getSpawnManager().createSpawn(player);
        player.sendMessage(CC.translate("&aSuccessfully! &cSpawn &a has been set."));
    }

}
