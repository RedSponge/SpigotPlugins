package com.redsponge.random.bedcolor;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.redsponge.random.bedcolor.commands.BedColorCommand;
import com.redsponge.random.bedcolor.events.player.BlockPlace;
import com.redsponge.random.bedcolor.events.player.onPlayerInteraction;

public class BedColor extends JavaPlugin{
	
	PluginManager pm = getServer().getPluginManager();
	FileConfiguration config;
	
	public static final String version = "1.0.2";
	public static final String name = "BedColor";
	public static final String author = "RedSponge";
	
	@Override
	public void onEnable() {
		registerEvents();
		setConfig();
		registerCommands();
		BedColorMethods.load(this);
		getLogger().info("The %name plugin by %author in version %version was enabled!".replaceAll("%version", version).replaceAll("%name", name).replaceAll("%author", author));
		getLogger().info(ConfigPaths.MAIN.getPath() + " " + ConfigPaths.COLOR_ENABLE.getPath());
	}
	
	@Override
	public void onDisable() {
		getLogger().info("The BedColor plugin was disabled!");
	}
	
	public void registerEvents() {
		pm.registerEvents(new onPlayerInteraction(this), this);
		pm.registerEvents(new BlockPlace(this), this);
	}
	
	public void registerCommands() {
		getCommand("bedcolor").setExecutor(new BedColorCommand(this));
	}
	
	public void setConfig() {
		config = getConfig();
		config.options().copyDefaults(true);
		config.getDefaults();
		saveConfig();
	}
	
	public enum ConfigPaths {
		MAIN(null, "bedcolors", false),
		MECHANICS(MAIN, "mechanics"),
		COLOR_BED_MECHANIC(MECHANICS, "colorBed"),
		COLOR_ENABLE(COLOR_BED_MECHANIC, "enable"),
		COLOR_FULL(COLOR_BED_MECHANIC, "colorFullBed"),
		
		COLOR_METHOD(COLOR_BED_MECHANIC, "colorMethod"),
		COLOR_METHOD_METHOD(COLOR_METHOD, "method"),
		
		LOSE_DYE(COLOR_BED_MECHANIC, "loseDye"),
		LOSE_DYE_CREATIVE(LOSE_DYE, "creative"),
		LOSE_DYE_SURVIVAL(LOSE_DYE, "survival"),
		
		SET_SPAWN_MECHANIC(MECHANICS, "setSpawn"),
		SET_SPAWN_ENABLE(SET_SPAWN_MECHANIC, "enable"),
		
		MESSAGES(MAIN, "messages"),
		
		ON_COLOR_MESSAGES(MESSAGES, "onColor"),
		SHOW_MESSAGE_COLOR(ON_COLOR_MESSAGES, "showMessageOnColor"),
		MESSAGE_COLOR(ON_COLOR_MESSAGES, "onColorMessage"),
		
		ON_BED_PLACE_MESSAGES(MESSAGES, "onBedPlace"),
		SHOW_MESSAGE_BED_PLACE(ON_BED_PLACE_MESSAGES, "showMessageOnBedPlace"),
		MESSAGE_BED_PLACE(ON_BED_PLACE_MESSAGES, "onBedPlaceMessage"),
		
		ON_SET_SPAWN_MESSAGES(MESSAGES, "onSetSpawn"),
		SET_SPAWN_MESSAGE(ON_SET_SPAWN_MESSAGES, "setSpawnMessage");
		
		private ConfigPaths parent;
		private String extension;
		private String path = "";
		ConfigPaths(ConfigPaths parent, String extension) {
			this(parent, extension, true);
		}
		ConfigPaths(ConfigPaths parent, String extension, boolean addDot) {
			this.parent = parent;
			this.extension = extension;
			if(this.parent != null) {
				path += this.parent.getPath();
			}
			if(addDot) {
				path += ".";
			}
			path += this.extension;
		}
		
		public String getPath() {
			return path;
		}
	}
	
}
