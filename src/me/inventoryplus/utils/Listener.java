package me.inventoryplus.utils;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Listener extends Event {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
}
