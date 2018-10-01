package com.doctorrokter.bb10dialer.activities;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.doctorrokter.bb10dialer.R;

public class CallActivity extends AppCompatActivity {

    private static String TAG = "CallActivity";

    private PointF downPT = new PointF();
    private PointF startPT = new PointF();
    private PointF defaultPT = new PointF();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller);

        Log.d(TAG, "on create");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "on resume");

        ImageView movingCircle = findViewById(R.id.movingCircle);

        Log.d(TAG, "onResume: " + movingCircle);

        defaultPT.set(movingCircle.getX(), movingCircle.getY());

        movingCircle.setOnTouchListener((View v, MotionEvent event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    movingCircle.setX((int)(startPT.x + event.getX() - downPT.x));
                    break;
                case MotionEvent.ACTION_DOWN:
                    downPT.set(event.getX(), event.getY());
                    startPT.set(movingCircle.getX(), movingCircle.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d(TAG, "onResume: moving done");
                    movingCircle.setX(defaultPT.x);
                    break;

            }
            return true;
        });
    }
}
