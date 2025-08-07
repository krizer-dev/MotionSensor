package com.krizer.motionsensortest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private TextView detectionStatusTextView;
    private MotionSensor mMotionSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detectionStatusTextView = findViewById(R.id.detectionStatusTextView);
        detectionStatusTextView.setText("센서 초기화 중...");
        mMotionSensor = new MotionSensor(this);

        mMotionSensor.setMotionDetectionListener(new MotionSensor.MotionDetectionListener() {
            @Override
            public void onMotionDetected(boolean detected) {
                runOnUiThread(() -> {
                    if (detected) {
                        detectionStatusTextView.setText("움직임 감지");
                        detectionStatusTextView.setBackgroundColor(getColor(android.R.color.holo_purple));
                    } else {
                        detectionStatusTextView.setText("움직임 감지 대기");
                        detectionStatusTextView.setBackgroundColor(getColor(android.R.color.holo_orange_light));
                    }
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    detectionStatusTextView.setText("오류: " + message);
                    detectionStatusTextView.setBackgroundColor(getColor(android.R.color.holo_red_light));
                });
                Log.e(TAG, "MotionSensor Error: " + message);
            }
        });
        mMotionSensor.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMotionSensor != null) {
            mMotionSensor.release();
        }
    }
}