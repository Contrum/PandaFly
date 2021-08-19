package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class HelpCommand extends BaseCommand {

    @Command(name = "help", permission = "pandafly.help")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        for (String lines : ComboFly.get().getMessageConfig().getStringList("HELP")) {
            player.sendMessage(CC.translate(lines
                    .replace("{discord}", ComboFly.get().getMainConfig().getString("DISCORD"))
                    .replace("{store}", ComboFly.get().getMainConfig().getString("STORE"))
                    .replace("{teamspeak}", ComboFly.get().getMainConfig().getString("TEAMSPEAK"))
                    .replace("{twitter}", ComboFly.get().getMainConfig().getString("TWITTER"))
                    .replace("{website}", ComboFly.get().getMainConfig().getString("WEBSITE"))));
        }
    }
}
