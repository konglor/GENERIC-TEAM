package com.generic.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.generic.model.FreightType;
import com.generic.model.Shipment;
import com.generic.tracker.WarehouseTracker;
import com.generic.util.Commands;

/**
 * Entry Point
 * 
 * To import run the command: import example.json
 * 
 * @author GENERIC TEAM
 *
 */
public class Main {
	private static WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
	private static Commands cmd = Commands.getInstance();

	public Main() {
		System.out.println("Available commands:");
		List<String> cmdList = cmd.getCommandList();
		for (String cmd : cmdList) {
			System.out.println(cmd);
		}
	}

	public static void main(String[] args) {
		Main app = new Main();

		Scanner in = new Scanner(System.in);
		loop: 
		while (true) {
			System.out.print(">> ");
			if (!in.hasNextLine())
				break;

			String[] arg = in.nextLine().split(" ");
			
			String command = arg[0].toLowerCase();
			String[] arguments = Arrays.copyOfRange(arg, 1, arg.length);

			if (cmd.exist(command)) {
				cmd.execute(command, arguments);
				continue;
			} else {
				System.out.println("** Invalid command!");
			}
		} // End of while loop
	} // End of Main
}
