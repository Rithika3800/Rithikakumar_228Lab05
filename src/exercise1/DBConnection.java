package exercise1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String Url = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
    private static final String User = "COMP228_F24_soh_15";
    private static final String Password = "Rithika123";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(Url, User, Password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect database" + e.getMessage());
        }
    }
}

