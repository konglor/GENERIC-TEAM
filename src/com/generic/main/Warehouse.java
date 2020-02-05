package com.generic.main;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	@Override
	public String toString()
	{
		
		
		String headerString  = String.format("|WAREHOUSEID: %d| FREIGHT RECEIPT STATUS: %s| SHIPMENT AVALIABLE: %d|"
											, warehouseID, (freightReceipt) ? "ENABLED" : "ENDED", shipments.size());
		
		StringBuilder warehouseInfo = new StringBuilder("");
		
		for (int i = 0; i <= headerString.length(); i++)
		{
			warehouseInfo.append("-");
		}
		
		warehouseInfo.append("\n" + headerString);
		warehouseInfo.append("\n*****************************************************************************");
		warehouseInfo.append("\n          *SHIPMENT RECEIVED*");
		warehouseInfo.append("\n****************************************");
		int count = 0;
		for (Shipment shipment : shipments)
		{
			count++;
			String shipmentID = shipment.getShipmentID();
			double weight = shipment.getWeight();
			long receiptDate = shipment.getReceiptDate();
			
			
			String shipmentInfo = String.format("%d.Shipment_Id: %s\n  Weight: %.1f\n  Receipt_Date: %s",
												count, shipmentID, weight, milliToDate(receiptDate));
			
			warehouseInfo.append("\n" + shipmentInfo);
			warehouseInfo.append("\n****************************************");
			
		}
		
		return warehouseInfo.toString();
	
	}
	private String milliToDate(long milliDate)
	{
		DateFormat simple = new SimpleDateFormat("dd MMMMM yyyy HH:mm:ss");
		
		Date result = new Date(milliDate);
		
		return simple.format(result);
		
	}
}
