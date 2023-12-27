package com.project.dao;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.demo.HibernetUtil;
import com.project.entity.Admin;
import com.project.entity.MenuCatagory;
/*
 * Amit Kumar Modak 
 */
public class AdminDao extends PasswordProtect{

//	public static void main(String[] args) {
////		addAdmin();
////		viewAdmin();
////		updateAdmin();
//		deleteAdmin();
//	}
	

	private static Scanner sn = new Scanner(System.in);
	public AdminDao(int choice) {
		switch (choice) {
		case 1:
			addAdmin();
			break;
		case 2:
			viewAdmin();
			break;
		case 3:
			updateAdmin();
			break;
		case 4:
			deleteAdmin();
			break;
		}
	}
	private static int totalEntities = 0;
	
	private static void addAdmin() {
		System.out.println("Enter admin email:");
		String email = sn.next();
		if (isAdminExists(email) != 0) {
			System.out.println("The email is already exist");
		} else {
			System.out.println("Enter admin name:");
			String name = sn.next();
			System.out.println("Enter admin contact no:");
			long contact = sn.nextLong();
			System.out.println("Enter admin password:");
			String password = sn.next();
			
			PasswordProtect objPasswordProtect = new PasswordProtect();
			String protected_password = objPasswordProtect.encryptPassword(password);
			
			Session session = HibernetUtil.getSessionFactory().openSession();
			Transaction tn = null;

			Admin admin = new Admin(name, contact, email, protected_password);

			session = HibernetUtil.getSessionFactory().openSession();
			tn = null;
			try {
				tn = session.beginTransaction();
				session.save(admin);
				tn.commit();
				System.out.println("Data is inserted successfully");
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
	private static void viewAdmin() {
		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from Admin";
			List<Admin> adminList = session.createQuery(hql, Admin.class).getResultList();
			totalEntities = adminList.size();
			if (totalEntities == 0) {
				System.out.println("No Item in the list");
			} else {
				System.out.println("Admin details:");
				System.out.println("-----------------------------------------------------------");
				for (Admin data : adminList) {

					System.out.printf("Admin ID: %d\tAdmin Name: %s\tAdmin Number: %d\tAdmin Email: %s\tPassword: %s\n",
							data.getAdmin_id(), data.getAdmin_name(), data.getAdmin_mno(), data.getAdmin_email(),
							data.getAdmin_password());

				}
				System.out.println("-----------------------------------------------------------");
			}
		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		} finally {
			session.close();
		}

	}

	private static void updateAdmin() {
		viewAdmin();
		if (totalEntities != 0) {
			System.out.println("Enter Admin id:");
			int id = sn.nextInt();
			System.out.println("To change name press 1");
			System.out.println("To change number press 2");
			System.out.println("To change email press 3");
			System.out.println("To change password press 4");
			System.out.print("Enter your choice: ");
			int ch = sn.nextInt();
			Session session = HibernetUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			int updatedEntities = 0;
			String hql;
			switch (ch) {
			case 1: // name
				System.out.println("Enter new Name: ");
				String newName = sn.next();

				try {
					transaction = session.beginTransaction();
					hql = "UPDATE Admin SET admin_name = :newAdminName WHERE admin_id = :adminId";
					updatedEntities = session.createQuery(hql).setParameter("newAdminName", newName)
							.setParameter("adminId", id).executeUpdate();
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
				long newNumber = sn.nextLong();
				try {
					transaction = session.beginTransaction();
					hql = "UPDATE Admin SET admin_mno = :newAdminNumber WHERE admin_id = :adminId";
					updatedEntities = session.createQuery(hql).setParameter("newAdminNumber", newNumber)
							.setParameter("adminId", id).executeUpdate();
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
				String newEmail = sn.next();
				if (isAdminExists(newEmail) != 0) {
					System.out.println("The catagory is already exist");
				} else {
					try {
						transaction = session.beginTransaction();
						hql = "UPDATE Admin SET admin_email = :newAdminEmail WHERE admin_id = :adminId";
						updatedEntities = session.createQuery(hql).setParameter("newAdminEmail", newEmail)
								.setParameter("adminId", id).executeUpdate();
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
			case 4:			//password
				System.out.println("Enter new password: ");
				String password = sn.next();
				PasswordProtect objPasswordProtect = new PasswordProtect();
				String protected_password = objPasswordProtect.encryptPassword(password);
				try {
					transaction = session.beginTransaction();
					hql = "UPDATE Admin SET admin_password = :newAdminPassword WHERE admin_id = :adminId";
					updatedEntities = session.createQuery(hql).setParameter("newAdminPassword", protected_password)
							.setParameter("adminId", id).executeUpdate();
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
	}
	private static void deleteAdmin() {
		viewAdmin();
		if (totalEntities != 0) {
			System.out.println("Enter Admin id: ");
			int id = sn.nextInt();

			Session session = HibernetUtil.getSessionFactory().openSession();
			Transaction tn = null;
			try {
				tn = session.beginTransaction();

				String hql = "delete from Admin where admin_id = :Aid";
				int updatedEntities = session.createQuery(hql).setParameter("Aid", id).executeUpdate();
				if (updatedEntities == 1) {
					tn.commit();
					System.out.println("Deleted Successfull");
				} else {
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
	}
	private static int isAdminExists(String new_admin) {
		Session session = HibernetUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<Admin> list = null;

		try {
			transaction = session.beginTransaction();
			String hql = "FROM Admin WHERE admin_email = :newAdmin";
			list = session.createQuery(hql, Admin.class).setParameter("newAdmin", new_admin).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace(); // Log or handle the exception appropriately
		} finally {
			session.close();
		}
		if (list != null && list.size() != 0) {
			return 1; // Data found

		} else {
			return 0;
		}
	}
}
