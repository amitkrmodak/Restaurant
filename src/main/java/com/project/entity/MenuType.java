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
@Table(name = "tbl_MenuType")
public class MenuType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menutype_id")
	private Integer menutype_id;
	
	@Column(name = "menutype_name", nullable = false, length = 100)
	private String menutype_name;
	
	@OneToMany(mappedBy = "menuType", cascade = CascadeType.ALL)
	private List<MenuItem> menuItem;

	public MenuType() {
		
	}

	public MenuType(Integer menutype_id, String menutype_name, List<MenuItem> menuItem) {
		super();
		this.menutype_id = menutype_id;
		this.menutype_name = menutype_name;
		this.menuItem = menuItem;
	}

	public MenuType(String menutype_name) {
		super();
		this.menutype_name = menutype_name;
	}

	public Integer getMenutype_id() {
		return menutype_id;
	}

	public void setMenutype_id(Integer menutype_id) {
		this.menutype_id = menutype_id;
	}

	public String getMenutype_name() {
		return menutype_name;
	}

	public void setMenutype_name(String menutype_name) {
		this.menutype_name = menutype_name;
	}

	public List<MenuItem> getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(List<MenuItem> menuItem) {
		this.menuItem = menuItem;
	}

	@Override
	public String toString() {
		return "MenuType [menutype_id=" + menutype_id + ", menutype_name=" + menutype_name + "]";
	}
	 
	
	
	
}
