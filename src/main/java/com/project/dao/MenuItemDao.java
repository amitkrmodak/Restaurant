package com.project.dao;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.demo.HibernetUtil;
import com.project.entity.MenuCatagory;
import com.project.entity.MenuItem;
import com.project.entity.MenuType;
/*
 * Amit Kumar Modak 
 */
public class MenuItemDao {
	static MenuCatagoryDao objMenuCatagoryDao = new MenuCatagoryDao();
	static MenuTypeDao objMenuTypeDao = new MenuTypeDao();
	static Scanner sn = new Scanner(System.in);
	protected static List<MenuItem> menu_item_list = null;
	protected static List<MenuItem> available_item_list = null;
	public MenuItemDao() {

	}

	public MenuItemDao(int choice) {
		getMenuItem();
		switch (choice) {
		case 1:
			addMenuItem();
			break;
		case 2:
			showMenuItem();
			break;
		case 3:
			updateMenuItem();
			break;
		case 4:
			deleteMenuItem();
			break;
		}
	}

//	public static void main(String[] args) {
//		int val = 0;
//		do {
//			System.out.println("Enter a val : ");
//			val=sn.nextInt();
//			new MenuItemDao(val);
//		}	while(val != -1);
//
//	}

	public static void addMenuItem() {

		MenuCatagoryDao.getMenuCatagory();
		if (MenuCatagoryDao.menu_catagory_list.size() == 0) {
			System.out.println("No catagory exists");
		}
		MenuTypeDao.getMenuType();
		if (MenuTypeDao.menu_type_list.size() == 0) {
			System.out.println("No type exists");
		} else {
			System.out.println("ADD MENU ITEM");
			System.out.println("-----------------------------------");
			MenuCatagoryDao.showMenuCatagory();
			System.out.println("Enter catagory ID: ");
			int catagory = sn.nextInt();

			MenuTypeDao.showMenuType();
			System.out.println("Enter type ID: ");
			int type = sn.nextInt();

			System.out.println("Enter Name: ");
			String name = sn.next();

			System.out.println("Enter Price: ");
			int price = sn.nextInt();

			System.out.println("Enter Availability: ");
			Boolean is_available = sn.nextBoolean();

			System.out.println("Enter Description: ");
			String description = sn.next();

			MenuCatagory existingMenuCatagory = objMenuCatagoryDao.getDataById(catagory);
			MenuType existingMenuType = objMenuTypeDao.getDataById(type);
			if (existingMenuCatagory == null || existingMenuType == null) {
				System.out.println("Error occured");
			} else {
				MenuItem objMenuItem = new MenuItem(name, price, is_available, description);
				objMenuItem.setMenuCatagory(existingMenuCatagory);
				objMenuItem.setMenuType(existingMenuType);

				Session session = HibernetUtil.getSessionFactory().openSession();
				Transaction tn = null;

				session = HibernetUtil.getSessionFactory().openSession();
				try {
					tn = session.beginTransaction();
					session.save(objMenuItem);
					tn.commit();
					System.out.println(objMenuItem);
					System.out.println("Data inserted successfully");
				} catch (Exception e) {
					if (tn != null && tn.isActive()) {
						tn.rollback();
					}
					e.printStackTrace(); // Log or handle the exception appropriately
				} finally {
					session.close();
//				getMenuItem();
				}
			}
		}
	}

