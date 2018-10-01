package com.doctorrokter.bb10dialer.activities;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        super.onResume();
        ImageView movingCircle = findViewById(R.id.movingCircle);
        ImageView answerGradigntBg = findViewById(R.id.answerGradientBackground);
        ImageView declineGradigntBg = findViewById(R.id.declineGradientBackground);
        ConstraintLayout callSwitcher = findViewById(R.id.callSwitcher);

        defaultPT.set(movingCircle.getX(), movingCircle.getY());

        movingCircle.setOnTouchListener((View v, MotionEvent event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    movingCircle.setX((int)(startPT.x + event.getX() - downPT.x));
                    Log.d(TAG, "onResume: default x = " + defaultPT.x + ", current x = " + movingCircle.getX());

                    double pathLength = callSwitcher.getWidth() / 2;
                    double movedInPercents = Math.abs((movingCircle.getX() * 100) / pathLength);

                    Log.d(TAG, "onResume: percentage = " + movedInPercents);

                    if (movingCircle.getX() < defaultPT.x) {
                        declineGradigntBg.setAlpha(0f);
                        answerGradigntBg.setAlpha(Math.abs((float)(movingCircle.getX() * 0.001)));
                    } else {
                        answerGradigntBg.setAlpha(0f);
                        declineGradigntBg.setAlpha(Math.abs((float)(movingCircle.getX() * 0.001)));
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    downPT.set(event.getX(), event.getY());
                    startPT.set(movingCircle.getX(), movingCircle.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    declineGradigntBg.setAlpha(0f);
                    answerGradigntBg.setAlpha(0f);
                    movingCircle.setX(defaultPT.x);
                    break;

            }
            return true;
        });
    }
}
