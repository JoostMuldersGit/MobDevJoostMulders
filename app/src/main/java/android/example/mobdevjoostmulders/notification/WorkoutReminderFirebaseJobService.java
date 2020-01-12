package android.example.mobdevjoostmulders.notification;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;

public class WorkoutReminderFirebaseJobService extends com.firebase.jobdispatcher.JobService {
    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = WorkoutReminderFirebaseJobService.this;
                ReminderWorkout.executeTask(context,ReminderWorkout.ACTION_REMINDER);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job,false);
            }
        };
        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        if(mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}
