package dev.panda.combofly.utilities.tablist.v1_7;

import dev.panda.combofly.utilities.tablist.v1_7.adapter.TabAdapter;
import dev.panda.combofly.utilities.tablist.v1_7.listener.TabListener;
import dev.panda.combofly.utilities.tablist.v1_7.packet.TabPacket;
import dev.panda.combofly.utilities.tablist.v1_7.runnable.TabRunnable;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Tablist_v1_7 {

	@Getter
	private static Tablist_v1_7 instance;
	
	private final TabAdapter adapter;
	
	public Tablist_v1_7(TabAdapter adapter, JavaPlugin plugin) {
		instance = this;
		this.adapter = adapter;
		
		new TabPacket(plugin);
		
		Bukkit.getServer().getPluginManager().registerEvents(new TabListener(this), plugin);
		Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new TabRunnable(adapter), 60L, 20L); //TODO: async to run 1 millis
	}
}