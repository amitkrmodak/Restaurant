package com.project.demo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernetUtil {
	public static SessionFactory sf;
	public static SessionFactory getSessionFactory() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernet.cfg.xml");
		sf = cfg.buildSessionFactory();
		return sf;
	}
}
