package com.project.dao;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.demo.HibernetUtil;
import com.project.entity.Customer;
import com.project.entity.Order;

/*
 * Amit Kumar Modak 
 */
public class CustomerDao {
	static Scanner sn = new Scanner(System.in);

//	public static void main(String[] args) {
////		addCustomer();
//		viewCustomer();
////		updateCustomer(2);
////		deleteCustomer();
//	}
	public CustomerDao() {

	}

//	public CustomerDao(int choice) {
//		switch (choice) {
//		case 1:
//			addCustomer();
//			break;
//		case 2:
//			viewCustomer();
//			break;
//		case 3:
//			updateCustomer();
//			break;
//		case 4:
//			deleteCustomer();
//			break;
//		}
//	}
	public static List<Customer> customer_list = null;

	public static void addCustomer() {

		System.out.print("Enter customer email : ");
		String customer_email = sn.next();
		if (isCustomerAccount(customer_email) == 1) {
			System.out.println("Already registered");
		} else {
			System.out.print("Enter customer name : ");
			String customer_name = sn.next();

			System.out.print("Enter customer no : ");
			long customer_no = sn.nextLong();

			System.out.print("Enter customer address : ");

			String customer_address = sn.next();

			System.out.print("Enter customer password : ");
			String customer_password = sn.next();
			
			PasswordProtect objPasswordProtect = new PasswordProtect();
			String protected_password = objPasswordProtect.encryptPassword(customer_password);

			Customer customer = new Customer(customer_name, customer_no, customer_email, customer_address,
					protected_password);

			Session session = HibernetUtil.getSessionFactory().openSession();
			Transaction tn = null;
			try {
				tn = session.beginTransaction();
				session.save(customer);
				tn.commit();
				System.out.println("Customer added successfully");
			} catch (Exception e) {
				tn.rollback();
				System.out.println("Exception occured " + e);
			} finally {
				session.close();
			}
		}

	}
	public static void viewCustomer() {
		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from Customer";
			customer_list = session.createQuery(hql, Customer.class).getResultList();
			if (customer_list.size() == 0) {
				System.out.println("No Item in the list");
			} else {
				System.out.println("customer details:");
				System.out.println("-----------------------------------------------------------");
				for (Customer data : customer_list) {
					System.out.printf("ID: %d\tName: %s\tNo: %d\tEmail: %s\tAddress: %s\tPassword: %s\n",
							data.getCustomer_id(), data.getCustomer_name(),
							data.getCustomer_no(), data.getCustomer_email(), data.getCustomer_address(),
							data.getCustomer_password());
					System.out.println("-----------------------------------------------------------");
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		} finally {
			session.close();
		}
	}

	public static void updateCustomer(int customerLoginId) {
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
				updatedEntities = session.createQuery(hql).setParameter("newName", name)
						.setParameter("c_id", customerLoginId).executeUpdate();
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
				updatedEntities = session.createQuery(hql).setParameter("newNumber", number)
						.setParameter("c_id", customerLoginId).executeUpdate();
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
			if (row != 0) {
				System.out.println("This Email is already registered");
				System.out.println("Try another email");
			} else {
				try {
					transaction = session.beginTransaction();
					hql = "UPDATE Customer SET customer_email = :newEmail WHERE customer_id = :c_id";
					updatedEntities = session.createQuery(hql).setParameter("newEmail", email)
							.setParameter("c_id", customerLoginId).executeUpdate();
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
		case 4: // password
			System.out.println("Enter new password: ");
			String password = sn.next();
			PasswordProtect objPasswordProtect = new PasswordProtect();
			String protected_password = objPasswordProtect.encryptPassword(password);
			try {
				transaction = session.beginTransaction();
				hql = "UPDATE Customer SET customer_password = :newPassword WHERE customer_id = :c_id";
				updatedEntities = session.createQuery(hql).setParameter("newPassword", protected_password)
						.setParameter("c_id", customerLoginId).executeUpdate();
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
		case 5: // address
			System.out.println("Enter new address: ");
			String address = sn.next();
			try {
				transaction = session.beginTransaction();
				hql = "UPDATE Customer SET customer_address = :newAddress WHERE customer_id = :c_id";
				updatedEntities = session.createQuery(hql).setParameter("newAddress", address)
						.setParameter("c_id", customerLoginId).executeUpdate();
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

	public static void deleteCustomer() {
		viewCustomer();
		System.out.print("Enter id of the customer : ");
		int id = sn.nextInt();

		Session session = HibernetUtil.getSessionFactory().openSession();
		;
		Transaction tn = null;
		try {
			tn = session.beginTransaction();

			String hql = "delete from Customer where customer_id = :customerId";
			int updatedEntities = session.createQuery(hql).setParameter("customerId", id).executeUpdate();
			if(updatedEntities == 0) {
				System.out.println("No Item Match");
			}
			else
			{
				System.out.println("Deleted successfully");
			}
			tn.commit();

		} catch (Exception e) {
			System.out.println("Can not delete");
		} finally {
			session.close();
		}

	}
	public static void deleteCustomer(int id) {
		OrderDao.viewAllOrder(id);
		if(OrderDao.customerOrderList.size() != 0)
		{
			for (Order order : OrderDao.customerOrderList) {
				int order_id = order.getOrder_id();
				
				Session session = HibernetUtil.getSessionFactory().openSession();
				Transaction tn = null;
				try {
					tn = session.beginTransaction();

					String hql = "delete from Order where order_id = :id";
					int updatedEntities = session.createQuery(hql).setParameter("id", order_id).executeUpdate();
					if (updatedEntities == 1) {
						tn.commit();
						System.out.println("Order deleted");
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
		Session session = HibernetUtil.getSessionFactory().openSession();
		Transaction tn = null;
		
		
		try {
			tn = session.beginTransaction();

			String hql = "delete from Customer where customer_id = :customerId";
			int updatedEntities = session.createQuery(hql).setParameter("customerId", id).executeUpdate();
			if(updatedEntities == 0) {
				System.out.println("No Item Match");
			}
			else
			{
				System.out.println("Deleted successfully");
			}
			tn.commit();

		} catch (Exception e) {
			if (tn != null) {
				tn.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static int isCustomerAccount(String customer_email) {
		Session session = HibernetUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<Customer> customerList = null;
		try {
			transaction = session.beginTransaction();
			String hql = "FROM Customer WHERE customer_email = :customerEmail";
			customerList = session.createQuery(hql, Customer.class).setParameter("customerEmail", customer_email)
					.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace(); // Log or handle the exception appropriately
		} finally {
			session.close();
		}
		if (customerList.size() != 0) {
			return 1; // have account

		} else {
			return 0;
		}
	}

}
