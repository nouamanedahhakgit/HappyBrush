package com.mylancaster.gardens;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String COMING_SOON_DATE = "2026/05/15";
    private TextView companyInfoTextView;
    private TextView countdownTimerTextView;
    private TextView accessStatusTextView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        companyInfoTextView = findViewById(R.id.tv_company_info);
        countdownTimerTextView = findViewById(R.id.tv_countdown_timer);
        accessStatusTextView = findViewById(R.id.tv_access_status);


        Button helpButton = findViewById(R.id.btn_help);

        helpButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
        });
        Button contactButton = findViewById(R.id.btn_contact);

        contactButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
            startActivity(intent);
        });


        startComingSoonTimer();
    }

    private void startComingSoonTimer() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            Date targetDate = sdf.parse(COMING_SOON_DATE);
            Date currentDate = new Date();

            if (currentDate.before(targetDate)) {
                // Coming Soon Mode - Show countdown
                long timeDifference = targetDate.getTime() - currentDate.getTime();
                startCountdownTimer(timeDifference);
                displayComingSoonMode(targetDate);
            } else {
                // Target date reached - Show full access
                displayFullAccessMode();
            }

        } catch (Exception e) {
            displayErrorMode();
        }
    }

    private void startCountdownTimer(long millisInFuture) {
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdownText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                // Countdown finished - switch to full access mode
                displayFullAccessMode();
            }
        }.start();
    }

    private void updateCountdownText(long millisUntilFinished) {
        long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
        long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;

        String countdownText = String.format(Locale.getDefault(),
                "%02d days %02d:%02d:%02d",
                days, hours, minutes, seconds);

        countdownTimerTextView.setText("⏰ " + countdownText);
    }

    private void displayComingSoonMode(Date targetDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        StringBuilder comingSoonInfo = new StringBuilder();
        comingSoonInfo.append("🎯 COMING SOON\n\n");
        comingSoonInfo.append("Lancaster Gardens RTM Portal\n");
        comingSoonInfo.append("is launching soon!\n\n");
        comingSoonInfo.append("📅 Launch Date:\n");
        comingSoonInfo.append(sdf.format(targetDate)).append("\n\n");

        comingSoonInfo.append("🏢 About the Company:\n");
        comingSoonInfo.append("• Lancaster Gardens (1-6) RTM Co. Ltd.\n");
        comingSoonInfo.append("• Company No: 15329344\n");
        comingSoonInfo.append("• Private Limited by Guarantee\n");
        comingSoonInfo.append("• Incorporated: Dec 2023\n\n");

        comingSoonInfo.append("📍 Location:\n");
        comingSoonInfo.append("• Southport, England\n\n");

        comingSoonInfo.append("🔒 Features Coming:\n");
        comingSoonInfo.append("• Full Company Details\n");
        comingSoonInfo.append("• Document Access\n");
        comingSoonInfo.append("• Management Portal\n");
        comingSoonInfo.append("• Member Services");

        companyInfoTextView.setText(comingSoonInfo.toString());
        accessStatusTextView.setText("🚀 Application Launching Soon!");
        accessStatusTextView.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));

        // Show countdown timer
        countdownTimerTextView.setVisibility(TextView.VISIBLE);
    }

    private void displayFullAccessMode() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countdownTimerTextView.setText("🎉 APPLICATION IS NOW LIVE!");
        countdownTimerTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

        StringBuilder fullInfo = new StringBuilder();
        fullInfo.append("🎊 WELCOME TO LANCASTER GARDENS RTM\n\n");

        fullInfo.append("📋 COMPANY DETAILS:\n");
        fullInfo.append("• Company: Lancaster Gardens (1-6) RTM Co. Ltd.\n");
        fullInfo.append("• Number: 15329344\n");
        fullInfo.append("• Type: Private company limited by guarantee\n");
        fullInfo.append("• Incorporated: 5th December 2023\n");
        fullInfo.append("• SIC Code: 98000\n\n");

        fullInfo.append("🏠 REGISTERED OFFICE:\n");
        fullInfo.append("71-73 HOGHTON STREET\n");
        fullInfo.append("SOUTHPORT\n");
        fullInfo.append("ENGLAND\n");
        fullInfo.append("PR9 0PR\n\n");

        fullInfo.append("👨‍💼 DIRECTOR:\n");
        fullInfo.append("• MR JOHN HAMPSON\n");
        fullInfo.append("• Status: Active\n\n");

        fullInfo.append("📊 STRUCTURE:\n");
        fullInfo.append("• Limited by Guarantee\n");
        fullInfo.append("• Guarantee Amount: £1\n");
        fullInfo.append("• PSC: No registerable Person\n\n");

        fullInfo.append("✅ STATUS: FULL ACCESS GRANTED");

        companyInfoTextView.setText(fullInfo.toString());
        accessStatusTextView.setText("✅ Full Access Active - Welcome!");
        accessStatusTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
    }

    private void displayErrorMode() {
        companyInfoTextView.setText("⚠️ System temporarily unavailable.\nPlease try again later.");
        accessStatusTextView.setText("System Error");
        accessStatusTextView.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        countdownTimerTextView.setVisibility(TextView.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}