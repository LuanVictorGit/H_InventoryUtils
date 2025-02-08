package me.inventoryplus.objects.managers;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import me.inventoryplus.Core;
import me.inventoryplus.objects.PlayerInv;

@Getter
public class Manager {

	private List<PlayerInv> players = new ArrayList<>();
	
	public PlayerInv getPlayer(String name) {
		return players.stream().filter(player -> player.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
	
	public static Manager get() {
		return Core.getInstance().getManager();
	}
	
}
