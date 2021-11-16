package edu.illinois.cs465.stainless;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

//warning interface should be tap to continue
//loading screen activity should just appear for 1 sec

public class LoadingScreenActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to activity_main layout
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
