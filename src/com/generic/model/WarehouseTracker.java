package com.generic.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 * This class will be responsible for tracking
 * a collection of warehouses and the shipments
 * in them. This will allow for consistency
 * of objects as a shipment can't exist without
 * a warehouse. It would provide only the
 * required functionalities such as
 * adding shipment, printing warehouse details,
 * enabling and disabled the freight receipt of a warehouse.
 * 
 * @author GENERIC TEAM
 *
 */
public class WarehouseTracker extends PersistentJson {
	private static WarehouseTracker warehouseTracker;
	
	// Stores a collection of warehouses mapped by their id 
	private Map<String, Warehouse> warehouses;

	// private constructor
	private WarehouseTracker() {}
	
	public static WarehouseTracker getInstance() {
		if (warehouseTracker == null) {
			synchronized(WarehouseTracker.class) {
				warehouseTracker = new WarehouseTracker();
				warehouseTracker.warehouses = new HashMap<>();
				warehouseTracker.id = "warehouse_contents";
			}
		}
		return warehouseTracker;
	}
	
		
	/**
	 * Create a method that checks if a 
	 * particular warehouse has it's freight receipt
	 * enabled
	 * @param warehouseID the warehouseID
	 * @return true if added, false if not
	 */
	
	public boolean freightIsEnabled(String warehouseID) {
		return warehouses.get(warehouseID).receivingFreight();
	}
	
	
	
	/**
	 * 
	 * Adds a new warehouse to the warehouse collection.
	 * If the warehouse already exists, we return false.
	 * @param  mWarehouse warehouse to add.
	 * @return true if add was successful,
	 * 		   false if add wasn't.
	 */
	public boolean addWarehouse(Warehouse mWarehouse) {
		if(warehouses.get(mWarehouse.getWarehouseID()) == null) {
			warehouses.put(mWarehouse.getWarehouseID(), mWarehouse);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Add shipment to a warehouse using warehouseID
	 * @param warehouseID warehouseID
	 * @param mShipment shipment to add
	 * @return re
	 */
	public boolean addShipment(String warehouseID, Shipment mShipment) {
		Warehouse theWarehouse = warehouses.get(warehouseID);
		if (theWarehouse != null && theWarehouse.receivingFreight()) {
			theWarehouse.addShipment(mShipment);
			return true;
		}
		return false;
	}
	
	
	public Warehouse get(String warehouseID) {
		return warehouses.get(warehouseID);
	}
	
	
	/**
	 * Adds shipment to a warehouse using warehouse object 
	 * @param theWarehouse warehouseID
	 * @param mShipment shipment to add
	 * @return true if added, false if not
	 */
	public boolean addShipment(Warehouse theWarehouse, Shipment mShipment) {
		return addShipment(theWarehouse.getId(), mShipment);
	}
	
	/**
	 * Enables a freight receipt in 
	 * a Warehouse.
	 * @param warehouseID warehouse id
	 * @return true if freight was successfully
	 *         enabled, false if not.
	 */
	 public boolean enableFreight(String warehouseID) {
		 Warehouse theWarehouse = warehouses.get(warehouseID);
		 if (theWarehouse != null) {
			 theWarehouse.enableFreight();
			 return true;
		}
		return false;
	 }
	
	 /**
	  * Disables freight receipt in a warehouse
	  * @param warehouseID warehouse id
	  * @return true if freight was successfully
	  *         disabled, false if not.
	  */
	public boolean endFreight(String warehouseID) {
		Warehouse theWarehouse = warehouses.get(warehouseID);
		if (theWarehouse != null) {
			theWarehouse.disableFreight();
			return true;
		}
		return false;
	}

	/**
	 * Checks if we have an empty collection of warehouses.
	 * @return true if empty, false if not.
	 */
	public boolean isEmpty() {
		return warehouses.size() == 0;
	}

	
	/**
	 * Getter for shipments size for a specified warehouse
	 * @return the size of the warehouse, -1 if
	 * 		   warehouse doesn't exist
	 */
	public int getWarehouseShipmentsSize(String warehouseID) {
		Warehouse theWarehouse = warehouses.get(warehouseID);
		return (theWarehouse != null ? theWarehouse.getShipmentSize() : -1);
	}
	
	/**
	 * Checks if a warehouse exists
	 * @param warehouseID warehouse id
	 * @return true if it does, false if not.
	 */
	public boolean warehouseExists (String warehouseID) {
		return (warehouses.get(warehouseID) != null);
	}
	
	
	/**
	 * Prints information about a warehouse for user to see.
	 * @param warehouseID the warehouse id number.
	 */
	public void printWarehouseDetails(String warehouseID) {
		Warehouse theWarehouse = warehouses.get(warehouseID);
		if (theWarehouse == null) {
			System.out.println("Warehouse cannot be found!");
			return; // TODO: throw exception
		}
		System.out.println(theWarehouse.toString());
	}
	
	/**
	 * Prints all available warehouses
	 */
	public void printAll() {
		warehouses.forEach((k, v) -> printWarehouseDetails(k));
	}

	/* this will be unconventional because
	 * we are trying to reproduce the original file
	 * and the original file have quite a strange format...
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON() {		
		// FOR CORRECT JSON FORMAT:
		/* 
		JSONObject warehouseTracker = new JSONObject();
		JSONArray warehouseJsonList = new JSONArray();
		JSONObject warehouseContents;

		List<Warehouse> warehousesList = new ArrayList<>(warehouses.values());
		
		for (Warehouse warehouse : warehousesList) {
			warehouseContents = warehouse.toJSON();
			warehouseJsonList.add(warehouseContents);
		}
		warehouseTracker.put(id, warehouseJsonList);
		return warehouseTracker; 
		*/
		
		// FOR REPLICATING ORIGINAL FILE FORMAT:
		JSONObject warehouseTracker = new JSONObject();
		JSONArray shipmentJsonList = new JSONArray();

		List<Warehouse> warehousesList = new ArrayList<>(warehouses.values());
		List<Shipment> shipmentList;
		
		for (Warehouse warehouse : warehousesList) {
			shipmentList = warehouse.getShipmentList();
			// to reproduce the original file
			for (Shipment shipment : shipmentList) {
				JSONObject shipmentJson = shipment.toJSON();
				// "shipment has-a warehouse instead of warehouse has-many shipments"
				shipmentJson.put("warehouse_id", warehouse.getId());
				shipmentJsonList.add(shipmentJson);
			}
		}
		warehouseTracker.put(id, shipmentJsonList);
		return warehouseTracker;
	}
}
