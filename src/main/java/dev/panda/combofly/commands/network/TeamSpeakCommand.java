package dev.panda.combofly.commands.network;

import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class TeamSpeakCommand extends BaseCommand {

    @Command(name = "teamspeak", permission = "pandafly.teamspeak", aliases = {"ts", "ts3"}, inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        sender.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("NETWORK.TEAMSPEAK")
                .replace("{teamspeak}", ComboFly.get().getMainConfig().getString("TEAMSPEAK"))));
    }
}
