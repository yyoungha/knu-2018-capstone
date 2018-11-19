package Controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class Board_controller {

    //전체 게시글 출력하기
    @RequestMapping(value="/total_board")
    public @ResponseBody
    String total_board() {
        Database db = new Database();
        Board board = new Board();
        JSONObject object = new JSONObject();
        int i=0;
        try {
            object.put("Posting",board.total_board());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object.toString();
    }
}