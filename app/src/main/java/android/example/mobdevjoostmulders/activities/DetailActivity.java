package android.example.mobdevjoostmulders.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.mobdevjoostmulders.R;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private TextView mWorkoutNameDetail;
    private TextView mWorkoutCategoryDetail;
    private TextView mWorkoutMuscleGroupDetail;
    private TextView mWorkoutDescriptionDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mWorkoutNameDetail = findViewById(R.id.tv_workout_name_detail);
        mWorkoutCategoryDetail = findViewById(R.id.tv_workout_category_detail);
        mWorkoutMuscleGroupDetail = findViewById(R.id.tv_workout_muscleGroup_detail);
        mWorkoutDescriptionDetail = findViewById(R.id.tv_workout_description_detail);


        Intent intentThatStartedTheActivity = getIntent();

        if(intentThatStartedTheActivity != null){
            if(intentThatStartedTheActivity.hasExtra("name")){
                String name = intentThatStartedTheActivity.getStringExtra("name");
                String category = intentThatStartedTheActivity.getStringExtra("category");
                String muscleGroup = intentThatStartedTheActivity.getStringExtra("muscleGroup");
                String description = intentThatStartedTheActivity.getStringExtra("description");

                mWorkoutNameDetail.setText(name);
                mWorkoutCategoryDetail.setText(category);
                mWorkoutMuscleGroupDetail.setText(muscleGroup);
                mWorkoutDescriptionDetail.setText(description);
            }
        }


    }


}
