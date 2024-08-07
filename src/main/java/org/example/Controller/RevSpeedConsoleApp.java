package org.example.Controller;

import org.example.DAO.PlanDAO;
import org.example.DAO.UserDAO;
import org.example.Model.Plans;
import org.example.Model.User;
import org.example.Service.PlanService;
import org.example.Service.UserService;
import org.example.Utils.DBConnection;
import java.sql.SQLException;


import org.example.Utils.Validations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class RevSpeedConsoleApp {
    private static final Logger logger=  LoggerFactory.getLogger(RevSpeedConsoleApp.class);
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()) {
            UserDAO userDAO = new UserDAO();
            PlanDAO planDAO = new PlanDAO();
            UserService userService = new UserService(userDAO);
            PlanService planService = new PlanService(planDAO);

            while (true) {
                System.out.println("Welcome to RevSpeed!");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        register(userService, connection);
                        break;
                    case 2:
                        login(userService, planService, connection);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void register(UserService userService, Connection connection) throws SQLException {
        logger.info("Registration process started...");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        String phone = "";
        try{

            System.out.print("Enter your phone number: ");
            phone = scanner.nextLine();
        }catch(IllegalArgumentException e) {



//            while (!Validations.validatePhoneNumber(phone)) {
//                System.out.println("Invalid phone number. Please Enter valid Number: ");
//                phone = scanner.nextLine();
//            }
            System.out.println(e.getMessage());
        }

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        while(!Validations.validateEmail(email)){
            System.out.println("enter valid email: ");
            email=scanner.nextLine();
        }
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
        System.out.print("Create a username: ");
        String username = scanner.nextLine();
        while (!Validations.validateUsername(username)) {
            System.out.println("enter a valid username again");
            username = scanner.nextLine();
        }
        System.out.print("Create a password: ");
        String password = scanner.nextLine();
        while(!Validations.validatePassword(password)){
            System.out.println("Invalid password enter again");
            password=scanner.nextLine();
        }

        User user = new User();
        user.setName(name);
        user.setPhoneNo(phone);
        user.setEmail(email);
        user.setAddress(address);
        user.setUsername(username);
        user.setPassword(password);
        try {


            boolean success = userService.registerUser(connection, user);
            if (success) {
                logger.info("User Registered Successfully");
                System.out.println("Registration successful! Please log in.");
            } //else {
//                logger.error("Unable to register, Error during registration");
//                System.out.println("Error during registration.");
//            }
        }catch(SQLException | IllegalArgumentException e){

            logger.error("Unable to register, Error during registration");
            System.out.println("Error during registration. "+e.getMessage());


        }
    }

    private static void login(UserService userService, PlanService planService, Connection connection) throws SQLException {
        logger.info("User logging in... ");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        while(!Validations.validateUsername(username)){
            System.out.println("enter a valid username again");
            username=scanner.nextLine();
        }
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        while(!Validations.validatePassword(password)){
            System.out.println("Invalid password enter again");
            password=scanner.nextLine();
        }

        User user = userService.loginUser(connection, username, password);
        if (user != null) {
            logger.info("Login successfull with username: "+username);

            System.out.println("Login successful!");
            userMenu(userService, planService, connection, user.getUsername());
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void userMenu(UserService userService, PlanService planService, Connection connection, String username) throws SQLException {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. View Plans");
            System.out.println("4. Subscribe to a Plan");
            System.out.println("5. View Subscriptions");
            System.out.println("6. Opt-out of a Plan");
           // System.out.println("7. Delete Profile");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewProfile(userService, connection, username);
                    break;
                case 2:
                    updateProfile(userService, connection, username);
                    break;
                case 3:
                    viewPlans(planService, connection);
                    break;
                case 4:
                    subscribeToPlan(planService,connection, username);
                    break;
                case 5:
                    viewSubscriptions(planService,connection, username);
                    break;
                case 6:
                    optOutOfPlan(planService,connection, username);
                    break;
//                case 7:
//                    deleteProfile(userService,connection, username);
//                    break;

                case 7:
                    logger.info("User Logged out...");
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void viewProfile(UserService userService, Connection connection, String username) {
        User user = userService.viewProfile(connection, username);
        if (user != null) {
            System.out.println("\nProfile Details:");
            System.out.println("username: "+ user.getUsername());
            System.out.println("Name: " + user.getName());
            System.out.println("Phone: " + user.getPhoneNo());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Address: " + user.getAddress());
        } else {
            System.out.println("Error retrieving profile.");
        }
    }

    private static void updateProfile(UserService userService, Connection connection, String username) throws SQLException, IllegalArgumentException {

        System.out.print("Enter your phone number: ");
        String phone = scanner.nextLine();
//        while (!Validations.validatePhoneNumber(phone)) {
//            System.out.println("Invalid phone number. Please Enter valid Number: ");
//            phone = scanner.nextLine();
//        }




        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        while(!Validations.validateEmail(email)){
            System.out.println("enter valid email: ");
            email=scanner.nextLine();
        }

        System.out.print("Enter new address: ");
        String address = scanner.nextLine();

        User user = new User();
        user.setUsername(username);
        user.setPhoneNo(phone);
        user.setEmail(email);
        user.setAddress(address);

        try {
            boolean success = userService.updateProfile(connection, user);
            if (success) {
                logger.info("Profile of " + username + "has updated successfully");
                System.out.println("Profile updated successfully.");
            }
        }catch(IllegalArgumentException e){
            System.out.println("Error updating profile. "+e.getMessage());
        }


    }

    private static void viewPlans(PlanService planService, Connection connection) {
        List<Plans> plans = planService.getAllPlans(connection);
        System.out.println("\nAvailable Plans:");
        System.out.println("    ID  " + "   Name    "+"  Speed   "+"    Price   "+"   Duration  ");
        for (Plans plan : plans) {
            System.out.println( "   "+plan.getId() + "   "+ plan.getName()+"   "
                     + plan.getSpeed() + "     $" + plan.getPrice() +"      "+ plan.getDuration());
        }
    }


    private static void subscribeToPlan(PlanService planService, Connection connection, String username) {
        System.out.print("Enter the plan ID to subscribe to: ");
        int planId = scanner.nextInt();
        scanner.nextLine();
            double amount = PlanDAO.priceOfPlan(connection,planId);

            System.out.println("You are about to make a payment of  $" + amount+"  for subscribing the plan");
            System.out.println("1. Confirm");
            System.out.println("2. Cancel");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1){
                boolean success = planService.subscribeToPlan(connection, username, planId);
                if (success) {
                    logger.info("User with username "+username+" subscribed to the plan of id: "+planId);
                System.out.println("Successfully subscribed to the plan.");
                } else {
                    logger.warn("User cancelled the payment");

                    System.out.println("Payment has been canceled. So, Failed to subscribe to the plan.");
                }
            }
    }


    private static void viewSubscriptions(PlanService planService, Connection connection, String username) {
        List<Plans> plans = planService.getUserSubscriptions(connection, username);
        System.out.println("\nYour Subscriptions:");
        for (Plans plan : plans) {
            System.out.println("ID: " + plan.getId() + ", Name: " + plan.getName() +
                    ", Speed: " + plan.getSpeed() + ", Price: $" + plan.getPrice() + ", Duration: "+plan.getDuration());
        }
    }

    private static void optOutOfPlan(PlanService planService, Connection connection, String username) {
        System.out.print("Enter the plan ID to opt out of: ");
        int planId = scanner.nextInt();
        scanner.nextLine();

        boolean success = planService.optOutOfPlan(connection, username, planId);
        if (success) {
            logger.warn("User opted out of the plan of id: "+planId);
            System.out.println("Successfully opted out of the plan.");
        } else {
            System.out.println("Failed to opt out of the plan.");
        }
    }

//    private static void deleteProfile(UserService userService,Connection connection, String username){
//        boolean userdata= userService.deleteProfile(connection,username);
//        if(userdata){
//            System.out.println("Your profile has been deleted");
//        }
//
//
//
//    }

//    private static boolean makePayment(Connection connection, String username) {
//        System.out.print("Enter payment amount: ");
//        double amount = scanner.nextDouble();
//        scanner.nextLine();
//        System.out.println("You are about to make a payment of $" + amount);
//        System.out.println("1. Confirm");
//        System.out.println("2. Cancel");
//        System.out.print("Choose an option: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        if (choice == 1) {
//            return true;
//
//        } else {
//            return false;
//            //System.out.println("Payment has been canceled.");
//        }
//
//    }


}
