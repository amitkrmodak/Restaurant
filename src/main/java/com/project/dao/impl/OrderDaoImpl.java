package com.project.dao.impl;

import java.util.List;
import java.util.Scanner;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.project.util.HibernetUtil;
import com.project.dao.OrderDao;
import com.project.entity.Customer;
import com.project.entity.MenuItem;
import com.project.entity.Order;
import com.project.exception.CustomException;

/*
 * Amit Kumar Modak 
 */
public class OrderDaoImpl implements OrderDao {
//	public static void main(String[] args) {
//		addOrder();
//		viewAllOrder();
//		updateOrder();
//		cancelOrder();
//
//	}
	static int total_Entities = 0;
	static int customer_total_entities = 0;
	static List<Order> customerOrderList = null;

	public OrderDaoImpl() {

	}

	public OrderDaoImpl(int choice) {
		switch (choice) {
		case 1:
			addOrder();
			break;
		case 2:
			viewAllOrder();
			break;
		case 3:
			updateOrder();
			break;
		case 4:
			deleteOrder();
			break;
		case 5:
			cancelOrder();
			break;
		}
	}

	static CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl();
	static MenuItemDaoImpl menuItemDaoImpl = new MenuItemDaoImpl();
	static Scanner sn = new Scanner(System.in);

	@Override
	public void addOrder() {
		customerDaoImpl.viewCustomer();
			if (CustomerDaoImpl.customer_list.size() != 0) {
				System.out.print("Enter customer id:");
				int c_id = sn.nextInt();

				menuItemDaoImpl.getMenuItem();
				menuItemDaoImpl.showAvailableMenuItem();
				if (MenuItemDaoImpl.menu_item_list.size() != 0) {
					System.out.println("Enter menu id: ");
					int m_id = sn.nextInt();

					Session session = HibernetUtil.getSessionFactory().openSession();
					Transaction tn = null;
					Customer existingCustomer = null;
					try {
						String hql = "from Customer where customer_id = :id";
						Query<Customer> query = session.createQuery(hql, Customer.class);
						query.setParameter("id", c_id);
						existingCustomer = query.getSingleResult();
						System.out.println("-----------------------------------");

					} catch (Exception e) {
						System.out.println("Throw Custom Exception");
						throw new CustomException("Exception Occured: " + e.getMessage());
						
					}

					MenuItem existingMenuItem = null;
					try {
						String hql = "from MenuItem where item_id = :mid";
						Query<MenuItem> query = session.createQuery(hql, MenuItem.class);
						query.setParameter("mid", m_id);
						existingMenuItem = query.getSingleResult();
						System.out.println("-----------------------------------");

					} catch (Exception e) {
						System.out.println("Throw Custom Exception");
						throw new CustomException("Exception Occured: " + e.getMessage());
					}

					String order_status = "Placed";

					System.out.println("Enter order item number:");
					int item_number = sn.nextInt();

					int menu_price = item_number * existingMenuItem.getItem_price();
					Order newOrder = new Order(order_status, item_number, menu_price);
					newOrder.setMenu(existingMenuItem);
					newOrder.setCustomer(existingCustomer);

					session = HibernetUtil.getSessionFactory().openSession();
					tn = null;
					try {
						tn = session.beginTransaction();
						session.save(newOrder);
						tn.commit();
					} catch (Exception e) {
						if (tn != null && tn.isActive()) {
							tn.rollback();
						}
						e.printStackTrace(); // Log or handle the exception appropriately
						System.out.println("Throw Custom Exception");
						throw new CustomException("Exception Occured: " + e.getMessage());
					} finally {
						session.close();
					}
				} else {
					System.out.println("Enter Menu first");
				}
			} else {
				System.out.println("Enter Customer first");
			}

	}

