package android.example.mobdevjoostmulders.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.example.mobdevjoostmulders.R;
import android.example.mobdevjoostmulders.data.DataBaseHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class WorkoutPlanAddActivity extends AppCompatActivity {

    private EditText mWorkoutPlanName;
    private EditText mWorkoutName;
    private EditText mWorkoutSets;
    private EditText mWorkoutReps;
    private EditText mWorkoutRest;
    private Button mSaveButton;
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_plan_add);

        mWorkoutPlanName = (EditText) findViewById(R.id.et_workout_plan_name);
        mWorkoutName = (EditText) findViewById(R.id.et_workout_plan_workout_name);
        mWorkoutSets = (EditText) findViewById(R.id.et_workout_plan_workout_sets);
        mWorkoutReps = (EditText) findViewById(R.id.et_workout_plan_workout_reps);
        mWorkoutRest = (EditText) findViewById(R.id.et_workout_plan_workout_rest);
        mSaveButton = (Button) findViewById(R.id.bt_save_workout_plan);

        mDataBaseHelper = new DataBaseHelper(this);
        mDb = mDataBaseHelper.getWritableDatabase();


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(WorkoutPlanAddActivity.this,WorkoutPlanActivity.class);
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
                    Toast.makeText(WorkoutPlanAddActivity.this, "You must put something in the text failed!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    public void addData(String workoutPlanName,String workoutName, int sets, int reps, int rest){
        boolean insertData = mDataBaseHelper.addData(workoutPlanName,workoutName,sets,reps,rest);

        if(insertData){
            Toast.makeText(WorkoutPlanAddActivity.this,"Data inserted succesful",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(WorkoutPlanAddActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }
}
