package edu.fordham.cis.wisdm.biometricidentification;

import android.content.Intent;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Class Description Here.
 * Created on 9/30/14.
 *
 * @author Andrew Johnston
 * @version 0.01
 */
public class WearableService extends WearableListenerService {


    private static final String TAG = "WearableService";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals("/Hello_World")) {
            //Fire an activity just so we can see if it works
            Intent i = new Intent(getBaseContext(), MainWearActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(i);
        }
    }
}
