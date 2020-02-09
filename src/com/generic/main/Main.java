package com.generic.main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.generic.model.FreightType;
import com.generic.model.Shipment;
import com.generic.model.Warehouse;
import com.generic.tracker.WarehouseTracker;

/**
 * Entry Point
 * 
 * To import run the command:
 * import example.json
 * 
 * @author GENERIC TEAM
 *
 */
public class Main {
	
	public static WarehouseTracker warehouseTracker;
	
	public Main() {
		warehouseTracker = WarehouseTracker.getInstance();
		
		System.out.println("Available Commands:");
		System.out.println("import <file>");
		System.out.println("export <file>");
		System.out.println("print");
		System.out.println("exit");
	}

	public static void main(String[] args) {
		Main app = new Main();
		Scanner in = new Scanner(System.in);
		
		loop:
		while(true) {
			System.out.print("> ");
			if (!in.hasNextLine())
				break;
				
			String[] command = in.nextLine().split(" ");
			switch(command[0].toLowerCase()) {
				case "import":
					String file = command[1];
					try {
						System.out.println("Importing "+file+"...");
						app.parseJson(new File("resource/"+file).getAbsolutePath());
					} catch (IOException | ParseException e) {
						System.out.println("System can not read the file!");
						System.exit(0);
					}
					break;
				case "export":
					// TODO: specify the file name
					warehouseTracker.exportWarehouseToJSON(15566);
					break;
				case "print":
					warehouseTracker.printAll();
					break;
				case "exit":
					break loop;
				default:
					System.out.println("** Invalid Command!");
					continue;
			}
		}
		System.out.println("Goodbye!");
	}

	/**
	 * Reads a file that is in JSON format containing
	 * various shipment information.
	 * @param filepath the path of JSON file 
	 */
	@SuppressWarnings("unchecked")
	public void parseJson(String filepath) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filepath);

		JSONObject jsonFile = (JSONObject) jsonParser.parse(reader);
		JSONArray warehouseContents = (JSONArray) jsonFile.get("warehouse_contents");
		warehouseContents.forEach(shipmentObject -> parseWarehouseContentsToObjects((JSONObject) shipmentObject));
		
		reader.close();
	}

	/**
	 * Parses and assigns shipment 
	 * object for each warehouse
	 * @param shipmentObject shipment object in json
	 */
	private void parseWarehouseContentsToObjects(JSONObject shipmentObject) {
		// get the warehouse ID
		String warehouseString = (String)shipmentObject.get("warehouse_id");
		int warehouseID = Integer.parseInt(warehouseString);

		String shipmentID = (String) shipmentObject.get("shipment_id");
		FreightType freight = FreightType.valueOf((String)shipmentObject.get("shipment_method").toString().toUpperCase());
		Number weight = (Number) shipmentObject.get("weight");
		Number receiptDate = (Number) shipmentObject.get("receipt_date");

		// add the warehouse
		warehouseTracker.addWarehouse(new Warehouse(warehouseID));
		
		// build a shipment
		Shipment shipment = new Shipment.Builder()
				.id(shipmentID)
				.type(freight)
				.weight(weight.doubleValue())
				.date(receiptDate.longValue())
				.build();
				
		// add the shipment to the warehouse
		warehouseTracker.addShipment(warehouseID, shipment);
		
		// print the contents of the warehouse
		warehouseTracker.printWarehouseDetails(warehouseID);		
	}
}
