package dev.panda.combofly.kit.commands;

import dev.panda.combofly.ComboFly;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dev.panda.chat.ChatUtil;

public class KitManagerCommand extends BaseCommand {

	@Command(name = "kitmanager", permission = "pandafly.kitmanager", aliases = {"km", "kitm", "kmanager"})
	@Override
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();
		String label = command.getLabel();

		if (args.length < 1) {
			this.getUsage(player, label);
			return;
		}

		if (args[0].equalsIgnoreCase("create")) {
			if (args.length < 2) {
				player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " create <kitName>"));
				return;
			}

			String kitName = ChatUtil.capitalize(args[1]);

			if (ComboFly.get().getKitConfig().getString("KITS." + kitName) != null) {
				player.sendMessage(ChatUtil.translate("&cKit " + kitName + " is already created."));
				return;
			}

			ComboFly.get().getKitManager().createKit(kitName);
			player.sendMessage(ChatUtil.translate("&aSuccesfully! &7Kit " + kitName + " has been &acreate&7."));
		}
		else if (args[0].equalsIgnoreCase("delete")) {
			if (args.length < 2) {
				player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " delete <kitName>"));
				return;
			}

			String kitName = ChatUtil.capitalize(args[1]);

			if (ComboFly.get().getKitConfig().getString("KITS." + kitName) == null){
				player.sendMessage(ChatUtil.translate("&cKit '" + kitName + "' not found."));
				return;
			}

			ComboFly.get().getKitManager().deleteKit(kitName);
			player.sendMessage(ChatUtil.translate("&aSuccessfully! &7Kit " + kitName + " has been &cdelete."));
		}
		else if (args[0].equalsIgnoreCase("edit")) {
			if (args.length < 2) {
				player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " edit <kitName>"));
				return;
			}

			String kitName = ChatUtil.capitalize(args[1]);

			if (ComboFly.get().getKitConfig().getString("KITS." + kitName) == null) {
				player.sendMessage(ChatUtil.translate("&cKit '" + kitName + "' not found."));
				return;
			}

			ComboFly.get().getKitManager().setName(player, kitName);
			ComboFly.get().getInventoryManager().getEditKit(player, kitName);
		}
		else if (args[0].equalsIgnoreCase("editor")) {
			ComboFly.get().getInventoryManager().getKitsEditor(player);
		}
		else if (args[0].equalsIgnoreCase("rename")){
			if (args.length < 3) {
				player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " rename <kitName> <newKitName>"));
				return;
			}

			String kitName = ChatUtil.capitalize(args[1]);

			if (ComboFly.get().getKitConfig().getString("KITS." + kitName) == null) {
				player.sendMessage(ChatUtil.translate("&cKit '" + kitName + "' not found."));
				return;
			}
			String newName = ChatUtil.capitalize(args[2]);

			if (ComboFly.get().getKitConfig().getString("KITS." + newName) != null) {
				player.sendMessage(ChatUtil.translate("&cKit " + newName + " is already created."));
				return;
			}

			ComboFly.get().getKitManager().renameKit(kitName, newName);
			player.sendMessage(ChatUtil.translate("&aSuccesfully! &7Kit " + kitName + " has been &arename &7to " + newName + "."));
		}
		else if (args[0].equalsIgnoreCase("cooldown")) {
			if (args.length < 4) {
				player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " cooldown <set|remove> <player> <kitName>"));
				return;
			}

			Player target = Bukkit.getPlayer(args[2]);

			if (target == null) {
				player.sendMessage(ChatUtil.translate("&cPlayer '" + args[2] + "' not found."));
				return;
			}

			String kitName = ChatUtil.capitalize(args[3]);

			if (ComboFly.get().getKitConfig().getString("KITS." + kitName) == null) {
				player.sendMessage(ChatUtil.translate("&cKit '" + kitName + "' not found."));
				return;
			}

			if (args[1].equalsIgnoreCase("remove")) {
				ComboFly.get().getKitManager().removeCooldown(kitName, target);
				player.sendMessage(ChatUtil.translate("&aSuccessfully! &7Kit " + kitName + " &acooldown &7has been &aremoved &7to " + target.getName() + "."));
			}
			if (args[1].equalsIgnoreCase("set")) {
				ComboFly.get().getKitManager().setCooldown(kitName, player, ComboFly.get().getKitManager().getDelayKitInt(kitName));
				player.sendMessage(ChatUtil.translate("&aSuccessfully! &7Kit " + kitName + " &acooldown &7has been &areset &7to " + target.getName() + "."));
			}
		}
		else if (args[0].equalsIgnoreCase("list")) {
			player.sendMessage(ChatUtil.translate("&cAvailable Kits: " + ComboFly.get().getKitManager().getKits()));
		}
		else {
			this.getUsage(player, label);
		}
	}

	private void getUsage(Player p, String label){
		p.sendMessage(ChatUtil.translate("&7&m--------------------------------------"));
		p.sendMessage(ChatUtil.translate("&4&lKitManager Commands"));
		p.sendMessage(ChatUtil.translate(""));
		p.sendMessage(ChatUtil.translate("&c/" + label + " create <kitName>"));
		p.sendMessage(ChatUtil.translate("&c/" + label + " delete <kitName>"));
		p.sendMessage(ChatUtil.translate("&c/" + label + " rename <kitName> <newKitName>"));
		p.sendMessage(ChatUtil.translate("&c/" + label + " edit <kitName>"));
		p.sendMessage(ChatUtil.translate("&c/" + label + " editor"));
		p.sendMessage(ChatUtil.translate("&c/" + label + " cooldown <set|remove> <player> <kitName>"));
		p.sendMessage(ChatUtil.translate("&c/" + label + " list"));
		p.sendMessage(ChatUtil.translate("&7&m--------------------------------------"));
	}
}
