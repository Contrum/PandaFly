package dev.panda.combofly.profile.commands.leaderboard;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.profile.Profile;
import dev.panda.combofly.profile.commands.leaderboard.menu.LeaderboardMenu;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.Objects;

public class LeaderboardCommand extends BaseCommand {

    @Command(name = "leaderboard", aliases = {"tops", "top", "topkills", "topdeaths"})
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        new LeaderboardMenu(ComboFly.get()).openMenu(player);
    }
}
