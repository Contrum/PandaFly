package dev.panda.combofly.koth.event;

import dev.panda.combofly.koth.KoTH;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class EnterCuboidEvent extends Event {

    public static final HandlerList handlerList = new HandlerList();
    public Player player;
    public KoTH koth;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
