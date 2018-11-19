package Controller;

import java.sql.SQLException;
import java.util.Vector;

public class Notice extends Database {

    public Notice() {
    }

    public Notice(int notice_number, String title, String content, String date, String admin) {
    }

    //메인화면에서 모든 공지사항 가져오기
    public Vector total_notice() throws SQLException {
        Vector Notice = new Vector();
        sql="select * from notification";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            int Notice_Number = rs.getInt("Notice_Number");
            String Title = rs.getString("Title");
            String Content = rs.getString("Content");
            String Date = rs.getString("Date");
            String Admin = rs.getString("Admin");

            Notice.add(Notice_Number);
            Notice.add(Title);
            Notice.add(Content);
            Notice.add(Date);
            Notice.add(Admin);
        }
        return Notice;
    }

    //메인화면에서 최근 공지사항 가져오기
    public Vector recent_notice() throws SQLException {
        Vector Notice = new Vector();
        sql="SELECT * " +
                "FROM Notification " +
                "WHERE Notice_Number = ( " +
                "SELECT max(Notice_Number) as cnt " +
                "FROM Notification " +
                ");";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            int Notice_Number = rs.getInt("Notice_Number");
            String Title = rs.getString("Title");
            String Content = rs.getString("Content");
            String Date = rs.getString("Date");
            String Admin = rs.getString("Admin");

            Notice.add(Notice_Number);
            Notice.add(Title);
            Notice.add(Content);
            Notice.add(Date);
            Notice.add(Admin);
        }
        return Notice;
    }
}