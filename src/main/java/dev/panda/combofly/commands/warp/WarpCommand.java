package dev.panda.combofly.commands.warp;

import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;

public class WarpCommand extends BaseCommand {

	@Command(name = "warp", permission = "pandafly.warp")
	@Override
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();
		String label = command.getLabel();

		if (args.length < 1) {
			player.sendMessage(CC.translate("&cUsage: /" + label + " <warp>"));
			player.sendMessage(CC.translate("&cAvailables Warps: " + ComboFly.get().getWarpManager().getWarps()));
			return;
		}

		String warpName = CC.capitalize(args[0]);

		if (ComboFly.get().getWarpConfig().getString("WARPS." + warpName) == null) {
			player.sendMessage(CC.getWarpNotFound(warpName));
			return;
		}

		if (ComboFly.get().getCombatManager().hasCooldown(player)) {
			player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("WARP.COMBAT")));
			return;
		}

		for (String warp : ComboFly.get().getWarpConfig().getConfiguration().getConfigurationSection("WARPS").getKeys(false)) {

			if (warpName.equals(warp)) {
				if (!player.hasPermission("pandafly.warp." + warpName.toLowerCase())) {
					player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("WARP.PERMISSION-WARP")));
					return;
				}

				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10F, 0.2F);
				player.teleport(ComboFly.get().getWarpManager().getWarpLocation(warp));
				player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("WARP.TELEPORTED")
						.replace("{warp}", warpName)));
			}
		}
	}
}
