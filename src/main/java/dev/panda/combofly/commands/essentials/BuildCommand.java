package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BuildCommand extends BaseCommand {

    public static List<String> mode = new ArrayList<>();

    @Command(name = "build")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (mode.contains(player.getName())) {
            mode.remove(player.getName());
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("BUILD.DISABLE")));
        }
        else {
            mode.add(player.getName());
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("BUILD.ENABLE")));
        }
    }
}
