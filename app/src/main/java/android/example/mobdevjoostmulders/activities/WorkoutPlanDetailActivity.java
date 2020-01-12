package android.example.mobdevjoostmulders.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.mobdevjoostmulders.R;
import android.os.Bundle;
import android.widget.TextView;

public class WorkoutPlanDetailActivity extends AppCompatActivity {

    private TextView mWorkoutPlanWorkoutName;
    private TextView mWorkoutPlanSets;
    private TextView mWorkoutPlanReps;
    private TextView mWorkoutPlanRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_plan_detail);

        mWorkoutPlanWorkoutName = findViewById(R.id.tv_workout_plan_detail_name);
        mWorkoutPlanSets = findViewById(R.id.tv_workout_plan_detail_sets);
        mWorkoutPlanReps = findViewById(R.id.tv_workout_plan_detail_reps);
        mWorkoutPlanRest = findViewById(R.id.tv_workout_plan_detail_rest);

        Intent intent = getIntent();

        if(intent != null){
            if(intent.hasExtra("workoutPlanWorkoutName")){
                String workoutPlanWorkoutName = intent.getStringExtra("workoutPlanWorkoutName");
                int workoutPlanWorkoutSets = intent.getIntExtra("workoutPlanWorkoutSets", 1);
                int workoutPlanWorkoutReps = intent.getIntExtra("workoutPlanWorkoutReps", 1);
                int workoutPlanWorkoutRest = intent.getIntExtra("workoutPlanWorkoutRest", 1);

                mWorkoutPlanWorkoutName.setText(workoutPlanWorkoutName);
                mWorkoutPlanSets.setText(String.valueOf(workoutPlanWorkoutSets));
                mWorkoutPlanReps.setText(String.valueOf(workoutPlanWorkoutReps));
                mWorkoutPlanRest.setText(String.valueOf(workoutPlanWorkoutRest));

            }
        }
    }
}
