package com.swapnotech.relaxation.meditation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.model.MWallpaperCat;
import com.swapnotech.relaxation.meditation.tools.MyLog;
import com.swapnotech.relaxation.meditation.tools.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dev on 11/27/2017.
 */

public abstract class AdRelax extends RecyclerView.Adapter<AdRelax.MyViewHolder> {
    private List<MWallpaperCat> recipes;
    private Context context;
    private LayoutInflater inflater;

    public AdRelax(Context context) {
        recipes = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addData(List<MWallpaperCat> recipes) {
        this.recipes = recipes;
        MyLog.e("adWall", " is " + recipes.size());
        notifyDataSetChanged();
    }

    @Override
    public AdRelax.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_category_new, parent, false);
        AdRelax.MyViewHolder holder = new AdRelax.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AdRelax.MyViewHolder holder, int position) {
        MWallpaperCat recipe = recipes.get(position);
        if (recipe != null) {
            holder.tvTitle.setText(recipe.getCategoryTitle());
            Picasso.with(context).load(recipe.getImage()).into(holder.img);
//            holder.img.setBackgroundResource(recipe.getImage());
//            Ion.with(holder.img).placeholder(recipe.getImage());
//            if (recipe.getCategoryThumb() != null || !recipe.getCategoryThumb().equals("") || !recipe.getCategoryThumb().equals("null"))
//                Ion.with(holder.img)
//                        .placeholder(R.drawable.placeholder_image)
//                        .error(R.drawable.placeholder_image)
//                        .load(recipe.getCategoryThumb());
//                Picasso.with(context).load(recipe.getCategoryThumb()).placeholder(R.drawable.placeholder_image).into(holder.img);

        }
//        else {
//            holder.img.setImageResource(R.drawable.placeholder_image);
//        }

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public abstract void onClickItem(int itemPositon, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            img = (ImageView) itemView.findViewById(R.id.img);

            Utils.setFont(tvTitle);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(getAdapterPosition(), v);
                }
            });
        }
    }
}
