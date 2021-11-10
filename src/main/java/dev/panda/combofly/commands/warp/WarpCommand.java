package dev.panda.combofly.commands.warp;

import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;

public class WarpCommand extends BaseCommand {

	@Command(name = "warp", permission = "pandafly.warp")
	@Override
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();
		String label = command.getLabel();

		if (args.length < 1) {
			player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <warp>"));
			player.sendMessage(ChatUtil.translate("&cAvailables Warps: " + ComboFly.get().getWarpManager().getWarps()));
			return;
		}

		String warpName = ChatUtil.capitalize(args[0]);

		if (ComboFly.get().getWarpConfig().getString("WARPS." + warpName) == null) {
			player.sendMessage(ChatUtil.translate("&cWarp '" + warpName + "' not found."));
			return;
		}

		if (ComboFly.get().getCombatManager().hasCooldown(player)) {
			player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("WARP.COMBAT")));
			return;
		}

		for (String warp : ComboFly.get().getWarpConfig().getConfiguration().getConfigurationSection("WARPS").getKeys(false)) {

			if (warpName.equals(warp)) {
				if (!player.hasPermission("pandafly.warp." + warpName.toLowerCase())) {
					player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("WARP.PERMISSION-WARP")));
					return;
				}

				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10F, 0.2F);
				player.teleport(ComboFly.get().getWarpManager().getWarpLocation(warp));
				player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("WARP.TELEPORTED")
						.replace("{warp}", warpName)));
			}
		}
	}
}
