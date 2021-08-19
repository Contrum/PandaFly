package dev.panda.combofly.profile.commands;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class KillStreakCommand extends BaseCommand {

    @Command(name = "killstreak", permission = "pandafly.killstreak")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        ComboFly.get().getMainConfig().getStringList("KILLSTREAK.COMMAND").forEach(key -> player.sendMessage(CC.translate(key)));
    }
}
