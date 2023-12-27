package com.project.dao;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.project.demo.HibernetUtil;
import com.project.entity.*;

/*
 * Amit Kumar Modak 
 */
public class MenuTypeDao {
//	public static void main(String[] args) {
//		int val = 0;
//		do {
//		System.out.println("Enter a val : ");
//		val=sn.nextInt();
//		new MenuTypeDao(val);
//		}while(val != -1);
//	}
	public MenuTypeDao() {
		getMenuType();
	}

	public MenuTypeDao(int choice) {
		switch(choice){
		 case 1:
			 addMenuType();
			 break;
		 case 2:
			 getMenuType();
			 showMenuType();
			 break;
		 case 3:
			 updateMenuType();
			 break;
		 case 4:
			 deleteMenuType();
			 break;
		 }
	}

	static Scanner sn = new Scanner(System.in);
	public static List<MenuType> menu_type_list = null;

	private static void addMenuType() {
		Session session = HibernetUtil.getSessionFactory().openSession();
		Transaction tn = null;

		System.out.println("Enter type name:");
		String newName = sn.next();
		if (isTypeExists(newName) != 0) {
			System.out.println("This type is already exists");
		} else {
			MenuType menuType = new MenuType(newName);

			session = HibernetUtil.getSessionFactory().openSession();
			tn = null;
			try {
				tn = session.beginTransaction();
				session.save(menuType);
				tn.commit();
				System.out.println("Data inserted successfully");
			} catch (Exception e) {
				if (tn != null && tn.isActive()) {
					tn.rollback();
				}
				e.printStackTrace(); // Log or handle the exception appropriately
			} finally {
				session.close();
				//getMenuType();
			}
		}

	}

	protected static void getMenuType() {
		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from MenuType";
			menu_type_list = session.createQuery(hql, MenuType.class).getResultList();
		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		} finally {
			session.close();
		}

	}

	protected static void showMenuType() {
		if (menu_type_list == null || menu_type_list.size() == 0 ) {
			System.out.println("No Item in the list");
		} else {
			System.out.println("-----------------------------------------------------------");
			for (MenuType data : menu_type_list) {
				System.out.printf("Type ID: %d\tType Name: %s\tOrder:\n", data.getMenutype_id(), data.getMenutype_name());

			}
			System.out.println("-----------------------------------------------------------");
		}
	}

	protected MenuType getDataById(int id) {		// use in MenuItem
		Session session = HibernetUtil.getSessionFactory().openSession();
		MenuType existingMenuType = null;
		try {
			String hql = "from MenuType where menutype_id = :Id";
			Query<MenuType> query = session.createQuery(hql, MenuType.class);
			query.setParameter("Id", id);
			existingMenuType = query.getSingleResult();
			return (existingMenuType);
		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		} finally {
			session.close();
			//getMenuType();
		}
		return (null);
	}

	private static void updateMenuType() {
		getMenuType();
		showMenuType();
		if (menu_type_list.size() != 0) {
			System.out.println("Enter Type id: ");
			int id = sn.nextInt();
			System.out.println("Enter new Type Name: ");
			String newName = sn.next();
			if (isTypeExists(newName) != 0) {
				System.out.println("This type is already exists");
			} else {

				Session session = HibernetUtil.getSessionFactory().openSession();
				Transaction tn = null;
				try {
					tn = session.beginTransaction();
					String hql = "UPDATE MenuType SET menutype_name = :Name WHERE menutype_id = :id";
					int updatedEntities = session.createQuery(hql).setParameter("Name", newName).setParameter("id", id)
							.executeUpdate();
					tn.commit();
					if (updatedEntities == 0) {
						System.out.println("No matching data");
					} else {
						System.out.println("Update Successfully");
					}
				} catch (Exception e) {
					if (tn != null && tn.isActive()) {
						tn.rollback();
					}
					e.printStackTrace(); // Log or handle the exception appropriately
				} finally {
					session.close();
					//getMenuType();
				}
			}
		}

	}

	private static void deleteMenuType() {
		getMenuType();
		showMenuType();
		if (menu_type_list.size() != 0) {
			System.out.println("Enter Type id: ");
			int id = sn.nextInt();

			Session session = HibernetUtil.getSessionFactory().openSession();
			Transaction tn = null;
			try {
				tn = session.beginTransaction();

				String hql = "delete from MenuType where menutype_id = :id";
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
				//getMenuType();
			}
		}

	}

	private static int isTypeExists(String new_type) {
		Session session = HibernetUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<MenuType> list = null;

		try {
			transaction = session.beginTransaction();
			String hql = "FROM MenuType WHERE menutype_name = :new_type";
			list = session.createQuery(hql, MenuType.class).setParameter("new_type", new_type).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace(); // Log or handle the exception appropriately
		} finally {
			session.close();
//			getMenuType();
		}
		if (list.size() != 0) {
			return 1; // Data found

		} else {
			return 0;
		}
	}
}
