package console;

import java.awt.Color;
import java.util.ArrayList;

import display.DisplayController;
import entities.*;
import game.GameManager;

public class Console {
	//static class to provide a universal console
	private static MessageBox box = new MessageBox();
	
	public static ArrayList<Message> getLivingMessages() {
		return box.getHandler().getLivingMessages();
	}
	
	public static ArrayList<Message> getAllMessages() {
		return box.getHandler().getMessages();
	}
	
	public static java.awt.image.BufferedImage getImage() {
		return box.getImage();
	}
	
	public static void err(String s) {
		log("<c=r><s=b>[ERROR]<s=i> "+s);
	}
	
	public static void warn(String s) {
		log("<c=y><s=b>[WARNING]<c=w><s=p> "+s);
	}
	
	public static void notify(String s) {
		log("<c=c><s=b>[NOTICE]<c=w><s=p> "+s);
	}
	
	public static void sysLog(String s) {
		log("<c=a><s=b>[SYSTEM]<c=w><s=p> "+s);
	}
	
	public static void gameLog(String s) {
		log("<c=b><s=b>[GAME]<c=w><s=p> "+s);
	}
	
	public static void appLog(String s) {
		log("<c=g><s=b>[APPLICATION]<c=w><s=p> "+s);
	}
	
	public static void comLog(String s) {
		log("<c=m><s=i>[Command Executed]<c=y><s=p> "+s);
	}
	
	public static void log(String s) {
		log(new Message(s));
	}
	
	public static void log(String s, int l) {
		log(new Message(s,l));
	}
	
	public static void log(Message msg) {
		//test for command
		if (msg.getText().length() > 0 && msg.getText().charAt(0) == '/') {
			parseCommand(msg.getText().substring(1));
			return;
		}
		box.getHandler().addMessage(msg);
	}
	
	public static void parseCommand(String command) {
		String[] components = command.split(" ");
		switch (components[0]) {
		case "":
			Console.err("No command entered");
			break;
		case "quit":
			System.exit(0);
			break;
		case "goto":
			if (components.length > 1) {
				switch (components[1]) {
				case "main":
					DisplayController.setDisplay(DisplayController.DISPLAY_MAIN);
					Console.comLog("Sent user to screen \"main\"");
					break;
				case "game":
					DisplayController.setDisplay(DisplayController.DISPLAY_GAME);
					Console.comLog("Sent user to screen \"game\"");
					break;
				case "update_notes":
					DisplayController.setDisplay(DisplayController.DISPLAY_UPDATE_NOTES);
					Console.comLog("Sent user to screen \"update_notes\"");
					break;
				case "pause":
					DisplayController.setDisplay(DisplayController.DISPLAY_PAUSE);
					Console.comLog("Sent user to screen \"pause\"");
					break;
				default:
					Console.err("No such value \""+components[1]+"\" for command \"goto\"");
					break;
				}
			} else
				Console.err("No value entered for command \"goto\"");
			break;
		case "player":
			if (components.length > 1) {
				switch (components[1]) {
				case "health":
					if (components.length > 2)
						switch (components[2]) {
						case "set":
							if (components.length > 3) {
								Entity.getPlayer().setHealth(Double.parseDouble(components[3]));
								Console.comLog("Set player health to "+components[3]);
							} else
								Console.err("No value entered for \"player health set\"");
							break;
						case "add":
							if (components.length > 3) {
								Entity.getPlayer().setHealth(Entity.getPlayer().getHealth()+Double.parseDouble(components[3]));
								Console.comLog("Added "+components[3]+" to the player health");
							} else
								Console.err("No value entered for \"player health add\"");
							break;
						case "remove":
							if (components.length > 3) {
								Entity.getPlayer().hurt(Double.parseDouble(components[3]));
								Console.comLog("Removed "+components[3]+" from the player health");
							} else
								Console.err("No value entered for \"player health remove\"");
							break;
						default:
							Console.err("Unkown argument\""+components[2]+"\" for \"player health\"");
						}
					break;
				case "maxHealth":
					if (components.length > 2)
						switch (components[2]) {
						case "set":
							if (components.length > 3) {
								Entity.getPlayer().setMaxHealth(Double.parseDouble(components[3]));
								Console.comLog("Set player max health to "+components[3]);
							} else
								Console.err("No value entered for \"player maxHealth set\"");
							break;
						case "add":
							if (components.length > 3) {
								Entity.getPlayer().setMaxHealth(Entity.getPlayer().getMaxHealth()+Double.parseDouble(components[3]));
								Console.comLog("Added "+components[3]+" to the player max health");
							} else
								Console.err("No value entered for \"player maxHealth add\"");
							break;
						case "remove":
							if (components.length > 3) {
								Entity.getPlayer().setMaxHealth(Entity.getPlayer().getMaxHealth()-Double.parseDouble(components[3]));
								Console.comLog("Removed "+components[3]+" from the player max health");
							} else
								Console.err("No value entered for \"player maxHealth remove\"");
							break;
						default:
							Console.err("Unkown argument\""+components[2]+"\" for \"player maxHealth\"");
						}
					break;
				default:
					Console.err("Unknown argument \""+components[1]+"\" for \"player\"");
				}
			} else
				Console.err("No value entered for command \"player\"");
			break;
		case "destroy":
			if (components.length > 1) {
				switch (components[1]) {
				case "enemies":
					for (int i = 0; i < Entity.entities.size(); i++)
						if (Entity.entities.get(i) instanceof enemies.Enemy)
							Entity.entities.get(i).destroy();
					Console.comLog("Destroyed all enemies");
					break;
				}
			} else
				Console.err("No value entered for command \"destroy\"");
			break;
		case "help":
			String[] commands = {
					"help: prints the commands",
					"quit: closes the application",
					"goto: goes to the specified screen",
					"destroy enemies: destroys all enemy objects",
					"player: set attributes about the player",
					"      health: set, add, or remove some amount of health",
					"      maxHealth: set",
					"      gold: set, add, or remove",
				};
			for (String s : commands) 
				Console.log("-"+s);
			break;
		default:
			Console.err("\""+components[0]+"\" is not a valid command");
		}
	}
	
	public static MessageBox getBox() {
		return box;
	}
	
	public static void setDisplayMode(int displayMode) {
		box.setDisplayMode(displayMode);
	}
	
	public static int getDisplayMode() {
		return box.getDisplayMode();
	}
}
