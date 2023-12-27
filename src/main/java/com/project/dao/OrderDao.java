package com.project.dao;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.project.demo.HibernetUtil;
import com.project.entity.Customer;
import com.project.entity.MenuItem;
import com.project.entity.Order;
/*
 * Amit Kumar Modak 
 */
public class OrderDao {
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
	public OrderDao() {

	}

	public OrderDao(int choice) {
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

	static CustomerDao objCustomerDao = new CustomerDao();
	static MenuItemDao objMenuItemDao = new MenuItemDao();
	static Scanner sn = new Scanner(System.in);

	private static void addOrder() {
		CustomerDao.viewCustomer();
		if (CustomerDao.customer_list.size() != 0) {
			System.out.print("Enter customer id:");
			int c_id = sn.nextInt();

			MenuItemDao.getMenuItem();
			MenuItemDao.showAvailableMenuItem();
			if (MenuItemDao.menu_item_list.size() != 0) {
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
					System.out.println("Exception Ocuured" + e);
				}

				MenuItem existingMenuItem = null;
				try {
					String hql = "from MenuItem where item_id = :mid";
					Query<MenuItem> query = session.createQuery(hql, MenuItem.class);
					query.setParameter("mid", m_id);
					existingMenuItem = query.getSingleResult();
					System.out.println("-----------------------------------");

				} catch (Exception e) {
					System.out.println("Exception Ocuured" + e);
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

	public static void addOrder(int c_id) {
		MenuItemDao.getMenuItem();
		MenuItemDao.showAvailableMenuItem();
		if (MenuItemDao.menu_item_list.size() != 0) {
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
				System.out.println("Exception Ocuured" + e);
			}

			MenuItem existingMenuItem = null;
			try {
				String hql = "from MenuItem where item_id = :mid";
				Query<MenuItem> query = session.createQuery(hql, MenuItem.class);
				query.setParameter("mid", m_id);
				existingMenuItem = query.getSingleResult();
				System.out.println("-----------------------------------");

			} catch (Exception e) {
				System.out.println("Exception Ocuured" + e);
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
			} finally {
				session.close();
			}
		}
	}

	private static void viewAllOrder() {
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
			System.out.println("Exception Ocuured" + e);
		} finally {
			session.close();
		}

	}

	public static void viewAllOrder(int c_id) {
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
			System.out.println("Exception Ocuured" + e);
		} finally {
			session.close();
		}
	}

	private static void updateOrder() {
		viewAllOrder();
		if (total_Entities != 0) {
		}

	}

	public static void cancelOrder() {
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
					}
				}
			} catch (Exception e) {
				System.out.println("Exception Ocuured" + e);
			} finally {
				session.close();
			}
		}
	}

	private static void deleteOrder() {
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
