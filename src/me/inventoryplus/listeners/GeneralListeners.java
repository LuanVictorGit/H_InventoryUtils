package me.inventoryplus.listeners;

import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.inventoryplus.Core;
import me.inventoryplus.objects.PlayerInv;
import me.inventoryplus.utils.InventoryReceiveItemEvent;
import me.inventoryplus.utils.InventoryUtils;

public class GeneralListeners implements Listener {

	private boolean stack_all;
	private int maxStack;
	
	public GeneralListeners() {
		FileConfiguration config = Core.getInstance().getConfig();
		ConfigurationSection section = config.getConfigurationSection("Config");
		maxStack = section.getInt("maxStack");
		stack_all = section.getBoolean("stack_all");
		if (maxStack > 255) maxStack = 255;
		else if (maxStack < 64) maxStack = 64;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryUpdate(InventoryReceiveItemEvent e) {
        Player p = e.getPlayer();
        PlayerInv pi = e.getPlayerInventory();
        ItemStack item = e.getCurrentItem();
        if ((item.getMaxStackSize() == 1 && !stack_all) || p.getGameMode() == GameMode.CREATIVE || e.isCancelled()) return;
        Inventory inv = p.getInventory();
        ItemStack[] content = inv.getContents();
        int amountBase = InventoryUtils.getAmount(p, item);
        int amount = 0;
        for (ItemStack it : content) {
        	if (it == null || !it.isSimilar(item)) continue;
        	if (amount >= amountBase) {
        		inv.remove(it);
        		continue;
        	}
        	amount += it.getAmount();
        	while (it.getAmount() < maxStack && amount < amountBase) {
        		it.setAmount(it.getAmount() + 1);
        		amount++;
        	}
        	while(amount > amountBase) {
        		it.setAmount(it.getAmount()-1);
        		amount--;
        	}
        }
        p.updateInventory();
        pi.setLastContent(content.clone());
    }
	
}
