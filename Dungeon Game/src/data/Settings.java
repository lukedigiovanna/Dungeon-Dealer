package data;

import java.awt.Color;
import java.util.*;
import java.io.*;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Settings {
	public static String filePath = "lib/data/settings.STGS";
	
	public static void init() {
		if (!hasFile())
			initNewFile();
		readSettings();
	}
	
	public static void readSettings() {
		if (!hasFile()) {
			console.Console.err("Error loading settings file");
			return;
		}
		console.Console.notify("Loading settings file");
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
			String s = null;
			ArrayList<Setting> list = new ArrayList<Setting>();
			while ((s = br.readLine()) != null) {
				//try to make a setting
				int index = s.indexOf("=");
				if (index < 0)
					continue;
				String function = s.substring(0, index);
				String value = s.substring(index+1);
				Setting setting = new Setting(function,value);
			}
		} catch (Exception e) {
			
		}
	}
	
	public static void initNewFile() {
		try {
			PrintStream ps = new PrintStream(new File(filePath));
			for (Setting s : settings)
				ps.println(s);
			console.Console.notify("New settings file created");
		} catch (IOException e) {
			
		}
	}
	
	public static Setting[] settings = {
			//default settings... modified when the readSettings method is called
			new Setting("control.moveUp","w") {public void function() { console.Console.err("fucking gay"); } },
			new Setting("control.moveLeft","a") {public void function() { } },
			new Setting("control.moveDown","s") {public void function() { } },
			new Setting("control.moveRight","d") {public void function() { } },
	};
	
	public static class Setting {
		public String name, value;
		
		public Setting(String name, String value) {
			this.name = name;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return name+"="+value;
		}
		
		public void function() {}	
	}
	
	public static boolean hasFile() {
		//search the directory
		String fileName = filePath;
		Scanner in = null;
		try {
			in = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			console.Console.err("SETTINGS file not found");
			console.Console.notify("Developing a settings file...");
			return false;
		}
		return true;
	}
}
