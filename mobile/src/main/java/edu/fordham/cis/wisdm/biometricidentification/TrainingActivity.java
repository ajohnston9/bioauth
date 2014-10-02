package edu.fordham.cis.wisdm.biometricidentification;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


public class TrainingActivity extends Activity {

    private Button mBeginButton;
    private Button mResetButton;
    private EditText mName;
    private EditText mEmail;
    private GoogleApiClient mGoogleApiClient;
    private boolean runTraining = false;

    private static final String TAG = "TrainingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        mBeginButton = (Button) findViewById(R.id.btnBegin);
        mResetButton = (Button) findViewById(R.id.btnReset);
        mName        = (EditText) findViewById(R.id.editName);
        mEmail       = (EditText) findViewById(R.id.editEmail);

        mBeginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTraining = true;
                Toast.makeText(TrainingActivity.this, "Please lock your phone and place it in " +
                    "your pocket", Toast.LENGTH_SHORT).show();
                //TODO: Set up asynchronous waiting thread for watch data
            }
        });
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName.setText(null);
                mName.setText(null);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        Log.d(TAG, "Connected to wearable.");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d(TAG, "Connection to wearable suspended. Code: " + i);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.d(TAG, "Fuck! Connection failed: " + connectionResult);
                    }
                })
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.training, menu);
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


    class Worker implements Runnable {

        @Override
        public void run() {
            NodeApi.GetConnectedNodesResult nodes =
                    Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
            for (Node node : nodes.getNodes()) {
                MessageApi.SendMessageResult result;
                Log.d(TAG, "Got here.");
                result= Wearable.MessageApi.sendMessage(
                        mGoogleApiClient, node.getId(), "/Hello_World", null).await();
                Log.d(TAG, "Sent to node: " + node.getId() + " with display name: " + node.getDisplayName());
                if (!result.getStatus().isSuccess()) {
                    Log.e(TAG, "ERROR: failed to send Message: " + result.getStatus());
                } else {
                    Log.d(TAG, "Message Successfully sent.");
                }
            }
        }
    }
}
