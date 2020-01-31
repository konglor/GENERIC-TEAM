package com.generic.main;

public class Shipment {
	private String shipmentID;
	private String freight;
	private float weight;
	private int receiptDate; //Need to figure out the date format
	
	public Shipment(String id, String f, float w, int d) {
		shipmentID = id;
		freight = f;
		weight = w;
		receiptDate = d;
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

	public int getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(int receiptDate) {
		this.receiptDate = receiptDate;
	}
	
}
