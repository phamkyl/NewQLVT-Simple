package qlvt.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DistributedDatabaseConnection {

    public static final String SERVER1_URL = "jdbc:sqlserver://DESKTOP-9LSVPH7\\KYLE:1433;databaseName=QLVT_NEW;user=sa;password=12345;trustServerCertificate=true";

    // Kết nối đến một server cụ thể
    public Connection connectToServer(String serverUrl) throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(serverUrl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("SQL Server JDBC Driver không tìm thấy.");
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(SERVER1_URL);
    }



}

