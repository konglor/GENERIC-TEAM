package com.generic.model;

/**
 * @author Justin Caughlan, Seyi Ola
 * This models a Shipment
 */

public class Shipment {
	private String shipmentID; //shipment identification number
	private FreightType freight; //freight type
	private double weight; // shipment weight
	private long receiptDate; //Need to figure out the date format
	
	/**
	 * Constructs a new Shipment
	 * @param shipmentID shipment identification number
	 * @param freight freight type
	 * @param weight shipment weight
	 * @param receiptDate shipment receipt
	 */
	public Shipment(String shipmentID, FreightType freight, double weight, long receiptDate) {
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

	
	public FreightType getFreight() {
		return freight;
	}

	public void setFreight(FreightType freight) {
		this.freight = freight;
	}

	public double getWeight() {
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
