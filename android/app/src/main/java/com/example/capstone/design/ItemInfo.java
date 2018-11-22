package com.example.capstone.design;

public class ItemInfo { //trade 할때 필요한 사진과 글 내용

    public int drawableId;
    public String text;
        public ItemInfo(int drawableId,String text){
            this.drawableId = drawableId;
            this.text = text;
        }
}

