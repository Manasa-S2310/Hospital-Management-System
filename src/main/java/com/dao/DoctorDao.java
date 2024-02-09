package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.entity.Doctor;


public class DoctorDao {

	private SessionFactory factory;

	public DoctorDao(SessionFactory factory) {
		super();
		this.factory = factory;
	}

	public boolean registerDoctor(Doctor d) {
		boolean f = false;
		try (Session session = factory.openSession()) {
			session.beginTransaction();
			session.save(d);
			session.getTransaction().commit();
			f = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	public List<Doctor> getAllDoctor() {
		List<Doctor> list = null;
		try (Session session = factory.openSession()) {
			Query<Doctor> query = session.createQuery("from Doctor order by id desc", Doctor.class);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Doctor getDoctorById(int id) {
		Doctor d = null;
		try (Session session = factory.openSession()) {
			d = session.get(Doctor.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	public boolean updateDoctor(Doctor d) {
		boolean f = false;
		try (Session session = factory.openSession()) {
			session.beginTransaction();
			session.merge(d);
			session.getTransaction().commit();
			f = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}


	public boolean deleteDoctor(int id)
	{ 
		boolean f = false; 
		try (
				Session session = factory.openSession())
		{
			session.beginTransaction(); Doctor d =
					session.get(Doctor.class, id); 
			if (d != null) 
			{ 
				session.delete(d);
				f = true;
			} 
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return f;
	}

	public Doctor login(String email, String psw) {
		Doctor d = null;
		try (Session session = factory.openSession()) {
			Query<Doctor> query = session.createQuery("from Doctor where email = :email and password = :password",
					Doctor.class);
			query.setParameter("email", email);
			query.setParameter("password", psw);
			List<Doctor> list = query.list();
			if (!list.isEmpty()) {
				d = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}


	public int countDoctor() {
		int count = 0;
		try (Session session = factory.openSession()) {
			Query<Long> query = session.createQuery("select count(*) from Doctor", Long.class);
			count = Math.toIntExact(query.uniqueResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int countAppointment() {
		try (Session session = factory.openSession()) {
			Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Appointment", Long.class);
			return query.uniqueResult().intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}


	public int countAppointmentByDoctorId(int doctorId) {
		int count = 0;
		try (Session session = factory.openSession()) {
			Query<Long> query = session.createQuery(
					"SELECT COUNT(*) FROM Appointment WHERE doctorId = :doctorId", 
					Long.class
					);
			query.setParameter("doctorId", doctorId);
			count = query.uniqueResult().intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}


	public int countUser() {
		int count = 0;
		try (Session session = factory.openSession()) {
			Query<Long> query = session.createQuery("select count(*) from User", Long.class);
			count = Math.toIntExact(query.uniqueResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}


	public int countSpecialist() {
		int count = 0;
		try (Session session = factory.openSession()) {
			Query<Long> query = session.createQuery("select count(*) from Specialist", Long.class);
			count = Math.toIntExact(query.uniqueResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public boolean checkOldPassword(int userId, String oldPassword) {
		boolean result = false;
		try (Session session = factory.openSession()) {
			Query<Long> query = session.createQuery("select count(*) from Doctor where id = :userId and password = :oldPassword", Long.class);
			query.setParameter("userId", userId);
			query.setParameter("oldPassword", oldPassword);
			result = query.uniqueResult() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean changePassword(int userId, String newPassword) {
		boolean success = false;
		try (Session session = factory.openSession()) {
			session.beginTransaction();
			Doctor doctor = session.get(Doctor.class, userId);
			if (doctor != null) {
				doctor.setPassword(newPassword);
				session.getTransaction().commit();
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

}
