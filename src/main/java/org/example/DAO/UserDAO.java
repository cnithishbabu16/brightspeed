package org.example.DAO;


import org.example.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class UserDAO {
    public boolean registerUser(Connection connection, User user) {
        String sql = "INSERT INTO users (username, password, name, phone, email, address) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getPhoneNo());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getAddress());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean usernameExists(Connection connection, String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public User loginUser(Connection connection, String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setName(rs.getString("name"));
                user.setPhoneNo(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User viewProfile(Connection connection,String username){
        String sql= "SELECT * FROM users where username=?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setName(rs.getString("name"));
                user.setPhoneNo(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                return user;
            }
        } catch (SQLException e) {
            //System.out.println("Error retrieving profile: " + e.getMessage());
            e.printStackTrace();
        }


        return null;
    }

    public boolean updateProfile(Connection connection, User user) {


        String sql = "UPDATE users SET phone = ?, email = ?, address = ? WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getPhoneNo());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getUsername());
            return pstmt.executeUpdate() > 0;


        } catch (SQLException e) {
            return false;
            //System.out.println("Error updating profile: " + e.getMessage());
        }

    }
//    public boolean deleteProfile(Connection connection, String username){
//        String sql = "DELETE FROM users WHERE username=? ";
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1,username);
//            int rows = pstmt.executeUpdate();
//            return rows > 0;
//
//        } catch (SQLException e) {
//            return false;
//
//        }
//
//
//    }

}
