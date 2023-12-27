package com.project.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
 * Amit Kumar Modak 
 */

@Entity
@Table(name = "tbl_MenuCatagory")
public class MenuCatagory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menucatagory_id")
	private Integer menucatagory_id;
	
	@Column(name = "menucatagory_name", nullable = false, length = 100)
	private String menucatagory_name;
	
	@OneToMany(mappedBy = "menuCatagory", cascade = CascadeType.ALL)
	private List<MenuItem> menuItem;

	public MenuCatagory() {
		
	}

	public MenuCatagory(Integer menucatagory_id, String menucatagory_name, List<MenuItem> menuItem) {
		
		this.menucatagory_id = menucatagory_id;
		this.menucatagory_name = menucatagory_name;
		this.menuItem = menuItem;
	}

	public MenuCatagory(String menucatagory_name, List<MenuItem> menuItem) {
		
		this.menucatagory_name = menucatagory_name;
		this.menuItem = menuItem;
	}

	public MenuCatagory(String menucatagory_name) {
		
		this.menucatagory_name = menucatagory_name;
	}

	public Integer getMenucatagory_id() {
		return menucatagory_id;
	}

	public void setMenucatagory_id(Integer menucatagory_id) {
		this.menucatagory_id = menucatagory_id;
	}

	public String getMenucatagory_name() {
		return menucatagory_name;
	}

	public void setMenucatagory_name(String menucatagory_name) {
		this.menucatagory_name = menucatagory_name;
	}

	public List<MenuItem> getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(List<MenuItem> menuItem) {
		this.menuItem = menuItem;
	}

	@Override
	public String toString() {
		return "MenuCatagory [menucatagory_id=" + menucatagory_id + ", menucatagory_name=" + menucatagory_name + "]";
	}

	
	
	
}
