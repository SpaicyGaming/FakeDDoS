package io.github.spaicygaming.fakeddos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class FakeDDosCmds implements CommandExecutor{

	private FakeDDoS main = FakeDDoS.getInstance();
	private String prefix = ChatColor.DARK_GRAY + " [" + ChatColor.AQUA + "Fake" + ChatColor.RED + "DDoS" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET;
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		
		/*
		 * DDoS command
		 */
		if (alias.equalsIgnoreCase("ddos")) {
			/*
			 * Argg length 0
			 */
			if (args.length == 0) {
				printMenu(sender);
			}
			
			/*
			 * Args length 1
			 */
			else if (args.length == 1){
				//Return if sender is not a player
				if (!(sender instanceof Player)) {
					sender.sendMessage(cp("OnlyPlayerCmd"));
					return false;
				}
				Player p = (Player)sender;
				//Return if the player doesn't have the permission
				if (!p.hasPermission("fakeddos.use")) {
					p.sendMessage(cp("NoPerms"));
					return false;
				}
				
				
				Player target = Bukkit.getServer().getPlayer(args[0]);
				// Target null (Offline)
				if (target == null) {
					p.sendMessage(cp("TargetOffline", "{target}", args[0]));
					return false;
				}
				
				if (target == p) {
					p.sendMessage(cp("AutoDDoSMessage"));
					return false;
				}		
				
				p.sendMessage(cp("SentDDoSMessage", "{target}", target.getName()));
				
				IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\":\"" + c("UnderAttacTitle") + "\"}");
				IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\":\"" + c("UnderAttacSubtitle").replace("{sender}", p.getName()) + "\"}");
				PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
				PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
				
				CraftPlayer craftplayer = (CraftPlayer) target;
				PlayerConnection connection = craftplayer.getHandle().playerConnection;
				connection.sendPacket(title);
				connection.sendPacket(subtitle);									
				
				// Effects and Sounds

				//Target
				if (main.getConfig().getBoolean("Effects.Target.Sounds")) {										
					target.getWorld().playSound(target.getLocation(), Sound.GHAST_SCREAM, 1, 1);										
				}
				if (main.getConfig().getBoolean("Effects.Target.Particles")) {
					target.getWorld().playEffect(target.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
				}
				
				//Sender
				if (main.getConfig().getBoolean("Effects.Sender.Sounds")) {
					p.getWorld().playSound(p.getLocation(), Sound.BLAZE_HIT, 1, 1);	
				}
				if (main.getConfig().getBoolean("Effects.Sender.Particles")) {
					p.getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
				}
			}
			
			/*
			 * Args != (0 && 1) 
			 */
			else{
				printMenu(sender);
			}
		}		
		
		/*
		 * FakeDDos command
		 */
		if (alias.equalsIgnoreCase("fakeddos")){
			if (args.length == 0){
				printMenu(sender);
			}
			else if (args.length == 1){
				/*
				 * Reload
				 */
				if (args[0].equalsIgnoreCase("reload")){
					if (!sender.hasPermission("fakeddos.reload")){
						sender.sendMessage(cp("NoPerms"));
						return false;
					}
					main.reloadConfig();
					sender.sendMessage(prefix + ChatColor.RED + "Config Reloaded.");
				}
				/*
				 * Info
				 */
				else if (args[0].equalsIgnoreCase("info")){
					sender.sendMessage(ChatColor.DARK_GREEN + "    --=-=-=-=-=-=-=-=-=--");
					sender.sendMessage(ChatColor.AQUA + "         Fake" + ChatColor.RED + "DDoS " + ChatColor.GRAY + "v" + main.getDescription().getVersion());
					sender.sendMessage(ChatColor.GOLD + "    Project: " + ChatColor.DARK_RED + ChatColor.ITALIC + main.projectlink);
					sender.sendMessage(ChatColor.DARK_GREEN + "      --=-=-=-=-=-=-=--");
					sender.sendMessage("");					
				}
				else {
					printMenu(sender);
				}
			}
			else{
				printMenu(sender);
			}
		}
	return false;
	}
	
	// Color
	private String c(String str){
		return ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(str));
	}
	// Color + Prefix
	private String cp(String str){
		return prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(str));
	}
	// Color + Prefix + Replace
	private String cp(String str, String var, String replacement){
		return prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(str).replace(var, replacement));
	}

	private void printMenu(CommandSender sender){
		sender.sendMessage("");
		sender.sendMessage(ChatColor.RED + "     --=-=" + ChatColor.GOLD  + " FakeDDoS " + ChatColor.GRAY + "v" + main.getDescription().getVersion() + ChatColor.RED + " =-=--");
		sender.sendMessage(ChatColor.AQUA + "   /ddos <player> " + ChatColor.GREEN + "->" + ChatColor.GRAY + " FakeDDoS a player.");
		sender.sendMessage(ChatColor.AQUA + "   /fakeddos info " + ChatColor.GREEN + "->" + ChatColor.GRAY + " Shows Info.");
		sender.sendMessage(ChatColor.AQUA + "   /fakeddos reload " + ChatColor.GREEN + "->" + ChatColor.GRAY + " Reloads the Config.");
		sender.sendMessage(ChatColor.RED + "          --=-=-=-=-=-=--");
		sender.sendMessage("");
	}

}
