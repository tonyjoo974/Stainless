package edu.illinois.cs465.stainless;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

//warning interface should be tap to continue
//loading screen activity should just appear for 1 sec

public class LoadingScreenActivity extends AppCompatActivity {
    Handler handler;
    private void goNext() {

        // Check if app is opening for the very first time
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        if (isFirstRun) {
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).apply();

            Intent myIntent = new Intent(this, NoteScreenActivity.class);
            startActivity(myIntent);
        } else {
            //go to activity_main layout
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("loadHome", true);
            startActivity(intent);
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.loading_screen);

        ConstraintLayout screenLayout = findViewById(R.id.screenLayout);
        screenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
                handler.removeMessages(0);
            }
        });

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goNext();
            }
        }, 1000);

    }
}
