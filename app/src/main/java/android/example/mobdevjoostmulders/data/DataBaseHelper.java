package android.example.mobdevjoostmulders.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.example.mobdevjoostmulders.workoutplan.WorkoutPlan;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = WorkoutPlans.WorkoutPlansEntry.TABLE_NAME;
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WORKOUT_PLANS_TABLE = "CREATE TABLE " + WorkoutPlans.WorkoutPlansEntry.TABLE_NAME + " (" +
            WorkoutPlans.WorkoutPlansEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 0," +
            WorkoutPlans.WorkoutPlansEntry.COLUMN_WORKOUT_PLAN_NAME + " TEXT NOT NULL, " +
            WorkoutPlans.WorkoutPlansEntry.COLUMN_WORKOUT_NAME + " TEXT NOT NULL, " +
            WorkoutPlans.WorkoutPlansEntry.COLUMN_AMOUNT_OF_SETS + " INTEGER NOT NULL, " +
            WorkoutPlans.WorkoutPlansEntry.COLUMN_AMOUNT_OF_REPS + " INTEGER NOT NULL, " +
            WorkoutPlans.WorkoutPlansEntry.COLuMN_AMOUNT_OF_REST + " INTEGER NOT NULL "
            +");";

        sqLiteDatabase.execSQL(SQL_CREATE_WORKOUT_PLANS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WorkoutPlans.WorkoutPlansEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String workoutPlanName, String workoutName, int sets, int reps, int rest){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WorkoutPlans.WorkoutPlansEntry.COLUMN_WORKOUT_PLAN_NAME,workoutPlanName);
        contentValues.put(WorkoutPlans.WorkoutPlansEntry.COLUMN_WORKOUT_NAME, workoutName);
        contentValues.put(WorkoutPlans.WorkoutPlansEntry.COLUMN_AMOUNT_OF_SETS, sets);
        contentValues.put(WorkoutPlans.WorkoutPlansEntry.COLUMN_AMOUNT_OF_REPS, reps);
        contentValues.put(WorkoutPlans.WorkoutPlansEntry.COLuMN_AMOUNT_OF_REST, rest);

        long result = database.insert(DATABASE_NAME,null,contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DATABASE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public boolean removeWorkoutPlan(String workoutPlanName){
        SQLiteDatabase mDb = getWritableDatabase();
        return mDb.delete(DATABASE_NAME, WorkoutPlans.WorkoutPlansEntry.COLUMN_WORKOUT_PLAN_NAME + " = '" + workoutPlanName + "'", null) > 0;
    }

}
