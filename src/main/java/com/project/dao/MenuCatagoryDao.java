package com.project.dao;

import com.project.entity.MenuCatagory;

public interface MenuCatagoryDao {

	void addMenuCatagory();

	MenuCatagory getDataById(int id);

	void getMenuCatagory();

	void showMenuCatagory();

	void updateMenuCatagory();

	void deleteMenuCatagory();

	int isCatagoryExists(String new_catagory);

}
