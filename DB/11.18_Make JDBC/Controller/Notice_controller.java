package Controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Vector;

@Controller
public class Notice_controller {
    JSONObject object = new JSONObject();
    @RequestMapping(value = "/json/notice")
    public String index() {
        return object.toString();
    }

    //메인화면에서 전체 공지사항 가져오기
    @RequestMapping(value="/total_noti")
    public @ResponseBody String total_notice() {
        Database db = new Database();
        Notice notice = new Notice();
        Vector jsonStr = new Vector();
        int i=0;
        try {
                jsonStr.add(notice.total_notice());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject returnObject = new JSONObject();
        JSONArray array = new JSONArray();


        object.put("",jsonStr);
        array.put(object);

        returnObject.put("Notification", array);
        return returnObject.toString();
    }

    //메인화면에서 최근 공지사항 가져오기
    @RequestMapping(value="/recent_noti")
    public @ResponseBody String recent_Notice() {
        Database db = new Database();
        Notice notice = new Notice();
        Vector jsonStr = new Vector();
        int i=0;
        try {
            jsonStr.add(notice.recent_notice());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject returnObject = new JSONObject();
        JSONArray array = new JSONArray();


        object.put("",jsonStr);
        array.put(object);

        returnObject.put("Notification", array);
        return returnObject.toString();
    }
}