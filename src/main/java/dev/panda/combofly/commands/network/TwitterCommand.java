package dev.panda.combofly.commands.network;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class TwitterCommand extends BaseCommand {

    @Command(name = "twitter", permission = "pandafly.twitter", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        sender.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("NETWORK.TWITTER")
                .replace("{discord}", ComboFly.get().getMainConfig().getString("TWITTER"))));
    }
}
