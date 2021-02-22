/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.earthquake.quakereport;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;
import com.example.earthquake.R;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<MyClass>> {

    public static final String TAG = EarthquakeActivity.class.getName();
   private static final String USGS_REQUEST_URL=
           "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private static MyAdapter myAdapter;
   private static final int EARTHQUAKE_LOADER_ID=1;
   private TextView view;
    private View mview;
   private static ArrayList<MyClass> earthquakes;


    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        view=findViewById(R.id.empty);

       //ConnectivityManager tells your app about the state of connectivity in the system.
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            androidx.loader.app.LoaderManager.getInstance(EarthquakeActivity.this).initLoader(EARTHQUAKE_LOADER_ID,null,EarthquakeActivity.this);
            // Get a reference to the LoaderManager, in order to interact with loaders.
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.indeterminateBar);
            loadingIndicator.setVisibility(View.GONE);
            view.setText("no_internet_connection");
            // Update empty state with no connection error message
        }


        earthquakes = new ArrayList<>();
        ListView earthquakeListView = (ListView) findViewById(R.id.list);


        myAdapter = new MyAdapter(this , new ArrayList<MyClass>());
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(myAdapter);
        earthquakeListView.setEmptyView(view);


        //adding an onclick listener
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyClass aClass=earthquakes.get(position);
                String Url =aClass.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Url));
                startActivity(i);
                Toast.makeText(EarthquakeActivity.this, "loading ", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @NonNull
    @Override
    public Loader<List<MyClass>> onCreateLoader(int id, @Nullable Bundle args) {
        return new EarthQuakeLoader(this, USGS_REQUEST_URL);

    }


    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<MyClass>> loader, List<MyClass> data) {
        myAdapter.clear();
        earthquakes.clear();
        earthquakes= (ArrayList<MyClass>) data;
        if (data!=null && !data.isEmpty()){
            myAdapter.addAll(data);
            view.setText("sorry no item to display");
            mview=findViewById(R.id.indeterminateBar);
            mview.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<MyClass>> loader) {
            myAdapter.clear();
            earthquakes.clear();
    }





//
//    private class background extends AsyncTask<String ,Void, List<MyClass>>{
//
//
//        @Override
//        protected List<MyClass> doInBackground(String... urls) {
//            List<MyClass> result=null;
//           if (urls.length<1||urls[0]==null){
//               return null;
//           }
//            try {
//               result=QueryUtils.fetchEarthquakeData(urls[0]);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//
//
//        @Override
//        protected void onPostExecute(List<MyClass> myClasses) {
//           myAdapter.clear();;
//           if(myClasses!=null&& !myClasses.isEmpty()){
//               myAdapter.addAll(myClasses);
//           }
//        }
//    }
//



}
