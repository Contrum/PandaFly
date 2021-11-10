package dev.panda.combofly.listeners;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.profile.Profile;
import dev.panda.lib.chat.ChatUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	
	public ChatListener() {
		Bukkit.getServer().getPluginManager().registerEvents(this, ComboFly.get());
	}
	
	@EventHandler
	private void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			message = PlaceholderAPI.setPlaceholders(player, message);
		}

		if (ComboFly.get().getFreezeManager().isFrozen(player)) {
			event.setCancelled(true);
			player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("CHAT.FROZEN")
					.replace("{player}", player.getName())
					.replace("{message}", message)));
			ComboFly.get().getStaffManager().sendMessageAllStaffs(ComboFly.get().getMessageConfig().getString("CHAT.FROZEN")
					.replace("{player}", player.getName())
					.replace("{message}", message));
			return;
		}

		if (ComboFly.get().getMessageConfig().getBoolean("CHAT.TOGGLE")) {
			String chat = ComboFly.get().getMessageConfig().getString("CHAT.FORMAT")
					.replace("{player}", player.getName())
					.replace("{prefix}", ComboFly.get().getRankManager().getRank().getPrefix(player.getUniqueId()))
					.replace("{suffix}", ComboFly.get().getRankManager().getRank().getSuffix(player.getUniqueId()))
					.replace("{kills}", String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getKd().getKills()))
					.replace("{message}", (player.hasPermission("pandafly.chat.color") ? (ChatUtil.translate(message
							.replace("%", "%%"))) : (message
							.replace("%", "%%"))));
			event.setFormat(ChatUtil.translate(chat));
		}
	}
}
