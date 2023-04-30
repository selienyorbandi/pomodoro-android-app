package com.example.pomodoroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer timer;
    private TextView timerTextView;
    private ImageView startButton, stopButton;

    private boolean isRunning = false;
    private long timeLeftInMillis = 25 * 60 * 1000;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });
    }

    private void startTimer() {
        startButton.setImageResource(R.drawable.ic_pause);
        stopButton.setEnabled(true);
        isRunning = true;

        timer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                isRunning = false;
                timerTextView.setText("00:00");
                startButton.setImageResource(R.drawable.ic_play);
                stopButton.setEnabled(false);
            }
        }.start();
    }

    private void pauseTimer() {
        startButton.setImageResource(R.drawable.ic_play);
        stopButton.setEnabled(true);
        isRunning = false;

        timer.cancel();
    }

    private void stopTimer() {
        startButton.setImageResource(R.drawable.ic_play);
        stopButton.setEnabled(false);
        isRunning = false;
        timeLeftInMillis = 25 * 60 * 1000;
        updateCountdownText();
        timer.cancel();
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }
}
