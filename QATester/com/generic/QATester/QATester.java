package com.generic.QATester;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.generic.model.Warehouse;
import com.generic.tracker.WarehouseTracker;


import com.generic.model.FreightType;
import com.generic.model.Shipment;


/**
 * A Junit class for unit testing
 * @author Seyi Ola
 *
 */

public class QATester {
	
	WarehouseTracker wTracker; // To hold our warehouse class
	Warehouse warehouse1;
	Warehouse warehouse2;
	Warehouse warehouse3;
	Shipment shipment1;
	Shipment shipment2;
	Shipment shipment3;
	Shipment shipment4;
	
	
	 
	
	public QATester() {
		//Singleton pattern for WarehouseController Class
		wTracker = WarehouseTracker.getInstance();
		
	
		// first warehouse and shipment
		wTracker.addWarehouse(new Warehouse(12513));
		wTracker.addShipment(12513, new Shipment("48934j", FreightType.valueOf("AIR"), 84, 1515354694451L));
		
		
		// second valid warehouse and shipment
		wTracker.addWarehouse(new Warehouse(15566));
		
		//Initialize a bunch of shipment objects
		shipment1 = new Shipment("123tr", FreightType.valueOf("RAIL"), 74.0, 1515354694451L);
		shipment2 = new Shipment("4231e", FreightType.valueOf("SHIP"), 88.0, 1515254694451L);
		
		
		// TURNS OUT A JUNIT TEST CALLS A THE CONSTRUCTOR EVERYTIME A
		// NEW TEST METHOD IS EXECUTED - LEARNING SOMETHING NEW
	}
	
	
	
	
	/**
	 * Test that there are no 
	 * warehouses with duplicate ID numbers constructed.
	 * @param argument pre-defined set warehouseIDs.
	 */
	@ParameterizedTest
	@ValueSource(ints = {12513, 15566})
	void testDuplicateWarehouse(int warehouseID)
	{
		assertFalse(wTracker.addWarehouse(new Warehouse(warehouseID)));
	}
	
	
	/**
	 * Testing all possible cases for
	 * ending and enabling freight receipt
	 * for a warehouse.
	 * 
	 */
	@Test
	void testFreightStatus()
	{
		// Enabling freight receipt on a warehouse that already
		// has freight receipt enabled
		assertTrue(wTracker.enableFreight(15566));
		
		// Adding shipments to a warehouse that has freight receipt enabled
		assertTrue(wTracker.addShipment(15566, shipment1));
		
		// Ending freight receipt on a warehouse that is enabled
		assertTrue(wTracker.endFreight(15566));
		
		// Ending freight receipt on a warehouse already disabled
		assertTrue(wTracker.endFreight(15566));
		
		// Adding shipment to a warehouse with freight receipt disabled
		assertFalse(wTracker.addShipment(15566, shipment2));
		
		// Enable freight receipt on a warehouse that is disabled
		assertTrue(wTracker.enableFreight(15566));
		
		// Adding shipment to a warehouse with freight receipt enabled
		assertTrue(wTracker.addShipment(15566, shipment2));

			

		// Enabling freight receipt on a warehouse that already
		// has freight receipt enabled
		assertFalse(wTracker.enableFreight(15576));

		// Adding shipments to a warehouse that has freight receipt enabled
		assertFalse(wTracker.addShipment(15116, shipment1));

		// Ending freight receipt on a warehouse that is enabled
		assertFalse(wTracker.endFreight(12316));

		// Ending freight receipt on a warehouse already disabled
		assertFalse(wTracker.endFreight(23111));

		// Adding shipment to a warehouse with freight receipt disabled
		assertFalse(wTracker.addShipment(65411, shipment2));
		
	}
	
	/**
	 * Tests printDetails for various warehouses.
	 */
	@ParameterizedTest
	@ValueSource(ints = {12513, 14566, 15566})
	void testPrintDetails(int warehouseID)
	{
		wTracker.printWarehouseDetails(warehouseID);	
	}
	
	/**
	 * Validates that warehouse is adding 
	 * shipments correctly.
	 */
	@Test
	void testWarehouseSize()
	{
		assertEquals(0, wTracker.getWarehouseShipmentsSize(15566));
	}


	
	
	

}
