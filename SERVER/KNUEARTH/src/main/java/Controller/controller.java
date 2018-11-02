package Controller;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class controller {
    @RequestMapping(value="/")
    public String test(){
        JSONObject returnObject = new JSONObject();
        returnObject.put("Key","Value");
        return "index";
    }
}
