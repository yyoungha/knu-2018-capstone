package Controller;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mysql.cj.jdbc.Driver;

import java.util.*;

@Controller
public class Member_controller {

    //로그인
    @RequestMapping(value="/SignIn")
    public @ResponseBody String SignIn() {
        Database db = new Database();
        Member member = new Member();
        JSONObject object = new JSONObject();

        try {
            object.put("Sign In",member.SignIn());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    //아이디 중복 체크 ==> 같은 아이디가 나오면 android에서 중복으로 체크하면 될듯
    @RequestMapping(value="/checkid")
    public @ResponseBody String CheckID() {
        Database db = new Database();
        Member member = new Member();
        JSONObject object = new JSONObject();

        try {
            object.put("checkid",member.checkID());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    //회원가입
    @RequestMapping(value="/signUp")
    public @ResponseBody String SignUp() {
        Database db = new Database();
        Member member = new Member();
        JSONObject object = new JSONObject();

        try {
            object.put("sign Up",member.SignUp());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    //이름,국적 가져오기
    @RequestMapping(value="/info")
    public @ResponseBody String getInfo() {
        Database db = new Database();
        Member member = new Member();
        JSONObject object = new JSONObject();

        try {
            object.put("Information",member.getInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    //메세지 수 가져오기
    @RequestMapping(value="/memMess")
    public @ResponseBody String getMessage_Count() {
        Database db = new Database();
        Member member = new Member();
        JSONObject object = new JSONObject();

        try {
            object.put("Message Count",member.getMessageCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    //전체 메세지 가져오기
    @RequestMapping(value="/total_message")
    public @ResponseBody String recent_Notice() {
        Database db = new Database();
        Member member = new Member();
        JSONObject object = new JSONObject();

        try {
            object.put("Total Message",member.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    //작성글 수 가져오기
    @RequestMapping(value="/memPost")
    public @ResponseBody String getPostingCount() {
        Database db = new Database();
        Member member = new Member();
        JSONObject object = new JSONObject();

        try {
            object.put("Posting_Count",member.getPostingCount());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    //전체 게시글 가져오기
    @RequestMapping(value="/total_posting")
    public @ResponseBody String total_Posting() {
        Database db = new Database();
        Member member = new Member();
        JSONObject object = new JSONObject();

        int i=0;
        try {
            object.put("Posting",member.total_Posting());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    //프사 가져오기
    @RequestMapping(value="/memimg")
    public @ResponseBody String getimg(){
        Database db = new Database();
        Member member = new Member();
        JSONObject object = new JSONObject();

        try {
            object.put("Image",member.getimg());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }
}
