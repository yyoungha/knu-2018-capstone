package org.test.knuearth;

import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.*;

public class mysqlTestTest extends TestCase {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/KNUEARTH?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PW = "root";

    @Test
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