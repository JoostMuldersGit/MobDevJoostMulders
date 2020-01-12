package android.example.mobdevjoostmulders.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.example.mobdevjoostmulders.R;
import android.example.mobdevjoostmulders.activities.WorkoutPlanAddActivity;
import android.example.mobdevjoostmulders.activities.WorkoutPlanDetailActivity;
import android.example.mobdevjoostmulders.data.DataBaseHelper;
import android.example.mobdevjoostmulders.notification.NotificationUtils;
import android.example.mobdevjoostmulders.workoutplan.WorkoutPlan;
import android.example.mobdevjoostmulders.workoutplan.WorkoutPlanAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class WorkoutPlanFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<WorkoutPlan>> , WorkoutPlanAdapter.OnItemClickListener {
    private ProgressBar mLoadingIndicator;
    private TextView mTextViewWorkoutPlanName;
    private RecyclerView mRecyclerView;
    private WorkoutPlanAdapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<WorkoutPlan> mWorkoutPlanArrayList;
    private static final int WORKOUT_PLAN_LOADER = 69;
    private WorkoutPlanAdapter.OnItemClickListener mOnItemClickListener;
    private FloatingActionButton mFloatingActionButton;
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDatabase;
    private NotificationManagerCompat mNotificationManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_plan,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLoadingIndicator = (ProgressBar) getView().findViewById(R.id.pb_loading_indicator_workout_plan);
        mTextViewWorkoutPlanName = (TextView) getView().findViewById(R.id.tv_workout_plan_name);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview_workoutplans);
        mFloatingActionButton = (FloatingActionButton) getView().findViewById(R.id.fab_add);
        mWorkoutPlanArrayList = new ArrayList<WorkoutPlan>();
        mDataBaseHelper = new DataBaseHelper(getActivity().getBaseContext());
        mDatabase = mDataBaseHelper.getWritableDatabase();
        mAdapter = new WorkoutPlanAdapter(mWorkoutPlanArrayList, getActivity().getBaseContext(), this);
        mLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(false);
        mNotificationManager = NotificationManagerCompat.from(getActivity().getBaseContext());

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int id = viewHolder.getAdapterPosition();
                String workoutPlanName = mWorkoutPlanArrayList.get(id).getWorkoutPlanName();
                Log.d("Value", "name:" + workoutPlanName);
                if(mDataBaseHelper.removeWorkoutPlan(workoutPlanName)){
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity().getBaseContext(), "Successfully removed!",Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(mRecyclerView);

        LoaderManager loaderManager = getLoaderManager();
        Loader<ArrayList<WorkoutPlan>> loader = loaderManager.getLoader(WORKOUT_PLAN_LOADER);


        if (loader == null) {
            loaderManager.initLoader(WORKOUT_PLAN_LOADER, null, this);
        }else{
            loaderManager.restartLoader(WORKOUT_PLAN_LOADER,null,this);
        }


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), WorkoutPlanAddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        String workoutPlanWorkoutName = mWorkoutPlanArrayList.get(position).getWorkoutName();
        int workoutPlanWorkoutSets = mWorkoutPlanArrayList.get(position).getSets();
        int workoutPlanWorkoutReps = mWorkoutPlanArrayList.get(position).getReps();
        int workoutPlanWorkoutRest = mWorkoutPlanArrayList.get(position).getSeconds();

        WorkoutPlanDetailFragment workoutPlanDetailFragment = (WorkoutPlanDetailFragment) getFragmentManager().findFragmentById(R.id.fragment_workout_plan_detail);

        if(workoutPlanDetailFragment != null && workoutPlanDetailFragment.isVisible()){
            WorkoutPlanDetailFragment newFragment = new WorkoutPlanDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("workoutPlanWorkoutName", workoutPlanWorkoutName);
            bundle.putInt("workoutPlanWorkoutSets", workoutPlanWorkoutSets);
            bundle.putInt("workoutPlanWorkoutReps", workoutPlanWorkoutReps);
            bundle.putInt("workoutPlanWorkoutRest", workoutPlanWorkoutRest);

            newFragment.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(workoutPlanDetailFragment.getId(),newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else{
            Intent intent = new Intent(getActivity().getBaseContext(), WorkoutPlanDetailActivity.class);
            intent.putExtra("workoutPlanWorkoutName", workoutPlanWorkoutName);
            intent.putExtra("workoutPlanWorkoutSets", workoutPlanWorkoutSets);
            intent.putExtra("workoutPlanWorkoutReps", workoutPlanWorkoutReps);
            intent.putExtra("workoutPlanWorkoutRest", workoutPlanWorkoutRest);
            this.startActivity(intent);
        }

    }

    @NonNull
    @Override
    public Loader<ArrayList<WorkoutPlan>> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<ArrayList<WorkoutPlan>>(getActivity().getBaseContext()) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Nullable
            @Override
            public ArrayList<WorkoutPlan> loadInBackground() {
                Cursor data = mDataBaseHelper.getData();
                Log.d("Values", "Database again");
                mWorkoutPlanArrayList.clear();
                while (data.moveToNext()) {
                    WorkoutPlan workoutPlan = new WorkoutPlan(data.getString(1), data.getString(2), data.getInt(3), data.getInt(4), data.getInt(5));
                    mWorkoutPlanArrayList.add(workoutPlan);

                }
                return mWorkoutPlanArrayList;
            }

            /*@Override
            public void deliverResult(@Nullable ArrayList<WorkoutPlan> data) {
                mWorkoutPlanArrayList = data;
                super.deliverResult(data);
            }*/
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<WorkoutPlan>> loader, ArrayList<WorkoutPlan> data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (data != null) {
            mWorkoutPlanArrayList = data;
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<WorkoutPlan>> loader) {

    }

}
