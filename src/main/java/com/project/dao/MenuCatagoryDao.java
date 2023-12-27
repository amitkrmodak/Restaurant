package com.project.dao;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.project.demo.HibernetUtil;
import com.project.entity.MenuCatagory;

/*
 * Amit Kumar Modak 
 */
public class MenuCatagoryDao {
//	public static void main(String[] args) {
//		int val = 0;
//		do {
//		System.out.println("Enter a val : ");
//		val=sn.nextInt();
//		new MenuCatagoryDao(val);
//		}while(val != -1);
//	}
	
	public MenuCatagoryDao() {
		getMenuCatagory();
	}

	public MenuCatagoryDao(int choice) {
		switch(choice){
		 case 1:
			 addMenuCatagory();
			 break;
		 case 2:
			 getMenuCatagory();
			 showMenuCatagory();
			 break;
		 case 3:
			 updateMenuCatagory();
			 break;
		 case 4:
			 deleteMenuCatagory();
			 break;
		 }
	}
	protected static List<MenuCatagory> menu_catagory_list = null;
	static Scanner sn = new Scanner(System.in);
	private static void addMenuCatagory() {

		System.out.println("Enter Catagory name:");
		String name = sn.next();
		if (isCatagoryExists(name) != 0) {
			System.out.println("The catagory is already exist");
		} else {
			Session session = HibernetUtil.getSessionFactory().openSession();
			Transaction tn = null;

			MenuCatagory menuCatagory = new MenuCatagory(name);

			session = HibernetUtil.getSessionFactory().openSession();
			tn = null;
			try {
				tn = session.beginTransaction();
				session.save(menuCatagory);
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
	
	protected MenuCatagory getDataById(int id)		// use in Menu Item
	{
		Session session = HibernetUtil.getSessionFactory().openSession();
		MenuCatagory existingMenuCatagory = null;
		try {
			String hql = "from MenuCatagory where menucatagory_id = :Id";
			Query<MenuCatagory> query = session.createQuery(hql, MenuCatagory.class);
			query.setParameter("Id", id);
			existingMenuCatagory = query.getSingleResult();
			return(existingMenuCatagory);

		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		} finally {
			session.close();
			
		}
		return(null);
	}
	protected static void getMenuCatagory(){
		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from MenuCatagory";
			menu_catagory_list = session.createQuery(hql, MenuCatagory.class).getResultList();
		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		} finally {
			session.close();
		}
		
	}
	protected static void showMenuCatagory() {
		if ( menu_catagory_list == null || menu_catagory_list.size() == 0) {
			System.out.println("No Item in the list");
		} else {
			System.out.println("Menu Catagory details:");
			System.out.println("-----------------------------------------------------------");
			for (MenuCatagory data : menu_catagory_list) {

				System.out.printf("Type ID: %d\tType Name: %s\n", data.getMenucatagory_id(),
						data.getMenucatagory_name());

			}
			System.out.println("-----------------------------------------------------------");
		}

	}

	private static void updateMenuCatagory() {
		getMenuCatagory();
		showMenuCatagory();
		if (menu_catagory_list.size() != 0) {
			System.out.println("Enter Catagory id: ");
			int id = sn.nextInt();
			System.out.println("Enter new Catagory Name: ");
			String newName = sn.next();
			if (isCatagoryExists(newName) != 0) {
				System.out.println("The catagory is already exist");
			} else {
				Session session = HibernetUtil.getSessionFactory().openSession();
				Transaction tn = null;
				try {
					tn = session.beginTransaction();
					String hql = "UPDATE MenuCatagory SET menucatagory_name = :Name WHERE menucatagory_id = :id";
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
				}
				finally {
					session.close();
					
				}
				
			}
		}

	}

	private static void deleteMenuCatagory() {
		getMenuCatagory();
		showMenuCatagory();
		if (menu_catagory_list.size() != 0) {
			System.out.println("Enter Type id: ");
			int id = sn.nextInt();

			Session session = HibernetUtil.getSessionFactory().openSession();
			;
			Transaction tn = null;
			try {
				tn = session.beginTransaction();

				String hql = "delete from MenuCatagory where menucatagory_id = :id";
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
				
			}
		}
	}

	private static int isCatagoryExists(String new_catagory) {
		Session session = HibernetUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<MenuCatagory> list = null;

		try {
			transaction = session.beginTransaction();
			String hql = "FROM MenuCatagory WHERE menucatagory_name = :new_catagory";
			list = session.createQuery(hql, MenuCatagory.class).setParameter("new_catagory", new_catagory)
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
		if (list.size() != 0) {
			return 1; // Data found

		} else {
			return 0;
		}
	}
}
