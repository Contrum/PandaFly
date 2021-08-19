package dev.panda.combofly.commands;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.panda.combofly.utilities.Description;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PandaFlyCommand extends BaseCommand {

    @Command(name = "pandafly", permission = "pandafly.plugin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /" + label + " <info|reload>"));
            return;
        }

        if (args[0].equalsIgnoreCase("info")) {
            if (!player.hasPermission("pandafly.info")) {
                player.sendMessage(CC.getNoPermission());
                return;
            }

            this.getInfo(player);
        }
        else if (args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission("pandafly.reload")) {
                player.sendMessage(CC.getNoPermission());
                return;
            }

            this.reloadFiles();
            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                if (ComboFly.get().getStaffManager().isStaffMode(online)) {
                    ComboFly.get().getStaffManager().setStaffModeItems(online);
                }
            }
            player.sendMessage(CC.translate("&7[&c" + Description.getName() + "&7] " +
                    "&aAll files has been successfully reloaded."));
        }
    }

    private void getInfo(Player player) {
        player.sendMessage(CC.translate("&7&m----*---------------------------*----"));
        player.sendMessage(CC.translate(" "));
        player.sendMessage(CC.translate("     &4\u2764 &c&l" + Description.getName() + " &4\u2764"));
        player.sendMessage(CC.translate(""));
        player.sendMessage(CC.translate(" &7\u27A5 &cAuthor&7: &f" + Description.getAuthors())
                .replace("[", "")
                .replace("]", ""));
        player.sendMessage(CC.translate(" &7\u27A5 &cVersion&7: &f" + Description.getVersion()));
        player.sendMessage(CC.translate(" &7\u27A5 &cDiscord&7: &ahttps://discord.io/Panda-Community"));
        player.sendMessage(CC.translate(" "));
        player.sendMessage(CC.translate("&7&m----*---------------------------*----"));
    }

    private void reloadFiles() {
        ComboFly.get().getMainConfig().reload();
        ComboFly.get().getKitConfig().reload();
        ComboFly.get().getMessageConfig().reload();
        ComboFly.get().getWarpConfig().reload();
        ComboFly.get().getProfileConfig().reload();
        ComboFly.get().getScoreboardConfig().reload();
        ComboFly.get().getStaffitemsConfig().reload();
    }
}
