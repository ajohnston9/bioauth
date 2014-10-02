package edu.fordham.cis.wisdm.biometricidentification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


public class MainActivity extends Activity {

    private Button mTrainingButton;
    private Button mAuthenticationButton;

    static final int TRAINING_MODE = 0;
    static final int AUTH_MODE     = 1;
    static final String TAG = "MainActivity_Phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTrainingButton       = (Button) findViewById(R.id.trainingButton);
        mAuthenticationButton = (Button) findViewById(R.id.authenticationButton);
        mTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TrainingActivity.class);
                startActivityForResult(i, TRAINING_MODE);
            }
        });
        mAuthenticationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(MainActivity.this, AuthActivity.class);
               startActivityForResult(i, AUTH_MODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TRAINING_MODE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Training data sent.", Toast.LENGTH_SHORT);
            //Take data and send as training data
        } else if (requestCode == AUTH_MODE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Authentication request sent.", Toast.LENGTH_SHORT);
            //Take data and send, wait for authentication
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
