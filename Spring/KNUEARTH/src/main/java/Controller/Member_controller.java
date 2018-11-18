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
    @RequestMapping(value="/json/member")
    public String index(){
        return "index";
    }

    //로그인
    @RequestMapping(value="/SignIn")
    public @ResponseBody String SignIn() {
        Database db = new Database();
        Member member = new Member();
        Vector jsonStr = new Vector();
        try {
            jsonStr.add(member.SignIn());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject returnObject = new JSONObject();
        JSONArray array = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("", jsonStr);
        array.put(object);

        returnObject.put("", array);
        return returnObject.toString();
    }

    //아이디 중복 체크 ==> 같은 아이디가 나오면 android에서 중복으로 체크하면 될듯
    @RequestMapping(value="/checkid")
    public @ResponseBody String CheckID() {
        Database db = new Database();
        Member member = new Member();
        String jsonStr = null;
        try {
            jsonStr = member.checkID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject returnObject = new JSONObject();
        JSONArray array = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("", jsonStr);
        array.put(object);

        returnObject.put("", array);
        return returnObject.toString();
    }

    //회원가입
    @RequestMapping(value="/signUp")
    public @ResponseBody String SignUp() {
        Database db = new Database();
        Member member = new Member();
        int jsonStr = 0;
        try {
            jsonStr = member.SignUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject returnObject = new JSONObject();
        JSONArray array = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("", jsonStr);
        array.put(object);

        returnObject.put("Sign_Up", array);
        return returnObject.toString();
    }

    //이름,국적 가져오기
    @RequestMapping(value="/info")
    public @ResponseBody String getInfo() {
        Database db = new Database();
        Member member = new Member();
        Vector jsonStr = new Vector();
        try {
            jsonStr.add(member.getInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject returnObject = new JSONObject();
        JSONArray array = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("", jsonStr);
        array.put(object);

        returnObject.put("", array);
        return returnObject.toString();
    }

    //메세지 가져오기
    @RequestMapping(value="/memMess")
    public @ResponseBody String getMessage_Count() {
        Database db = new Database();
        Member member = new Member();
        int jsonStr = 0;
        try {
            jsonStr = member.getMessageCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject returnObject = new JSONObject();
        JSONArray array = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("", jsonStr);
        array.put(object);

        returnObject.put("Message_count", array);
        return returnObject.toString();
    }

    //전체 메세지 가져오기
    @RequestMapping(value="/total_message")
    public @ResponseBody String recent_Notice() {
        Database db = new Database();
        Member member = new Member();
        Vector jsonStr = new Vector();
        int i=0;
        try {
            jsonStr.add(member.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject returnObject = new JSONObject();
        JSONArray array = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("",jsonStr);
        array.put(object);

        returnObject.put("Notification", array);
        return returnObject.toString();
    }

    //작성글 수 가져오기
    @RequestMapping(value="/memPost")
    public @ResponseBody String getPostingCount() {
        Database db = new Database();
        Member member = new Member();
        int jsonStr = 0;
        try {
            jsonStr = member.getPostingCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject returnObject = new JSONObject();
        JSONArray array = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("", jsonStr);
        array.put(object);

        returnObject.put("Posting_count", array);
        return returnObject.toString();
    }

    //전체 게시글 가져오기
    @RequestMapping(value="/total_posting")
    public @ResponseBody String total_Posting() {
        Database db = new Database();
        Member member = new Member();
        Vector jsonStr = new Vector();
        int i=0;
        try {
            jsonStr.add(member.total_Posting());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject returnObject = new JSONObject();
        JSONArray array = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("",jsonStr);
        array.put(object);

        returnObject.put("Notification", array);
        return returnObject.toString();
    }

    //프사 가져오기
    @RequestMapping(value="/memimg")
    public @ResponseBody String getimg(){
        Database db = new Database();
        Member member = new Member();
        String jsonStr = null;
        try {
            jsonStr = member.getimg();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject returnObject = new JSONObject();
        JSONArray array = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("", jsonStr);
        array.put(object);

        returnObject.put("img_address", array);
        return returnObject.toString();
    }
//        String[] jsonStr = new String[1];
//        try{
//            jsonStr = db.selectName();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        JSONObject returnObject = new JSONObject();
//        JSONArray array = new JSONArray();
//
//        JSONObject object = new JSONObject();
//        for(String tmp:jsonStr){
//            object = new JSONObject();
//            object.put("Name", tmp);
//            array.put(object);
//        }
//        returnObject.put("MemberName", array);
//
//        return returnObject.toString();
}
