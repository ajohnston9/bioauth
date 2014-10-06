package edu.fordham.cis.wisdm.biometricidentification;

import android.os.PowerManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.ArrayList;

/**
 * The TrainingDataReceiver class takes the data sent from the watch and hands it off to
 * the training activity to be packaged up and sent to the server
 *
 * @author Andrew H. Johnston
 * @version 0.01ALPHA
 */
public class TrainingDataReceiver extends WearableListenerService  {

    private GoogleApiClient               mGoogleApiClient;
    private PowerManager.WakeLock         mWakeLock;
    private ArrayList<AccelerationRecord> mAccelerationRecords = new ArrayList<>();

    private final static String TAG = "TrainingDataReceiver";
    private final static String PATH = "/training-data";

    public TrainingDataReceiver() {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock                 = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        mWakeLock.acquire();
    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        //Ensure that the message is actually the training data
        if (event.getPath().equals(PATH)) {
            byte[] bRecs = event.getData();
            //The data comes in as an ArrayList<AccelerationRecord>, so reinflate it

        }
    }

}
