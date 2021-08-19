package dev.panda.combofly.commands.spawn;

import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import dev.panda.combofly.ComboFly;

public class SpawnCommand extends BaseCommand {

	@Command(name = "spawn", permission = "pandafly.spawn")
	@Override
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();

		if (ComboFly.get().getCombatManager().hasCooldown(player)) {
			player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("SPAWN.COMBAT")));
			return;
		}

		if (ComboFly.get().getSpawnManager().hasCooldown(player)) {
			player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("SPAWN.COMBAT")));
			return;
		}

		if (player.hasPermission("pandafly.spawn.bypass")) {
			player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10F, 0.2F);
			player.teleport(ComboFly.get().getSpawnManager().getSpawnLocation());
			player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("SPAWN.TELEPORTED")));
			return;
		}

		ComboFly.get().getSpawnManager().setCooldown(player);
		ComboFly.get().getSpawnManager().setRunnable(player);
	}
}

