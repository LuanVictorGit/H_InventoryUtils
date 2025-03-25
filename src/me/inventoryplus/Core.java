package me.inventoryplus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import lombok.Getter;
import me.inventoryplus.listeners.GeneralListeners;
import me.inventoryplus.objects.PlayerInv;
import me.inventoryplus.objects.managers.Manager;

@Getter
public class Core extends JavaPlugin {

	@Getter private static Core instance;
	private String tag, version = "§d"+getDescription().getVersion();
	private Manager manager;
	private BukkitTask task;
	
	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		reloadPlugin();
		
		List<Listener> listeners = new ArrayList<>();
		listeners.add(new Listener() {
			
			@EventHandler(priority = EventPriority.MONITOR)
			public void onJoin(PlayerJoinEvent e) {
				PlayerInv.check(e.getPlayer());
			}
			
			@EventHandler(priority = EventPriority.MONITOR)
			public void onQuit(PlayerQuitEvent e) {
				PlayerInv.check(e.getPlayer()).remove();
			}
			
		});
		listeners.add(new GeneralListeners());
		listeners.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
		
		sendConsole(" ");
		sendConsole(tag + " &aH_InventoryPlus iniciado com sucesso! &6[Author lHawk_] " + version);
		sendConsole(" ");
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
		if (task != null) task.cancel();
		sendConsole(" ");
		sendConsole(tag + " &cH_InventoryPlus desligado com sucesso! &6[Author lHawk_] " + version);
		sendConsole(" ");
	}
	
	public void reloadPlugin() {
		reloadConfig();
		tag = getConfig().getString("Config.tag");
		
		manager = new Manager();
		if (task != null) task.cancel();
		task = new TaskChecker().runTaskTimerAsynchronously(this, 0, 1);
	}
	
	private void sendConsole(String msg) {Bukkit.getConsoleSender().sendMessage(msg.replace("&", "§"));}
	
}
