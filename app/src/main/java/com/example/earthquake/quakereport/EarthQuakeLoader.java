package com.example.earthquake.quakereport;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.net.MalformedURLException;
import java.util.List;

public class EarthQuakeLoader extends AsyncTaskLoader<List<MyClass>> {
    String murl;
    public EarthQuakeLoader(@NonNull Context context, String url) {
        super(context);
        murl=url;
    }

    @Nullable
    @Override
    public List<MyClass> loadInBackground() {
        List<MyClass>  earthquakes=null;
        if(murl==null){

            return null;
        }
        try {

           earthquakes=QueryUtils.fetchEarthquakeData(murl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return earthquakes;
    }

    @Override
    protected void onStartLoading()
    {

        forceLoad();
    }
}
