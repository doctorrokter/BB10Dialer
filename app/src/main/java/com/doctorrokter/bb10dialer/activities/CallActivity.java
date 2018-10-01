package com.doctorrokter.bb10dialer.activities;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
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
    private double movedInPercents = 0d;

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
        ImageView answerShape = findViewById(R.id.answerShape);
        ImageView declineShape = findViewById(R.id.declineShape);

        ConstraintLayout callSwitcher = findViewById(R.id.callSwitcher);

        movingCircle.setOnTouchListener((View v, MotionEvent event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    movingCircle.setX((int)(startPT.x + event.getX() - downPT.x));
                    Log.d(TAG, "onResume: start x = " + startPT.x + ", current x = " + movingCircle.getX());

                    double pathLength = callSwitcher.getWidth() / 2;
                    this.movedInPercents = Math.abs((movingCircle.getX() * 100) / pathLength);

                    if (movingCircle.getX() < startPT.x) {
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

                    if (this.movedInPercents >= 50) {
                        float val = callSwitcher.getWidth() / 2.65f;
                        if (movingCircle.getX() < 0.0) {
                            movingCircle.setX(-val);
                        } else {
                            movingCircle.setX(val);
                        }

                    } else {
                        movingCircle.setX(startPT.x);
                    }

                    break;

            }
            return true;
        });
    }
}
