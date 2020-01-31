package com.generic.main;
import java.util.List;


public class Warehouse {
	private String warehouseID;
	private boolean freightReceipt;
	private List<Shipment> shipments;
	
	public Warehouse(String id, boolean receipt) {
		warehouseID = id;
		freightReceipt = receipt;
	}
	
	public void enableFreight() {
		freightReceipt = true;
	}
	
	public void disableFreight() {
		freightReceipt = false;
	}
	
	public boolean receivingFreight() {
		return freightReceipt;
	}
	
	public String getWarehouseID() {
		return warehouseID;
	}
	
	public void addShipment(Shipment shipment) {
		shipments.add(shipment);
	}
}
