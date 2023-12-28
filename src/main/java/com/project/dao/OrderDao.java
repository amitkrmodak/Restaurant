package com.project.dao;
/*
 * Amit Kumar Modak 
 */
public interface OrderDao {

	void addOrder();

	void addOrder(int c_id);

	void viewAllOrder();

	void viewAllOrder(int c_id);

	void updateOrder();

	void cancelOrder();

	void deleteOrder();

}
