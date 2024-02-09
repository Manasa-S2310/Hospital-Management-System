package com.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.db.DBConnect;
import com.entity.User;

public class UserDAO {

	private SessionFactory factory;

	public UserDAO(SessionFactory factory) {
		this.factory = factory;
	}

	public boolean register(User u) throws Exception {
		Transaction tx = null;

		Session session = factory.openSession();
		tx = session.beginTransaction();
		session.save(u);
		tx.commit();
		session.close();

		return true;
	}
	//    public User login(String em, String psw) throws Exception {
	//        Session session = factory.openSession();
	//        String hql = "FROM User U WHERE U.email = :email AND U.password = :password";
	//        User u = (User) session.createQuery(hql)
	//                .setParameter("email", em)
	//                .setParameter("password", psw)
	//                .uniqueResult();
	//        session.close();
	//
	//        return u;
	//    }

	public User login(String em, String psw) {
		try (Session session = DBConnect.getSessionFactory().openSession()) {
			return session.createQuery("FROM User WHERE email = :email AND password = :password", User.class)
					.setParameter("email", em)
					.setParameter("password", psw)
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean checkOldPassword(int userid, String oldPassword) throws Exception {
		Session session = factory.openSession();
		String hql = "FROM User U WHERE U.id = :id AND U.password = :password";
		User user = (User) session.createQuery(hql)
				.setParameter("id", userid)
				.setParameter("password", oldPassword)
				.uniqueResult();
		session.close();

		return user != null;
	}

	public boolean changePassword(int userid, String newPassword) throws Exception {
		Transaction tx = null;

		Session session = factory.openSession();
		tx = session.beginTransaction();
		User user = session.get(User.class, userid);
		user.setPassword(newPassword);
		tx.commit();
		session.close();

		return true;
	}

}
