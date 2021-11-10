package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.entity.Player;

public class ReplyCommand extends BaseCommand {

	@Command(name = "reply", aliases = "r", permission = "pandafly.reply")
	@Override
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();
		String label = command.getLabel();
		
		if (args.length < 1) {
			player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <message>"));
			return;
		}

		Player target = MessageCommand.getInstance().lastMessage.get(player);

		if (target == null) {
			player.sendMessage(ChatUtil.translate("&cNothing to reply."));
			return;
		}

		StringBuilder message = new StringBuilder();

		for (int i = 0; i != args.length; i++) {
			message.append(args[i]).append(" ");
		}

		String playerName = ComboFly.get().getRankManager().getRank().getPrefix(player.getUniqueId()) + player.getName();
		String targetName = ComboFly.get().getRankManager().getRank().getPrefix(target.getUniqueId()) + target.getName();

		MessageCommand.getInstance().lastMessage.put(player, target);
		MessageCommand.getInstance().lastMessage.put(target, player);

		target.playSound(target.getLocation(), org.bukkit.Sound.ORB_PICKUP, 1F, 1F);

		player.sendMessage(ChatUtil.translate("&7(To " + targetName + "&7) " + message));
		target.sendMessage(ChatUtil.translate("&7(From " + playerName + "&7) " + message));
	}
}
