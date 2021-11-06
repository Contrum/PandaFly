package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;

import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeCommand extends BaseCommand {

    @Command(name = "gamemode", permission = "pandafly.gamemode", aliases = "gm")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();
        
        if (args.length < 1) {
            player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <mode>"));
            return;
        }

        if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival") ||
                args[0].equalsIgnoreCase("s")) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative") ||
                args[0].equalsIgnoreCase("c")) {
            player.setGameMode(GameMode.CREATIVE);
        }
        else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure") ||
                args[0].equalsIgnoreCase("a")) {
            player.setGameMode(GameMode.ADVENTURE);
        }
        else {
            player.sendMessage(ChatUtil.translate("&cGamemode '" + args[0] + "' not found."));
            return;
        }
        player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("GAMEMODE.CHANGE")
                .replace("{gamemode}", String.valueOf(player.getGameMode()))));
    }
}
