package me.inventoryplus.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import lombok.Getter;
import lombok.Setter;
import me.inventoryplus.Core;
import me.inventoryplus.objects.managers.Manager;

@Getter
@Setter
public class PlayerInv {

	private String name;
	private Player player;
	private ItemStack[] lastContent;
	
	public PlayerInv(String name) {
		this.name = name;
		this.lastContent = getPlayer().getInventory().getContents().clone();
		Manager.get().getPlayers().add(this);
	}
	
	public Player getPlayer() {
		if (player == null) player = Bukkit.getPlayerExact(name);
		return player;
	}
	
	public void remove() {
		Manager.get().getPlayers().remove(this);
	}
	
	// getting playerInv object from player metadata.
	public static PlayerInv check(Player p) {
		try {
			if (p.hasMetadata("playerinv")) return (PlayerInv) p.getMetadata("playerinv").get(0).value();
			PlayerInv pi = Manager.get().getPlayer(p.getName());
			if (pi == null) pi = new PlayerInv(p.getName());
			p.setMetadata("playerinv", new FixedMetadataValue(Core.getInstance(), pi));
			return pi;
		} catch (Exception e) {
			if (p != null) p.removeMetadata("playerinv", Core.getInstance());
			return check(p);
		}
	}
	
}
