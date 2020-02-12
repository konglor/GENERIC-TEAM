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
			}
			
			// TODO: remove these commands
			switch (command.toLowerCase()) {

			case "enablef":
				int warehouseID1;
				try {
					warehouseID1 = Integer.parseInt(arg[1]);
					if (!warehouseTracker.isEmpty()) {
						warehouseTracker.enableFreight(warehouseID1);
						System.out.println("** Freight enabled for warehouse #" + warehouseID1);

					} else {
						System.out.println("** No warehouse avaliable, please import data first");
					}
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					System.out.println("** Please input an integer");
				}
				break;
				
			case "disablef":
				int warehouseID2;
				try {
					warehouseID2 = Integer.parseInt(arg[1]);
					if (!warehouseTracker.isEmpty()) {
						warehouseTracker.endFreight(warehouseID2);
						System.out.println("** Freight ended for warehouse #" + warehouseID2);

					} else {
						System.out.println("** No warehouses avaliable, please import data first");
					}
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					System.out.println("** Please input an integer");
				}
				break;

			case "add":
				try {
					int mWarehouseID = Integer.parseInt(arg[1]);
					if (!warehouseTracker.isEmpty()) {

						if (!warehouseTracker.warehouseExists(mWarehouseID)) {
							System.out.println("** Warehouse with ID #" + mWarehouseID + " does not exist");
						} else if (!warehouseTracker.freightIsEnabled(mWarehouseID)) {
							System.out.println("** Warehouse with id #" + mWarehouseID + " freight has ended");
						}
						
						else{
							boolean added = app.addShipmentOp(mWarehouseID , in);
							if (!added) {
								System.out.println("** Shipment could not be added because"
										         + " freight has ended for this warehouse");
							}
						}
					} else {
						System.out.println("** No warehouses with avaliable, please import data first");
					}
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					System.out.println("** Please input a warehouse_id");
				}
				break;
			case "exit":
				in.close();
				break loop;
			default:
				System.out.println("** Invalid Command!");
				continue;
			}
		}
		
		System.out.println("Goodbye!");
		
	}

	/**
	 * Adds shipments to the warehouse using the command line interface
	 * 
	 * @param warehouseID
	 * @return
	 */
	public boolean addShipmentOp(int warehouseID , Scanner sc) {

		
		//Scanner sc = new Scanner(System.in);
		boolean added = false;
		String option = "";
		

		do {
			
			String shipmentID;
			String shipmentString;
			long receiptDate = 0;
			double weight = 0;

			System.out.print("Enter shipmentID(i.e. iafr4, 15566) -> ");
			shipmentID = sc.next();
			
			System.out.print("Enter shipment_method(i.e air, truck, ship, rail) -> ");
			
			//TODO: 
			ArrayList<String> shipmentMethodList = new ArrayList<String>(Arrays.asList(new String [] {FreightType.AIR.toString()
																									, FreightType.RAIL.toString()
																									, FreightType.TRUCK.toString()
																									, FreightType.SHIP.toString()}));
			shipmentString = sc.next().toUpperCase();
			while(!shipmentMethodList.contains(shipmentString))
			{
				System.out.print("Please enter a valid shipment_method(i.e air, truck, ship, rail) -> ");
				shipmentString = sc.next().toUpperCase();
			}
			
			System.out.print("Enter weight(i.e. 84.71, 321) -> ");
			while (!sc.hasNextDouble()) {
				System.out.print("Please enter a valid weight(i.e. 84.71, 321) -> ");
				sc.next();
			}
			weight = sc.nextDouble();
			
			System.out.print("Enter receipt_date(i.e 1515354694451) -> ");
			while (!sc.hasNextDouble()) {
				System.out.print("Please enter a valid receipt_date(i.e 1515354694451) -> ");
				sc.next();
			}
			receiptDate = sc.nextLong();
			
			// build a shipment
			Shipment shipment = new Shipment.Builder()
											.id(shipmentID)
											.type(FreightType.valueOf(shipmentString))
											.weight(weight)
											.date(receiptDate)
											.build();

			// add the shipment to the warehouse
			added = warehouseTracker.addShipment(warehouseID, shipment);

			if (added) {
				System.out.println("Incoming shipment has been sucessfully added");
				System.out.print("Would you like to add another shipment to warehouse_" + warehouseID + "?(Y/N) -> ");
				while (!sc.hasNext()) {
					System.out.print("Please enter ('Y/N') ->");
				}
				option = sc.next();
			}

		} while (option.equalsIgnoreCase("Y") && added);
		

		return added;
	}
}
