package android.example.mobdevjoostmulders.workout;

import android.content.Context;
import android.example.mobdevjoostmulders.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private ArrayList<Workout> mWorkoutArrayList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{ void onItemClick(int position);}

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){ mOnItemClickListener = onItemClickListener; }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTextViewName;
        public TextView mTextViewCategory;
        public ImageView mImageViewWorkout;
        public OnItemClickListener mOnItemClickListener;

        public WorkoutViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mImageViewWorkout = itemView.findViewById(R.id.workout_image);
            mTextViewName = itemView.findViewById(R.id.tv_workout_name);
            mTextViewCategory = itemView.findViewById(R.id.tv_workout_category);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }
    public WorkoutAdapter(ArrayList<Workout> workoutArrayList,Context context, OnItemClickListener onItemClickListener){
        mWorkoutArrayList = workoutArrayList;
        mContext = context;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_list_item,parent,false);
        WorkoutViewHolder workoutViewHolder = new WorkoutViewHolder(view,mOnItemClickListener);
        return workoutViewHolder;
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        Workout currentWorkout = mWorkoutArrayList.get(position);
        holder.mTextViewName.setText(currentWorkout.getName());
        holder.mTextViewCategory.setText(currentWorkout.getCategory());
        holder.mImageViewWorkout.setImageBitmap(currentWorkout.getImageBitmap());
    }

    @Override
    public int getItemCount() {
        return mWorkoutArrayList.size();
    }
}
