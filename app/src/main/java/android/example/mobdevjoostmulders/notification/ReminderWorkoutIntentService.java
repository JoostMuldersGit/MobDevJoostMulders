package android.example.mobdevjoostmulders.notification;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class ReminderWorkoutIntentService extends IntentService {

    public ReminderWorkoutIntentService() {
        super("ReminderWorkoutService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        ReminderWorkout.executeTask(this,action);
    }
}
