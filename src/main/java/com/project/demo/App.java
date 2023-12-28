package com.project.demo;

import java.util.Scanner;
/*
 * Amit Kumar Modak 
 */ 
public class App {
	static Scanner sn = new Scanner(System.in);

	public static void main(String[] args) {
		int ch;
		Login login = new Login();
		do {
			System.out.println("Welcome to dashboard");
			System.out.println("For admin Press 1");
			System.out.println("For customer Press 2");
			System.out.println("For Exit Press 10");
			System.out.print("Enter your choice: ");
			ch = sn.nextInt();
			
			switch (ch) {
			case 1:
				login.adminLogin();
				break;
			case 2:
				login.customerLogin();
				break;
			case 10:
				System.out.println("Exited");
				System.out.println("Thank you");
				break;
			default:
				System.out.println("Invaild Choice; Try Again");
			}
		} while(ch != 10);
	}
}
