package dev.panda.combofly.commands.warp;

import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.entity.Player;
import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;

public class WarpManagerCommand extends BaseCommand {

	@Command(name = "warpmanager", permission = "pandafly.warpmanager", aliases = {"warpm", "wmanager", "wm"})
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
				player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " create <warpName>"));
				return;
			}

			String warpName = ChatUtil.capitalize(args[1]);

			if (ComboFly.get().getWarpConfig().getString("WARPS." + warpName) != null) {
				player.sendMessage(ChatUtil.translate("&cWarp '" + warpName + "' not found."));
				return;
			}

			ComboFly.get().getWarpManager().createWarp(warpName, player);
			player.sendMessage(ChatUtil.translate("&aWarp '" + warpName + "' has been create."));
		}
		else if (args[0].equalsIgnoreCase("delete")) {

			if (args.length < 2) {
				player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " delete <warpName>"));
				return;
			}
			String warpName = ChatUtil.capitalize(args[1]);

			if (ComboFly.get().getWarpConfig().getString("WARPS." + warpName) == null) {
				player.sendMessage(ChatUtil.translate("&cWarp '" + warpName + "' not found."));
				return;
			}

			ComboFly.get().getWarpManager().deleteWarp(warpName);
			player.sendMessage(ChatUtil.translate("&aWarp '" + warpName + "' has been delete."));
		}
		else if (args[0].equalsIgnoreCase("set")){

			if (args.length < 2) {
				player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " set <warpName>"));
				return;
			}
			String warpName = ChatUtil.capitalize(args[1]);

			if (ComboFly.get().getWarpConfig().getString("WARPS." + warpName) == null) {
				player.sendMessage(ChatUtil.translate("&cWarp '" + warpName + "' not found."));
				return;
			}

			ComboFly.get().getWarpManager().createWarp(warpName, player);
			player.sendMessage(ChatUtil.translate("&aYou have set new Location for Warp '" + warpName + "'."));
		}
		else if (args[0].equalsIgnoreCase("list")) {
			player.sendMessage(ChatUtil.translate("&cAvailables Warps: " + ComboFly.get().getWarpManager().getWarps()));
		}
		else {
			this.getUsage(player, label);
		}
	}

	private void getUsage(Player player, String label) {
		player.sendMessage(ChatUtil.translate("&7&m-------------------------"));
		player.sendMessage(ChatUtil.translate("&4&lWarpManager Commands"));
		player.sendMessage(ChatUtil.translate(""));
		player.sendMessage(ChatUtil.translate("&c/" + label + " create <warpName>"));
		player.sendMessage(ChatUtil.translate("&c/" + label + " delete <warpName>"));
		player.sendMessage(ChatUtil.translate("&c/" + label + " set <warpName>"));
		player.sendMessage(ChatUtil.translate("&c/" + label + " list"));
		player.sendMessage(ChatUtil.translate("&7&m-------------------------"));
	}
}
