package dev.panda.combofly.profile.commands;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.profile.Profile;
import dev.panda.combofly.utilities.CC;
import dev.panda.combofly.utilities.Ints;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StatsCommand extends BaseCommand {

    @Command(name = "stats", permission = "pandafly.stats")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();

        if (args.length < 1) {
            if (!player.hasPermission("pandafly.stats")) {
                player.sendMessage(CC.getNoPermission());
                return;
            }

            String playerKills = String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getKd().getKills());
            String playerDeaths = String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getKd().getDeaths());
            String playerBalance = String.valueOf(ComboFly.get().getBalanceType().getBalance(player));

            for (String lines : ComboFly.get().getMessageConfig().getStringList("STATS.PLAYER")) {
                player.sendMessage(CC.translate(lines
                        .replace("{player}", player.getName())
                        .replace("{kills}", playerKills)
                        .replace("{deaths}", playerDeaths)
                        .replace("{balance}", playerBalance)));
            }
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (args.length < 2) {
            if (!player.hasPermission("pandafly.stats.others")) {
                player.sendMessage(CC.getNoPermission());
                return;
            }

            if (target == null) {
                player.sendMessage(CC.getPlayerNotFound(args[0]));
                return;
            }

            String targetKills = String.valueOf(Profile.getProfiles().get(target.getUniqueId()).getKd().getKills());
            String targetDeaths = String.valueOf(Profile.getProfiles().get(target.getUniqueId()).getKd().getDeaths());
            String targetBalance = String.valueOf(ComboFly.get().getBalanceType().getBalance(target));

            for (String lines : ComboFly.get().getMessageConfig().getStringList("STATS.OTHERS")) {
                player.sendMessage(CC.translate(lines
                        .replace("{target}", target.getName())
                        .replace("{kills}", targetKills)
                        .replace("{deaths}", targetDeaths)
                        .replace("{balance}", targetBalance)));
            }
            return;
        }

        if (args[1].equalsIgnoreCase("set")) {
            if (!player.hasPermission("pandafly.stats.set")) {
                player.sendMessage(CC.getNoPermission());
                return;
            }

            if (args.length < 3) {
                player.sendMessage(CC.translate("&cUsage: /" + label + " <player> <set|give|remove> <kills|deaths> <amount>"));
                return;
            }

            if (target == null) {
                player.sendMessage(CC.getPlayerNotFound(args[0]));
                return;
            }

            Integer amount = Ints.tryParse(args[3]);

            if (amount == null) {
                player.sendMessage(CC.translate("&cAmount must be a number."));
                return;
            }

            if (amount < 0) {
                player.sendMessage(CC.translate("&cAmount must be positive."));
                return;
            }

            if (args[2].equalsIgnoreCase("kills")) {
                Profile.getProfiles().get(player.getUniqueId()).getKd().setKills(amount);
                player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("STATS.SET-KILLS")
                        .replace("{target}", target.getName())
                        .replace("{kills}", String.valueOf(amount))));
            }
            else if (args[2].equalsIgnoreCase("deaths")) {
                Profile.getProfiles().get(player.getUniqueId()).getKd().setDeaths(amount);
                player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("STATS.SET-DEATHS")
                        .replace("{target}", target.getName())
                        .replace("{deaths}", String.valueOf(amount))));
            }
            else {
                player.sendMessage(CC.translate("&cUsage: /" + label + " <player> <set|give|remove> <kills|deaths> <amount>"));
            }
        }
        else if (args[1].equalsIgnoreCase("give")) {
            if (!player.hasPermission("pandafly.stats.give")) {
                player.sendMessage(CC.getNoPermission());
                return;
            }

            if (args.length < 3) {
                player.sendMessage(CC.translate("&cUsage: /" + label + " <player> <set|give|remove> <amount>"));
                return;
            }

            if (target == null) {
                player.sendMessage(CC.getPlayerNotFound(args[0]));
                return;
            }

            Integer amount = Ints.tryParse(args[3]);

            if (amount == null) {
                player.sendMessage(CC.translate("&cAmount must be a number."));
                return;
            }

            if (amount < 0) {
                player.sendMessage(CC.translate("&cAmount must be positive."));
                return;
            }

            int targetKills = Profile.getProfiles().get(target.getUniqueId()).getKd().getKills();
            int targetDeaths = Profile.getProfiles().get(target.getUniqueId()).getKd().getDeaths();

            if (args[2].equalsIgnoreCase("kills")) {
                Profile.getProfiles().get(player.getUniqueId()).getKd().setKills(targetKills + amount);
                player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("STATS.GIVE-KILLS")
                        .replace("{target}", target.getName())
                        .replace("{kills}", String.valueOf(amount))));
            }
            else if (args[2].equalsIgnoreCase("deaths")) {
                Profile.getProfiles().get(player.getUniqueId()).getKd().setDeaths(targetDeaths + amount);
                player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("STATS.GIVE-DEATHS")
                        .replace("{target}", target.getName())
                        .replace("{deaths}", String.valueOf(amount))));
            }
            else {
                player.sendMessage(CC.translate("&cUsage: /" + label + " <player> <set|give|remove> <kills|deaths> <amount>"));
            }
        }
        else if (args[1].equalsIgnoreCase("remove")) {
            if (!player.hasPermission("pandafly.stats.remove")) {
                player.sendMessage(CC.getNoPermission());
                return;
            }

            if (args.length < 3) {
                player.sendMessage(CC.translate("&cUsage: /" + label + " <player> <set|give|remove> <amount>"));
                return;
            }

            if (target == null) {
                player.sendMessage(CC.getPlayerNotFound(args[0]));
                return;
            }

            Integer amount = Ints.tryParse(args[3]);

            if (amount == null) {
                player.sendMessage(CC.translate("&cAmount must be a number."));
                return;
            }

            if (amount < 0) {
                player.sendMessage(CC.translate("&cAmount must be positive."));
                return;
            }

            if (args[2].equalsIgnoreCase("kills")) {
                Profile.getProfiles().get(player.getUniqueId()).getKd().decrementKills(amount);
                player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("STATS.REMOVE-KILLS")
                        .replace("{target}", target.getName())
                        .replace("{kills}", String.valueOf(amount))));
            }
            else if (args[2].equalsIgnoreCase("deaths")) {
                Profile.getProfiles().get(player.getUniqueId()).getKd().decrementDeaths(amount);
                player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("STATS.REMOVE-DEATHS")
                        .replace("{target}", target.getName())
                        .replace("{deaths}", String.valueOf(amount))));
            }
            else {
                player.sendMessage(CC.translate("&cUsage: /" + label + " <player> <set|give|remove> <kills|deaths> <amount>"));
            }
        }
    }
}
