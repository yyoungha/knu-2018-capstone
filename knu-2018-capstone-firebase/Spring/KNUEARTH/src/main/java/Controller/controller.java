package Controller;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class controller {
    @RequestMapping(value="/")
    public String index(){
        return "index";
    }

    //JSON CALL
    @RequestMapping(value="/json")
    public @ResponseBody String doc(){
        JSONObject returnObject = new JSONObject();
        JSONArray array = new JSONArray();

        JSONObject object = new JSONObject();

        object.put("Key0", "Value0");
        array.put(object);

        object = new JSONObject();
        object.put("Key1", "Value1");
        array.put(object);

        returnObject.put("Array", array);

        return returnObject.toString();
    }
}
