package dev.panda.combofly.commands.network;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class TwitterCommand extends BaseCommand {

    @Command(name = "twitter", permission = "pandafly.twitter", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        sender.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("NETWORK.TWITTER")
                .replace("{discord}", ComboFly.get().getMainConfig().getString("TWITTER"))));
    }
}
