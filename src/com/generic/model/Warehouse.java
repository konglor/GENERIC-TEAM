package com.generic.model;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This class creates a model of a Warehouse.
 * 
 * @author GENERIC TEAM
 */

public class Warehouse extends PersistentJson {
	
	private static final String WAREHOUSE_DETAIL_FORMAT_STRING = "| WAREHOUSEID: %s| FREIGHT RECEIPT STATUS: %s| SHIPMENT AVALIABLE: %d|";

	private boolean freightReceiptEnabled; // freight receipt
	private List<Shipment> shipments; // List of shipments

	/**
	 * Construct a new warehouse
	 * @param id warehouse identification number
	 * @param receipt freightReceipt status
	 */
	public Warehouse(String warehouseID) {
		this.shipments = new ArrayList<Shipment>();
		this.id = warehouseID;
		this.freightReceiptEnabled = true;
	}
	
	/**
	 * Enables freight receipt
	 */
	public void enableFreight() {
		freightReceiptEnabled = true;
	}
	
	/**
	 * Disables freight receipt
	 */
	public void disableFreight() {
		freightReceiptEnabled = false;
	}

	/**
	 * Gets the freightReceipt Status
	 * @return freightReceipt
	 */
	public boolean receivingFreight() {
		return freightReceiptEnabled;
	}
	
	/**
	 * Gets the warehouseID
	 * @return warehouseID
	 */
	public String getWarehouseID() {
		return id;
	}
	
	/**
	 * Adds a shipment to the warehouse if freightReceipt
	 * enabled, does not add if it isn't enabled
	 * @param mShipment shipment to be received
	 * @return true if add successful, false if not.
	 */
	public boolean addShipment(Shipment mShipment) {
		if (freightReceiptEnabled) {
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
		String headerString = String.format(WAREHOUSE_DETAIL_FORMAT_STRING, id, (freightReceiptEnabled) ? "ENABLED" : "ENDED", getShipmentSize());
		String headerFormat = new String(new char[headerString.length()]).replace("\0", "-");
		StringBuilder warehouseInfo = new StringBuilder()
				.append(headerFormat).append("\n")
				.append(headerString).append("\n")
				.append(headerFormat).append("\n")
				.append("          *SHIPMENT RECEIVED*").append("\n")
				.append("****************************************").append("\n");
		if (!isEmpty()) {
			int count = 1;
			for (Shipment shipment : shipments) {
				String shipmentInfo = shipment.toString();
				warehouseInfo.append(count++).append(".").append(shipmentInfo).append("\n");
				warehouseInfo.append("****************************************").append("\n");
			}
		} else {
			warehouseInfo.append("        *NO SHIPMENTS RECEIVED YET*").append("\n");
		}
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
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject warehouseInfo = new JSONObject();
		JSONArray shipmentList = new JSONArray();
		JSONObject shipmentContents;

		for (Shipment shipment : shipments) {
			shipmentContents = shipment.toJSON();
			shipmentList.add(shipmentContents);
		}
		warehouseInfo.put("Warehouse_" + id, shipmentList);
		return warehouseInfo;
		
	}
}
