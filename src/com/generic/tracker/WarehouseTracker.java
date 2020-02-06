package com.generic.tracker;

import java.util.HashMap;
import java.util.Map;
import com.generic.model.Shipment;
import com.generic.model.Warehouse;
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
 * 
 * THE MAJOR REASON I MAKE THE METHODS RETURN SOMETHING IS FOR TESTING
 * THE ONLY METHOD THAT SHOLD BE ALLOWED TO HAVE 
 * A VOID RETURN TYPE IN THIS CLASS IS printWarehouseDetails() and addWarehouse
 * 
 * @author Seyi Ola
 *
 */
public class WarehouseTracker {
	
	private Map<Integer, Warehouse> warehouses; // Stores a collection of warehouses
											    // mapped by their id 
	
	public WarehouseTracker()
	{
		// Store the warehouse
		warehouses = new HashMap<>();	
	}
	
	/**
	 * /**
	 * Adds a new warehouse to the warehouse collection.
	 * If the warehouse already exists, we return false.
	 * @param mWarehouse warehouse to add.
	 * @return true if add was successful,
	 * 		   false if add wasn't.
	 */
	public boolean addWarehouse(Warehouse mWarehouse)
	{
		boolean added = false;
		if(warehouses.get(mWarehouse.getWarehouseID()) == null)
		{
			warehouses.put(mWarehouse.getWarehouseID(), mWarehouse);
			added = true;
		}
		return added;
	}
	
	/**
	 * Adds a shipment to a warehouse, if the warehouseID
	 * does not exist, it returns -1, if
	 * the shipment could not be added because it's 
	 * freight receipt has ended, it returns 0,
	 * and if the shipment was successfully added, it 
	 * returns 1.
	 * @param warehouseID the warehouseID
	 * @param shipment the shipment to add to this warehouse
	 * @return -1 if ID does not exist
	 * 		  , 0 if freight receipt is disabled,
	 * 		  , 1 if successfully added.
	 * 			
	 */
	public int addShipment(int warehouseID, Shipment mShipment)
	{
		Warehouse theWarehouse = warehouses.get(warehouseID);
		
		int logCode = 0;
		
		if (theWarehouse == null)
		{
			logCode = -1;
		}else if (theWarehouse.addShipment(mShipment))
		{
			logCode = 1;
		}
		
		return logCode;
	}
	
	/**
	 * Enables a warehouse receipt freight, if the warehouseID
	 * does not exist, it returns -1, if
	 * the freight receipt could not be enabled because it's 
	 * freight receipt is enabled already, it returns 0,
	 * and if the warehouse freight is already enabled, it 
	 * returns 1.
	 * @param warehouseID the warehouse ID
	 * @return log number based on result(-1:non-existent warehouse,
	 * 									   0: already enabled,
	 * 									   1: successfully added).
	 */
	
	 public int enableFreight(int warehouseID)
	 {
		Warehouse theWarehouse = warehouses.get(warehouseID);
		
		int logCode = 0;
		
		if (theWarehouse == null) 
		{
			logCode = -1;
		}else if (!theWarehouse.receivingFreight()) 
		{
			theWarehouse.enableFreight();
			logCode = 1;
		}
		
		return logCode;
	 }
	 
	/**
	 * Disables a warehouse receipt, if the warehouseID does not exist, it returns
	 * -1, if the freight receipt could not be disabled because it's freight receipt
	 * is disabled already, it returns 0, and if the warehouse freight is already
	 * disabled, it returns 1.
	 * @param warehouseID the warehouse ID
	 * @return log number based on result(-1:non-existent warehouse,
	 * 									   0: already disabled,
	 * 									   1: successfully added).
	 */

	public int endFreight(int warehouseID) {
		Warehouse theWarehouse = warehouses.get(warehouseID);

		int logCode = 0;

		if (theWarehouse == null) {
			logCode = -1;
		} else if (theWarehouse.receivingFreight()) {
			theWarehouse.disableFreight();
			logCode = 1;
		}

		return logCode;
	}
	
	/**
	 * Checks if we have an empty collection of warehouses.
	 * @return true if empty, false if not.
	 */
	public boolean isEmpty()
	{
		return warehouses.size() == 0;
	}
	
	public Warehouse getWarehouse(int warehouseID)
	{
		return warehouses.get(warehouseID);
	}
	
	/**
	 * Prints information about a warehouse for user to see.
	 * @param warehouseID the warehouse id number.
	 */
	public void printWarehouseDetails(int warehouseID) 
	{
		Warehouse theWarehouse = warehouses.get(warehouseID);
		
		if (theWarehouse == null) 
		{
			System.out.println("Warehouse with ID: " + warehouseID + " doesn't exist");
		}else 
		{
			System.out.println(theWarehouse.toString());
		}

	}
	

}