	public static void getMenuItem() {
		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from MenuItem";
			menu_item_list = session.createQuery(hql, MenuItem.class).getResultList();
		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		} finally {
			session.close();
		}
	}

	public static void showMenuItem() {

		if (menu_item_list == null || menu_item_list.size() == 0) {
			System.out.println("No Item in the list");
		} else {
			System.out.println("Show");
			System.out.println("Menu Item details:");
			System.out.println("==================================================");
			for (MenuItem data : menu_item_list) {
				System.out.printf("Item ID: %d\tItem Name: %s\tPrice: %d\tAvailable: %s\nDescription: %s\n",
						data.getItem_id(), data.getItem_name(), data.getItem_price(), data.getItem_isAvailable(),
						data.getItem_description());
				System.out.println("Catyagory: " + data.getMenuCatagory());
				System.out.println("Type: " + data.getMenuType());
				System.out.println("-----------------------------------------------------------");

			}
			System.out.println("==================================================");
		}
	}

	public static void showAvailableMenuItem() {
		Session session = HibernetUtil.getSessionFactory().openSession();
		try {
			String hql = "from MenuItem where item_isAvailable =: isAvalable";
			available_item_list = session.createQuery(hql, MenuItem.class).setParameter("isAvalable", true).getResultList();
		} catch (Exception e) {
			System.out.println("Exception Ocuured" + e);
		} finally {
			session.close();
		}
		
		if (available_item_list == null || available_item_list.size() == 0) {
			System.out.println("No Item in the list");
		} else {
			System.out.println("Show");
			System.out.println("Menu Item details:");
			System.out.println("==================================================");
			for (MenuItem data : available_item_list) {
//				if (data.getItem_isAvailable()) {
					System.out.printf("Item ID: %d\tItem Name: %s\tPrice: %d\nDescription: %s\n",
							data.getItem_id(), data.getItem_name(), data.getItem_price(),
							data.getItem_description());
					System.out.println("Catyagory: " + data.getMenuCatagory());
					System.out.println("Type: " + data.getMenuType());
					System.out.println("-----------------------------------------------------------");

//				}
				System.out.println("==================================================");
			}
		}
	}

	public static void updateMenuItem() {
		if (menu_item_list == null || menu_item_list.size() == 0) {
			System.out.println("No menu item exists");
		} else {
			showMenuItem();
			System.out.println("Enter id of the item : ");
			int id = sn.nextInt();

			System.out.println("To change name press 1");
			System.out.println("To change catagory press 2");
			System.out.println("To change type press 3");
			System.out.println("To change price press 4");
			System.out.println("To change availability press 5");
			System.out.println("To change description press 6");
			System.out.print("Enter your choice: ");
			int ch = sn.nextInt();
			Session session = HibernetUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			int updatedEntities = 0;
			String hql;
			switch (ch) {
			case 1: // name
				System.out.println("Enter name of the item : ");
				String name = sn.next();
				try {
					transaction = session.beginTransaction();
					hql = "UPDATE MenuItem SET item_name = :newName WHERE item_id = :itemId";
					updatedEntities = session.createQuery(hql).setParameter("newName", name).setParameter("itemId", id)
							.executeUpdate();
					transaction.commit();
					if (updatedEntities == 0) {
						System.out.println("No match data");
					} else {
						System.out.println("Update Successfully");
					}
				} catch (Exception e) {
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					e.printStackTrace(); // Log or handle the exception appropriately
				} finally {
					session.close();
//					getMenuItem();
				}
				break;
			case 2: // catagory
				MenuCatagoryDao.showMenuCatagory();
				System.out.println("Enter catagory Id of the item : ");
				int catagory = sn.nextInt();

				MenuCatagory existingMenuCatagory = objMenuCatagoryDao.getDataById(catagory);
				try {
					transaction = session.beginTransaction();
					hql = "UPDATE MenuItem SET menuCatagory = :newCatagory WHERE item_id = :itemId";
					updatedEntities = session.createQuery(hql).setParameter("newCatagory", existingMenuCatagory)
							.setParameter("itemId", id).executeUpdate();
					transaction.commit();
					if (updatedEntities == 0) {
						System.out.println("No match data");
					} else {
						System.out.println("Update Successfully");
					}
				} catch (Exception e) {
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					e.printStackTrace(); // Log or handle the exception appropriately
				} finally {
					session.close();
//					getMenuItem();
				}
				break;
			case 3: // type
				MenuTypeDao.showMenuType();
				System.out.println("Enter type Id of the item : ");
				int type = sn.nextInt();

				MenuType existingMenuType = objMenuTypeDao.getDataById(type);
				try {
					transaction = session.beginTransaction();
					hql = "UPDATE MenuItem SET menuType = :newType WHERE item_id = :itemId";
					updatedEntities = session.createQuery(hql).setParameter("newType", existingMenuType)
							.setParameter("itemId", id).executeUpdate();
					transaction.commit();
					if (updatedEntities == 0) {
						System.out.println("No match data");
					} else {
						System.out.println("Update Successfully");
					}
				} catch (Exception e) {
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					e.printStackTrace(); // Log or handle the exception appropriately
				} finally {
					session.close();
//					getMenuItem();
				}
				break;
			case 4: // price
				System.out.println("Enter price of the item : ");
				int price = sn.nextInt();

				try {
					transaction = session.beginTransaction();
					hql = "UPDATE MenuItem SET item_price = :newPrice WHERE item_id = :itemId";
					updatedEntities = session.createQuery(hql).setParameter("newPrice", price)
							.setParameter("itemId", id).executeUpdate();
					transaction.commit();
					if (updatedEntities == 0) {
						System.out.println("No match data");
					} else {
						System.out.println("Update Successfully");
					}
				} catch (Exception e) {
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					e.printStackTrace(); // Log or handle the exception appropriately
				} finally {
					session.close();
//					getMenuItem();
				}

				break;
			case 5: // availability
				System.out.println("Enter availability of the item : ");
				Boolean isAvailable = sn.nextBoolean();

				try {
					transaction = session.beginTransaction();
					hql = "UPDATE MenuItem SET item_isAvailable = :newIsAvailable WHERE item_id = :itemId";
					updatedEntities = session.createQuery(hql).setParameter("newIsAvailable", isAvailable)
							.setParameter("itemId", id).executeUpdate();
					transaction.commit();
					if (updatedEntities == 0) {
						System.out.println("No match data");
					} else {
						System.out.println("Update Successfully");
					}
				} catch (Exception e) {
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					e.printStackTrace(); // Log or handle the exception appropriately
				} finally {
					session.close();
				}
				break;
			case 6: // description
				System.out.println("Enter description of the item : ");
				String description = sn.next();

				try {
					transaction = session.beginTransaction();
					hql = "UPDATE MenuItem SET item_description = :newDescription WHERE item_id = :itemId";
					updatedEntities = session.createQuery(hql).setParameter("newDescription", description)
							.setParameter("itemId", id).executeUpdate();
					transaction.commit();
					if (updatedEntities == 0) {
						System.out.println("No match data");
					} else {
						System.out.println("Update Successfully");
					}
				} catch (Exception e) {
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					e.printStackTrace(); // Log or handle the exception appropriately
				} finally {
					session.close();
				}
				break;
			default:
				System.out.println("Invaild Choice; Try Again");
			}
		}

	}

	public static void deleteMenuItem() {
		showMenuItem();
		System.out.print("Enter id of the item : ");
		int id = sn.nextInt();

		Session session = HibernetUtil.getSessionFactory().openSession();
		Transaction tn = null;
		try {
			tn = session.beginTransaction();

			String hql = "delete from MenuItem where item_id = :itemId";
			session.createQuery(hql).setParameter("itemId", id).executeUpdate();

			tn.commit();
			System.out.println("Delete Successfull");
		} catch (Exception e) {
			System.out.println("Can not delete");
		} finally {
			session.close();
//			getMenuItem();
		}

	}
}
