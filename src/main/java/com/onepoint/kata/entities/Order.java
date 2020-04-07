package com.onepoint.kata.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author srh
 *
 */
@Table(name = "KATA_ORDER")
@Entity
public class Order {
	@Id
    @GeneratedValue
	/* id de la commande */
	private long id;
	@Column
	/* status de la commande */
	private OrderStatus status;
	/* liste des produits */
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name ="KATA_ORDER_PRODUCTS")
	private List<Product> products;
	/* montant de la livraison */

	
	@OneToOne
	@JoinColumn(name ="id")
	private Bill bill;
	
	@Column
	private double totalAmount; 
	
	@Column 
	private int weight;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public List<Product> getProducts() {
		return products;
	}
	public int getShipmentAmount() {
		return (this.getWeight() % 10) * 25;
	}

	public double getTotalAmount() {
		return this.totalAmount;
	}

	public int getWeight() {
		return this.weight;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
		if (this.products!=null && !this.products.isEmpty()) {
			this.totalAmount = this.products.stream().mapToDouble(Product::getPrice).sum();
			this.weight = this.products.stream().mapToInt(Product::getWeight).sum();
			if (this.status.equals(OrderStatus.PAID)) {
				this.genereateBill();
			}
		}
	}
	
	
	// -------------- Méthodes privées ------------------
	/**
	 * 
	 */
	private void genereateBill() {
		if (this.bill == null) {
			this.bill = new Bill();
			this.bill.setCreationDate(new Date());
			double amount = this.getTotalAmount();
			if (amount > 1000) {
				// 5% de réduction
				amount = amount  -  ((amount * 5) / 100);
				amount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP).doubleValue();
				
			}
			this.bill.setAmount(amount);
			
			this.bill.setShipmentAmount(this.getShipmentAmount());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
