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
        viewHolder.tvDescription.setText(communityItems.get(i).getStrDescription());
        viewHolder.tvName.setText(communityItems.get(i).getStrName());
        viewHolder.ivPicture.setImageResource(communityItems.get(i).getResPicture());
        viewHolder.ivAvatar.setImageResource(communityItems.get(i).getResAvatar());
        final int id = i;
        viewHolder.btnComments.setOnClickListener(new View.OnClickListener() {
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
        TextView tvName;
        TextView tvDescription;
        ImageView ivPicture;
        ImageView ivAvatar;
        Button btnComments;

        public viewHolder(View itemView) {
            super(itemView);
            tvDescription = (TextView)itemView.findViewById(R.id.tvDescription);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            ivAvatar = (ImageView)itemView.findViewById(R.id.ivIcon);
            ivPicture = (ImageView)itemView.findViewById(R.id.ivPicture);
            btnComments = (Button)itemView.findViewById(R.id.btnComments);
        }
    }

}
