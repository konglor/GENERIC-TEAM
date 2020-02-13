package com.generic.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

import com.generic.model.PersistentJson;
import com.generic.tracker.WarehouseTracker;

public class Commands {
	private static Commands command;

	private static Map<String, Executable> commandList;
	
	private Commands() {}
	
	public void execute(String command, String[] args) {
		commandList.get(command).execute(args);
	}
	
	public static Commands getInstance() {
		if (commandList == null) {
			synchronized(Commands.class) {
				command = new Commands();
				commandList = new HashMap<>();
				init();
			}
		}
		return command;
	}
	
	public static List<String> getCommandList() {
		return new ArrayList<>(commandList.keySet());
	}
	
	public static boolean exist(String command) {
		return commandList.containsKey(command);
	}
	
	private static void init() {
		// some annotations here to help users how to use commands
		commandList.put("import", (arg -> {
			try {
				System.out.println(arg);
				new Persistent().parseJson(new File("resource/" + arg[0]).getAbsolutePath());
			} catch (IOException | ParseException e) {
				System.out.println("** System can not read the file!");
				e.printStackTrace();
			}
		}));
		
		commandList.put("export", (arg -> {
				WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
				try {
					String mWarehouseID = arg[0];
					if (!warehouseTracker.warehouseExists(mWarehouseID)) {
						return;
					}
					PersistentJson warehouse = warehouseTracker.get(mWarehouseID);
					new Persistent().exportToJSON(warehouse);
					
					System.out.println("Sucessfully exported warehouse");
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					System.out.println("** Please input a warehouse ID to export");
				}
		}));
		
		commandList.put("print", (arg -> {
				String warehouseID;
				try {
					WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
					warehouseID = arg[1];
					warehouseTracker.printWarehouseDetails(warehouseID);
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					System.out.println("** Please input a warehouseID");
				}
		}));
		
		commandList.put("printall", (arg -> {
			WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
			if (!warehouseTracker.isEmpty()) {
					warehouseTracker.printAll();
				} else {
					System.out.println("** No warehouses avaliable, please import data first");
				}
		}));
	}
}
