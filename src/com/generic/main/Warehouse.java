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
	
	public void addShipment(String id, String freight, float weight, int date) {
		if(freightReceipt = true) {
			Shipment s = new Shipment(id, freight, weight, date);
			shipments.add(s);
		}else {
			System.out.println("Warehouse " + warehouseID + "is not currently accepting shipments");
		}
	}
}
