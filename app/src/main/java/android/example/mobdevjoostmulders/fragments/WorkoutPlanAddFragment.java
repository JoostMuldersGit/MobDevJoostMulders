package android.example.mobdevjoostmulders.fragments;


import android.database.sqlite.SQLiteDatabase;
import android.example.mobdevjoostmulders.activities.WorkoutPlanAddActivity;
import android.example.mobdevjoostmulders.data.DataBaseHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.example.mobdevjoostmulders.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WorkoutPlanAddFragment extends Fragment {
    private EditText mWorkoutPlanName;
    private EditText mWorkoutName;
    private EditText mWorkoutSets;
    private EditText mWorkoutReps;
    private EditText mWorkoutRest;
    private Button mSaveButton;
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_workout_plan_add,container,false);

        mWorkoutPlanName = (EditText) view.findViewById(R.id.et_workout_plan_name);
        mWorkoutName = (EditText) view.findViewById(R.id.et_workout_plan_workout_name);
        mWorkoutSets = (EditText) view.findViewById(R.id.et_workout_plan_workout_sets);
        mWorkoutReps = (EditText) view.findViewById(R.id.et_workout_plan_workout_reps);
        mWorkoutRest = (EditText) view.findViewById(R.id.et_workout_plan_workout_rest);
        mSaveButton = (Button) view.findViewById(R.id.bt_save_workout_plan);

        mDataBaseHelper = new DataBaseHelper(getActivity().getBaseContext());
        mDb = mDataBaseHelper.getWritableDatabase();


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String workoutPlanName = mWorkoutPlanName.getText().toString();
                String workoutName = mWorkoutName.getText().toString();
                int workoutSets = Integer.parseInt(mWorkoutSets.getText().toString());
                int workoutReps = Integer.parseInt(mWorkoutReps.getText().toString());
                int workoutRest = Integer.parseInt(mWorkoutRest.getText().toString());
                if(workoutName.length() != 0 && workoutSets != 0 && workoutReps != 0 && workoutRest != 0){
                    addData(workoutPlanName,workoutName,workoutSets,workoutReps,workoutRest);
                    mWorkoutPlanName.setText("");
                    mWorkoutName.setText("");
                    mWorkoutSets.setText("");
                    mWorkoutReps.setText("");
                    mWorkoutRest.setText("");
                }else{
                    Toast.makeText(getActivity().getBaseContext(), "You must put something in the text failed!", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    public void addData(String workoutPlanName,String workoutName, int sets, int reps, int rest){
        boolean insertData = mDataBaseHelper.addData(workoutPlanName,workoutName,sets,reps,rest);

        if(insertData){
            Toast.makeText(getActivity().getBaseContext(),"Data inserted succesful",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity().getBaseContext(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }

}
