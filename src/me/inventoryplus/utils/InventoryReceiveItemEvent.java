package me.inventoryplus.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.inventoryplus.objects.PlayerInv;

@Getter
@Setter
@AllArgsConstructor
public class InventoryReceiveItemEvent extends Listener {

	private Player player;
	private PlayerInv playerInventory;
	private ItemStack currentItem;
	
}
