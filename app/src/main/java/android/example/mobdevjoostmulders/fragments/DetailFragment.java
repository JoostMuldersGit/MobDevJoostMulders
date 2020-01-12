package android.example.mobdevjoostmulders.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.example.mobdevjoostmulders.R;
import android.widget.TextView;

public class DetailFragment extends Fragment {
    private TextView mWorkoutNameDetail;
    private TextView mWorkoutCategoryDetail;
    private TextView mWorkoutMuscleGroupDetail;
    private TextView mWorkoutDescriptionDetail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,container,false);
        Bundle bundle = getArguments();

        mWorkoutNameDetail = view.findViewById(R.id.tv_workout_name_detail);
        mWorkoutCategoryDetail = view.findViewById(R.id.tv_workout_category_detail);
        mWorkoutMuscleGroupDetail = view.findViewById(R.id.tv_workout_muscleGroup_detail);
        mWorkoutDescriptionDetail = view.findViewById(R.id.tv_workout_description_detail);

        String name = "";
        String categrory = "";
        String muscleGroup = "";
        String description = "";

        if(bundle != null){
            name = bundle.getString("name");
            categrory = bundle.getString("category");
            muscleGroup = bundle.getString("muscleGroup");
            description = bundle.getString("description");
        }

        mWorkoutNameDetail.setText(name);
        mWorkoutCategoryDetail.setText(categrory);
        mWorkoutMuscleGroupDetail.setText(muscleGroup);
        mWorkoutDescriptionDetail.setText(description);

        return view;
    }

}
