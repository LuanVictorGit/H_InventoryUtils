package me.inventoryplus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.inventoryplus.objects.PlayerInv;
import me.inventoryplus.utils.InventoryReceiveItemEvent;
import me.inventoryplus.utils.InventoryUtils;

public class TaskChecker extends BukkitRunnable {
	
	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerInv pi = PlayerInv.check(p);
            ItemStack[] lastContent = pi.getLastContent().clone();
            ItemStack[] currentContent = p.getInventory().getContents().clone();
            pi.setLastContent(currentContent);
            ItemStack currentItem = InventoryUtils.isSimilar(lastContent, currentContent);
            if (currentItem == null || currentItem.getType() == Material.AIR) continue;
            InventoryReceiveItemEvent event = new InventoryReceiveItemEvent(p, pi, currentItem.clone());
            Bukkit.getScheduler().runTask(Core.getInstance(), ()-> Bukkit.getPluginManager().callEvent(event));
        }
	}

}
