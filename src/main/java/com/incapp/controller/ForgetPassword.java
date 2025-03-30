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

import com.incapp.mail.SendMail;
import com.incapp.model.DAO;

/**
 * Servlet implementation class Register
 */
@WebServlet("/ForgetPassword")
public class ForgetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email=request.getParameter("email");
			
			DAO db = new DAO();
			String r=db.getPassword(email);
			db.closeConnection();
			HttpSession session = request.getSession();
			if(r==null) {
				session.setAttribute("msg", "Email ID does not exist!");
			}else {
				session.setAttribute("msg", "Password sent to your mail successfull!");
				SendMail.passwordMail(email, r);
			}
			response.sendRedirect("ForgetPassword.jsp");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect("Error.jsp");
		}

	}

}
