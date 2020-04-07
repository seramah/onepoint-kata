package com.onepoint.kata.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author srh
 *
 */
@Table(name = "KATA_PRODUCT")
@Entity
public class Product {
	@Id
    @GeneratedValue
	/* id du produit */
	private long id;
	@Column
	/* nom du produit */
	private String name;
	@Column
	/* prix du produit */
	private double price;
	@Column
	/* poids du produit */
	private int weight;
	
	// ---------- GETTERS/SETTERS --------------
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
