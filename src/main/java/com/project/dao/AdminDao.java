package com.project.dao;
/*
 * Amit Kumar Modak 
 */
public interface AdminDao {

	void addAdmin();

	void viewAdmin();

	void updateAdmin();

	void deleteAdmin();

	int isAdminExists(String new_admin);
	
}
