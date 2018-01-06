package com.redsponge.random.bedcolor;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.block.Bed;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.redsponge.random.bedcolor.BedColor.ConfigPaths;

public class BedColorMethods {
	
	private static boolean colorFull;
	
	static BedColor plugin;
	static FileConfiguration config;
	
	public static void load(BedColor instance) {
		plugin = instance;
		config = plugin.getConfig();
		
		colorFull = config.getBoolean(ConfigPaths.COLOR_FULL.getPath());
	}
	
	private static BlockState getSecondBedPart(BlockState b1) {
		BlockFace facing = getBedFacing(b1);
		if(isBedFace(b1)) {
			return b1.getBlock().getRelative(facing.getOppositeFace()).getState();
		}
		else {
			return b1.getBlock().getRelative(facing).getState();
		}
	}
	
	@SuppressWarnings("deprecation")
	private static boolean isBedFace(BlockState b1) {
		if(b1.getBlock().getData() >= 8) {
			return true;
		}
		return false;
	}
	
	private static BlockFace getBedFacing(BlockState b1) {
		return ((org.bukkit.material.Bed) b1.getData()).getFacing();
	}
	
	public static void colorBed(BlockState bed,DyeColor color, Player p) {
		Bed bed1 = (Bed) bed;
		bed1.setColor(color);
		bed1.update();
		if(colorFull) {
			Bed bed2 = (Bed) getSecondBedPart(bed);
			bed2.setColor(color);
			bed2.update();
		}
	}
	
	public static DyeColor getColorByItem(ItemStack item) {
		short dataValue = item.getDurability();
		DyeColor[] colors = {DyeColor.BLACK, DyeColor.RED, DyeColor.GREEN, DyeColor.BROWN, DyeColor.BLUE, DyeColor.PURPLE, DyeColor.CYAN, DyeColor.SILVER,
			DyeColor.GRAY, DyeColor.PINK, DyeColor.LIME, DyeColor.YELLOW, DyeColor.LIGHT_BLUE, DyeColor.MAGENTA, DyeColor.ORANGE, DyeColor.WHITE
		};
		return colors[dataValue];
	}
	
	public static ChatColor getChatColorByDyeColor(DyeColor color) {
		
		DyeColor[] dyeColors = {DyeColor.BLACK, DyeColor.RED, DyeColor.GREEN, DyeColor.BROWN, DyeColor.BLUE, DyeColor.PURPLE, DyeColor.CYAN, DyeColor.SILVER,
			DyeColor.GRAY, DyeColor.PINK, DyeColor.LIME, DyeColor.YELLOW, DyeColor.LIGHT_BLUE, DyeColor.MAGENTA, DyeColor.ORANGE, DyeColor.WHITE
		};
		ChatColor[] chatColors = {ChatColor.BLACK, ChatColor.RED, ChatColor.DARK_GREEN, ChatColor.GOLD, ChatColor.DARK_BLUE, ChatColor.DARK_PURPLE, ChatColor.AQUA, ChatColor.GRAY,
			ChatColor.DARK_GRAY, ChatColor.LIGHT_PURPLE, ChatColor.GREEN, ChatColor.YELLOW, ChatColor.BLUE, ChatColor.LIGHT_PURPLE, ChatColor.GOLD, ChatColor.WHITE
		};
		
		for(int i = 0; i < dyeColors.length; i++) {
			if(dyeColors[i] == color) return chatColors[i];
		}
		return null;
	}
	
	public static boolean playerCanLoseDye(Player player) {
		return (config.getBoolean(ConfigPaths.LOSE_DYE_SURVIVAL.getPath()) && (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)) || (config.getBoolean(ConfigPaths.LOSE_DYE_CREATIVE.toString()) && (player.getGameMode() == GameMode.CREATIVE));
	}
	
	public static String setMessagePrefixes(String message, Player player, Block block, DyeColor color) {
		String[][] prefixes = 
			{
				{"%p", player.getDisplayName()},
				{"%xpos", Integer.toString(block.getLocation().getBlockX())},
				{"%ypos", Integer.toString(block.getLocation().getBlockY())},
				{"%zpos", Integer.toString(block.getLocation().getBlockZ())},
				{"%color", getChatColorByDyeColor(color).toString() + color.toString().toLowerCase()},
				{"silver", "light gray"}
			};
		String newMessage = message;
		for(String[] prefix : prefixes) {
			newMessage = newMessage.replaceAll(prefix[0], prefix[1]);
		}
		
		newMessage = ChatColor.translateAlternateColorCodes('$', newMessage);
		return newMessage;
	}
}
