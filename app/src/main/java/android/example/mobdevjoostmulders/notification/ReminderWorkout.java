package android.example.mobdevjoostmulders.notification;

import android.app.Notification;
import android.content.Context;

public class ReminderWorkout {
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static final String ACTION_REMINDER = "workout-reminder";

    public static void executeTask(Context context, String action){
        if(ACTION_DISMISS_NOTIFICATION.equals(action)){
            NotificationUtils.clearAllNotifications(context);
        }else if(ACTION_REMINDER.equals(action)){
            notifyReminder(context);
        }
    }

    private static void notifyReminder(Context context) {
        NotificationUtils.remindUserOfWorkout(context);
    }
}
