package com.project.dao;

public interface OrderDao {

	void addOrder();

	void addOrder(int c_id);

	void viewAllOrder();

	void viewAllOrder(int c_id);

	void updateOrder();

	void cancelOrder();

	void deleteOrder();

}
