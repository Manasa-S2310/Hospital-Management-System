package com.user.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.entity.User;

@WebServlet("/user_register")
public class UserRegister extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			String fullName = req.getParameter("fullName");
			String email = req.getParameter("email");
			String password = req.getParameter("password");

			User u = new User(fullName, email, password);

			Configuration cfg = new Configuration().configure().addAnnotatedClass(User.class);
			try (SessionFactory sf = cfg.buildSessionFactory();
					Session session = sf.openSession()) {

				Transaction tr = session.beginTransaction();

				session.save(u);

				tr.commit();
			}

			HttpSession session = req.getSession();
			session.setAttribute("sucMsg", "Registered Successfully");
			resp.sendRedirect("signup.jsp");

		} catch (Exception e) {
			e.printStackTrace();

			HttpSession session = req.getSession();
			session.setAttribute("errorMsg", "Something went wrong on the server");
			resp.sendRedirect("signup.jsp");
		}
	}
}
