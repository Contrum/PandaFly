package dev.panda.combofly.utilities.scoreboard.listener;

import dev.panda.combofly.utilities.scoreboard.Assemble;
import dev.panda.combofly.utilities.scoreboard.AssembleBoard;
import dev.panda.combofly.utilities.scoreboard.events.AssembleBoardCreateEvent;
import dev.panda.combofly.utilities.scoreboard.events.AssembleBoardDestroyEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AssembleListener implements Listener {

	private final Assemble assemble;

	public AssembleListener(final Assemble assemble) {
		this.assemble = assemble;
	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final AssembleBoardCreateEvent createEvent = new AssembleBoardCreateEvent(event.getPlayer());
		Bukkit.getPluginManager().callEvent(createEvent);

		if (createEvent.isCancelled()) {
			return;
		}

		this.getAssemble().getBoards().put(event.getPlayer().getUniqueId(),
				new AssembleBoard(event.getPlayer(), this.getAssemble()));
	}

	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event) {
		final AssembleBoardDestroyEvent destroyEvent = new AssembleBoardDestroyEvent(event.getPlayer());
		Bukkit.getPluginManager().callEvent(destroyEvent);

		if (destroyEvent.isCancelled()) {
			return;
		}

		this.getAssemble().getBoards().remove(event.getPlayer().getUniqueId());
		event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}

	public Assemble getAssemble() {
		return this.assemble;
	}
}
