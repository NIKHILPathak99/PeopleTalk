package com.incapp.model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;

public class DAO {
	private Connection c;
	public DAO() throws SQLException,ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		c=DriverManager.getConnection("jdbc:mysql://localhost:3306/peopletalk","root","Nikhil@727537");
	}
	public void closeConnection() throws SQLException{
		c.close();
	}
	
	public boolean register(HashMap<String, Object> user)throws SQLException {
		try {
			PreparedStatement p=c.prepareStatement("insert into users (email,password,name,phone,gender,dob,state,city,area,photo) values (?,?,?,?,?,?,?,?,?,?)");
			p.setString(1, (String)user.get("email"));
			p.setString(2, (String)user.get("password"));
			p.setString(3, (String)user.get("name"));
			p.setString(4, (String)user.get("phone"));
			p.setString(5, (String)user.get("gender"));
			p.setDate(6, (java.sql.Date)user.get("dob"));
			p.setString(7, (String)user.get("state"));
			p.setString(8, (String)user.get("city"));
			p.setString(9, (String)user.get("area"));
			p.setBinaryStream(10, (InputStream)user.get("photo"));
			p.executeUpdate();
			return true;
		}catch (SQLIntegrityConstraintViolationException e) {
			return false;
		}
	}
	
	public String login(String email,String password)throws SQLException {
		PreparedStatement p=c.prepareStatement("select name from users where email=? and password=?");
		p.setString(1, email);
		p.setString(2, password);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			return rs.getString("name");
		}else {
			return null;
		}
	}
	public HashMap<String,String> getUserDetails(String email)throws SQLException {
		PreparedStatement p=c.prepareStatement("select * from users where email=?");
		p.setString(1, email);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			HashMap<String,String> user=new HashMap<>();
			user.put("name", rs.getString("name"));
			user.put("phone", rs.getString("phone"));
			user.put("gender", rs.getString("gender"));
			user.put("dob", rs.getString("dob"));
			user.put("state", rs.getString("state"));
			user.put("city", rs.getString("city"));
			user.put("area", rs.getString("area"));
			return user;
		}else {
			return null;
		}
	}
	public byte[] getPhoto(String email)throws SQLException {
		PreparedStatement p=c.prepareStatement("select photo from users where email=? ");
		p.setString(1, email);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			byte[] photo=rs.getBytes("photo");
			if(photo==null) {
				return null;
			}else {
				return photo;
			}
			
		}else {
			return null;
		}
	}
	
	public void changePhoto(String email,InputStream photo)throws SQLException {
		PreparedStatement p=c.prepareStatement("update users set photo=? where email=?");
		p.setBinaryStream(1, photo);
		p.setString(2, email);
		p.executeUpdate();
	}
	
	public String changePassword(String email,String oldpassword,String newpassword)throws SQLException {
		PreparedStatement p=c.prepareStatement("update users set password=? where email=? and password=?");
		p.setString(1, newpassword);
		p.setString(2, email);
		p.setString(3, oldpassword);
		int r=p.executeUpdate();
		if(r==0) {
			return "Old Password is wrong!";
		}else {
			return "Password Updated!";
		}
	}
	
	public void updateProfile(HashMap<String, Object> user)throws SQLException {
		PreparedStatement p=c.prepareStatement("update users set name=? , phone=?,gender=?,dob=?,state=?,city=?,area=? where email=?");
		p.setString(1, (String)user.get("name"));
		p.setString(2, (String)user.get("phone"));
		p.setString(3, (String)user.get("gender"));
		p.setDate(4, (java.sql.Date)user.get("dob"));
		p.setString(5, (String)user.get("state"));
		p.setString(6, (String)user.get("city"));
		p.setString(7, (String)user.get("area"));
		p.setString(8, (String)user.get("email"));
		p.executeUpdate();
	}
	public ArrayList<HashMap<String,String>> searchPeople(String state,String city,String area)throws SQLException {
		PreparedStatement p=c.prepareStatement("select * from users where state=? and city=? and area like ?");
		p.setString(1, state);
		p.setString(2, city);
		p.setString(3, "%"+area+"%");
		ResultSet rs=p.executeQuery();
		ArrayList<HashMap<String,String>> users=new ArrayList<HashMap<String,String>>();
		while(rs.next()) {
			HashMap<String,String> user=new HashMap<>();
			user.put("email", rs.getString("email"));
			user.put("name", rs.getString("name"));
			user.put("phone", rs.getString("phone"));
			user.put("gender", rs.getString("gender"));
			user.put("dob", rs.getString("dob"));
			user.put("state", rs.getString("state"));
			user.put("city", rs.getString("city"));
			user.put("area", rs.getString("area"));
			users.add(user);
		}
		return users;
	}
	public void sendMessage(String email,String u_email,String message,InputStream file,String fname)throws SQLException {
		
		PreparedStatement p=c.prepareStatement("insert into talks (semail,remail,message,msg_date,msg_time,file,file_name) values (?,?,?,CURRENT_DATE,CURRENT_TIME,?,?)");
		p.setString(1, email);
		p.setString(2,u_email);
		p.setString(3,message);
		p.setBinaryStream(4, file);
		p.setString(5, fname);
		p.executeUpdate();
		
	}
	public ArrayList<HashMap<String,String>> getMessages(String semail,String remail)throws SQLException {
		PreparedStatement p=c.prepareStatement("select * from talks where semail=? and remail=?");
		p.setString(1, semail);
		p.setString(2, remail);
		ResultSet rs=p.executeQuery();
		ArrayList<HashMap<String,String>> messages=new ArrayList<HashMap<String,String>>();
		while(rs.next()) {
			HashMap<String,String> message=new HashMap<>();
			message.put("id", rs.getString("id"));
			message.put("message", rs.getString("message"));
			message.put("file_name", rs.getString("file_name"));
			message.put("msg_date", rs.getString("msg_date"));
			message.put("msg_time", rs.getString("msg_time"));
			messages.add(message);
		}
		return messages;
	}
	public HashMap<String,Object> getFile(int id) throws SQLException {
		PreparedStatement p=c.prepareStatement("select * from talks where id=?");
		p.setInt(1, id);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			 byte file[]=rs.getBytes("file");
			 String fname=rs.getString("file_name");
			 HashMap<String,Object> data=new HashMap<>();
			 data.put("fname", fname);
			 data.put("file", file);
			 return data;
		}
		else
			return null;
	}
	
	public String getPassword(String email)throws SQLException {
		PreparedStatement p=c.prepareStatement("select password from users where email=?");
		p.setString(1, email);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			return rs.getString("password");
		}else {
			return null;
		}
	}
	
}
