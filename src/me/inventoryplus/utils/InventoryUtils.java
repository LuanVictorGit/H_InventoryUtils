package me.inventoryplus.utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

	public static ItemStack isSimilar(ItemStack[] content, ItemStack[] content2) {
		if (content == content2) return null;
		if (content == null || content2 == null || content.length != content2.length) return null;
		for (int i = 0; i < content.length; i++) {
			ItemStack item = content[i];
			ItemStack item2 = content2[i];
			if (item != null && item2 != null) {
				if (!item.isSimilar(item2) || item.getAmount() != item2.getAmount()) return item;
			}
			if (item == null && item2 != null) return item2;
			if (item != null && item2 == null) return item;
		}
		return null;
	}
	
	public static int getAmount(Player p, ItemStack it) {
		if (it == null) return 0;
		int amount = 0;
		Inventory inv = p.getInventory();
		for (ItemStack item : inv.getContents()) {
			if (item == null || item.getType() == Material.AIR || !item.isSimilar(it)) continue;
			amount += item.getAmount();
		}
		return amount;
	}

}
