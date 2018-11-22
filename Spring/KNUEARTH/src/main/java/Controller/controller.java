package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.AndroidPushNotificationsService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class controller {
    @RequestMapping(value="/")
    public String index(){
        return "index";
    }


    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    @RequestMapping(value = "/sendmessage", method = RequestMethod.POST, produces = {"application/json"})
    public @ResponseBody ResponseEntity<String> send(@RequestBody Map<String, Object> paramInfo) throws JSONException{
        Map<String, Object> retVal = new HashMap<String, Object>();

        //System.out.println("token: " + paramInfo.get("token"));
        //System.out.println("message: " + paramInfo.get("message"));

        //FCM 메시지 전송//
        JSONObject body = new JSONObject();

        //DB에 저장된 여러개의 토큰(수신자)을 가져와서 설정할 수 있다.//
        List<String> tokenlist = new ArrayList<String>();
        //DB과정 생략(직접 대입)//
        tokenlist.add("token value 1");
        tokenlist.add("token value 2");
        tokenlist.add("token value 3");

        JSONArray array = new JSONArray();

        for(int i=0; i<tokenlist.size(); i++) {
            array.put(tokenlist.get(i));
        }

        body.put("registration_ids", array); //여러개의 메시지일 경우 registration_ids, 단일 메세지는 to사용//

        JSONObject notification = new JSONObject();
        notification.put("title", "FCM Test App");
        notification.put("body", paramInfo.get("message"));

        body.put("notification", notification);

        System.out.println(body.toString());

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();

            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
