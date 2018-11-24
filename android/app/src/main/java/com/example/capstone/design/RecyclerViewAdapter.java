package com.example.capstone.design;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {
    private Context context;
    private List<CommunityItem> communityItems;


    public RecyclerViewAdapter(Context context, List<CommunityItem> communityItems) {
        this.context = context;
        this.communityItems = communityItems;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.cardview_item, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.tv_DESCRIPTION.setText(communityItems.get(i).getStrDescription());
        viewHolder.tv_NAME.setText(communityItems.get(i).getStrName());
        viewHolder.img_PICTURE.setImageResource(communityItems.get(i).getResPicture());
        viewHolder.img_AVATAR.setImageResource(communityItems.get(i).getResAvatar());
        viewHolder.tv_DATE.setText(communityItems.get(i).getStrDate());
        final int id = i;
        viewHolder.btn_COMMENTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Comment.class);
                intent.putExtra("Avatar", communityItems.get(id).getResAvatar());
                intent.putExtra("Nick", communityItems.get(id).getStrName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return communityItems.size();
    }

    public  static class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_NAME;
        TextView tv_DESCRIPTION;
        ImageView img_PICTURE;
        ImageView img_AVATAR;
        Button btn_COMMENTS;
        TextView tv_DATE;

        public viewHolder(View itemView) {
            super(itemView);
            tv_DESCRIPTION = (TextView)itemView.findViewById(R.id.tvDescription);
            tv_NAME = (TextView)itemView.findViewById(R.id.tvName);
            img_AVATAR = (ImageView)itemView.findViewById(R.id.ivIcon);
            img_PICTURE = (ImageView)itemView.findViewById(R.id.ivPicture);
            btn_COMMENTS = (Button)itemView.findViewById(R.id.btnComments);
            tv_DATE = (TextView)itemView.findViewById(R.id.tvDateScript);
        }
    }

}
