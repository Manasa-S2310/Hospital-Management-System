package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.entity.Appointment;

public class AppointmentDAO {

	private final SessionFactory sessionFactory;

	public AppointmentDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;	        
	}	    
	public boolean addAppointment(Appointment ap) {
		try (Session session = sessionFactory.openSession()) {
			session.save(ap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Appointment> getAllAppointmentByLoginUser(int userId) {
		List<Appointment> list = new ArrayList<>();
		try (Session session = sessionFactory.openSession()) {
			Query<Appointment> query = session.createQuery("FROM Appointment WHERE userId = :userId", Appointment.class);
			query.setParameter("userId", userId);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Appointment> getAllAppointmentByDoctorLogin(int doctorId) {
		List<Appointment> list = new ArrayList<>();
		try (Session session = sessionFactory.openSession()) {
			Query<Appointment> query = session.createQuery("FROM Appointment WHERE doctorId = :doctorId", Appointment.class);
			query.setParameter("doctorId", doctorId);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Appointment getAppointmentById(int id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Appointment.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public boolean updateCommentStatus(int id, int doctId, String comm) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();

			try {
				Query query = session.createQuery("UPDATE Appointment SET status = :status WHERE id = :id AND doctorId = :doctorId");
				query.setParameter("status", comm);
				query.setParameter("id", id);
				query.setParameter("doctorId", doctId);
				int result = query.executeUpdate();

				transaction.commit();

				return result > 0;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public List<Appointment> getAllAppointment() {
		List<Appointment> list = new ArrayList<>();
		try (Session session = sessionFactory.openSession()) {
			Query<Appointment> query = session.createQuery("FROM Appointment ORDER BY id DESC", Appointment.class);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
