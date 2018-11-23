package com.example.capstone.design;

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

public class CommentAdapter extends ArrayAdapter<CommentItem> {
    private Context context;
    private final int layoutResourceId;
    private List<CommentItem> commentItems;

    public CommentAdapter(Context context, int resource, List<CommentItem> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layoutResourceId = resource;
        this.commentItems = objects;
    }


    public static class DataHolder {
        TextView tvNameCom;
        TextView tvMessageCom;
        ImageView ivAvatarCom;
        TextView tvDateCom;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataHolder holder =null;
        if(convertView==null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            convertView = inflater.inflate(layoutResourceId, parent, false);

            holder = new DataHolder();
            holder.ivAvatarCom = (ImageView)convertView.findViewById(R.id.ivAvatarCom);
            holder.tvNameCom = (TextView)convertView.findViewById(R.id.tvNameCom);
            holder.tvMessageCom = (TextView)convertView.findViewById(R.id.tvMessageCom);
            holder.tvDateCom = (TextView)convertView.findViewById(R.id.tvDateCom);
            convertView.setTag(holder);
        } else {
            holder =(DataHolder)convertView.getTag();
        }

        CommentItem commentItem = commentItems.get(position);
        holder.ivAvatarCom.setImageResource(commentItem.getResAvatar());
        holder.tvNameCom.setText(commentItem.getStrName());
        holder.tvMessageCom.setText(commentItem.getStrMessage());
        holder.tvDateCom.setText(commentItem.getStrDate());
        return convertView;
    }

}
