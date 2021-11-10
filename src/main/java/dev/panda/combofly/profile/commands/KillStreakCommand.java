package dev.panda.combofly.profile.commands;

import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.entity.Player;

public class KillStreakCommand extends BaseCommand {

    @Command(name = "killstreak", permission = "pandafly.killstreak")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        ComboFly.get().getMainConfig().getStringList("KILLSTREAK.COMMAND").forEach(key -> player.sendMessage(ChatUtil.translate(key)));
    }
}
