package android.example.mobdevjoostmulders.fragments;


import android.example.mobdevjoostmulders.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WorkoutPlanDetailFragment extends Fragment {
    private TextView mWorkoutPlanWorkoutName;
    private TextView mWorkoutPlanSets;
    private TextView mWorkoutPlanReps;
    private TextView mWorkoutPlanRest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_plan_detail,container,false);

        Bundle bundle = getArguments();

        mWorkoutPlanWorkoutName = view.findViewById(R.id.tv_workout_plan_detail_name);
        mWorkoutPlanSets = view.findViewById(R.id.tv_workout_plan_detail_sets);
        mWorkoutPlanReps = view.findViewById(R.id.tv_workout_plan_detail_reps);
        mWorkoutPlanRest = view.findViewById(R.id.tv_workout_plan_detail_rest);

        String workoutPlanWorkoutName = "";
        int workoutPlanSets = 1;
        int workoutPlanReps = 1;
        int workoutPlanRest = 1;

        if(bundle != null){
            workoutPlanWorkoutName = bundle.getString("workoutPlanWorkoutName");
            workoutPlanSets = bundle.getInt("workoutPlanWorkoutSets");
            workoutPlanReps = bundle.getInt("workoutPlanWorkoutReps");
            workoutPlanRest = bundle.getInt("workoutPlanWorkoutRest");
        }

        mWorkoutPlanWorkoutName.setText(workoutPlanWorkoutName);
        mWorkoutPlanSets.setText(String.valueOf(workoutPlanSets));
        mWorkoutPlanReps.setText(String.valueOf(workoutPlanReps));
        mWorkoutPlanRest.setText(String.valueOf(workoutPlanRest));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
