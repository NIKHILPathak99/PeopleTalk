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
@WebServlet("/ChangePhoto")
@MultipartConfig
public class ChangePhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email=request.getParameter("email");
			Part p = request.getPart("photo");
			InputStream photo = p.getInputStream();
			
			DAO db = new DAO();
			db.changePhoto(email,photo);
			db.closeConnection();
			HttpSession session = request.getSession();
			session.setAttribute("msg", "Photo Updated!");
			response.sendRedirect("EditProfile.jsp");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect("Error.jsp");
		}

	}

}
