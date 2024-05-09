package com.example.musclegain2000.workout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclegain2000.R;

import java.util.ArrayList;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements Filterable {
    // Member variables.
    private static final String LOG_TAG = HomeAdapter.class.getName();
    private static ArrayList<Workout> WorkoutitemData;
    private ArrayList<Workout> mWorkoutItemDataAll;

    private Context mContext;
    private int lastPosition = -1;
    private OnItemClickListener mListener;



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public HomeAdapter(Context context, ArrayList<Workout> itemsData) {
        this.WorkoutitemData = itemsData;
        this.mWorkoutItemDataAll= itemsData;
        this.mContext = context;
        Log.i(LOG_TAG, "konstuctor: " + context +", "+itemsData);
    }


    public static ArrayList<Workout> getWorkoutitemData() {
        return WorkoutitemData;
    }

    @Override
    public ViewHolder onCreateViewHolder(
             ViewGroup parent, int viewType) {
        Log.i(LOG_TAG, "onCreateViewHolder, " + mContext);

        View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card,parent,false);

        return new ViewHolder(v, (OnItemClickListener) mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get current sport.
        Workout currentItem = WorkoutitemData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentItem);


        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return WorkoutitemData.size();
    }


    /**
     * RecycleView filter
     * **/

 @Override
    public Filter getFilter() {
        return WorkoutFilter;
    }

    private Filter WorkoutFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Workout> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = mWorkoutItemDataAll.size();
                results.values = mWorkoutItemDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Workout item : mWorkoutItemDataAll) {
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            WorkoutitemData = (ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Member Variables for the TextViews
        private TextView mTitleText;
        private ImageView mItemImage;
        private TextView type;

        ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
//TODO............
            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.workoutcardTitle);
            mItemImage = itemView.findViewById(R.id.workoutcardimage);
            itemView.findViewById(R.id.workout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) ;
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }


        void bindTo(Workout currentItem){
            mTitleText.setText(currentItem.getName());
            Log.i(LOG_TAG, String.valueOf(currentItem.getName()));
            Log.i(LOG_TAG, String.valueOf(mTitleText));

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(currentItem.getImage()).into(mItemImage);
            //TODO.......
        }
    }
}