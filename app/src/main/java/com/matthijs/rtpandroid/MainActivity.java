package com.matthijs.rtpandroid;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.record) {
            Intent captureVideoIntent = new Intent(getApplicationContext(), VideoCaptureActivity.class);
            startActivityForResult(captureVideoIntent, VIDEO_CAPTURE_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This is where to filename is returned from VideoCapture Activity
     * A call to 
     *
     * @param requestCode
     * @param resultCode
     * @param data
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
