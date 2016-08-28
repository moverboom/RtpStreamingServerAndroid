package com.matthijs.rtpandroid;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Bram on 4-6-2016.
 */
public class AsyncMovieUploaderTask extends AsyncTask<Video, Void, Void> {

    // Attributes
    private static final String TAG = "AsyncMovieUploaderTask";
    private static final String DISCOVERY_SERVER_URL = "http://192.168.178.11/discoveryserver/movie/list/add/";
    private JSONObject jsonMovieObj;
    private Video video;

    @Override
    protected Void doInBackground(Video... params) {
        this.video = params[0];

        // Initialize json object
        jsonMovieObj = new JSONObject();

        // Setup jsonMovieObj
        try {
//            jsonMovieObj.put("movieID", video.getMovieID());
            jsonMovieObj.put("movieName", video.getName());
            jsonMovieObj.put("ipAddress", video.getIp().getHostName());
            jsonMovieObj.put("portNumber", video.getPort());
        } catch (JSONException e) {
            Log.i(TAG, "Setup jsonMovieObj failed when putting movie object into it. See error message:");
            e.printStackTrace();
        }

        // Execute Post request to server see:executePostRequest()
        executePostRequest(DISCOVERY_SERVER_URL, jsonMovieObj);
        Log.i(TAG, "executePostRequest activated");
        Log.i(TAG, "Json object posted via http: " + jsonMovieObj.toString());

        return null;
    }

    private void executePostRequest(String urlName, JSONObject jsonObj) {

        try {
            URL url = new URL(urlName);
            HttpURLConnection connection = null;

            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);   // necessary to do a post request
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.connect();

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(jsonObj.toString());
            wr.flush();

            // Show return POST request for debugging
            StringBuilder sb = new StringBuilder();
            int HttpResult = connection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                Log.i(TAG, "HTTP_OK response from server");
            } else {
                // Show POST request error
                Log.i(TAG, "Something went wrong with POST request:" + connection.getResponseMessage());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