	@Override
	public void addOrder(int c_id) {
		
		menuItemDaoImpl.getMenuItem();
		menuItemDaoImpl.showAvailableMenuItem();
		if (MenuItemDaoImpl.menu_item_list.size() != 0) {
			System.out.println("Enter menu id: ");
			int m_id = sn.nextInt();

			Session session = HibernetUtil.getSessionFactory().openSession();
			Transaction tn = null;
			Customer existingCustomer = null;
			try {
				String hql = "from Customer where customer_id = :id";
				Query<Customer> query = session.createQuery(hql, Customer.class);
				query.setParameter("id", c_id);
				existingCustomer = query.getSingleResult();
				System.out.println("-----------------------------------");

			} catch (Exception e) {
				System.out.println("Throw Custom Exception");
				throw new CustomException("Exception Occured: " + e.getMessage());
			}

			MenuItem existingMenuItem = null;
			try {
				String hql = "from MenuItem where item_id = :mid";
				Query<MenuItem> query = session.createQuery(hql, MenuItem.class);
				query.setParameter("mid", m_id);
				existingMenuItem = query.getSingleResult();
				System.out.println("-----------------------------------");

			} catch (Exception e) {
				System.out.println("Throw Custom Exception");
				throw new CustomException("Exception Occured: " + e.getMessage());
			}

			String order_status = "Placed";

			System.out.println("Enter order item number:");
			int item_number = sn.nextInt();

			int menu_price = item_number * existingMenuItem.getItem_price();
			Order newOrder = new Order(order_status, item_number, menu_price);
			newOrder.setMenu(existingMenuItem);
			newOrder.setCustomer(existingCustomer);

			session = HibernetUtil.getSessionFactory().openSession();
			tn = null;
			try {
				tn = session.beginTransaction();
				session.save(newOrder);
				tn.commit();
			} catch (Exception e) {
				if (tn != null && tn.isActive()) {
					tn.rollback();
				}
				e.printStackTrace(); // Log or handle the exception appropriately
				System.out.println("Throw Custom Exception");
				throw new CustomException("Exception Occured: " + e.getMessage());
			} finally {
				session.close();
			}
		}
	}

