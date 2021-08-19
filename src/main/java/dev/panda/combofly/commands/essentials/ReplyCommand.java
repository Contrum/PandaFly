package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class ReplyCommand extends BaseCommand {

	@Command(name = "reply", aliases = "r", permission = "pandafly.reply")
	@Override
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();
		String label = command.getLabel();
		
		if (args.length < 1) {
			player.sendMessage(CC.translate("&cUsage: /" + label + " <message>"));
			return;
		}

		Player target = MessageCommand.getInstance().lastMessage.get(player);

		if (target == null) {
			player.sendMessage(CC.translate("&cNothing to reply."));
			return;
		}

		StringBuilder message = new StringBuilder();

		for (int i = 0; i != args.length; i++) {
			message.append(args[i]).append(" ");
		}

		String playerName = ComboFly.get().getRankManager().getRank().getPrefix(player) + player.getName();
		String targetName = ComboFly.get().getRankManager().getRank().getPrefix(target) + target.getName();

		MessageCommand.getInstance().lastMessage.put(player, target);
		MessageCommand.getInstance().lastMessage.put(target, player);

		target.playSound(target.getLocation(), org.bukkit.Sound.ORB_PICKUP, 1F, 1F);

		player.sendMessage(CC.translate("&7(To " + targetName + "&7) " + message));
		target.sendMessage(CC.translate("&7(From " + playerName + "&7) " + message));
	}
}
