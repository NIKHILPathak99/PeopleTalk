package com.incapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.incapp.model.DAO;

/**
 * Servlet implementation class Register
 */
@WebServlet("/UpdateProfile")
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session=request.getSession();
			String email=(String)session.getAttribute("email");
			HashMap<String, Object> user = new HashMap<>();
			user.put("email", email);
			user.put("name", request.getParameter("name"));
			user.put("phone", request.getParameter("phone"));
			user.put("gender", request.getParameter("gender"));
			user.put("state", request.getParameter("state"));
			user.put("city", request.getParameter("city"));
			user.put("area", request.getParameter("area"));
			String d = request.getParameter("dob");

			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy"); // Date Pattern
			java.util.Date date = null;

			date = sdf1.parse(d);// Returns a Date format object with the pattern

			java.sql.Date dob = new java.sql.Date(date.getTime());

			user.put("dob", dob);
			
			DAO db = new DAO();
			db.updateProfile(user);
			db.closeConnection();
			session.setAttribute("msg", "Profile Updated!");
			response.sendRedirect("EditProfile.jsp");

		} catch (ClassNotFoundException | SQLException | ParseException e) {
			e.printStackTrace();
			response.sendRedirect("Error.jsp");
		}

	}

}
