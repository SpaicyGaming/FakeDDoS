package io.github.spaicygaming.fakeddos;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class FakeDDoS extends JavaPlugin implements Listener {

	public String projectlink= "http://bit.ly/FakeDDoS";
	private static FakeDDoS instance;
	
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
	}	
	
	public void onDisable(){
		getLogger().info("FakeDDoS has been disabled");
	}
	
	public static FakeDDoS getInstance(){
		return instance;
	}

	
}
