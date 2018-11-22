package service;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;


@Service
public class AndroidPushNotificationsService {
    private static final String FIREBASE_SERVER_KEY ="AAAAn5AfB_U:APA91bFxf8kjSHimRR0memCb2N_AKij5ma59y7HTs9UQF4YhA-6fMkYz4EW5kqTBLIYLtxdPtghkgTmIktqduMLelF864uUIvrh7KeDD73Vq21tk-R1FgsiS1IasHp67wY4zgLC0uvpe";
    private static final String FIREBASE_API_URL ="https://fcm.googleapis.com/fcm/send";

    /**
     https://fcm.googleapis.com/fcm/send
     Content-Type:application/json
     Authorization:key=FIREBASE_SERVER_KEY*/

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        /*
         https://fcm.googleapis.com/fcm/send
         Content-Type:application/json
         Authorization:key=FIREBASE_SERVER_KEY
         */

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

        interceptors.add(new HeaderRequestInterceptor("Authorization", "key="+FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }

}
