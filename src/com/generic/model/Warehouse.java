package com.generic.model;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This class creates a model of a Warehouse.
 * 
 * @author Justin Caughlan, Seyi Ola
 * @author GENERIC TEAM
 */

public class Warehouse {
	
	private static final String WAREHOUSE_FORMAT_STRING = "| WAREHOUSEID: %d| FREIGHT RECEIPT STATUS: %s| SHIPMENT AVALIABLE: %d|";
	private static final String WAREHOUSE_DETAIl_FORMAT_STRING = "%d.Shipment_Id: %s\n  Weight: %.1f\n  Freight_Type: %s\n  Receipt_Date: %s";
	
	private int warehouseID; // warehouse ID
	private boolean freightReceipt; // freight receipt
	private List<Shipment> shipments; // List of shipments

	/**
	 * Construct a new warehouse
	 * @param id warehouse identification number
	 * @param receipt freightReceipt status
	 */
	public Warehouse(int warehouseID) {
		this.shipments = new ArrayList<Shipment>();
		this.warehouseID = warehouseID;
		this.freightReceipt = true;
	}
	
	/**
	 * Enables freight receipt
	 */
	public void enableFreight() {
		freightReceipt = true;
	}
	
	/**
	 * Disables freight receipt
	 */
	public void disableFreight() {
		freightReceipt = false;
	}

	/**
	 * Gets the freightReceipt Status
	 * @return freightReceipt
	 */
	public boolean receivingFreight() {
		return freightReceipt;
	}
	
	/**
	 * Gets the warehouseID
	 * @return warehouseID
	 */
	public int getWarehouseID() {
		return warehouseID;
	}
	
	/**
	 * Adds a shipment to the warehouse if freightReceipt
	 * enabled, does not add if it isn't enabled
	 * @param mShipment shipment to be received
	 * @return true if add successful, false if not.
	 */
	public boolean addShipment(Shipment mShipment) {
		if (freightReceipt) {
			shipments.add(mShipment);
			return true;
		}
		return false;
	}

	/**
	 * Getter for number of shipments 
	 * in the warehouse
	 * @return
	 */
	public int getShipmentSize() {
		return shipments.size();
	}
	
	@Override
	public String toString() {
		String headerString = String.format(WAREHOUSE_FORMAT_STRING, warehouseID, (freightReceipt) ? "ENABLED" : "ENDED", getShipmentSize());
		String headerFormat = new String(new char[headerString.length()]).replace("\0", "-");
		StringBuilder warehouseInfo = new StringBuilder()
				.append(headerFormat).append("\n")
				.append(headerString).append("\n")
				.append(headerFormat).append("\n")
				.append("          *SHIPMENT RECEIVED*").append("\n")
				.append("****************************************").append("\n");
		if (!isEmpty()) {
			int count = 0;
			for (Shipment shipment : shipments)
			{
				count++;
				String shipmentID = shipment.getShipmentID();
				double weight = shipment.getWeight();
				long receiptDate = shipment.getReceiptDate();
				FreightType fType = shipment.getFreight();

				String shipmentInfo = String.format(WAREHOUSE_DETAIl_FORMAT_STRING, count, shipmentID, weight, fType.toString().toLowerCase(), milliToDate(receiptDate));
				warehouseInfo.append(shipmentInfo).append("\n");
			}
			
		} else {
			warehouseInfo.append("        *NO SHIPMENTS RECEIVED YET*").append("\n");
		}
		warehouseInfo.append("****************************************");
		return warehouseInfo.toString();
	}
	
	/**
	 * Checks if warehouse is empty.
	 * Obviously so we don't loop through an
	 * try to print an empty list.
	 * @return true if shipments size 
	 * 		   is 0 and false if not
	 */
	public boolean isEmpty() {
		return (shipments.size() == 0);
	}
	
	/**
	 * Exports a warehouse object to a JSON 
	 * file.
	 * 
	 * A NICE THING TO ADD WILL BE TO ALLOW
	 * USER SPECIFY A DESTINATION PATH FOR FILE.
	 */
	@SuppressWarnings("unchecked")
	public void exportToJSON() {
		JSONArray warehouseContents = new JSONArray();
		JSONObject shipmentContents;

		for (Shipment shipment : shipments) {
			shipmentContents = new JSONObject();
			
			String shipmentIDValue = new StringBuilder('"')
				.append('"')
				.append(shipment.getShipmentID())
				.append('"')
				.toString();

			String freightTypeValue = new StringBuilder('"')
				.append('"')
				.append(shipment.getFreight().toString().toLowerCase())
				.append('"')
				.toString();
			
			shipmentContents.put("shipment_id", shipmentIDValue);
			shipmentContents.put("shipment_method", freightTypeValue);
			shipmentContents.put("weight", shipment.getWeight());
			shipmentContents.put("receipt_date", shipment.getReceiptDate());
	
			warehouseContents.add(shipmentContents);
		}
		JSONObject warehouseInfo = new JSONObject();
		warehouseInfo.put("Warehouse_" + warehouseID, warehouseContents);
		
		//Write JSON file
		try (FileWriter file = new FileWriter("warehouse_"+ warehouseID + ".json")) {
			file.flush();
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.println(warehouseInfo.toJSONString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Converts milliseconds to a date 
	 * @param milliDate date in milliseconds
	 * @return date in simple format
	 */
	private String milliToDate(long milliDate) {
		DateFormat simple = new SimpleDateFormat("dd MMMMM yyyy HH:mm:ss");
		Date result = new Date(milliDate);
		return simple.format(result);
	}
}
