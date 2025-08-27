package com.aurionpro.service;

import com.aurionpro.dao.UserDao;
import com.aurionpro.model.User;

public class UserService {

	 private UserDao userDao = new UserDao();
	 
	 public boolean registerUser(String fullName, String username, String password, String dept, String role) {
		 if (userDao.isUsernameTaken(username)) {
		        return false; 
		    }
		 return userDao.registerUser(fullName, username, password, dept, role);
	 }
	 
	 public String validateUser(String username, String password, String role) {
		 return userDao.validateUser(username, password, role);
	 }
	 
	 public int getUserIdByUsername(String username) {
		    return userDao.getUserIdByUsername(username);
		}

	 public String getUserDepartment(String username) {
		// TODO Auto-generated method stub
		return userDao.getUserDepartment(username);
	 }
	 public String getUserFullname(String username) {
		 return userDao.getUserFullname(username);
	 }
	 
	 public User getUserByUsername(String username) {
		 return userDao.getUserByUsername(username);
	 }
}
