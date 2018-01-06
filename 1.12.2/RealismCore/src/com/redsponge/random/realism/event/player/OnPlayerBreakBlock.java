package com.redsponge.random.realism.event.player;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.redsponge.random.realism.RealismCore;
import com.redsponge.random.realism.config.ConfigManager;

public class OnPlayerBreakBlock implements Listener {
	
	private RealismCore plugin;
	private FileConfiguration config;
	
	public OnPlayerBreakBlock(RealismCore plugin) {
		this.plugin = plugin;
		this.config = this.plugin.getConfig();
	}
	
	@EventHandler
	public void playerBreakBlock(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
			bareHandPunch(p, e.getBlock());
		}
	}
	
	private void bareHandPunch(Player p, Block b) {
		List<String> sharpBlocks = config.getStringList(ConfigManager.BLOCK_BREAK_BARE_HAND_SHARP_BLOCKS_BLOCKLIST);
		if(sharpBlocks.contains(b.getType().toString())) {
			p.damage(config.getDouble(ConfigManager.BLOCK_BREAK_BARE_HAND_SHARP_BLOCKS_DAMAGE));
		}
		
	}
}
