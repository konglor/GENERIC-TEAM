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
	
	
	Warehouse warehouse1;
	Warehouse warehouse2;
	Warehouse warehouse3;
	
	WarehouseTracker wTracker;
	
	Shipment shipment1;
	Shipment shipment2;
	Shipment shipment3;
	Shipment shipment4;
	
	
	 
	
	public QATester() {
		//Consider using a singleton pattern for WarehouseController Class
		wTracker = new WarehouseTracker();
		
	
		// first warehouse and shipment
		wTracker.addWarehouse(new Warehouse(12513, true));
		wTracker.addShipment(12513, new Shipment("48934j", FreightType.valueOf("air"), 84, 1515354694451L));
		
		
		// second valid warehouse and shipment
		wTracker.addWarehouse(new Warehouse(15566, true));
		wTracker.addShipment(15566, new Shipment("1adf4", FreightType.valueOf("truck") , 354, 1515354694451L));
		
		//second warehouse and shipment
		wTracker.addWarehouse(new Warehouse(15566, false));
		
		
		
		//Initialize a bunch of shipment objects
		shipment1 = new Shipment("123tr", FreightType.valueOf("rail"), 74.0, 1515354694451L);
		shipment2 = new Shipment("4231e", FreightType.valueOf("ship"), 88.0, 1515254694451L);
		
		
		assertEquals(1, wTracker.addShipment(15566, shipment2));

		
	}
	
	/**
	 * Test to ensure that a warehouse key is always
	 * mapped to object's instance. This implies that
	 * the warehouse key will always be same as the
	 * warehouseID field
	 * @param warehouseID pre-defined(already existing) set of warehouseIDs
	 */
	@ParameterizedTest
	@ValueSource(ints = {12513,15566})
	void testWarehouseTrackerIDKeyMapping(int warehouseID) 
	{
		assertEquals(warehouseID, wTracker.getWarehouse(warehouseID).getWarehouseID());
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
		assertFalse(wTracker.addWarehouse(new Warehouse(warehouseID, true)));
	}
	
	
	/**
	 * Testing all possible cases for
	 * ending and enabling freight receipt
	 * for a warehouse.
	 * (-1:non-existent warehouse,
	 * 	 0: already disabled / enabled depending on case,
	 * 	 1: successfully added).
	 */
	@Test
	void testFreightStatus()
	{
		// Enabling freight receipt on a warehouse that already
		// has freight receipt enabled
		assertEquals(0,wTracker.enableFreight(15566));
		
		// Adding shipments to a warehouse that has freight receipt enabled
		assertEquals(1, wTracker.addShipment(15566, shipment1));
		
		// Ending freight receipt on a warehouse that is enabled
		assertEquals(1, wTracker.endFreight(15566));
		
		// Ending freight receipt on a warehouse already disabled
		assertEquals(0, wTracker.endFreight(15566));
		
		// Adding shipment to a warehouse with freight receipt disabled
		assertEquals(0, wTracker.addShipment(15566, shipment2));
		
		// Enable freight receipt on a warehouse that is disabled
		assertEquals(1, wTracker.enableFreight(15566));
		
		// Adding shipment to a warehouse with freight receipt enabled
		assertEquals(1, wTracker.addShipment(15566, shipment2));

			

		// Enabling freight receipt on a warehouse that already
		// has freight receipt enabled
		assertEquals(-1, wTracker.enableFreight(15576));

		// Adding shipments to a warehouse that has freight receipt enabled
		assertEquals(-1, wTracker.addShipment(15116, shipment1));

		// Ending freight receipt on a warehouse that is enabled
		assertEquals(-1, wTracker.endFreight(12316));

		// Ending freight receipt on a warehouse already disabled
		assertEquals(-1, wTracker.endFreight(23111));

		// Adding shipment to a warehouse with freight receipt disabled
		assertEquals(-1, wTracker.addShipment(65411, shipment2));
		
	}
	
	/**
	 * Tests print details for various warehouses
	 */
	
	@ParameterizedTest
	@ValueSource(ints = {12513, 14566, 15566})
	void testPrintDetails(int warehouseID)
	{
		wTracker.printWarehouseDetails(warehouseID);	
	}
	
	/**
	 * Validates that warehouse is adding 
	 * shipments correctly
	 * @param size
	 */
	@Test
	void testWarehouseSize()
	{
		assertEquals(2, wTracker.getWarehouse(15566).getShipmentSize());
	}


	
	
	

}
