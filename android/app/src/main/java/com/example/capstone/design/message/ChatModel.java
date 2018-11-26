package com.example.capstone.design.message;

import java.util.HashMap;
import java.util.Map;

public class ChatModel {
    public Map<String,Boolean> users = new HashMap<>(); //채팅방 유저들
    public Map<String,Comment> comments = new HashMap<>(); //채팅방의 내용

    public static class Comment {
        public String uid;
        public String message;
        public Object timestamp;
        public Map<String,Object> readUsers = new HashMap<>();
    }
}
