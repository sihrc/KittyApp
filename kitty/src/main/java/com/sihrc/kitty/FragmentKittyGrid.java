package com.sihrc.kitty;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Created by chris on 12/22/13.
 */
public class FragmentKittyGrid extends Fragment{
    //List of kitties to show
    ArrayList<Kitty> kitties;

    //ImageAdapter
    AdapterImage kittyAdapter;

    //Database
    HandlerDatabase db;

    //Public Constructor to decide the kitties
    public FragmentKittyGrid(){
        this.kitties = new ArrayList<String>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_kitty_grid, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new HandlerDatabase(getActivity());
        db.open();
        populateGridView();
    }

    private void populateGridView(){
        GridView grid = (GridView) getView().findViewById(R.id.fragment_kitty_grid);
        kittyAdapter = new AdapterImage(getActivity(), kitties);
        grid.setAdapter(kittyAdapter);
    }

    //Update the Grid with new kitties
    private void updateGridView(){
        kitties.clear();
        kitties.addAll(db.getAllKitties());
        populateGridView();
    }

    //Get New Kitty Urls - Async Task
    private void getKitties(final String mode){
        new AsyncTask<Void, Void, String>() {
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //Request TimeOut
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
            }

            @Override
            protected String doInBackground(Void... params) {
                //Get next url
                String[] parts = (getActivity().getSharedPreferences("KittyApp", Context.MODE_PRIVATE).getString("url","https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=cutebabykitten&start=0&userip=MyIP&imgsz=large")).split("start=");
                String url = parts[0] + "start=" + String.valueOf((Integer.valueOf(parts[1].substring(0, parts[1].indexOf('&'))) + (mode.equals("next")?1:-1))) + parts[1].substring(parts[1].indexOf("&"));

                //HTTP GET Request
                HttpGet getImages = new HttpGet(url);
                try {
                    //Parsing HTTP Response
                    response = client.execute(getImages);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line;
                    while ((line = reader.readLine())!=null){
                        sb.append(line);
                        sb.append(System.getProperty("line.separator"));
                    }
                    return sb.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "failed"; //Default URL for failed Requests
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONArray results = new JSONObject(s).getJSONObject("responseData").getJSONArray("results");
                    for (int i = 0; i < results.length(); i++){
                        getImageAndPush(results.getJSONObject(i).getString("unescapedUrl"));
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }.execute();
    }

    private void getImageAndPush(final String url){
        new AsyncTask<Void, Void, byte[]>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected byte[] doInBackground(Void... params) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(url);
                try{
                    HttpResponse response = client.execute(request);
                    HttpEntity entity = response.getEntity();
                    int imageLength = (int)(entity.getContentLength());
                    InputStream is = entity.getContent();
                    byte[] imageBlob = new byte[imageLength];
                    int bytesRead = 0;
                    while (bytesRead < imageLength) {
                        int n = is.read(imageBlob, bytesRead, imageLength - bytesRead);
                        bytesRead += n;
                    }
                    return new byte[0];
                } catch (Exception e) {
                    e.printStackTrace();
                    return new byte[0];
                }
            }

            @Override
            protected void onPostExecute(byte[] bytes) {
                super.onPostExecute(bytes);
                db.addKittyToDatabase(bytes);
                updateGridView();
            }
        }.execute();
    }

}
