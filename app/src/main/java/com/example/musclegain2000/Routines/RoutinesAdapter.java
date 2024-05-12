package com.example.musclegain2000.Routines;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.musclegain2000.MainActivity;
import com.example.musclegain2000.R;
import com.example.musclegain2000.workout.Workout;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;



public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.ViewHolder> {
    // Member variables.
    private static final String LOG_TAG = MainActivity.class.getName();
    private ArrayList<Routines> routinesitemData = new ArrayList<>();
    private  Context mContext;

    Animation scaleup;
    Animation scaledown;
    private OnItemClickListener mListener;

    private int lastPosition = -1;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    RoutinesAdapter(Context context, ArrayList<Routines> itemsData) {
        this.routinesitemData = itemsData;
        this.mContext = context;



        Log.i(LOG_TAG, "konstuctor: " + context +", "+itemsData);
    }

    @NonNull
    @Override
    public RoutinesAdapter.ViewHolder onCreateViewHolder(
             ViewGroup parent, int viewType) {
        scaledown = AnimationUtils.loadAnimation(mContext,R.anim.itemclick2);
        scaleup = AnimationUtils.loadAnimation(mContext,R.anim.itemclick2);


        View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.routines,parent,false);

        return new ViewHolder(v, (OnItemClickListener) mListener);

    }
    @Override
    public void onBindViewHolder(RoutinesAdapter.ViewHolder holder, int position) {
        // Get current sport.
        Routines currentItem = routinesitemData.get(position);


        holder.bindTo(currentItem);
        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }
    @Override
    public int getItemCount() {
        return routinesitemData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        // Member Variables for the TextViews
        private TextView mTitleText;
        private ShapeableImageView mItemImage;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.itemTitle);
            mItemImage = itemView.findViewById(R.id.itemImage);
            itemView.findViewById(R.id.routines);

      itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              v.startAnimation(scaledown);
              v.startAnimation(scaleup);

              itemView.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      if (listener != null) {
                          int position = getAdapterPosition();
                          if (position != RecyclerView.NO_POSITION) ;
                          {
                              listener.onItemClick(position);
                          }
                      }// Code to be executed after the delay
                  }
              }, 300);
          }
      });






        }
// Load the image into a regular ImageView using Glide

        void bindTo(Routines currentItem){




            mTitleText.setText(currentItem.getName());
            Log.i(LOG_TAG, String.valueOf(currentItem.getName()));
            Log.i(LOG_TAG, String.valueOf(mTitleText));

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext)
                    .load(currentItem.getImageResource())
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            // Get the bitmap from the Drawable
                            Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();

                            // Set the bitmap to the ShapeableImageView
                            mItemImage.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            // Placeholder cleanup if needed
                        }
                    });
        }
    }
}