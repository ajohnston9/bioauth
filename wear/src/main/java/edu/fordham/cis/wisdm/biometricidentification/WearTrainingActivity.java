package edu.fordham.cis.wisdm.biometricidentification;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;


public class WearTrainingActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor        mAccelerometer;

    private ArrayList<AccelerationRecord> mAccelerationRecords = new ArrayList<AccelerationRecord>();

    private AtomicBoolean shouldCollect = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear_training);
        //Easy way to keep watch from sleeping on me
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        CollectTask t = new CollectTask();
        Timer timer = new Timer();
        timer.schedule(t, 60*1000*2); //Wait two minutes then run
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
            //TODO: Send AccelerationRecord back to the phone
            //TODO: Close the window
        }
    }
}
