package com.redsponge.random.bedcolor.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.redsponge.random.bedcolor.BedColor;
import com.redsponge.random.bedcolor.BedColor.ConfigPaths;

public class BedColorCommand implements CommandExecutor{
	
	BedColor plugin;
	FileConfiguration config;
	
	public BedColorCommand(BedColor instance) {
		plugin = instance;
		config = plugin.getConfig();
		
		String actionSetter = config.getString(ConfigPaths.COLOR_METHOD_METHOD.getPath());
		
		if(actionSetter.equals("right")) {
			action = "right";
		}
		else if (actionSetter.equals("left")) {
			action = "left";
		}
		else if(actionSetter.equals("both")) {
			action = "right or left";
		}
	}
	
	String action;
	
	
			
	enum SubCommands {
		COMMANDS("commands","displays this menu!"),
		INFO("info", "displays the name, version and author of the plugin"),
		COLORING("coloring", "info about the main functionality of this plugin"),
		SPAWNPOINT("spawnpoint", "info about the power of beds to save your spawn point");
		
		private String label;
		private String helpInfo;
		SubCommands(String label, String helpInfo) {
			this.label = label;
			this.helpInfo = helpInfo;
		}
		public String getLabel() {
			return label;
		}
		public String getHelpInfo() {
			return helpInfo;
		}
	}

	String header = ChatColor.DARK_GRAY + "===========[" + ChatColor.RED + BedColor.name + ChatColor.DARK_GRAY + "]===========";
	String footer = ChatColor.DARK_GRAY + "===============================";
	
	String help_starter = ChatColor.RED + "/bedcolor ";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String param = null;
		if(args.length < 1) {
			param = SubCommands.COMMANDS.getLabel();
		}
		else {
			param = args[0];
		}
		if(param.equals(SubCommands.INFO.getLabel())) {
			String[][] msg = {
					{ChatColor.RED + "Name: " + ChatColor.WHITE + BedColor.name},
					{ChatColor.RED + "Version: " + ChatColor.WHITE + BedColor.version},
					{ChatColor.RED + "By: " + ChatColor.WHITE + BedColor.author}
			};
			displayMessage(sender, msg);
		}
		else if(param.equals(SubCommands.COMMANDS.getLabel())) {
			String[][] msg = {
				{getCommandHelp(SubCommands.COMMANDS)},
				{getCommandHelp(SubCommands.COLORING)},
				{getCommandHelp(SubCommands.INFO)},
				{getCommandHelp(SubCommands.SPAWNPOINT)}
			};
			displayMessage(sender, msg);
			return true;
		}
		else if(param.equals(SubCommands.COLORING.getLabel())) {
			String[][] msg = {
				{ChatColor.GOLD.toString() + ChatColor.UNDERLINE.toString() + ChatColor.BOLD.toString() + "The legend of the Bed, Vol. 1:"},
				{" "},
				{ChatColor.GOLD + "The legends say that"},
				{ChatColor.GOLD + "one human being with incredible power"},
				{ChatColor.GOLD + "will be able to complete the ancient ritual"},
				{ChatColor.GOLD + "that was started by the " + ChatColor.YELLOW.toString()  + ChatColor.ITALIC.toString() + "BED COLORERS" + ChatColor.GOLD + " tribe 20489 years ago"},
				{" "},
				{ChatColor.GOLD + "Prove to everyone you're that human being! Show everyone your power!"},
				{ChatColor.GOLD + "BECOME THE ULTIMA.. well.. who am I kidding, just right click the bed with dye to color it"},
				{" "},
				{ChatColor.DARK_GRAY.toString() + ChatColor.ITALIC.toString() + "[" + action + " click a bed with dye to color it]"},
				{" "},
				{ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.ITALIC.toString() + "The End"}
			};
			displayMessage(sender, msg);
			return true;
		}
		else if(param.equals(SubCommands.SPAWNPOINT.getLabel())) {
			String[][] msg = {
					{ChatColor.GOLD.toString() + ChatColor.UNDERLINE.toString() + ChatColor.BOLD.toString() + "The legend of the Bed, Vol. 2:"},
					{" "},
					{ChatColor.GOLD + "Vol 1 is in /bedcolor coloring"},
					{" "},
					{ChatColor.GOLD + "After you've completed the ancient ritual of the " + ChatColor.YELLOW.toString() + ChatColor.ITALIC.toString() + "BED COLORERS"},
					{ChatColor.GOLD + "You felt very good with yourself, but not for long..."},
					{" "},
					{ChatColor.GOLD + "You started fearing about death and what will happen if you die"},
					{ChatColor.GOLD + "after you accomplished such major things in your life (yea, I\'m talking about the ritual, k?)"},
					{ChatColor.GOLD + "You started being very careful and made sure you were safe all the time. (or at least tried)"},
					{ChatColor.GOLD + "But you didn't notice that one little zombie that was running towards you.."},
					{" "},
					{ChatColor.GOLD + "The zombie almost killed you, and while you\'re running for your life, you saw someone shining in the dark, it was flying"},
					{" "},
					{ChatColor.GOLD + "You realised it was a ghost of a member of the " + ChatColor.YELLOW.toString() + ChatColor.ITALIC.toString() + "BED COLORERS"},
					{ChatColor.GOLD + "He flew to you and whispered you this message:"},
					{ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "The Special Item That Brought me Back, Might be what Brings You Back As Well!"},
					{" "},
					{ChatColor.GOLD + "You realised what he meant, and started running towards the bed. In your last breaths, you touched it just before dying"},
					{" "},
					{ChatColor.GOLD + "you closed your eyes, and suddenly, when the zombie killed you, you saw yourself, floating and shining in the void"},
					{ChatColor.GOLD + "you started flying towards yourself, achieving more and more speed as you see all the things that you've done in your life"},
					{ChatColor.GOLD + "Suddenly, you started feeling again! you opened your eyes and stood up to find out where are you"},
					{ChatColor.GOLD + "you were next to the bed."},
					{ChatColor.GOLD + "you realised it saved you! and that It\'ll forever will."},
					{" "},
					{ChatColor.DARK_GRAY.toString() + ChatColor.ITALIC.toString() + "[right click a bed to set your spawnpoint]"},
					{" "},
					{ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.ITALIC.toString() + "The End"}
					
			};
			displayMessage(sender, msg);
			return true;
		}
		return false;
	}
	
	private void displayMessage(CommandSender sender, String[][] msg) {
		sender.sendMessage(header);
		for(String[] line : msg) {
			sender.sendMessage(line[0] + "\n");
		}
		sender.sendMessage(footer);
	}
	
	private String getCommandHelp(SubCommands command) {
		return help_starter + command.getLabel() + ChatColor.YELLOW + " - " + command.getHelpInfo();
	}
}
