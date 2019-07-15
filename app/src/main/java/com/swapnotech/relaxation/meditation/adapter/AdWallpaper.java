package com.swapnotech.relaxation.meditation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.activity.DeepZoomSample;
import com.swapnotech.relaxation.meditation.activity.MainActivity;
import com.swapnotech.relaxation.meditation.activity.ProgressBarDownload;
import com.swapnotech.relaxation.meditation.activity.SingleImageActivity;
import com.swapnotech.relaxation.meditation.model.MWallpaper;
import com.swapnotech.relaxation.meditation.model.Mdownload;
import com.swapnotech.relaxation.meditation.tools.Global;
import com.swapnotech.relaxation.meditation.tools.MyApp;
import com.swapnotech.relaxation.meditation.tools.MyLog;
import com.swapnotech.relaxation.meditation.tools.Utils;

import java.util.ArrayList;

/**
 * Created by Dev on 11/22/2017.
 */

public abstract class AdWallpaper extends RecyclerView.Adapter<AdWallpaper.MyViewHolder> {
    private ArrayList<MWallpaper> flashArrayList;
    private Context context;
    private LayoutInflater inflater;
    ArrayList<Mdownload> mdownloadArrayList;
    private MWallpaper mWallpaper;

    public AdWallpaper(Context context) {
        flashArrayList = new ArrayList<>();
        mdownloadArrayList = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MWallpaper> recipes) {
//        mdownloadArrayList = DBManager.getInstance().getData(DBManager.TABLE_DOWNLOAD, new Mdownload());
//        MyLog.e("downloadList", " size " + mdownloadArrayList.size());
        this.flashArrayList = recipes;
        notifyDataSetChanged();
    }

    public void setDownloadedData(ArrayList<Mdownload> mdownloadArrayList) {
        this.mdownloadArrayList = mdownloadArrayList;
    }

    @Override
    public AdWallpaper.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_wallpaper, parent, false);
        AdWallpaper.MyViewHolder holder = new AdWallpaper.MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(AdWallpaper.MyViewHolder holder, int position) {
//
        mWallpaper = flashArrayList.get(position);
        holder.tvShare.setText(mWallpaper.getShare());
        holder.tvViews.setText(mWallpaper.getView());
        holder.imgThumb.setBackgroundResource(mWallpaper.getImage());

//        holder.tvShrtDes.setText(recipe.getS());
//        Utils.setFont("AvenirNext-Regular", holder.tvTitle);
//        Utils.setFont("AvenirNextCondensed-Medium", holder.tvPastPrice, holder.tvDiscount, holder.tvPrice);

//        if (mWallpaper.getThumbnail() != null && !mWallpaper.getThumbnail().equals("") && !mWallpaper.getThumbnail().equals("null")) {
//            Ion.with(holder.imgThumb)
//                    .placeholder(R.drawable.placeholder_image)
//                    .fitXY()
//                    .error(R.drawable.placeholder_image)
//                    .load(mWallpaper.getThumbnail());
//
//
////            Ion.with(context)
////                    .load(mWallpaper.getThumbnail())
////                   .asCachedBitmap()
////                    .intoImageView(holder.imgThumb);
//
////            Picasso.with(context).load(mWallpaper.getThumbnail()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.placeholder_image).into(holder.imgThumb);
//        } else {
//            holder.imgThumb.setImageResource(R.drawable.placeholder_image);
//        }

    }

    @Override
    public int getItemCount() {
        return flashArrayList.size();
    }

    public abstract void onClickItem(int position, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvViews, tvShare;
        public ImageView imgThumb;

        //        int scaledSize = context.getResources().getDimensionPixelSize(R.dimen.sp14);
        public MyViewHolder(View itemView) {
            super(itemView);

            tvViews = (TextView) itemView.findViewById(R.id.tvViews);
            tvShare = (TextView) itemView.findViewById(R.id.tvShare);
            imgThumb = (ImageView) itemView.findViewById(R.id.imgWall);
            Utils.setFont(tvViews, tvShare);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onClickItem(getAdapterPosition(), v);

//                    Global.orderLayoutDissmiss = 0;
//
//                    MWallpaper recipe = flashArrayList.get(getAdapterPosition());
//                    SingleImageActivity.mWallpaper = recipe;
//                    ProgressBarDownload.mWallpaper = recipe;
//                    DeepZoomSample.mWallpaper = recipe;
//                    Global.singleWallpaperURL = recipe.getOriginal();
//                    MainActivity.showFrag = 1;
//                    MainActivity.selectTabPos = 1;
//                    MainActivity.getInstance().isNotification = true;
////                    MyApp.getInstance().setupAnalyticsEvent("Wallpaper", mWallpaper.getCategory_title());
//
//
//                     //trying going to deepZoomActivity from here when click image which is already downloaded but not work until yet
//                    for (int i = 0; i < mdownloadArrayList.size(); i++) {
//                        if (mWallpaper.getThumbnail().equals(mdownloadArrayList.get(i).getDownloadFile())) {
//                            MyLog.e("downloadList", " already downloaded ");
//                            Toast.makeText(context, "already downloaded", Toast.LENGTH_SHORT).show();
//                            context.startActivity(new Intent(context, DeepZoomSample.class));
//
//                            return;
//                        } else {
//                            Toast.makeText(context, "start Download", Toast.LENGTH_SHORT).show();
//                            context.startActivity(new Intent(context, ProgressBarDownload.class));
//
//                        }
//                    }
//                    context.startActivity(new Intent(context, ProgressBarDownload.class));

                }
            });

        }


    }

}
