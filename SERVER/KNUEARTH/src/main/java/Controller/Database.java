package Controller;

import java.sql.*;

public class Database {
    private static Connection db;
    private final static String SERVER = "127.0.0.1";
    private final static String USER = "KNUEARTH";
    private final static String PASSWORD = "root";
    private final static String DATABASE = "KNUEARTH";

    public static void main(String[] args) {
        Statement state = null;
        ResultSet result = null;

        try {
            db = DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + "?serverTimezome=UTC&useUnicode=true&characterEncoding=utf8", USER, PASSWORD);
            state = db.createStatement();
            result = state.executeQuery("SELECT * FROM ...");
            while(result.next()) {
                // Do Something like result.getInt("Col Name")
            }
            state.execute("INSERT, UPDATE, DELETE SQL");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(state != null) {
                    state.close();
                }
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}