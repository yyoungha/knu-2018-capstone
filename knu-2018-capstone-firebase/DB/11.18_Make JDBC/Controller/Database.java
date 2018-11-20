package Controller;

import java.sql.*;
import java.util.Vector;

public class Database {
    StringBuffer sb = new StringBuffer("");
    String jdbcDriver;
    String jdbcUrl;
    Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;
    String sql;
    private  int NUMBER;

    public Database() {
        jdbcDriver = "com.mysql.cj.jdbc.Driver"; //jdbc drive경로&
        jdbcUrl = "jdbc:mysql://localhost:3306/KNUEARTH?serverTimezone=UTC&useSSL=false"; //db경로
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(jdbcUrl, "root", "root"); //jdbcurl / id / password
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try{
            getNumber();
        }catch(Exception e){}

    }

    public void getNumber() throws SQLException{
        sql = "SELECT * from Member";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();
        int i=0;
        while(rs.next()){
            ++i;
        }
        NUMBER=i;
    }
    public String[] selectName() throws SQLException{
        sql = "SELECT * from Member";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();
        String ret = "";
        String[] temp = new String[NUMBER];
        int i=0;
        while(rs.next()){
            temp[i] = rs.getString("Name");
            ++i;
        }
        return temp;
    }
}