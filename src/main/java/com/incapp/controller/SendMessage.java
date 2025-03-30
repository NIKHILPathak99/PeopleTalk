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
@WebServlet("/SendMessage")
@MultipartConfig
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String u_email=request.getParameter("u_email");
			String message=request.getParameter("message");
			
			HttpSession session = request.getSession();
			String email=(String)session.getAttribute("email");
			Part p = request.getPart("ufile");
			InputStream file;
			if (p.getSize() == 0) {
				file = null;
			} else {
				file = p.getInputStream();
			}
			String fname=p.getSubmittedFileName();
			
			DAO db = new DAO();
			db.sendMessage(email,u_email,message,file,fname);
			db.closeConnection();
			
			SendMail.notificationMail(u_email,email);
			
			session.setAttribute("msg", "Success!");
			response.sendRedirect("Talk.jsp");

		} catch (ClassNotFoundException | SQLException  e) {
			e.printStackTrace();
			response.sendRedirect("Error.jsp");
		}

	}

}
