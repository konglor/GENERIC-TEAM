package com.generic.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.generic.model.FreightType;
import com.generic.model.Shipment;
import com.generic.tracker.WarehouseTracker;
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

	public static void welcome() {
		System.out.println("Available commands:");
		List<String> cmdList = cmd.getCommandList();
		for (String cmd : cmdList) {
			System.out.println(cmd);
		}
	}

	public static void main(String[] args) {
		welcome();

		@SuppressWarnings("resource")
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
				} catch(CommandsException ex) {
					break;
				}
			} else {
				System.out.println("** Invalid command!");
			}
		} // End of while loop
		
		in.close();
		
		System.out.println("Goodbye!");
	} // End of Main
}
