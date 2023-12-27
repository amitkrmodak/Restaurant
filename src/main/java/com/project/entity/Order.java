package com.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * Amit Kumar Modak 
 */

@Entity
@Table(name = "tbl_order")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Integer order_id;
	
	@ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
	
	@Column(name = "order_status", nullable = false, length = 50)
	private String order_status;
	
	@Column(name = "order_itemNumber", nullable = false, length = 50)
	private int order_itemNumber;
	
	@Column(name = "order_price", nullable = false, length = 50)
	private int order_price;
	
//	@ElementCollection
//    @CollectionTable(name = "order_menu", joinColumns = @JoinColumn(name = "order_id"))
//    @Column(name = "menu_item")
//    private List<Integer> menu;
	
//	@Column(name = "order_menu", nullable = false, length = 50)
//	private List<Integer> menu;
	
	@ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menu;

	public Order() {
		
	}

	public Order(Integer order_id, Customer customer, String order_status, int order_itemNumber, int order_price,
			MenuItem menu) {
		super();
		this.order_id = order_id;
		this.customer = customer;
		this.order_status = order_status;
		this.order_itemNumber = order_itemNumber;
		this.order_price = order_price;
		this.menu = menu;
	}

	public Order(String order_status, int order_itemNumber, int order_price) {
		super();
		this.order_status = order_status;
		this.order_itemNumber = order_itemNumber;
		this.order_price = order_price;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public int getOrder_itemNumber() {
		return order_itemNumber;
	}

	public void setOrder_itemNumber(int order_itemNumber) {
		this.order_itemNumber = order_itemNumber;
	}

	public int getOrder_price() {
		return order_price;
	}

	public void setOrder_price(int order_price) {
		this.order_price = order_price;
	}

	public MenuItem getMenu() {
		return menu;
	}

	public void setMenu(MenuItem menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", order_status=" + order_status + ", order_itemNumber="
				+ order_itemNumber + ", order_price=" + order_price + "]";
	}
	
	
	
}
