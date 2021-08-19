package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MessageCommand extends BaseCommand {

	@Getter private static MessageCommand instance;
	public Map<Player, Player> lastMessage;

	public MessageCommand() {
		super();
		instance = this;
		lastMessage = new HashMap<>();
	}

	@Command(name = "message", aliases = {"msg", "tell", "m"}, permission = "pandafly.message")
	@Override
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();
		String label = command.getLabel();
		
		if (args.length < 2) {
			player.sendMessage(CC.translate("&cUsage: /" + label + " <player> <message>"));
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);

		if (target == null) {
			player.sendMessage(CC.getPlayerNotFound(args[0]));
			return;
		}

		StringBuilder message = new StringBuilder();

		for (int i = 1; i != args.length; i++) {
			message.append(args[i]).append(" ");
		}

		String playerName = ComboFly.get().getRankManager().getRank().getPrefix(player) + player.getName();
		String targetName = ComboFly.get().getRankManager().getRank().getPrefix(target) + target.getName();

		lastMessage.put(player, target);
		lastMessage.put(target, player);

		target.playSound(target.getLocation(), org.bukkit.Sound.ORB_PICKUP, 1F, 1F);

		player.sendMessage(CC.translate("&7(To " + targetName + "&7) " + message));
		target.sendMessage(CC.translate("&7(From " + playerName + "&7) " + message));
	}
}
