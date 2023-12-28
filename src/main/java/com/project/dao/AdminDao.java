package com.project.dao;

public interface AdminDao {

	void addAdmin();

	void viewAdmin();

	void updateAdmin();

	void deleteAdmin();

	int isAdminExists(String new_admin);
	
}
