package android.example.mobdevjoostmulders.workoutplan;

import android.example.mobdevjoostmulders.workout.Workout;

import java.util.ArrayList;

public class WorkoutPlan {
    private String workoutPlanName;
    private String workoutName;
    private int sets;
    private int reps;
    private int seconds;

    public WorkoutPlan(){

    }

    public WorkoutPlan(String workoutPlanName, String workoutName, int sets, int reps, int seconds) {
        this.workoutPlanName = workoutPlanName;
        this.workoutName = workoutName;
        this.sets = sets;
        this.reps = reps;
        this.seconds = seconds;
    }

    public String getWorkoutPlanName() {
        return workoutPlanName;
    }

    public void setWorkoutPlanName(String workoutPlanName) {
        this.workoutPlanName = workoutPlanName;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workout) {
        this.workoutName = workout;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
