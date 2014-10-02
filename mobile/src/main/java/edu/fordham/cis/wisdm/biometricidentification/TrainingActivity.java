package edu.fordham.cis.wisdm.biometricidentification;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TrainingActivity extends Activity {

    private Button mBeginButton;
    private Button mResetButton;
    private EditText mName;
    private EditText mEmail;

    private boolean runTraining = false;

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
}
