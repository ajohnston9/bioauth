package edu.fordham.cis.wisdm.biometricidentification;

import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * The TrainingDataReceiver class takes the data sent from the watch and hands it off to
 * the training activity to be packaged up and sent to the server
 *
 * @author Andrew H. Johnston
 * @version 0.01ALPHA
 */
public class TrainingDataReceiver extends WearableListenerService  {

    private PowerManager.WakeLock         mWakeLock;
    private ArrayList<AccelerationRecord> mAccelerationRecords = new ArrayList<AccelerationRecord>();

    private final static String TAG = "TrainingDataReceiver";

    public static String name;
    public static String email;

    @Override
    public void onCreate() {
        super.onCreate();
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock                 = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        mWakeLock.acquire();
        Log.wtf(TAG, "Service Started");
    }


    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        try {
            for (DataEvent event : dataEvents) {
                byte[] data = event.getDataItem().getData();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                mAccelerationRecords = (ArrayList<AccelerationRecord>) objectInputStream.readObject();
                Log.wtf(TAG, "Records is of size: " + mAccelerationRecords.size());
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(500l); //Vibrate for half a second
            }
        } catch (Exception e) {
            Log.wtf(TAG, "Something happened: " +e.getClass().getName() + ": " +e.getMessage());
        } finally {
            mWakeLock.release();
        }
    }

    private void writeToFile() {

    }

}
