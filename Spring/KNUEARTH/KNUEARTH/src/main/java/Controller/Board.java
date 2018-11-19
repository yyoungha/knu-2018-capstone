package Controller;

import java.io.Writer;
import java.sql.SQLException;
import java.util.Vector;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.scene.chart.PieChart;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.sql.*;
import java.util.Vector;

public class Board extends Database{
    private int Posting_Number;
    private String Writer_id;
    private String Foreword;
    private String Title;
    private String Date;
    private String Content;
    private String Image;
    private String Comment;

    // 전체 게시글 뿌려주기
    public Vector total_board() throws SQLException {
        Vector total_board = new Vector();
        sql="select b.Posting_Number, b.Writer_id, b.Foreword, b.Title, b.Date, b.Content,b.Image, m.Image from bulletin_board b, member m where m.Writer_id = b.Writer_id;";
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

            total_board.add(Posting_Number);
            total_board.add(Writer_id);
            total_board.add(Foreword);
            total_board.add(Title);
            total_board.add(Date);
            total_board.add(Content);
            total_board.add(Image);
            total_board.add(Writer_Image);
        }
        return total_board;
    }
}