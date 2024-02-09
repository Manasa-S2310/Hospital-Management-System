package com.admin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.specialistDAO;
import com.db.DBConnect;



@WebServlet("/addSpecialist")
public class AddSpecialist extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		String specName=req.getParameter("specName");

		specialistDAO dao=new specialistDAO(DBConnect.getSessionFactory());
		boolean f=dao.addSpecialist(specName);

		HttpSession session = req.getSession();

		if (f) {
			session.setAttribute("succMsg", "Specialist Addedd");
			resp.sendRedirect("admin/index.jsp");
		} 
		else {
			session.setAttribute("errorMsg", "Something went wrong on server");
			resp.sendRedirect("admin/index.jsp");
		}			
	}

}
