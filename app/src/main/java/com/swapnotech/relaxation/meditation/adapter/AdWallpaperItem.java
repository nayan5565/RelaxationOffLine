package com.swapnotech.relaxation.meditation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.model.MItems;
import com.swapnotech.relaxation.meditation.tools.MyLog;
import com.swapnotech.relaxation.meditation.tools.Utils;

import java.util.ArrayList;

/**
 * Created by Dev on 11/27/2017.
 */

public abstract class AdWallpaperItem extends RecyclerView.Adapter<AdWallpaperItem.MyViewHolder> {
    private ArrayList<MItems> recipes;
    private Context context;
    private LayoutInflater inflater;
    MItems recipe;

    public AdWallpaperItem(Context context) {
        recipes = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MItems> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public AdWallpaperItem.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_wallpaper_item, parent, false);
        AdWallpaperItem.MyViewHolder holder = new AdWallpaperItem.MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(AdWallpaperItem.MyViewHolder holder, int position) {
        recipe = recipes.get(position);
        holder.tvTitle.setText(recipe.getTitle());
        holder.tvMail.setText(recipe.getWallpaper().size()+"");
        holder.imgThumb.setBackgroundResource(recipe.getImage());

////        Picasso.with(context).load(Global.API_BASE_BACKEND + recipe.getThumb()).placeholder(R.drawable.placeholder).into(holder.imgThumb);
//        if (recipe.getThumb() != null && !recipe.getThumb().equals("") && !recipe.getThumb().equals("null")) {
//            MyLog.e("AdItem______", " image " + recipe.getPhoto());
//            Ion.with(holder.imgThumb)
//                    .placeholder(R.drawable.placeholder_image)
//                    .error(R.drawable.placeholder_image)
//                    .load(recipe.getThumb());
////            Picasso.with(context).load(recipe.getPhoto()).placeholder(R.drawable.placeholder_image).into(holder.imgThumb);
//        } else {
//            holder.imgThumb.setImageResource(R.drawable.placeholder_image);
//        }

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public abstract void onClickItem(int position, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvMail;
        public ImageView imgThumb, imgMail;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvMail = (TextView) itemView.findViewById(R.id.tvMail);
            imgThumb = (ImageView) itemView.findViewById(R.id.img);
            imgMail = (ImageView) itemView.findViewById(R.id.imgMail);


            Utils.setFont(tvTitle,tvMail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(getAdapterPosition(), v);
                }
            });

//            if (Utils.getUser().getType() != Global.USER_ADMIN)
//                imgCart.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onClickItem(getAdapterPosition(), v);
//                    }
//                });
        }


    }

}
