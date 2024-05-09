package com.example.musclegain2000.Routines;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclegain2000.MainActivity;
import com.example.musclegain2000.R;

import java.util.ArrayList;



public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.ViewHolder> {
    // Member variables.
    private static final String LOG_TAG = MainActivity.class.getName();
    private ArrayList<Routines> RoutinesitemData = new ArrayList<>();
    private  Context mContext;


    private OnItemClickListener mListener;

    private int lastPosition = -1;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    RoutinesAdapter(Context context, ArrayList<Routines> itemsData) {
        this.RoutinesitemData = itemsData;
        this.mContext = context;
        Log.i(LOG_TAG, "konstuctor: " + context +", "+itemsData);
    }

    @NonNull
    @Override
    public RoutinesAdapter.ViewHolder onCreateViewHolder(
             ViewGroup parent, int viewType) {


        View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.routines,parent,false);

        return new ViewHolder(v, (OnItemClickListener) mListener);

    }
    @Override
    public void onBindViewHolder(RoutinesAdapter.ViewHolder holder, int position) {
        // Get current sport.
        Routines currentItem = RoutinesitemData.get(position);


        holder.bindTo(currentItem);
        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return RoutinesitemData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        // Member Variables for the TextViews
        private TextView mTitleText;
        private ImageView mItemImage;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.itemTitle);
            mItemImage = itemView.findViewById(R.id.itemImage);
            itemView.findViewById(R.id.routines);

      itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (listener!=null) {
                  int position = getAdapterPosition();
                  if (position !=RecyclerView.NO_POSITION);{
                      listener.onItemClick(position);
                  }
              }
          }
      });



        }



        void bindTo(Routines currentItem) {
            mTitleText.setText(currentItem.getName());
            Log.i(LOG_TAG, String.valueOf(currentItem.getName()));
            Log.i(LOG_TAG, String.valueOf(mTitleText));
            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);
        }


    }


}