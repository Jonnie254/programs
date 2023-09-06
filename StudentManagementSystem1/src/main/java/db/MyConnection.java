package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private static final String username = "";
    private static final String password = "";
    private static final String dataconn = "jdbc:mysql://localhost:3306/student_management";
    private static Connection conn = null;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dataconn, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
        }
        return conn;
    }
}
