package com.redsponge.random.realism;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.redsponge.random.realism.event.player.OnPlayerBreakBlock;

public class RealismCore extends JavaPlugin {
	
	private PluginManager pm;
	private FileConfiguration config;
	
	public void onEnable() {
		registerConfig();
		registerEvents();
	}
	
	public void registerEvents() {
		pm = getServer().getPluginManager();
		pm.registerEvents(new OnPlayerBreakBlock(this), this);
	}
	
	public void registerConfig() {
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
	}
	
}
