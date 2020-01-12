package android.example.mobdevjoostmulders.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.example.mobdevjoostmulders.R;
import android.example.mobdevjoostmulders.activities.MainActivity;
import android.example.mobdevjoostmulders.activities.WorkoutPlanActivity;
import android.example.mobdevjoostmulders.fragments.WorkoutPlanFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class NotificationUtils {

    private static final int WORKOUT_REMINDER_PENDING_INTENT_ID = 33;
    private static final int WORKOUT_REMINDER_NOTIFICATION_ID = 22;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 44;
    private static final String CHANNEL_ID = "channel";
    public static void remindUserOfWorkout(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID
                ,context.getString(R.string.notification_channel_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,CHANNEL_ID)
            .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
            .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
            .setLargeIcon(largeIcon(context))
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_content))
            .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notification_reminder_workout_body)))
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setContentIntent(contentIntent(context))
            .addAction(ignoreReminderAction(context))
            .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(WORKOUT_REMINDER_NOTIFICATION_ID,notificationBuilder.build());

    }

    private static NotificationCompat.Action ignoreReminderAction(Context context){
        Intent ignoreReminderIntent = new Intent(context, ReminderWorkoutIntentService.class);
        ignoreReminderIntent.setAction(ReminderWorkout.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(context,
            ACTION_IGNORE_PENDING_INTENT_ID,
            ignoreReminderIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.ic_directions_run_black_24dp, context.getString(R.string.no_thanks),ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }

    public static void clearAllNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private static PendingIntent contentIntent(Context context){
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context,
            WORKOUT_REMINDER_PENDING_INTENT_ID,
            startActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context){
        Resources res = context.getResources();

        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_notifications_black_24dp);
        return largeIcon;
    }
}
