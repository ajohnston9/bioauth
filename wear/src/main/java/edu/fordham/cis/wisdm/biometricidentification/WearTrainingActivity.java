package edu.fordham.cis.wisdm.biometricidentification;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;


public class WearTrainingActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor        mAccelerometer;
    private TextView      mPrompt;
    private TextView      mProgress;

    private ArrayList<AccelerationRecord> mAccelerationRecords = new ArrayList<AccelerationRecord>();

    private AtomicBoolean shouldCollect = new AtomicBoolean(false);

    private static final String TAG = "WearTrainingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear_training);
        //Easy way to keep watch from sleeping on me
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mProgress = (TextView) findViewById(R.id.txtProgress);
        mPrompt = (TextView) findViewById(R.id.txtPrompt);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        CollectTask t = new CollectTask();
        Timer timer = new Timer();
        timer.schedule(t, 60 * 1000 * 2); //Wait two minutes then run
        shouldCollect.set(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wear_training, menu);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (shouldCollect.get()) {
            long timestamp = Calendar.getInstance().getTimeInMillis();
            mAccelerationRecords.add(
                    new AccelerationRecord(event.values[0], event.values[1], event.values[2], timestamp)
            );
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Not used but must be overridden
    }

    class CollectTask extends TimerTask {

        @Override
        public void run() {
            shouldCollect.set(false);
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(mAccelerationRecords);
                oos.flush();
                oos.close(); //FIXME: Close this here?
            } catch (IOException e) {
                Log.d(TAG, "Something fucky happened: " + e.getMessage());
            }
            byte[] data = baos.toByteArray();
            //FIXME: Next line is debugging. Remove before production.
            Log.d(TAG, "Byte array is of size: " + data.length);
            //Vibrate and tell the user to check their phone
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(500L); //Vibrate for half a second
            mProgress.setText("Training Complete.");
            mPrompt.setText("Please unlock your phone.");
            //Send the data back to the phone

            //TODO: Close the window
        }
    }
}
