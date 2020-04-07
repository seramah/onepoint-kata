package com.onepoint.kata.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author srh
 *
 */
@Table(name = "KATA_BILL")
@Entity
public class Bill {
	
	@Id
    @GeneratedValue
	/* id de la facture */
	private long id;
	@Column
	/* montant de la facture */
	private double amount;
	@Column
	/* date de création de la facture */
	private Date creationDate;
	
	@Column
	/*Montant de la livraison*/
	private int shipmentAmount;
	
	
	
	
	// ---------- GETTERS/SETTERS --------------
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
		
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getShipmentAmount() {
		return shipmentAmount;
	}
	public void setShipmentAmount(int shipmentAmount) {
		this.shipmentAmount = shipmentAmount;
	}
	
}
