package android.example.mobdevjoostmulders.workoutplan;

import android.content.Context;
import android.database.Cursor;
import android.example.mobdevjoostmulders.R;
import android.example.mobdevjoostmulders.data.WorkoutPlans;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutPlanAdapter extends RecyclerView.Adapter<WorkoutPlanAdapter.WorkoutPlanViewHolder>{
    private ArrayList<WorkoutPlan> mWorkoutPlanArrayList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;


    public WorkoutPlanAdapter(ArrayList<WorkoutPlan> workoutPlanArrayList, Context context, OnItemClickListener onItemClickListener){
        mWorkoutPlanArrayList = workoutPlanArrayList;
        mContext = context;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public WorkoutPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_plan_list_item,parent,false);
        WorkoutPlanViewHolder workoutPlanViewHolder = new WorkoutPlanViewHolder(view,mOnItemClickListener);
        return workoutPlanViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutPlanViewHolder holder, int position) {
        WorkoutPlan currentWorkoutPlan = mWorkoutPlanArrayList.get(position);
        holder.mTextViewWorkoutPlanName.setText(currentWorkoutPlan.getWorkoutPlanName());
    }

    @Override
    public int getItemCount() {
        return mWorkoutPlanArrayList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public static class WorkoutPlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTextViewWorkoutPlanName;
        private OnItemClickListener mOnItemClickListener;

        public WorkoutPlanViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mTextViewWorkoutPlanName = itemView.findViewById(R.id.tv_workout_plan_name);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
           mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }


}
