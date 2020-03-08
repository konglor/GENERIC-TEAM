package com.generic.main;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.generic.model.WarehouseTracker;
import com.generic.util.Commands;
import com.generic.util.CommandsException;

/**
 * Entry Point
 * 
 * To import run the command: import example.json
 * 
 * @author GENERIC TEAM
 *
 */
public class Main {

	public static Commands cmd = Commands.getInstance();

	@Deprecated
	public static void welcome() {
		System.out.println("Available commands:");
		List<String> cmdList = cmd.getCommandList();
		for (String cmd : cmdList) {
			System.out.println(cmd);
		}
	}
	
	@Deprecated
	public static void loop() {
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.print(">> ");
			if (!in.hasNextLine())
				break;

			String[] arg = in.nextLine().split(" ");
			
			String command = arg[0].toLowerCase();
			String[] arguments = Arrays.copyOfRange(arg, 1, arg.length);

			if (cmd.exist(command)) {
				try {
					cmd.execute(command, arguments);
				} catch(CommandsException | ArrayIndexOutOfBoundsException ex) {
					break;
				}
			} else {
				System.out.println("** Invalid command!");
			}
		} // End of while loop
		
		in.close();
		
		System.out.println("Goodbye!");
	}

	@Deprecated // soon... As the project is transitioning from CLI to FX
	public static void main(String[] args) {
		// automatically loads existing data
		WarehouseTracker tracker = WarehouseTracker.getInstance();
		tracker.printAll();

	} // End of Main
}
