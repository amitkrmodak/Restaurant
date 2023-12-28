package com.project.dao;

import com.project.entity.MenuType;

public interface MenuTypeDao {

	void addMenuType();

	void getMenuType();

	void showMenuType();

	MenuType getDataById(int id);

	void updateMenuType();

	void deleteMenuType();

	int isTypeExists(String new_type);

}