	@Override
	public void viewAllOrder() {
		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from Order";
			List<Order> orderList = session.createQuery(hql, Order.class).getResultList();
			total_Entities = orderList.size();
			if (orderList.size() == 0) {
				System.out.println("No Item in the list");
			} else {
				System.out.println("order details:");
				System.out.println("-----------------------------------------------------------");
				for (Order order : orderList) {
					System.out.println("-----------------------------------------------------------");
					System.out.printf("Order ID: %d\tStatus: %s\tItem Number: %d\tPrice: %d\n", order.getOrder_id(),
							order.getOrder_status(), order.getOrder_itemNumber(), order.getOrder_price());
					System.out.println("Customer Details: " + order.getCustomer());
					System.out.println("Menu Item Details: " + order.getMenu());
					System.out.println("-----------------------------------------------------------");
				}
			}
		} catch (Exception e) {
			System.out.println("Throw Custom Exception");
			throw new CustomException("Exception Occured: " + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public void viewAllOrder(int c_id) {
		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from Order where customer_id =:id";
			customerOrderList = session.createQuery(hql, Order.class).setParameter("id", c_id).getResultList();
			customer_total_entities = customerOrderList.size();
			if (customerOrderList.size() == 0) {
				System.out.println("No Item in the list");
			} else {
				System.out.println("order details:");
				System.out.println("-----------------------------------------------------------");
				for (Order order : customerOrderList) {
					System.out.println("-----------------------------------------------------------");
					System.out.printf("Order ID: %d\tStatus: %s\tItem Number: %d\tPrice: %d\n", order.getOrder_id(),
							order.getOrder_status(), order.getOrder_itemNumber(), order.getOrder_price());
					System.out.println("Menu Item Details: " + order.getMenu());
					System.out.println("-----------------------------------------------------------");
				}
			}
		} catch (Exception e) {
			System.out.println("Throw Custom Exception");
			throw new CustomException("Exception Occured: " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void updateOrder() {
		int ch;
		viewAllOrder();
		System.out.println("Enter id of the order: ");
		int id = sn.nextInt();
		System.out.println("To change customer id press 1");
		System.out.println("To change order status press 2");
		System.out.println("To change item number press 3");
		System.out.print("Enter your choice: ");
		ch = sn.nextInt();
		Session session = HibernetUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		int updatedEntities = 0;
		String hql;
		switch (ch) {
		case 1: // customer ID
			customerDaoImpl.viewCustomer();
			System.out.println("Enter customer ID: ");
			int cusId = sn.nextInt();

			try {
				transaction = session.beginTransaction();
				hql = "UPDATE Order SET cus_id = :newCusId WHERE order_id = :orderId";
				updatedEntities = session.createQuery(hql).setParameter("newCusId", cusId).setParameter("orderId", id)
						.executeUpdate();
				transaction.commit();
				if (updatedEntities == 0) {
					System.out.println("No matching data");
				} else {
					System.out.println("Update Successfully");
				}
			} catch (Exception e) {
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				e.printStackTrace(); // Log or handle the exception appropriately
				System.out.println("Throw Custom Exception");
				throw new CustomException("Exception Occured: " + e.getMessage());
			}
			break;
		case 2: // order status
			System.out.println("Enter order status: ");
			String status = sn.next();

			try {
				transaction = session.beginTransaction();
				hql = "UPDATE Order SET order_status = :newStatus WHERE order_id = :orderId";
				updatedEntities = session.createQuery(hql).setParameter("newStatus", status).setParameter("orderId", id)
						.executeUpdate();
				transaction.commit();
				if (updatedEntities == 0) {
					System.out.println("No matching data");
				} else {
					System.out.println("Update Successfully");
				}
			} catch (Exception e) {
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				e.printStackTrace(); // Log or handle the exception appropriately
				System.out.println("Throw Custom Exception");
				throw new CustomException("Exception Occured: " + e.getMessage());
			}
			break;
		case 3: // item number
			Query<Order> query;
			try {
				transaction = session.beginTransaction();
				hql = "FROM Order WHERE order_id = :orderId";
				query = session.createQuery(hql, Order.class);
				query.setParameter("orderId", id);
				transaction.commit();

			} catch (Exception e) {
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				e.printStackTrace(); // Log or handle the exception appropriately
				System.out.println("Throw Custom Exception");
				throw new CustomException("Exception Occured: " + e.getMessage());
			}
			Order updatedOrder = session.get(Order.class, id);
			if (updatedOrder.getOrder_status() != "out for delivery") {
				System.out.println("Enter item number: ");
				int itemNumber = sn.nextInt();
				int newPrice = itemNumber * (updatedOrder.getOrder_price() / updatedOrder.getOrder_itemNumber());
				try {
					transaction = session.beginTransaction();
					hql = "UPDATE Order SET order_itemNumber = :newItemNumber, order_price = :newOrderPrice WHERE order_id = :orderId";
					updatedEntities = session.createQuery(hql).setParameter("newItemNumber", itemNumber)
							.setParameter("newOrderPrice", newPrice).setParameter("orderId", id).executeUpdate();
					transaction.commit();
					if (updatedEntities == 0) {
						System.out.println("No matching data");
					} else {
						System.out.println("Update Successfully");
					}
				} catch (Exception e) {
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					e.printStackTrace(); // Log or handle the exception appropriately
					System.out.println("Throw Custom Exception");
					throw new CustomException("Exception Occured: " + e.getMessage());
				}
			} else {
				System.out.println("Can not update");
				System.out.println("The order is out for delivery");
			}
			break;
		default:
			System.out.println("Invalid Choice; Try Again");
		}
		session.close();
	}

	@Override
	public void cancelOrder() {
		if (customer_total_entities != 0) {
			System.out.print("Enter id of the order : ");
			int id = sn.nextInt();
			Session session = HibernetUtil.getSessionFactory().openSession();
			Transaction tn = null;
			try {
				String hql = "from Order where order_id =:id and order_status = :status";
				List<Order> orderList = session.createQuery(hql, Order.class).setParameter("id", id)
						.setParameter("status", "delivered").getResultList();
				if (orderList.size() != 0) {
					System.out.println("This item is already delivered");
					System.out.println("Can not delete now");
				} else {
					try {
						tn = session.beginTransaction();

						hql = "UPDATE Order SET order_status = :newStatus WHERE order_id = :orderId";
						int updatedEntities = session.createQuery(hql).setParameter("newStatus", "cancel")
								.setParameter("orderId", id).executeUpdate();

						if (updatedEntities == 1) {
							tn.commit();
							System.out.println("Order Cancelled Successfull");
						} else {
							System.out.println("Wrong id");
						}
					} catch (Exception e) {
						if (tn != null) {
							tn.rollback();
						}
						e.printStackTrace();
						System.out.println("Throw Custom Exception");
						throw new CustomException("Exception Occured: " + e.getMessage());
					}
				}
			} catch (Exception e) {
				System.out.println("Throw Custom Exception");
				throw new CustomException("Exception Occured: " + e.getMessage());
			} finally {
				session.close();
			}
		}
	}

	@Override
	public void deleteOrder() {
		viewAllOrder();
		if (total_Entities != 0) {
			System.out.println("Enter Order id: ");
			int id = sn.nextInt();

			Session session = HibernetUtil.getSessionFactory().openSession();
			Transaction tn = null;
			try {
				tn = session.beginTransaction();

				String hql = "delete from Order where order_id = :id";
				int updatedEntities = session.createQuery(hql).setParameter("id", id).executeUpdate();
				if (updatedEntities == 1) {
					tn.commit();
					System.out.println("Deleted Successfull");
				} else {
					System.out.println("Wrong id");
				}
			} catch (Exception e) {
				System.out.println("Can not delete");
			} finally {
				session.close();
				// getMenuType();
			}
		}

	}
}
