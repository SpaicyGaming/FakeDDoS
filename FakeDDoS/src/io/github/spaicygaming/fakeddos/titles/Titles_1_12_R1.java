package io.github.spaicygaming.fakeddos.titles;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.github.spaicygaming.fakeddos.FakeDDoS;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

public class Titles_1_12_R1 implements Titles{

	@Override
	public void sendTitles(Player sender, Player target) {
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\":\"" + FakeDDoS.c("UnderAttacTitle") + "\"}");
		IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\":\"" + FakeDDoS.c("UnderAttacSubtitle").replace("{sender}", sender.getName()) + "\"}");
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
		
		CraftPlayer craftplayer = (CraftPlayer) target;
		PlayerConnection connection = craftplayer.getHandle().playerConnection;
		connection.sendPacket(title);
		connection.sendPacket(subtitle);
		
	}

	
}
