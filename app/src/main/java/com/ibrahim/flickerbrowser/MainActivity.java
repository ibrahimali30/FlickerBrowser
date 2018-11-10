package com.ibrahim.flickerbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements GetFlickerJsonData.OnDataAvailable
                                                          ,RecyclerItemClickLIstener.OnRecyclerClickListener{
    private static final String TAG = "MainActivity";
    private FlickerResyclerViewAdapter mFlickerResyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starts");


        activateToolbar(false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickLIstener(this,recyclerView,this));

        mFlickerResyclerViewAdapter = new FlickerResyclerViewAdapter(new ArrayList<Photo>(),this);
        recyclerView.setAdapter(mFlickerResyclerViewAdapter);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String queryResult = sharedPreferences.getString(FLICKER_QUERY,"");
        if (queryResult.length()>0) {

            GetFlickerJsonData getFlickerJsonData = new GetFlickerJsonData(this, "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", false);
//        getFlickerJsonData.executeOnSameThread("android,nougat");
            getFlickerJsonData.execute(queryResult);
        }
        Log.d(TAG, "onResume: ends ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id==R.id.search){
            Intent intent = new Intent(this,SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   @Override
    public void onDataAvailable (List<Photo> data, DownloadStatus status){
       Log.d(TAG, "onDataAvailable: starts");
        if (status ==DownloadStatus.OK){
            mFlickerResyclerViewAdapter.loadDAta(data);
        }else {
            Log.e(TAG, "onDataAvailable: failed"+status);
        }

       Log.d(TAG, "onDataAvailable: ends");
    }

    @Override
    public void OnItemClick(View view, int position) {
        Log.d(TAG, "OnItemClick:= starts ");
        Log.d(TAG, "OnItemClick: ----------------------"+GetFlickerJsonData.mPhotoList.get(position).getTitle());

        Intent intent = new Intent(getApplicationContext(),PhotoDetailActivity.class);
        intent.putExtra("i",position);
        startActivity(intent);
    }

    @Override
    public void OnItemLongClick(View view, int position) {
        Log.d(TAG, "OnItemLongClick:= starts");

    }
}


