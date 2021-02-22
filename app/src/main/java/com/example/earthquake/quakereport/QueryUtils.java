package com.example.earthquake.quakereport;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static URL createURl(String url) throws MalformedURLException {
        URL murl=new URL(url);
        return murl;
    }
    private static String  makeHttpRequest(URL url) throws IOException {
        String responce="";
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        if(url!=null){
            urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200) {
                inputStream = urlConnection.getInputStream();
                responce = readFromStream(inputStream);
            }
        }
        return responce;

    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bf = new BufferedReader(inputStreamReader);
            String line = bf.readLine();
            while (line != null) {
                output.append(line);
                line = bf.readLine();
            }

        }


        return output.toString();
    }
    private static List<MyClass> extractFeatureFromJson(String eathquakeJSON){
     if(TextUtils.isEmpty(eathquakeJSON)){
         return null;
     };
        List<MyClass> earthquakes=new ArrayList<>();
        try{
            JSONObject BASEJSONRESPONCE=new JSONObject(eathquakeJSON);
            JSONArray earthquakeArray =BASEJSONRESPONCE.getJSONArray("features");
            for(int i=0;i<earthquakeArray.length();i++){
                JSONObject currentEarthquake=earthquakeArray.getJSONObject(i);
                JSONObject properties=currentEarthquake.getJSONObject("properties");
                double mag=properties.getDouble("mag");
                String location=properties.getString("place");
                long time=properties.getLong("time");
                String  url=properties.getString("url");
                MyClass myClass=new MyClass(mag,location,time,url);
                earthquakes.add(myClass);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return earthquakes;
    }
    public static List<MyClass> fetchEarthquakeData(String  responce) throws MalformedURLException {
        URL url=createURl(responce);
        String JSONResponce=null;
        try {
            JSONResponce=makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<MyClass> earthquakes=extractFeatureFromJson(JSONResponce);
        return earthquakes;
    }
}

