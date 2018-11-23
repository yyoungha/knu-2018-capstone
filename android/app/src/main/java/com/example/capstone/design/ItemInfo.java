package com.example.capstone.design;

public class ItemInfo { //trade 할때 필요한 사진과 글 내용

    public int drawableId;
    public String txt1,date;
        public ItemInfo(int drawableId,String txt1,String txt2){
            this.drawableId = drawableId;
            this.txt1 = txt1;
            this.date = txt2;
        }
}

