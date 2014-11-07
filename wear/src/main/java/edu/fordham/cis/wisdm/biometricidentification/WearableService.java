package edu.fordham.cis.wisdm.biometricidentification;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Listens on the Wearable for the signal to start training and accordingly starts the training process
 * @author Andrew Johnston
 * @version 0.01ALPHA
 */
public class WearableService extends WearableListenerService {


    private static final String TAG = "WearableService";
    private static final String START_TRAINING = "/start-training";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "Message received for path " + messageEvent.getPath());
        if (messageEvent.getPath().equals(START_TRAINING)) {
            Intent i = new Intent(getBaseContext(), WearTrainingActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(i);
        }
    }
}
