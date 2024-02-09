package com.dao;


import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.entity.Specialist;

public class specialistDAO {


	private SessionFactory factory;

	public specialistDAO(SessionFactory factory) {
		// TODO Auto-generated constructor stub
		super();
		this.factory = factory;
	}

	public boolean addSpecialist(String spec) {
		boolean f = false;

		try (Session session = factory.openSession()) {
			Transaction tx = session.beginTransaction();

			Specialist specialist = new Specialist();
			specialist.setSpecialistName(spec);

			session.save(specialist);

			tx.commit();
			f = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return f;
	}

	public List<Specialist> getAllSpecialist() {
		List<Specialist> list = null;

		try (Session session = factory.openSession()) {
			Transaction tx = session.beginTransaction();

			list = session.createQuery("FROM Specialist", Specialist.class).getResultList();

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
