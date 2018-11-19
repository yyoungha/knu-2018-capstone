package Controller;

import java.sql.SQLException;
import java.util.Vector;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.scene.chart.PieChart;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.sql.*;
import java.util.Vector;

public class Market extends Database {
    private int Posting_Number;
    private String Writer_id;
    private String Foreword;
    private String Title;
    private String Date;
    private String Content;
    private String Image;
    private String Comment;

    // Bathroom 게시글 뿌려주기
    public Vector total_bathroom() throws SQLException {
        Vector total_bath= new Vector();
        sql="select b.Posting_Number, b.Writer_id, b.Foreword, b.Title, b.Date, b.Content,b.Image, m.Image from bathroom b, member m where m.Writer_id = b.Writer_id;";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            int Posting_Number = rs.getInt("b.Posting_Number");
            String Writer_id=rs.getString("b.Writer_id");
            String Foreword = rs.getString("b.Foreword");
            String Title = rs.getString("b.Title");
            String Date = rs.getString("b.Date");
            String Content = rs.getString("b.Content");
            String Image = rs.getString("b.Image");
            String Writer_Image = rs.getString("m.Image");

            total_bath.add(Posting_Number);
            total_bath.add(Writer_id);
            total_bath.add(Foreword);
            total_bath.add(Title);
            total_bath.add(Date);
            total_bath.add(Content);
            total_bath.add(Image);
            total_bath.add(Writer_Image);
        }
        return total_bath;
    }
    //daily 게시글 불러오기
    public Vector total_daily() throws SQLException {
        Vector total_daily = new Vector();
        sql="select d.Posting_Number, d.Writer_id, d.Foreword, d.Title, d.Date, d.Content,d.Image, m.Image from daily d, member m where m.Writer_id = d.Writer_id;";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            int Posting_Number = rs.getInt("d.Posting_Number");
            String Writer_id=rs.getString("d.Writer_id");
            String Foreword = rs.getString("d.Foreword");
            String Title = rs.getString("d.Title");
            String Date = rs.getString("d.Date");
            String Content = rs.getString("d.Content");
            String Image = rs.getString("d.Image");
            String Writer_Image = rs.getString("m.Image");

            total_daily.add(Posting_Number);
            total_daily.add(Writer_id);
            total_daily.add(Foreword);
            total_daily.add(Title);
            total_daily.add(Date);
            total_daily.add(Content);
            total_daily.add(Image);
            total_daily.add(Writer_Image);
        }
        return total_daily;
    }
    //electronic 게시글 불러오기
    public Vector total_electronic() throws SQLException {
        Vector total_electronic = new Vector();
        sql="select e.Posting_Number, e.Writer_id, e.Foreword, e.Title, e.Date, e.Content,e.Image, m.Image from electronic e, member m where m.Writer_id = e.Writer_id;";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            int Posting_Number = rs.getInt("e.Posting_Number");
            String Writer_id=rs.getString("e.Writer_id");
            String Foreword = rs.getString("e.Foreword");
            String Title = rs.getString("e.Title");
            String Date = rs.getString("e.Date");
            String Content = rs.getString("e.Content");
            String Image = rs.getString("e.Image");
            String Writer_Image = rs.getString("m.Image");

            total_electronic.add(Posting_Number);
            total_electronic.add(Writer_id);
            total_electronic.add(Foreword);
            total_electronic.add(Title);
            total_electronic.add(Date);
            total_electronic.add(Content);
            total_electronic.add(Image);
            total_electronic.add(Writer_Image);
        }
        return total_electronic;
    }
    //interior 게시글 불러오기
    public Vector total_interior() throws SQLException {
        Vector total_interior = new Vector();
        sql="select i.Posting_Number, i.Writer_id, i.Foreword, i.Title, i.Date, i.Content,i.Image, m.Image from interior i, member m where m.Writer_id = i.Writer_id;";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            int Posting_Number = rs.getInt("i.Posting_Number");
            String Writer_id=rs.getString("i.Writer_id");
            String Foreword = rs.getString("i.Foreword");
            String Title = rs.getString("i.Title");
            String Date = rs.getString("i.Date");
            String Content = rs.getString("i.Content");
            String Image = rs.getString("i.Image");
            String Writer_Image = rs.getString("m.Image");

            total_interior.add(Posting_Number);
            total_interior.add(Writer_id);
            total_interior.add(Foreword);
            total_interior.add(Title);
            total_interior.add(Date);
            total_interior.add(Content);
            total_interior.add(Image);
            total_interior.add(Writer_Image);
        }
        return total_interior;
    }
    //kitchen 게시글 불러오기
    public Vector total_kitchen() throws SQLException {
        Vector total_kitchen = new Vector();
        sql="select k.Posting_Number, k.Writer_id, k.Foreword, k.Title, k.Date, k.Content,k.Image, m.Image from kitchen k, member m where m.Writer_id = k.Writer_id;";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            int Posting_Number = rs.getInt("k.Posting_Number");
            String Writer_id=rs.getString("k.Writer_id");
            String Foreword = rs.getString("k.Foreword");
            String Title = rs.getString("k.Title");
            String Date = rs.getString("k.Date");
            String Content = rs.getString("k.Content");
            String Image = rs.getString("k.Image");
            String Writer_Image = rs.getString("m.Image");

            total_kitchen.add(Posting_Number);
            total_kitchen.add(Writer_id);
            total_kitchen.add(Foreword);
            total_kitchen.add(Title);
            total_kitchen.add(Date);
            total_kitchen.add(Content);
            total_kitchen.add(Image);
            total_kitchen.add(Writer_Image);
        }
        return total_kitchen;
    }
    //office 게시글 불러오기
    public Vector total_office() throws SQLException {
        Vector total_office = new Vector();
        sql="select o.Posting_Number, o.Writer_id, o.Foreword, o.Title, o.Date, o.Content,o.Image, m.Image from office o, member m where m.Writer_id = o.Writer_id;";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            int Posting_Number = rs.getInt("o.Posting_Number");
            String Writer_id=rs.getString("o.Writer_id");
            String Foreword = rs.getString("o.Foreword");
            String Title = rs.getString("o.Title");
            String Date = rs.getString("o.Date");
            String Content = rs.getString("o.Content");
            String Image = rs.getString("o.Image");
            String Writer_Image = rs.getString("m.Image");

            total_office.add(Posting_Number);
            total_office.add(Writer_id);
            total_office.add(Foreword);
            total_office.add(Title);
            total_office.add(Date);
            total_office.add(Content);
            total_office.add(Image);
            total_office.add(Writer_Image);
        }
        return total_office;
    }
}
