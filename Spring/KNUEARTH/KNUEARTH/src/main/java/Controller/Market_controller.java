package Controller;


import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Market_controller {

    //bathroom 게시글 출력하기
    @RequestMapping(value="/total_bath")
    public @ResponseBody
    String total_bath() {
        Database db = new Database();
        Market market = new Market();
        JSONObject object = new JSONObject();
        int i=0;
        try {
            object.put("Posting",market.total_bathroom());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    //daily 게시글 출력하기
    @RequestMapping(value="/total_daily")
    public @ResponseBody
    String total_daily() {
        Database db = new Database();
        Market market = new Market();
        JSONObject object = new JSONObject();
        int i=0;
        try {
            object.put("Posting",market.total_daily());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    //Electronic 게시글 출력하기
    @RequestMapping(value="/total_electronic")
    public @ResponseBody
    String total_electronic() {
        Database db = new Database();
        Market market = new Market();
        JSONObject object = new JSONObject();

        try {
            object.put("Posting",market.total_electronic());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    //Interior 게시글 출력하기
    @RequestMapping(value="/total_interior")
    public @ResponseBody
    String total_Interior() {
        Database db = new Database();
        Market market = new Market();
        JSONObject object = new JSONObject();
        int i=0;
        try {
            object.put("Posting",market.total_interior());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    //kitchen 게시글 출력하기
    @RequestMapping(value="/total_kitchen")
    public @ResponseBody
    String total_kitchen() {
        Database db = new Database();
        Market market = new Market();
        JSONObject object = new JSONObject();
        int i=0;
        try {
            object.put("Posting",market.total_kitchen());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    //office 게시글 출력하기
    @RequestMapping(value="/total_office")
    public @ResponseBody
    String total_office() {
        Database db = new Database();
        Market market = new Market();
        JSONObject object = new JSONObject();
        int i=0;
        try {
            object.put("Posting",market.total_office());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }
}
