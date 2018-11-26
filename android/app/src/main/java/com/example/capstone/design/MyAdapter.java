package com.example.capstone.design;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> { //커스텀 adapter임 listview랑 비슷하지만 차이가 조금 있음 복잡함으로 여기는 잘 건드리지 않는게 좋음

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_Picture;
        TextView item_Title;
        TextView item_Date;

        View view;

        MyViewHolder(View view){
            super(view);
            this.view = view;
            item_Picture = view.findViewById(R.id.item_picture);
            item_Title = view.findViewById(R.id.item_title);
            item_Date = view.findViewById(R.id.item_date);
        }

        protected View getView(){
            return view;
        }
    }

    private ArrayList<Write> itemInfoArrayList;
    MyAdapter(ArrayList<Write> itemInfoArrayList){
        this.itemInfoArrayList = itemInfoArrayList;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Uri uri = Uri.parse(itemInfoArrayList.get(position).getUrl());
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        Picasso.with(myViewHolder.view.getContext()).load(uri).into(myViewHolder.item_Picture);
        myViewHolder.item_Title.setText(itemInfoArrayList.get(position).getTitle());
        myViewHolder.item_Date.setText(itemInfoArrayList.get(position).getDate());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Trade_contents.class);
                intent.putExtra("CONTENT",itemInfoArrayList.get(position).getContent());
                intent.putExtra("DATE",itemInfoArrayList.get(position).getDate());
                intent.putExtra("TITLE",itemInfoArrayList.get(position).getTitle());
                intent.putExtra("UID",itemInfoArrayList.get(position).getUid());
                intent.putExtra("URL",itemInfoArrayList.get(position).getUrl());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return itemInfoArrayList.size();
    }
}
