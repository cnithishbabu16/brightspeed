//
//package org.example.Utils;
//
//import org.example.Model.User;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Validations {
//
//        public static boolean validateUsername(User user) {
////            if (user == null) {
////                throw new IllegalArgumentException("User cannot be null");
////            }
//
//            if (user.getUsername() == null || user.getUsername().isEmpty()) {
//                throw new IllegalArgumentException("Username cannot be empty");
//            } else {
//                String usernameRegex = "[a-z]+[0-9]+";
//                Pattern pattern = Pattern.compile(usernameRegex);
//                Matcher matcher = pattern.matcher(user.getUsername());
//                return matcher.matches();
//
//            }
//        }
//
//        public static boolean validatePassword(User user) {
//
//            if (user.getPassword() == null || user.getPassword().isEmpty()) {
//                throw new IllegalArgumentException("Password cannot be empty");
//            }
//            else{
//                String pswd="(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[!@#$%^&*]).{6,}";
//                Pattern pattern = Pattern.compile(pswd);
//                Matcher matcher = pattern.matcher(user.getPassword());
//                return matcher.matches();
//
//            }
//        }
//
////            if (user.getName() == null || user.getName().isEmpty()) {
////                throw new IllegalArgumentException("Name cannot be empty");
////            }
//
////            if (user.getPhoneNo() == null || user.getPhoneNo().isEmpty()) {
////                throw new IllegalArgumentException("Phone number cannot be empty");
////            }
//        public static boolean validateEmail(User user){
//            if (user.getEmail() == null || user.getEmail().isEmpty()) {
//                throw new IllegalArgumentException("Email cannot be empty");
//            }
//            else {
//
//                String emailRegex = "[a-z][a-zA-Z0-9]+@[a-zA-Z]+[.]com";
//                Pattern pattern = Pattern.compile(emailRegex);
//                Matcher matcher = pattern.matcher(user.getEmail());
//                return matcher.matches();
//            }
////            if (user.getAddress() == null || user.getAddress().isEmpty()) {
////                throw new IllegalArgumentException("Address cannot be empty");
////            }
//        }
//
////        public static void validateUsername(String username) {
////            if (username == null || username.isEmpty()) {
////                throw new IllegalArgumentException("Username cannot be empty");
////            }
////        }
////
////        public static void validatePassword(String password) {
////            if (password == null || password.isEmpty()) {
////                throw new IllegalArgumentException("Password cannot be empty");
////            }
////        }
//
//        public static void validatePlanId(int planId) {
//            if (planId <= 0) {
//                throw new IllegalArgumentException("Invalid plan ID");
//            }
//        }
//    }
//
//

package org.example.Utils;

import org.example.Model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
    private static final String PHONE_PATTERN = "^\\d{10}$";

//    public static void validateUser(User user) {
//        if (user == null) {
//            throw new IllegalArgumentException("User cannot be null");
//        }
//
//        validateUsername(user.getUsername());
//        validatePassword(user.getPassword());
//
//        if (user.getName() == null || user.getName().isEmpty()) {
//            throw new IllegalArgumentException("Name cannot be empty");
//        }
//
//        if (user.getPhoneNo() == null || user.getPhoneNo().isEmpty()) {
//            throw new IllegalArgumentException("Phone number cannot be empty");
//        } else if (user.getPhoneNo().length()<10||user.getPhoneNo().length()>10) {
//            throw new IllegalArgumentException("phone number should contain exactly 10 characters ");
//
//        }
//
//        validateEmail(user.getEmail());
//
//        if (user.getAddress() == null || user.getAddress().isEmpty()) {
//            throw new IllegalArgumentException("Address cannot be empty");
//        }
//    }

    public static boolean validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        else{
            String usernameRegex="[a-zA-Z]+[0-9]+";
            Pattern pattern = Pattern.compile(usernameRegex);
            Matcher matcher = pattern.matcher(username);
            return matcher.matches();
        }
//        if (!username.matches("^[a-zA-Z0-9]{3,20}$")) {
//            throw new IllegalArgumentException("Username must be alphanumeric and between 3 to 20 characters");
//        }
    }


    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {

            throw new IllegalArgumentException("Email cannot be empty");
        }
        else {
            String emailRegex = "[a-z][a-zA-Z0-9]+@[a-zA-Z]+[.]com";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        else{
            String pswd="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{6,}";
            Pattern pattern = Pattern.compile(pswd);
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
        }


//        if (password.length() < 4) {
//            throw new IllegalArgumentException("Password must be at least 4 characters long");
//        }
//        if (!password.matches(".*[A-Z].*")) {
//            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
//        }
//        if (!password.matches(".*[a-z].*")) {
//            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
//        }
//        if (!password.matches(".*\\d.*")) {
//            throw new IllegalArgumentException("Password must contain at least one digit");
//        }
//        if (!password.matches(".*[!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?].*")) {
//            throw new IllegalArgumentException("Password must contain at least one special character");
//        }
    }
    public static boolean validatePhoneNumber(String phone){
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        } else if (phone.length() != 10) {
            throw new IllegalArgumentException("phone number should contain exactly 10 characters ");

        }else{
            return true;
        }
    }
    public static void validatePhone(String phone) {
        if (phone == null || !Pattern.matches(PHONE_PATTERN, phone)) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits long.");
        }
    }

    public static void validatePlanId(int planId) {
        if (planId <= 0) {
            throw new IllegalArgumentException("Invalid plan ID");
        }
    }
}
