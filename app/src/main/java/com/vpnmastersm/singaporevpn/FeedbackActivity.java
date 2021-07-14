package com.vpnmastersm.singaporevpn;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

public class FeedbackActivity extends Activity {
    private ProgressBar mProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the splash screen
        super.setContentView(R.layout.feedback);
        setTitle(R.string.feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Button btn = (Button)findViewById(R.id.send);
        EditText edt = (EditText)findViewById(R.id.feedback_text);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
            intent.putExtra(Intent.EXTRA_SUBJECT, "singapore vpn Feedback");
            intent.putExtra(Intent.EXTRA_TEXT, edt.getText());
            intent.setData(Uri.parse("mailto:smkoki2020@gmail.com")); // or just "mailto:" for blank
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
            startActivity(intent);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startApp();
            }
        });


    }

    private void doWork() {
        for (int progress=0; progress<60; progress+=10) {
            try {
                Thread.sleep(800);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startApp() {
        Intent intent = new Intent(FeedbackActivity.this, Activity_Nav.class);
        startActivity(intent);
    }
}
