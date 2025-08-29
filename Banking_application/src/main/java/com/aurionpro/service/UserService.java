package com.aurionpro.service;

import java.util.List;

import com.aurionpro.dao.UserDAO;
import com.aurionpro.model.Customer;
import com.aurionpro.model.User;
import com.aurionpro.model.UserCustomer;


public class UserService {

    private UserDAO userDAO;

    // Constructor injection (better for testing)
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    // Alternative constructor for dependency injection
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public boolean usernameExists(String username) {
        try {
            return userDAO.usernameExists(username);
        } catch (Exception ex) {
            ex.printStackTrace();
            return true; // conservative: assume exists on error
        }
    }

    public int registerUser(User user, Customer customer) {
        try {
            return userDAO.registerUserWithCustomer(user, customer);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public User login(String username, String password, String role) {
        try {
            return userDAO.validateLogin(username, password, role);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<UserCustomer> getInactiveAndKycPendingCustomers(){
    	return userDAO.getInactiveAndKycPendingCustomers();
    }
    
    public boolean verifyKycAndActivateCustomer(int userId) {
    	return userDAO.verifyKycAndActivateCustomer(userId);
    }
    public boolean rejectCustomer(int userId, String remarks) {
        return userDAO.rejectCustomer(userId, remarks);
    }

}