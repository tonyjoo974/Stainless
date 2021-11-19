package edu.illinois.cs465.stainless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Stain_Activity extends AppCompatActivity {

    private TextView title;
    private ImageView img;
    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stain);


        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        title = (TextView) findViewById(R.id.txtName);
        img = (ImageView) findViewById(R.id.stainThumbnail);

        // Receive data
        Intent intent = getIntent();
        String stainName = intent.getExtras().getString("StainName");
        int image = intent.getExtras().getInt("Thumbnail");

        // Add to Favorite
        toggleButton = (ToggleButton) findViewById(R.id.myToggleButton);
        toggleButton.setChecked(RecyclerFavoritesViewAdapter.alreadyFavorited(stainName));
        if (toggleButton.isChecked()) {
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_yellow));
        } else {
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_grey));
        }
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_yellow));
                    if(!RecyclerFavoritesViewAdapter.alreadyFavorited(stainName)) {
                        RecyclerFavoritesViewAdapter.addFavoriteStain(new Stain(stainName, null, image));
                        Toast.makeText(getApplication(), "Added to Favorites!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_grey));
                    RecyclerFavoritesViewAdapter.removeFavoriteStain(stainName);
                    Toast.makeText(getApplication(), "Removed from Favorites!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        title.setText(stainName);
        img.setImageResource(image);


    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void washable_fabric_sol(View v){
        startActivity(new Intent(Stain_Activity.this, Solution_Activity.class));
    }

    public void carpet_sol(View v){
        startActivity(new Intent(Stain_Activity.this, Solution_Activity.class));
    }

    public void furniture_sol(View v){
        startActivity(new Intent(Stain_Activity.this, Solution_Activity.class));
    }
}