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
@WebServlet("/Register")
@MultipartConfig
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HashMap<String, Object> user = new HashMap<>();
			user.put("email", request.getParameter("email"));
			user.put("name", request.getParameter("name"));
			user.put("phone", request.getParameter("phone"));
			user.put("gender", request.getParameter("gender"));
			user.put("password", request.getParameter("password"));
			user.put("state", request.getParameter("state"));
			user.put("city", request.getParameter("city"));
			user.put("area", request.getParameter("area"));
			String d = request.getParameter("dob");

			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy"); // Date Pattern
			java.util.Date date = null;

			date = sdf1.parse(d);// Returns a Date format object with the pattern

			java.sql.Date dob = new java.sql.Date(date.getTime());

			user.put("dob", dob);

			Part p = request.getPart("photo");
			InputStream photo;
			if (p.getSize() == 0) {
				photo = null;
			} else {
				photo = p.getInputStream();
			}
			user.put("photo", photo);

			DAO db = new DAO();
			boolean r = db.register(user);
			db.closeConnection();
			HttpSession session = request.getSession();
			if (r) {
				session.setAttribute("name", request.getParameter("name"));
				session.setAttribute("email", request.getParameter("email"));
				response.sendRedirect("profile.jsp");
			} else {
				session.setAttribute("msg", "Email ID Already Exist!");
				response.sendRedirect("index.jsp");
			}

		} catch (ClassNotFoundException | SQLException | ParseException e) {
			e.printStackTrace();
			response.sendRedirect("Error.jsp");
		}

	}

}
