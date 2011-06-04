package com.silthus.rcskills.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import com.silthus.rcskills.RCPermissions;
import com.silthus.rcskills.RCPlayer;
import com.silthus.rcskills.RCSkills;
import com.silthus.rcskills.config.RCConfig;
import com.silthus.rcskills.extras.Messaging;

public class RCPlayerListener extends PlayerListener {
	@SuppressWarnings("unused")
	private final RCSkills plugin;

	private Player player;

	public RCPlayerListener(RCSkills instance) {
		plugin = instance;
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		player = event.getPlayer();
		RCPlayer p = new RCPlayer(player);
		// exchanges all items of itemID and then tries to lvlup the player
		// TODO: make configurable in config
		if (p.checkForItems()) {
			p.checkLevelUP();
		}
		if (RCConfig.useDailyExp == true) {
			// need to check for vip permissions first
			if (!p.hasJoinedToday()
					&& RCPermissions.permission(player, "lvlup.exp.vip")) {
				// adds the daily ration vip exp
				p.addExp(RCConfig.vipExp);
				Messaging.sendMessage(player,
						"Du hast soeben deine tägliche Ration von "
								+ ChatColor.YELLOW + RCConfig.vipExp
								+ ChatColor.WHITE + " EXP bekommen.");
				p.setJoinedToday();
				// checks for lvlup after the player got exp
				p.checkLevelUP();
			} else if (!p.hasJoinedToday()
					&& RCPermissions.permission(player, "lvlup.exp.normal")) {
				// adds the daily ration exp
				p.addExp(RCConfig.normalExp);
				Messaging.sendMessage(player,
						"Du hast soeben deine tägliche Ration von "
								+ ChatColor.YELLOW + RCConfig.normalExp
								+ ChatColor.WHITE + " EXP bekommen.");
				p.setJoinedToday();
				// checks for lvlup after the player got exp
				p.checkLevelUP();
			}
			// save all changes
			p.writeDatabase();
		}
	}
}
