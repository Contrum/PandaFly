package dev.panda.combofly.profile.commands.leaderboard;

import dev.panda.combofly.profile.commands.leaderboard.menu.LeaderboardMenu;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class LeaderboardCommand extends BaseCommand {

    @Command(name = "leaderboard", aliases = {"tops", "top", "topkills", "topdeaths"})
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        new LeaderboardMenu().openMenu(player);
    }
}
