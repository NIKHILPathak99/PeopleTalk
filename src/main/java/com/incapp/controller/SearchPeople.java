package com.incapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@WebServlet("/SearchPeople")
public class SearchPeople extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String state=request.getParameter("state");
			String city=request.getParameter("city");
			String area=request.getParameter("area");
			
			DAO db = new DAO();
			ArrayList<HashMap<String,String>> users = db.searchPeople(state,city,area);
			db.closeConnection();
			HttpSession session = request.getSession();
			if (users.isEmpty()) {
				session.setAttribute("msg", "No Record Found!");
				response.sendRedirect("profile.jsp");
			} else {
				session.setAttribute("state", state);
				session.setAttribute("city", city);
				session.setAttribute("area", area);
				session.setAttribute("users", users);
				response.sendRedirect("PeopleSearch.jsp");
				
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect("Error.jsp");
		}

	}

}
