package dev.panda.combofly.utilities.tablist.v1_7.listener;

import dev.panda.combofly.utilities.tablist.v1_7.Tablist_v1_7;
import dev.panda.combofly.utilities.tablist.v1_7.layout.TabLayout;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class TabListener implements Listener {

	private final Tablist_v1_7 tablist;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		TabLayout layout = new TabLayout(tablist, player);
		layout.create();
		layout.setHeaderAndFooter();
		
		TabLayout.getLayoutMapping().put(player.getUniqueId(), layout);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		TabLayout.getLayoutMapping().remove(player.getUniqueId());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		TabLayout.getLayoutMapping().remove(player.getUniqueId());
	}
}
