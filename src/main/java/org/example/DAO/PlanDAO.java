package org.example.DAO;

import org.example.Model.Plans;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {

    public List<Plans> getAllPlans(Connection connection) {
        String sql = "SELECT * FROM plans";
        List<Plans> plans = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Plans plan = new Plans();
                plan.setId(rs.getInt("id"));
                plan.setName(rs.getString("name"));
                plan.setSpeed(rs.getString("speed"));
                plan.setPrice(rs.getDouble("price"));
                plan.setDuration(rs.getString("duration"));
                plans.add(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans;
    }
    public List<Plans> getUserSubscriptions(Connection connection, String username) {
        String sql = "SELECT p.* FROM plans p JOIN subscriptions s ON p.id = s.plan_id WHERE s.username = ?";
        List<Plans> plans = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Plans plan = new Plans();
                plan.setId(rs.getInt("id"));
                plan.setName(rs.getString("name"));
                plan.setSpeed(rs.getString("speed"));
                plan.setPrice(rs.getDouble("price"));
                plans.add(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans;
    }

    public boolean subscribeToPlan(Connection connection, String username, int planId) {
//        // Check if the plan ID exists
//
//        String checkPlanSql = "SELECT * FROM plans WHERE id = ?";
//        try (PreparedStatement pstmtCheckPlan = connection.prepareStatement(checkPlanSql)) {
//            pstmtCheckPlan.setInt(1, planId);
//            ResultSet rsPlan = pstmtCheckPlan.executeQuery();
//            if (!rsPlan.next()) {
//                //System.out.println("Invalid plan ID. Please try again.");
//                return true;
//
//            }
//        } catch (SQLException e) {
//            System.out.println("Error checking plan ID: " + e.getMessage());
//
//        }
//
//        // Check if the user is already subscribed to the plan
//        String checkSubscriptionSql = "SELECT * FROM subscriptions WHERE username = ? AND plan_id = ?";
//        try (PreparedStatement pstmtCheckSubscription = connection.prepareStatement(checkSubscriptionSql)) {
//            pstmtCheckSubscription.setString(1, username);
//            pstmtCheckSubscription.setInt(2, planId);
//            ResultSet rsSubscription = pstmtCheckSubscription.executeQuery();
//            if (rsSubscription.next()) {
//                System.out.println("You are already subscribed to this plan.");
//
//            }
//        } catch (SQLException e) {
//            System.out.println("Error checking subscription: " + e.getMessage());
//
//        }
        String sql = "INSERT INTO subscriptions (username, plan_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, planId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean optOutOfPlan(Connection connection, String username, int planId) {
        String sql = "DELETE FROM subscriptions WHERE username = ? AND plan_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, planId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean planExists(Connection connection, int planId) {
        String sql = "SELECT 1 FROM plans WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, planId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }


    public boolean isUserSubscribed(Connection connection, String username, int planId) {
        String sql = "SELECT 1 FROM subscriptions WHERE username = ? AND plan_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, planId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static double priceOfPlan(Connection connection, int planId) {
        String sql = "SELECT price FROM plans where id = ?";
        double d = 0.0;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, planId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // Move cursor to the first row
                    d = rs.getDouble("price");
                } else {
                    // Handle the case where no data is found
                    System.out.println("No plan found with id: " + planId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return d;

    }


}
