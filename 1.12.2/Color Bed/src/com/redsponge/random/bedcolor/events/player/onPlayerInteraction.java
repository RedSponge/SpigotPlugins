package com.redsponge.random.bedcolor.events.player;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Bed;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.redsponge.random.bedcolor.BedColor;
import com.redsponge.random.bedcolor.BedColor.ConfigPaths;
import com.redsponge.random.bedcolor.BedColorMethods;


public class onPlayerInteraction implements Listener {

	BedColor plugin;
	FileConfiguration config;
	
	public onPlayerInteraction(BedColor instance) {
		plugin = instance;
		config = plugin.getConfig();
	}
	
	
	@EventHandler
	public void onPlayerInteractionEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		ItemStack rightItem = player.getInventory().getItemInMainHand();
		ItemStack leftItem = player.getInventory().getItemInOffHand();
		DyeColor color = null;
		String actionType = config.getString(ConfigPaths.COLOR_METHOD_METHOD.getPath());
		
		if(actionType.equals("right")) {
			if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		}
		else if(actionType.equals("left")) {
			if(event.getAction() != Action.LEFT_CLICK_BLOCK) return;
		}
		else if(actionType.equals("both")) {
			if(!(event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		}
		else {
			plugin.getLogger().info(ChatColor.RED.toString() + "Please check your config, you stated in bedcolors.colorMethod something else than left, right or both. please check and fix your config");
			return;
		}
		
		if(block.getType() != Material.BED_BLOCK) return;
		if(!config.getBoolean(ConfigPaths.COLOR_ENABLE.getPath())) return;
		
		Bed bed = (Bed) block.getState();
		
		if(rightItem.getType() == Material.INK_SACK) {
			color = BedColorMethods.getColorByItem(rightItem);
			if(bed.getColor() == color) {
				return;
			}
			if(BedColorMethods.playerCanLoseDye(player)) {
				rightItem.setAmount(rightItem.getAmount() - 1);
			}
		}
		else if(leftItem.getType() == Material.INK_SACK) {
			color = BedColorMethods.getColorByItem(leftItem);
			if(bed.getColor() == color) {
				return;
			}
			if(BedColorMethods.playerCanLoseDye(player)) {
				leftItem.setAmount(leftItem.getAmount() - 1);
			}
		}
		else if(config.getBoolean(ConfigPaths.SET_SPAWN_ENABLE.getPath()) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(player.getWorld().getTime() > 13000) return;
			event.setCancelled(true);
			player.setBedSpawnLocation(bed.getLocation(), true);
			player.sendMessage(BedColorMethods.setMessagePrefixes(config.getString(ConfigPaths.SET_SPAWN_MESSAGE.getPath()), player, bed.getLocation().getBlock(), DyeColor.YELLOW));
			return;
		}
		else {
			return;
		}
		event.setCancelled(true);
		BedColorMethods.colorBed(bed, color, player);
		
		if(config.getBoolean(ConfigPaths.SHOW_MESSAGE_COLOR.getPath())) {
			String msg = BedColorMethods.setMessagePrefixes(config.getString(ConfigPaths.MESSAGE_COLOR.getPath()), player, block, color);
			player.sendMessage(msg);
		}
	}
	
}
