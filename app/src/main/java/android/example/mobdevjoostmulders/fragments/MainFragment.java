package android.example.mobdevjoostmulders.fragments;


import android.content.Intent;
import android.example.mobdevjoostmulders.activities.DetailActivity;
import android.example.mobdevjoostmulders.activities.WorkoutPlanActivity;
import android.example.mobdevjoostmulders.workout.Workout;
import android.example.mobdevjoostmulders.workout.WorkoutAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.example.mobdevjoostmulders.R;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Workout>>, WorkoutAdapter.OnItemClickListener{
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private TextView mTextViewWorkoutName;
    private TextView mTextViewWorkoutCategory;
    private static RecyclerView mRecyclerView;
    public static WorkoutAdapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<Workout> mWorkoutArrayList;
    private static final int WORKOUT_LOADER = 66;
    private static final String URL_FOR_JSON = "url";
    private WorkoutAdapter.OnItemClickListener mOnItemClickListener;
    private static Bundle mBundleRecyclerViewSata;
    private final String KEY_RECYCLER_STATE = "recycler_state";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        setHasOptionsMenu(true);


        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLoadingIndicator = (ProgressBar) getView().findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay = (TextView)  getView().findViewById(R.id.tv_error_message_display);
        mTextViewWorkoutName = (TextView)  getView().findViewById(R.id.tv_workout_name);
        mTextViewWorkoutCategory = (TextView)  getView().findViewById(R.id.tv_workout_category);
        mRecyclerView = (RecyclerView)  getView().findViewById(R.id.recyclerview_workouts);
        mWorkoutArrayList = new ArrayList<Workout>();
        mLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        mAdapter = new WorkoutAdapter(mWorkoutArrayList, getActivity().getBaseContext(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        Bundle queryBundle = new Bundle();
        queryBundle.putString(URL_FOR_JSON, "https://api.myjson.com/bins/dfjd4");
        LoaderManager loaderManager = getLoaderManager();
        Loader<ArrayList<Workout>> workoutJsonLoader = loaderManager.getLoader(WORKOUT_LOADER);

        if (workoutJsonLoader == null) {
            loaderManager.initLoader(WORKOUT_LOADER, queryBundle, this);
        }else{
            loaderManager.restartLoader(WORKOUT_LOADER,queryBundle,this);
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<Workout>> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<ArrayList<Workout>>(getActivity().getBaseContext()) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(args == null){
                    return;
                }
                if(!mWorkoutArrayList.isEmpty()){
                    deliverResult(mWorkoutArrayList);
                }else{
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }

            }
            @Nullable
            @Override
            public ArrayList<Workout> loadInBackground() {
                String url = args.getString(URL_FOR_JSON);
                if(url == null || TextUtils.isEmpty(url)){
                    return null;
                }
                URL obj = null;
                try {
                    obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", "Mozilla/5.0");
                    BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    JSONObject myResponse = new JSONObject(response.toString());
                    JSONArray jsonArray = myResponse.getJSONArray("workouts");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Workout workout = new Workout();
                        workout.setName(jsonObject.getString("name"));
                        workout.setCategory(jsonObject.getString("category"));
                        workout.setMuscleGroup(jsonObject.getString("muscle"));
                        workout.setDescription(jsonObject.getString("description"));
                        URL urlFromJson = new URL(jsonObject.getString("imageUrl"));
                        Bitmap bitmapImage = BitmapFactory.decodeStream(urlFromJson.openConnection().getInputStream());
                        Bitmap resizedBitmapImage = Bitmap.createScaledBitmap(bitmapImage,100,100,true);
                        workout.setImageBitmap(resizedBitmapImage);
                        mWorkoutArrayList.add(workout);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return mWorkoutArrayList;
            }

            @Override
            public void deliverResult(@Nullable ArrayList<Workout> data) {
                mWorkoutArrayList = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Workout>> loader, ArrayList<Workout> data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if(data == null){
            showErrorMessage();
        }else{
            mWorkoutArrayList = data;
            showRecyclerView();
            mAdapter.notifyDataSetChanged();

        }


    }

    public void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    public void showRecyclerView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Workout>> loader) {
    }

    @Override
    public void onItemClick(int position) {
        String name = mWorkoutArrayList.get(position).getName();
        String category = mWorkoutArrayList.get(position).getCategory();
        String muscleGroup = mWorkoutArrayList.get(position).getMuscleGroup();
        String description = mWorkoutArrayList.get(position).getDescription();

        DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.fragment_detail);
        if(detailFragment != null && detailFragment.isVisible()){
            DetailFragment newDetailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("name",name);
            bundle.putString("category",category);
            bundle.putString("muscleGroup",muscleGroup);
            bundle.putString("description",description);
            newDetailFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(detailFragment.getId(),newDetailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            Intent intent = new Intent(getActivity().getBaseContext(), DetailActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("category",category);
            intent.putExtra("muscleGroup",muscleGroup);
            intent.putExtra("description",description);
            this.startActivity(intent);
        }

    }


}
