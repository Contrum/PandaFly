package dev.panda.combofly.utilities.tablist.v1_7.packet;

import dev.panda.combofly.utilities.tablist.v1_7.util.Reflection;
import dev.panda.combofly.utilities.tablist.v1_7.util.TinyProtocol;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardTeam;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TabPacket extends TinyProtocol {

	public TabPacket(Plugin plugin) {
		super(plugin);
	}

	@Override
	public Object onPacketOutAsync(Player reciever, Channel channel, Object object) {
		if(object instanceof PacketPlayOutScoreboardTeam) {
			PacketPlayOutScoreboardTeam scoreboardTeam = (PacketPlayOutScoreboardTeam) object;
			
			int mode = Reflection.getField(scoreboardTeam.getClass(), "f", int.class).get(scoreboardTeam);
			String teamName = Reflection.getField(scoreboardTeam.getClass(), "a", String.class).get(scoreboardTeam);
			
			if(mode == 4 && !teamName.equals("tab")) {
				Reflection.getField(scoreboardTeam.getClass(), "a", String.class).set(scoreboardTeam, "tab");
				Reflection.getField(scoreboardTeam.getClass(), "b", String.class).set(scoreboardTeam, "tab");
				
				Reflection.getField(scoreboardTeam.getClass(), "f", int.class).set(scoreboardTeam, 3);
			}
		}
		
		return super.onPacketOutAsync(reciever, channel, object);
	}
}
