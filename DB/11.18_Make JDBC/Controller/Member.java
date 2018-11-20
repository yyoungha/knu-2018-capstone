package Controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.scene.chart.PieChart;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.sql.*;
import java.util.Vector;

public class Member extends Database {

    private String Writer_id;
    private String Name;
    private String Student_id;
    private String Password;
    private String Nation;
    private String Major;

    // 로그인
    public Vector SignIn() throws SQLException {
        Vector SignIn = new Vector();
        sql="select Writer_id, Password from member where Writer_id='makeastir' and Password='kyu'";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            String Writer_id=rs.getString("Writer_id");
            String password = rs.getString("Password");

            SignIn.add(Writer_id);
            SignIn.add(Password);
        }
        return SignIn;
    }



    //아이디 중복 검사
    public String checkID() throws SQLException {
        String checkID=null;
        sql="select Writer_id from member where Writer_id="+ Writer_id + "";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            checkID=rs.getString("Writer_id");
        }

        return checkID;
    }

    //회원가입
    public int SignUp() throws SQLException {
        int check=0;
        sql="insert into Member VALUES(?,?,?,?,?,?,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,Writer_id);
        pstmt.setString(2,Name);
        pstmt.setString(3,Student_id);
        pstmt.setString(4,Password);
        pstmt.setString(5,Nation);
        pstmt.setString(6,Major);

        rs=pstmt.executeQuery();

        return check;
    }

    //이름,국적 가져오기
    public Vector getInfo() throws SQLException {
        Vector Info = new Vector();
        sql="select Name, Nation from member where Writer_id="+Writer_id+"";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            Name=rs.getString("Name");
            Nation=rs.getString("Nation");

            Info.add(Name);
            Info.add(Nation);
        }
        return Info;
    }

    //메세지 수 가져오기
    public int getMessageCount() throws SQLException {
        int Message_Count=0;
        sql="select count(*) as message_count from message where Receiver_id = " + Writer_id + "";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            Message_Count=rs.getInt("message_count");
        }

        return Message_Count;
    }

    //모든 메세지 가져오기
    public Vector getMessage() throws SQLException {
        Vector Message = new Vector();
        sql="select Writer_id, Receiver_id,Content,Date from message where Receiver_id = 'makeastir' OR Writer_id = 'makeastir'";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();

        while(rs.next()){
            String Writer_id = rs.getString("Writer_id");
            String Receiver_id = rs.getString("Receiver_id");
            String Content = rs.getString("Content");
            String Date = rs.getString("Date");

            Message.add(Writer_id);
            Message.add(Receiver_id);
            Message.add(Content);
            Message.add(Date);
        }
        return Message;
    }

    //작성한 게시글 수 가져오기
    public int getPostingCount() throws SQLException{
        int posting_Count =0;

        sql = "select count(Writer_id) as Posting_Count" +
                " from( select Writer_id from bathroom" +
                " UNION ALL " +
                "select Writer_id from bulletin_board" +
                " UNION ALL" +
                " select Writer_id from daily " +
                "UNION ALL" +
                " select Writer_id from electronic " +
                "UNION ALL" +
                " select Writer_id from interior" +
                " UNION ALL" +
                " select Writer_id from kitchen" +
                " UNION ALL" +
                " select Writer_id from office" +
                ")member where Writer_id = "+Writer_id+"";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();
        while(rs.next()){
            posting_Count=rs.getInt("Posting_Count");
        }

        return posting_Count;
    }

    //작성한 모든 게시글 가져오기
    public Vector total_Posting() throws SQLException{
        Vector Posting = new Vector();

        sql = "SELECT Writer_id, Title, Foreword, Content, Date " +
                "FROM (SELECT Writer_id, Title, Foreword, Content, Date from bathroom " +
                "UNION ALL " +
                "SELECT Writer_id, Title, Foreword, Content, Date from bulletin_board " +
                "UNION ALL " +
                "SELECT Writer_id, Title, Foreword, Content, Date from daily " +
                "UNION ALL " +
                "SELECT Writer_id, Title, Foreword, Content, Date from electronic " +
                "UNION ALL " +
                "SELECT Writer_id, Title, Foreword, Content, Date from interior " +
                "UNION ALL " +
                "SELECT Writer_id, Title, Foreword, Content, Date from kitchen " +
                "UNION ALL " +
                "SELECT Writer_id, Title, Foreword, Content, Date from office " +
                ")member where Writer_id = " + Writer_id + "";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();
        while(rs.next()){
            String Writer_id =rs.getString("Writer_Id");
            String Title = rs.getString("Title");
            String Foreword = rs.getString("Foreword");
            String Content = rs.getString("Content");
            String Date = rs.getString("Date");

            Posting.add(Writer_id);
            Posting.add(Title);
            Posting.add(Foreword);
            Posting.add(Content);
            Posting.add(Date);
        }

        return Posting;
    }



    // 사용자 프사 가져오기
    public String getimg() throws SQLException{
        String img_address =null;

        sql = "select Image as img_address from member where Writer_id = "+Writer_id+"";
        pstmt = conn.prepareStatement(sql);
        rs=pstmt.executeQuery();
        while(rs.next()){
            img_address=rs.getString("img_address");
        }

        return img_address;
    }

}
