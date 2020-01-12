package android.example.mobdevjoostmulders.notification;

import android.content.Context;

import androidx.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;

public class ReminderUtils {
    private static final int REMINDER_INTERVAL_MINUTES = 1444;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;
    private static final String REMINDER_JOB_TAG = "workout_reminder_tag";

    private static boolean sInitialized;

    synchronized public static void scheduleReminder(@NonNull final Context context){
        if(sInitialized)return;
        GooglePlayDriver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job reminderJob = dispatcher.newJobBuilder()
            .setService(WorkoutReminderFirebaseJobService.class)
            .setTag(REMINDER_JOB_TAG)
            .setLifetime(Lifetime.FOREVER)
            .setRecurring(true)
            .setTrigger(com.firebase.jobdispatcher.Trigger.executionWindow(
                REMINDER_INTERVAL_SECONDS,
                REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS
            )).setReplaceCurrent(true)
            .build();
        dispatcher.schedule(reminderJob);
        sInitialized = true;
    }
}
