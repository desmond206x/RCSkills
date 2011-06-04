package com.silthus.rcskills.extras;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.silthus.rcskills.RCSkills;

public class Messaging {
	
	public static void sendMessage(Player player, String message) {
        player.sendMessage(getTag() + message);
    }
	
	public static void sendMessage(CommandSender sender, String message) {
        if (isPlayer(sender)) {
            Player player = (Player) sender;
            player.sendMessage(getTag() + message);
        }
    }
	
	public static void sendMessage(Server server, String message) {
        server.broadcastMessage(getTag() + message);
    }
	
	private static boolean isPlayer(CommandSender sender){
        return sender instanceof Player;
    }
    
    private static String getTag() {
    	String s = null;
    	s = "[" + colorizeText(RCSkills.tag, ChatColor.GREEN) + "] ";
    	return s;
    }
    
    public static String colorizeText(String text, ChatColor color){
        return color + text + ChatColor.WHITE;
    }
    
    

}
