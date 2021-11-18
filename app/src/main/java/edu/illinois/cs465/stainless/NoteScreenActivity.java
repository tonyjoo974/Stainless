package edu.illinois.cs465.stainless;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class NoteScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_screen);

        LinearLayout screenLayout = findViewById(R.id.screenLayout);
        screenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to activity_main layout
                Intent intent = new Intent(NoteScreenActivity.this, MainActivity.class);
                intent.putExtra("loadHome", true);
                startActivity(intent);
                finish();
            }
        });

    }
}
