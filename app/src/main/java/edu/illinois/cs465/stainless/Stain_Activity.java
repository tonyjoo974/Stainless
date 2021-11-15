package edu.illinois.cs465.stainless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Stain_Activity extends AppCompatActivity {

    private TextView title;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stain);

        title = (TextView) findViewById(R.id.txtName);
        img = (ImageView) findViewById(R.id.stainThumbnail);

        // Receive data
        Intent intent = getIntent();
        String stainName = intent.getExtras().getString("StainName");
        int image = intent.getExtras().getInt("Thumbnail");

        title.setText(stainName);
        img.setImageResource(image);
    }
}