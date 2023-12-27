package com.project.demo;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;


import com.project.entity.Admin;
import com.project.entity.Customer;
import com.project.dao.*;



public class Login {
	static Scanner sn = new Scanner(System.in);
	PasswordProtect objPasswordProtect = new PasswordProtect();
	static int customerLoginId = 0;
	private static void adminDashboard() {
		int ch;
		do {
			System.out.println("==============Welcome to Admin Dashboard==============");
			System.out.println("For Customer Details press 1");
			System.out.println("For Menu Catagory press 2");
			System.out.println("For Menu Type press 3");
			System.out.println("For Menu Item press 4");
			System.out.println("For Order details press 5");
			System.out.println("For update profile press 6");
			System.out.println("To Exit press 10");
			System.out.print("Enter your choice: ");
			ch = sn.nextInt();
			switch (ch) {
			case 1: // Customer Details
				int cch;
				//CustomerDao objCustomerDao = new CustomerDao();
				do {
					System.out.println("==============Customer Details==============");
					System.out.println("To add customer press 1");
					System.out.println("To view customer press 2");
					System.out.println("To update customer press 3");
					System.out.println("To delete customer press 4");
					System.out.println("To exit  press 10");
					System.out.print("Enter your choice: ");
					cch = sn.nextInt();
					switch (cch) {
					case 1: 	//add customer
						CustomerDao.addCustomer();
						break;
					case 2:
						CustomerDao.viewCustomer();
						break;
					case 3:
						CustomerDao.viewCustomer();
						System.out.println("Enter Customer Id : ");
						int id = sn.nextInt();
						CustomerDao.updateCustomer(id);
						break;
					case 4:
						CustomerDao.deleteCustomer();
						break;
					case 10:
						System.out.println("Exited from  customer");
						break;
					default:
						System.out.println("Invaild Choice; Try Again");
					}
				} while (cch != 10);
				break;
			case 2: 	//catagory
				int mcch;
				do {
					System.out.println("==============Menu Catagory==============");
					System.out.println("To add Menu Catagory press 1");
					System.out.println("To view Menu Catagory press 2");
					System.out.println("To update Menu Catagory press 3");
					System.out.println("To delete Menu Catagory press 4");
					System.out.println("To exit  press 10");
					System.out.print("Enter your choice: ");
					mcch = sn.nextInt();
					switch (mcch) {
					case 1: 	//add customer
						new MenuCatagoryDao(1);
						break;
					case 2:
						new MenuCatagoryDao(2);
						break;
					case 3:
						new MenuCatagoryDao(3);
						break;
					case 4:
						new MenuCatagoryDao(4);
						break;
					case 10:
						System.out.println("Exited from  customer");
						break;
					default:
						System.out.println("Invaild Choice; Try Again");
					}
				} while (mcch != 10);
				break;
			case 3:		//type
				int mtch;
				do {
					System.out.println("==============Menu Type==============");
					System.out.println("To add Menu Type press 1");
					System.out.println("To view Menu Type press 2");
					System.out.println("To update Menu Type press 3");
					System.out.println("To delete Menu Type press 4");
					System.out.println("To exit  press 10");
					System.out.print("Enter your choice: ");
					mtch = sn.nextInt();
					switch (mtch) {
					case 1: 	
						new MenuTypeDao(1);
						break;
					case 2:
						new MenuTypeDao(2);
						break;
					case 3:
						new MenuTypeDao(3);
						break;
					case 4:
						new MenuTypeDao(4);
						break;
					case 10:
						System.out.println("Exited from  customer");
						break;
					default:
						System.out.println("Invaild Choice; Try Again");
					}
				} while (mtch != 10);
				break;
			case 4: // Menu item
				int mch;
				do {
					System.out.println("==============Menu ==============");
					System.out.println("To add menu press 1");
					System.out.println("To view menu press 2");
					System.out.println("To update menu press 3");
					System.out.println("To delete menu press 4");
					System.out.println("To exit  press 10");
					System.out.print("Enter your choice: ");
					mch = sn.nextInt();
					switch (mch) {
					case 1:
						new MenuItemDao(1);
						break;
					case 2:
						new MenuItemDao(2);
						break;
					case 3:
						new MenuItemDao(3);
						break;
					case 4:
						new MenuItemDao(4);
						break;
					case 10:
						System.out.println("Exited from menu");
						break;
					}
				} while (mch != 10);
				break;
				
			case 5: // Order details
				int och;
				do {
					System.out.println("==============Order==============");
					System.out.println("To add order press 1");
					System.out.println("To view order press 2");
					//System.out.println("To update order press 3");
					System.out.println("To delete order press 4");
					System.out.println("To exit  press 10");
					System.out.print("Enter your choice: ");
					och = sn.nextInt();
					switch (och) {
					case 1:
						new OrderDao(1);
						break;
					case 2:
						new OrderDao(2);
						break;
//					case 3:
//						new OrderDao(3);
//						break;
					case 4:
						new OrderDao(4);
						break;
					case 10:
						System.out.println("Exited from order");
						break;
					}
				} while (och != 10);
				break;
			case 6:
				new AdminDao(3);
				break;
			case 10:
				System.out.println("Exited from dashboard");
				break;
			default:
				System.out.println("Invaild Choice; Try Again");
			}
		} while (ch != 10);

	}
	void customerLogin()
	{
		//CustomerDao objCustomerDao = new CustomerDao();
		while(true) {
			System.out.print("Enter 1 to register otherwise enter any number to login:");
			int ch = sn.nextInt();
			if(ch == 1) {
				System.out.println("--Customer Registration--");
				System.out.print("Enter customer email : ");
				String customer_email = sn.next();

				int status = CustomerDao.isCustomerAccount(customer_email);
				
			    if(status != 0)
			    {
			    	System.out.println("The email is already registered");
			    	
			    }
			    else
			    {
			    	CustomerDao.addCustomer();
			    }
			}
			else
			{
				break;
			}
		}
		System.out.print("Enter Customer email: ");
		String c_email = sn.next();
		System.out.print("Enter Customer Password: ");
		String c_pass = sn.next();
		
		String protected_password = objPasswordProtect.encryptPassword(c_pass);

		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from Customer where customer_email = :useremail and customer_password = :userpassword";
			List<Customer> customerList = session.createQuery(hql, Customer.class).setParameter("useremail", c_email)
					.setParameter("userpassword", protected_password).getResultList();
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
			System.out.println("For deleting profile press 6");
			System.out.println("For exit press 10");
			System.out.print("Enter your choice: ");
			ch = sn.nextInt();
			switch (ch) {
				case 1:
					MenuItemDao.showAvailableMenuItem();
					break;
				case 2:
					OrderDao.viewAllOrder(customerLoginId);
					//viewOrder();
					break;
				case 3:
					OrderDao.addOrder(customerLoginId);
					//addOrder();
					break;
				case 4:
					OrderDao.viewAllOrder(customerLoginId);
					OrderDao.cancelOrder();
					//cancelOrder();
					break;
				case 5:
					CustomerDao.updateCustomer(customerLoginId);
					//updateProfile();
					break;
				case 6:
					CustomerDao.deleteCustomer(customerLoginId);
					ch=10;
				case 10:
					System.out.println("Existing from customer Dashboard");
					break;
				default:
					System.out.println("Invalid choice");
					
			}
		} while( ch != 10);
		
	}
	void adminLogin() {
		System.out.print("Enter Admin email: ");
		String ad_email = sn.next();
		System.out.print("Enter Admin Password: ");
		String ad_pass = sn.next();
		
		String protected_password = objPasswordProtect.encryptPassword(ad_pass);

		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from Admin where admin_email = :useremail and admin_password = :userpassword";
			List<Admin> admins = session.createQuery(hql, Admin.class).setParameter("useremail", ad_email)
					.setParameter("userpassword", protected_password).getResultList();
//				System.out.println(admins);

			if (admins.size() == 0) {
				System.out.println("Invalid Details");
				System.out.println("Enter Details again");

			}

			else {
				System.out.println("Login Successful");
				adminDashboard();
			}
		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		} finally {
			session.close();
		}

	}
}