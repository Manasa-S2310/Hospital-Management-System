package com.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBConnect {
	private static SessionFactory sessionFactory;

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
