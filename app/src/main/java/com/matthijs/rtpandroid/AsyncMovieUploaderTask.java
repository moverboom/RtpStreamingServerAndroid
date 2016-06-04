package com.matthijs.rtpandroid;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Bram on 4-6-2016.
 */
public class AsyncMovieUploaderTask extends AsyncTask<Video, Void, Void> {

    // Attributes
    private static final String TAG = "AsyncMovieUploaderTask";
    private static final String DISCOVERY_SERVER_URL = "http://www.bramreinold.nl/movie/list/add/";
    private JSONObject jsonMovieObj;
    private Video video;

    @Override
    protected Void doInBackground(Video... params) {
        this.video = params[0];

        // Initialize json object
        jsonMovieObj = new JSONObject();

        // Setup jsonMovieObj
        try {
            jsonMovieObj.put("movieID", video.getId());
            jsonMovieObj.put("movieName", video.getName());
            jsonMovieObj.put("ipAddress", video.getIp());
            jsonMovieObj.put("portNumber", video.getPort());
        } catch (JSONException e) {
            Log.i(TAG, "Setup jsonMovieObj failed when putting movie object into it. See error message:");
            e.printStackTrace();
        }

        // Execute Post request to server see:executePostRequest()
        executePostRequest(DISCOVERY_SERVER_URL, jsonMovieObj);
        
        return null;
    }

    private void executePostRequest(String urlName, JSONObject jsonObj) {

        try {
            URL url = new URL(urlName);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            // Add the json into the request body
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(jsonObj.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
