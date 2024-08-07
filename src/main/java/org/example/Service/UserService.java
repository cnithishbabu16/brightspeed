package org.example.Service;

import org.example.DAO.UserDAO;
import org.example.Model.User;

import java.sql.Connection;
import java.sql.SQLException;

import org.example.Utils.Validations;


public class UserService {
    User user;
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public boolean registerUser(Connection connection, User user) throws SQLException {
        if (userDAO.usernameExists(connection, user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        //Validations.validateUser(user);
        Validations.validatePhone(user.getPhoneNo());
        return userDAO.registerUser(connection,user);

    }

    public User loginUser(Connection connection,String username,String password){
//        Validations.validateUsername(username);
//        Validations.validatePassword(password);

        return userDAO.loginUser(connection, username, password);
    }

    public User viewProfile(Connection connection,String username){
        return userDAO.viewProfile(connection,username);
    }

    public boolean updateProfile(Connection connection,User user){
        //Validations.validateUser(user);
        Validations.validatePhone(user.getPhoneNo());
        return userDAO.updateProfile(connection,user);

    }

//    public boolean deleteProfile(Connection connection, String username){
//        return userDAO.deleteProfile(connection,username);
//    }
}
