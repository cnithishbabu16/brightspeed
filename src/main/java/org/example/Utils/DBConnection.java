
package org.example.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection(){
        final String url="jdbc:mysql://localhost:3306/revspeed";
        final String user="root";
        final String pwd = "2001";

        Connection connection;

        try {
            connection= DriverManager.getConnection(url,user,pwd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
