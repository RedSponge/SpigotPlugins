package com.redsponge.random.bedcolor.events.player;

import org.bukkit.Material;
import org.bukkit.block.Bed;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.redsponge.random.bedcolor.BedColor;
import com.redsponge.random.bedcolor.BedColor.ConfigPaths;
import com.redsponge.random.bedcolor.BedColorMethods;

public class BlockPlace implements Listener{
	
	FileConfiguration config;
	BedColor plugin;
	
	public BlockPlace(BedColor instance) {
		plugin = instance;
		config = plugin.getConfig();
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		if(block.getType() != Material.BED_BLOCK) return;
		
		Bed bed = (Bed) block.getState();
		if(!config.getBoolean(ConfigPaths.SHOW_MESSAGE_BED_PLACE.getPath())) return;
		player.sendMessage(BedColorMethods.setMessagePrefixes(config.getString(ConfigPaths.MESSAGE_BED_PLACE.getPath()), player, block, bed.getColor()));
	}
	
}
