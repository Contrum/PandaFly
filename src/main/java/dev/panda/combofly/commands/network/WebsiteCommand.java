package dev.panda.combofly.commands.network;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class WebsiteCommand extends BaseCommand {

    @Command(name = "website", permission = "pandafly.website", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        sender.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("NETWORK.WEBSITE")
                .replace("{website}", ComboFly.get().getMainConfig().getString("WEBSITE"))));
    }
}
