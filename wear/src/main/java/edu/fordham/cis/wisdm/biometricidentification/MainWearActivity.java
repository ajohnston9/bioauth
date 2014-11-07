package edu.fordham.cis.wisdm.biometricidentification;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

/**
 * This is the main activity of the application. Seeing as the application is launched via
 * the phone, the MainActivity will not do much.
 *
 * @author Andrew H. Johnston
 * @version 0.01ALPHA
 */
public class MainWearActivity extends Activity {

    private TextView mTextView;

    private static final String TAG = "MainWearActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wear);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }

}
