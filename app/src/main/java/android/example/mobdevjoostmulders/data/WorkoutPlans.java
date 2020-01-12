package android.example.mobdevjoostmulders.data;

import android.provider.BaseColumns;

public class WorkoutPlans {

    public static final class WorkoutPlansEntry implements BaseColumns {
        public static final String TABLE_NAME = "workoutPlans";
        public static final String COLUMN_WORKOUT_PLAN_NAME = "workoutPlanName";
        public static final String COLUMN_WORKOUT_NAME = "workoutName";
        public static final String COLUMN_AMOUNT_OF_SETS = "workoutSets";
        public static final String COLUMN_AMOUNT_OF_REPS = "workoutReps";
        public static final String COLuMN_AMOUNT_OF_REST = "workoutRest";
    }
}
