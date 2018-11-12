package com.codingsquid.spring4Tutorial;

import java.sql.Connection;
import java.sql.DriverManager;


public class mysqlTest {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/knuearth";
    private static final String USER = "0x01";
    private static final String PW = "root";

    public void testConnection() throws Exception{
        Class.forName(DRIVER);

        try(Connection con = DriverManager.getConnection(URL,USER,PW)){
            System.out.println("성공");
            System.out.println(con);
        }catch(Exception e){
            System.out.println("에러발생");
            e.printStackTrace();
        }
    }
}