package com.project.demo;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.project.entity.Admin;
import com.project.entity.Customer;
import com.project.entity.MenuItem;
import com.project.entity.Order;


public class CustomerLogin {
	
	static Scanner sn = new Scanner(System.in);
	static int customerLoginId = 0;
	
	public CustomerLogin() {
		while(true) {
			System.out.print("Enter 1 to register otherwise enter any number to login:");
			int ch = sn.nextInt();
			if(ch == 1) {
				System.out.println("--Customer Registration--");
				System.out.print("Enter customer email : ");
				String customer_email = sn.next();
				int status = isCustomerAccount(customer_email);
				
			    if(status != 0)
			    {
			    	System.out.println("The email is already registered");
			    	
			    }
			    else
			    {
			    	addCustomer(customer_email);
			    }
			}
			else
			{
				break;
			}
		} 
		customerLogin();
	}
	private static int isCustomerAccount(String customer_email) {
		Session session = HibernetUtil.getSessionFactory().openSession();
	    Transaction transaction = null;
	    List<Customer> customerList = null;
	    try {
	        transaction = session.beginTransaction();
	        String hql = "FROM Customer WHERE customer_email = :customerEmail";
	        customerList = session.createQuery(hql, Customer.class)
	                .setParameter("customerEmail", customer_email)
	                .getResultList();
	        transaction.commit();
	    } 
	    catch (Exception e) {
	        if (transaction != null && transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace(); // Log or handle the exception appropriately
	    }
	    finally {
			session.close();
		}
	    if (customerList.size() != 0) {
			return 1; // have account

		} else {
			return 0;
		}
	}
	private static void customerLogin()
	{
		System.out.print("Enter Customer email: ");
		String c_email = sn.next();
		System.out.print("Enter Customer Password: ");
		String c_pass = sn.next();

		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from Customer where customer_email = :useremail and customer_password = :userpassword";
			List<Customer> customerList = session.createQuery(hql, Customer.class).setParameter("useremail", c_email)
					.setParameter("userpassword", c_pass).getResultList();
			if (customerList.size() == 0) {
				System.out.println("Invalid Details");
				System.out.println("Enter Details again");
				
			}

			else {
				System.out.println("Login Successful");
				for (Customer data : customerList) {
					customerLoginId = data.getCustomer_id();
				}
				customerDashboard();
			}
		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		} 
		finally {
			session.close();
		}
	}
	private static void customerDashboard() {
		int ch;
		do {
			System.out.println("Welcome to Customer Dashbord-------");
			System.out.println("For View Available Menu Details press 1");
			System.out.println("For View Order Details press 2");
			System.out.println("For Place new order press 3");
			System.out.println("For cancel existing order press 4");
			System.out.println("For updating profile press 5");
			System.out.println("For exit press 6");
			System.out.print("Enter your choice: ");
			ch = sn.nextInt();
			switch (ch) {
				case 1:
					viewAvailableMenu();
					break;
				case 2:
					viewOrder();
					break;
				case 3:
					addOrder();
					break;
				case 4:
					cancelOrder();
					break;
				case 5:
					updateProfile();
					break;
				case 6:
					System.out.println("Existing");
					break;
				default:
					System.out.println("Invalid choice");
					
			}
		} while( ch != 5);
		
	}
	private static void cancelOrder() {
		viewOrder();
		System.out.print("Enter id of the order : ");
		int id = sn.nextInt();

		Session session = HibernetUtil.getSessionFactory().openSession();;
		Transaction tn = null;
		try {
	        tn = session.beginTransaction();

	        String hql = "UPDATE Order SET order_status = :newStatus WHERE order_id = :orderId";
            int updatedEntities = session.createQuery(hql)
                    .setParameter("newStatus", "cancel")
                    .setParameter("orderId", id)
                    .executeUpdate();

	        if(updatedEntities == 1) {
	        	tn.commit();
	        	System.out.println("Order Cancelled Successfull");
	        }
	        else {
	        	System.out.println("Wrong id");
	        }
	    } catch (Exception e) {
	        if (tn != null) {
	            tn.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	private static void addOrder() {
		int c_id = customerLoginId;
		viewAvailableMenu();
//		List<Integer> menuList = new ArrayList<>();
		System.out.println("Enter menu id: ");
		int m_id = sn.nextInt();

		Session session = HibernetUtil.getSessionFactory().openSession();
		Transaction tn = null;
		MenuItem existingMenuItem = null;
		try {
			String hql = "from MenuItem where item_id = :mid";
			Query<MenuItem> query = session.createQuery(hql, MenuItem.class);
			query.setParameter("mid", m_id);
			existingMenuItem = query.getSingleResult();
			System.out.println("-----------------------------------");
			
		}
		 catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		}
		finally {
			session.close();
		}
		
		String order_status = "Placed";
		
		System.out.println("Enter order item number:");
		int item_number = sn.nextInt();
		
		int menu_price = item_number * existingMenuItem.getItem_price();
		Order newOrder = new Order(c_id, order_status, item_number, menu_price, null);
		newOrder.setMenu(existingMenuItem);
		
		session = HibernetUtil.getSessionFactory().openSession();
		tn = null;
		try {
			tn = session.beginTransaction();
			session.save(newOrder);
			tn.commit();
		} 
		catch (Exception e) {
	        if (tn != null && tn.isActive()) {
	            tn.rollback();
	        }
	        e.printStackTrace(); // Log or handle the exception appropriately
	    }
		finally {
			session.close();
		}
		
	}
	private static void viewOrder() {
		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from Order where cus_id =:id";
			List<Order> orderList = session.createQuery(hql, Order.class)
					.setParameter("id",customerLoginId)
					.getResultList();
			if (orderList.size() == 0) {
				System.out.println("No Item in the list");
			} else {
				System.out.println("order details:");
				System.out.println("-----------------------------------------------------------");
				for (Order order : orderList) {
	                System.out.println("-----------------------------------------------------------");
	                System.out.printf("Order ID: %d\tStatus: %s\tItem Number: %d\tPrice: %d\n",
	                        order.getOrder_id(), order.getOrder_status(),
	                        order.getOrder_itemNumber(), order.getOrder_price());
	                System.out.println("Menu Item Details: " + order.getMenu());
	                System.out.println("-----------------------------------------------------------");
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		}
		finally {
			session.close();
		}
		
	}
	private static void viewAvailableMenu() {
		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from MenuItem";
			List<MenuItem> menu = session.createQuery(hql, MenuItem.class).getResultList();
			if (menu.size() == 0) {
				System.out.println("No Item in the list");
			} else {
				System.out.println("Menu Items:");
				System.out.println("-----------------------------------------------------------");
				for (MenuItem menuItem : menu) {
//					System.out.println(menu);
					if(menuItem.getItem_isAvailable())
					{
						System.out.printf(
								"ID: %d\tName: %s\tCategory: %s\tType: %s\tPrice: %d\t\nDescription: %s\n",
								menuItem.getItem_id(), menuItem.getItem_name(), menuItem.getItem_catagory(),
								menuItem.getItem_type(), menuItem.getItem_price(),
								menuItem.getItem_description());
						System.out.println("-----------------------------------------------------------");
					}
					
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		}
		finally {
			session.close();
		}
		
	}
	private static void addCustomer(String customer_email) {
		System.out.print("Enter customer name : ");
		String customer_name = sn.next();
		
		String customer_type = "normal";

		System.out.print("Enter customer no : ");
		long customer_no = sn.nextLong();

		System.out.print("Enter customer address : ");
		String customer_address = sn.next();

		System.out.print("Enter customer password : ");
		String customer_password = sn.next();

		Customer customer = new Customer(customer_name, customer_type, customer_no, customer_email, customer_address, customer_password);

		Session session = HibernetUtil.getSessionFactory().openSession();
		Transaction tn = null;
		try {
			tn = session.beginTransaction();
			session.save(customer);
			tn.commit();
		} catch (Exception e) {
			tn.rollback();
			System.out.println("Exception occured " + e);
		}
		finally {
			session.close();
		}
		
	}
	private static void updateProfile() {
		viewProfile();
		System.out.println("To change name press 1");
		System.out.println("To change number press 2");
		System.out.println("To email press 3");
		System.out.println("To password press 4");
		System.out.println("To address press 5");
		System.out.print("Enter your choice: ");
		int ch = sn.nextInt();
		Session session = HibernetUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		int updatedEntities = 0;
		String hql;
		switch (ch) {
		case 1: // name
			System.out.println("Enter new Name: ");
			String name = sn.next();

			try {
				transaction = session.beginTransaction();
				hql = "UPDATE Customer SET customer_name = :newName WHERE customer_id = :c_id";
				updatedEntities = session.createQuery(hql)
						.setParameter("newName", name)
						.setParameter("c_id", customerLoginId)
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
			}
			break;
		case 2: // number
			System.out.println("Enter new number: ");
			int number = sn.nextInt();

			try {
				transaction = session.beginTransaction();
				hql = "UPDATE Customer SET customer_no = :newNumber WHERE customer_id = :c_id";
				updatedEntities = session.createQuery(hql)
						.setParameter("newNumber", number)
						.setParameter("c_id", customerLoginId)
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
			}
			break;
		case 3: // email
			System.out.println("Enter new email: ");
			String email = sn.next();
			int row = isCustomerAccount(email);
			if(row != 0)
			{
				System.out.println("This Email is already registered");
				System.out.println("Try another email");
			}
			else
			{
				try {
					transaction = session.beginTransaction();
					hql = "UPDATE Customer SET customer_email = :newEmail WHERE customer_id = :c_id";
					updatedEntities = session.createQuery(hql)
							.setParameter("newEmail", email)
							.setParameter("c_id", customerLoginId)
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
				}
			}
			
			break;
		case 4: //password
			System.out.println("Enter new password: ");
			String password = sn.next();
			try {
				transaction = session.beginTransaction();
				hql = "UPDATE Customer SET customer_password = :newPassword WHERE customer_id = :c_id";
				updatedEntities = session.createQuery(hql)
						.setParameter("newPassword", password)
						.setParameter("c_id", customerLoginId)
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
			}
			break;
		case 5: 		//address
			System.out.println("Enter new address: ");
			String address = sn.next();
			try {
				transaction = session.beginTransaction();
				hql = "UPDATE Customer SET customer_address = :newAddress WHERE customer_id = :c_id";
				updatedEntities = session.createQuery(hql)
						.setParameter("newAddress", address)
						.setParameter("c_id", customerLoginId)
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
			}
			break;
		default:
			System.out.println("Invalid Choice; Try Again");
		}
		session.close();
	}

	private static void viewProfile() {
		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from Customer where customer_id = :c_id";
			List<Customer> list = session.createQuery(hql, Customer.class)
					.setParameter("c_id", customerLoginId)
					.getResultList();
			
				System.out.println("Customeer details:");
				System.out.println("-----------------------------------------------------------");
				for (Customer data : list) {
					System.out.println("-----------------------------------------------------------");
					System.out.printf("Name: %s\tNumber: %d\tEmail: %s\tAddress: %s\tPassword: %s\n",
							data.getCustomer_name(),data.getCustomer_no(),data.getCustomer_email(), data.getCustomer_address(), data.getCustomer_password());
					System.out.println("-----------------------------------------------------------");
				}
			
		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		} finally {
			session.close();
		}
		
	}
}
