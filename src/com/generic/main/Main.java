package com.generic.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.generic.model.FreightType;
import com.generic.model.Shipment;
import com.generic.model.Warehouse;
import com.generic.tracker.WarehouseTracker;

/**
 * 
 * @author Justin Caughlan, Seyi Ola
 *
 */

public class Main {
	
	public static WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();


	public static void main(String[] args) {
		
		// For Testing will be removed, as a user is expected
		// to pass the path to Json file they would
		// like to read
		File jsonfile = new File("resource/example.json");
		String jsonfilePath = jsonfile.getAbsolutePath();
		parseJson(jsonfilePath);
		
		// Export to a warehouse Json file. Right
		// now this will export to the project folder,
		// although not stated in the specification, we
		// should have a feature that allows user
		// specify a destination path for their file.
		warehouseTracker.exportWarehouseToJSON(15566);
		
		/*
		Scanner scanner = new Scanner(System.in);
		System.out.println("Import (I) or Export (E) Warehouse Contents");
		String response = scanner.nextLine();
		if(response.equalsIgnoreCase("I")) {
			//Call JSON Parser
		}
		*/
	}
	
		
	/**
	 * Reads a file that is in JSON format containing
	 * various shipment information.
	 * @param filepath the path of JSON file 
	 */
	
	@SuppressWarnings("unchecked")
	public static void parseJson(String filepath)
	{
		
		JSONParser jsonParser = new JSONParser();
		
		try (FileReader reader = new FileReader(filepath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
             
            JSONObject jsonFile = (JSONObject) obj;
    
            JSONArray warehouseContents = (JSONArray) jsonFile.get("warehouse_contents");
            
            //prints out the json array for testing, will be removed
            System.out.println(warehouseContents);

            //Iterate over warehouse array
            warehouseContents.forEach(shipmentObject -> parseWarehouseContentsToObjects((JSONObject) shipmentObject));
 
        } catch (FileNotFoundException e) {
        	throw new IllegalArgumentException(e.getMessage());
        } catch (IOException e) {
        	throw new IllegalArgumentException(e.getMessage());
        } catch (ParseException e) {
        	throw new IllegalArgumentException(e.getMessage());
        }
	}
	
	
	/**
	 * Parses and assigns shipment 
	 * object for each warehouse
	 * @param shipmentObject shipment object in json
	 */
	
	
	private static void parseWarehouseContentsToObjects(JSONObject shipmentObject)
	{
		// Get warehouse tracker instance
		//WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
		
		
		// 	Get all fields from JSON file and assign 
		int warehouseID = Integer.parseInt((String)shipmentObject.get("warehouse_id"));
		FreightType freight = FreightType.valueOf((String)shipmentObject.get("shipment_method").toString().toUpperCase());

		
		// SOME SLICK STUFF
		// GOING ON HERE. THIS CHECKS IF THE RETURN VALUE OF 
		// get("shipment_id") is null due what may be(or not be)
		// a typo in the json file(Shipment_id), then returns
		// get ("Shipment_id") instead
		// 
		String shipmentID = ((shipmentObject.get("shipment_id") != null) ? 
							(String)shipmentObject.get("shipment_id") :
							(String) shipmentObject.get("Shipment_id"));
		Number weight = ((Number)shipmentObject.get("weight"));
		Number receiptDate = ((Number)shipmentObject.get("receipt_date"));

				
		warehouseTracker.addWarehouse(new Warehouse(warehouseID));
		
		warehouseTracker.addShipment(warehouseID, new Shipment(shipmentID
															 , freight
															 , weight.doubleValue()
															 , receiptDate.longValue()));
		
		// This is to see results, it will be removed.
		warehouseTracker.printWarehouseDetails(warehouseID);
		System.out.println("\n****************************************");		
	}
	
	
	
	
	
}
