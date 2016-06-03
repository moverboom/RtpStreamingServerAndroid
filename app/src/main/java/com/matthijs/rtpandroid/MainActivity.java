package com.matthijs.rtpandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Matthijs Overboom on 12-5-16.
 */
public class MainActivity extends AppCompatActivity {
    private int VIDEO_CAPTURE_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Server.getNewInstance(this.getBaseContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.record) {
            //Create an Intent for the VideoCaptureActivity
            Intent captureVideoIntent = new Intent(getApplicationContext(), VideoCaptureActivity.class);
            //Start activity and provide the request code (later used in onActivityResult)
            startActivityForResult(captureVideoIntent, VIDEO_CAPTURE_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This is where to filename is returned from VideoCapture Activity
     * Probably a good place to initialize sending the data to the REST API
     *
     * @param requestCode VIDEO_CAPTURE_CODE in our case. Used to indicated the returning Activity
     * @param resultCode Indicated the state in which the Activity returned
     * @param data Contains data supplied by the Activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == VIDEO_CAPTURE_CODE) {
            if(resultCode == RESULT_OK) {
                String fileName = data.getStringExtra("FILE_NAME");
                Toast.makeText(this, fileName, Toast.LENGTH_LONG).show();
            }
        }
    }
}
