package io.github.spaicygaming.fakeddos;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class FakeDDoS extends JavaPlugin implements Listener {

	public String projectlink= "http://bit.ly/FakeDDoS";
	private static FakeDDoS instance;
	boolean checkupdates = getConfig().getBoolean("CheckForUpdates");
	private Object[] updates;
	
	public void onEnable(){
		instance = this;
		ConsoleCommandSender console = getServer().getConsoleSender();
		
	    console.sendMessage(ChatColor.GREEN + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
	    console.sendMessage(ChatColor.AQUA + "           Fake" + ChatColor.RED + "DDoS");
	    console.sendMessage(ChatColor.AQUA + "          Version " + getDescription().getVersion());
	    console.sendMessage(ChatColor.GOLD + " Project: " + ChatColor.RED + ChatColor.ITALIC + projectlink);
	    console.sendMessage(ChatColor.GREEN + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
	    
	    saveDefaultConfig();
	    
	    getCommand("ddos").setExecutor(new FakeDDosCmds());
	    getCommand("fakeddos").setExecutor(new FakeDDosCmds());
	    
	    if (!getConfig().getString("ConfigVersion").equals("1.3")) {
	        console.sendMessage("[FakeDDoS] " + ChatColor.RED + "OUTDATED CONFIG FILE DETECTED, PLEASE DELETE THE OLD ONE!");
	    }
	    
	    //Check for Updates
		if (checkupdates){
			updates = UpdateChecker.getLastUpdate();
			getLogger().info("Checking for updates...");
			if (updates.length == 2){
				console.sendMessage(ChatColor.GREEN + (Separatori(70)));
				getLogger().info("Update found! Download here: " + "https://www.spigotmc.org/resources/fakeddos.40583/");
				getLogger().info("New version: " + updates[0]);
				getLogger().info("Current Version: " + getDescription().getVersion());
				getLogger().info("What's new: " + updates[1]);
				console.sendMessage(ChatColor.GREEN + (Separatori(70)));
			} else {
				getLogger().info("No new version available." );
			}
		}
	}	
	
	public void onDisable(){
		getLogger().info("FakeDDoS has been disabled");
	}
	
	public static FakeDDoS getInstance(){
		return instance;
	}
	
	private String Separatori(int value){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value; i++){
			sb.append("=");
		}
		return sb.toString();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		
		if (checkupdates && (p.isOp() || p.hasPermission("*") || p.hasPermission("fakeddos.notify"))){
			if(updates.length == 2){
				p.sendMessage(ChatColor.GREEN + Separatori(31));
				p.sendMessage("§6§l[§3Fake§cDDoS§6] New update available:");
				p.sendMessage("§6Current version: §e" + getDescription().getVersion());
				p.sendMessage("§6New version: §e" + updates[0]);
				p.sendMessage("§6What's new: §e" + updates[1]);
				p.sendMessage(ChatColor.GREEN + Separatori(31));
			}
		}
	}
	
	
}
