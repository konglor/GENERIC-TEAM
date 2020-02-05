package com.generic.main;

public class Shipment {
	private String shipmentID;
	private String freight;
	private float weight;
	private long receiptDate; //Need to figure out the date format
	
	public Shipment(String shipmentID, String freight, float weight, long receiptDate) {
		this.shipmentID = shipmentID;
		this.freight = freight;
		this.weight = weight;
		this.receiptDate = receiptDate;
	}

	public String getShipmentID() {
		return shipmentID;
	}

	public void setShipmentID(String shipmentID) {
		this.shipmentID = shipmentID;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public long getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(long receiptDate) {
		this.receiptDate = receiptDate;
	}
	
}
