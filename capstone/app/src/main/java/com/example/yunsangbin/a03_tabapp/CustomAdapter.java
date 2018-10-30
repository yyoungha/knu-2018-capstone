package com.example.yunsangbin.a03_tabapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<DataItem> {
    Context context;
    int layoutResourceId;
    List<DataItem> data=null;


    public CustomAdapter(Context context, int resource, List<DataItem> objects) {
        super(context, resource, objects);

        this.layoutResourceId = resource;
        this.context =context;
        this.data=objects;
    }


    static class DataHolder {
        ImageView ivPhoto;
        TextView tvDescription;
        TextView tvName;
        TextView tvKakaoId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataHolder holder =null;
        if(convertView==null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            convertView = inflater.inflate(layoutResourceId, parent, false);

            holder = new DataHolder();
            holder.ivPhoto = (ImageView)convertView.findViewById(R.id.ivIcon);
            holder.tvDescription = (TextView)convertView.findViewById(R.id.tvDescription);
            holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
            //holder.tvKakaoId = (TextView)convertView.findViewById(R.id.tvKakaoDetail);

            convertView.setTag(holder);
        } else {
            holder =(DataHolder)convertView.getTag();
        }

        DataItem dataItem =data.get(position);
        holder.tvDescription.setText(dataItem.description);
        holder.tvName.setText(dataItem.name);
        holder.ivPhoto.setImageResource(dataItem.resIdThumbnail);
        holder.tvKakaoId.setText(dataItem.kakaoId);

        return convertView;
    }

}
