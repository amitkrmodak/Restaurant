package com.project.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
 * Amit Kumar Modak 
 */

@Entity
@Table(name = "tbl_menuItem")
public class MenuItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Integer item_id;
	
	@Column(name = "item_name", nullable = false, length = 200)
	private String item_name;
	
	@ManyToOne
    @JoinColumn(name = "item_catagory_id")
    private MenuCatagory menuCatagory;  
	
	@ManyToOne
    @JoinColumn(name = "item_type_id")
    private MenuType menuType;             
	
	@Column(name = "item_price", nullable = false, length = 10)
	private Integer item_price;
	
	@Column(name = "item_isAvailable", nullable = false, length = 15)
	private Boolean item_isAvailable;
	
	@Column(name = "item_description", length = 500)
	private String item_description;
	
	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
	private List<Order> orders;

	public MenuItem() {
		
	}

	public MenuItem(String item_name, Integer item_price, Boolean item_isAvailable, String item_description) {
		
		this.item_name = item_name;
		this.item_price = item_price;
		this.item_isAvailable = item_isAvailable;
		this.item_description = item_description;
	}

	public MenuItem(Integer item_id, String item_name, MenuCatagory menuCatagory, MenuType menuType, Integer item_price,
			Boolean item_isAvailable, String item_description, List<Order> orders) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.menuCatagory = menuCatagory;
		this.menuType = menuType;
		this.item_price = item_price;
		this.item_isAvailable = item_isAvailable;
		this.item_description = item_description;
		this.orders = orders;
	}

	public MenuItem(String item_name, MenuCatagory menuCatagory, MenuType menuType, Integer item_price,
			Boolean item_isAvailable, String item_description, List<Order> orders) {
		super();
		this.item_name = item_name;
		this.menuCatagory = menuCatagory;
		this.menuType = menuType;
		this.item_price = item_price;
		this.item_isAvailable = item_isAvailable;
		this.item_description = item_description;
	}

	public Integer getItem_id() {
		return item_id;
	}

	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public MenuCatagory getMenuCatagory() {
		return menuCatagory;
	}

	public void setMenuCatagory(MenuCatagory menuCatagory) {
		this.menuCatagory = menuCatagory;
	}

	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

	public Integer getItem_price() {
		return item_price;
	}

	public void setItem_price(Integer item_price) {
		this.item_price = item_price;
	}

	public Boolean getItem_isAvailable() {
		return item_isAvailable;
	}

	public void setItem_isAvailable(Boolean item_isAvailable) {
		this.item_isAvailable = item_isAvailable;
	}

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "MenuItem [item_id=" + item_id + ", item_name=" + item_name + ", menuCatagory=" + menuCatagory
				+ ", menuType=" + menuType + ", item_price=" + item_price + ", item_isAvailable=" + item_isAvailable
				+ ", item_description=" + item_description + ", orders=" + orders + "]";
	}
	
	
	
}
