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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Solution_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);


        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // mock material
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(new Material("Apple", R.drawable.apple, this));
        materials.add(new Material("Banana", R.drawable.banana, this, true));
        materials.add(new Material("Curry", R.drawable.curry, this));

        ArrayList<LinearLayout> materialLayouts = new ArrayList();
        materialLayouts.add(findViewById(R.id.material1));
        materialLayouts.add(findViewById(R.id.material2));
        materialLayouts.add(findViewById(R.id.material3));

        for (int i = 0; i < 3; i++) {
            ((ImageView) materialLayouts.get(i).getChildAt(0)).setImageResource(materials.get(i).getThumbnail());
            ((TextView) materialLayouts.get(i).getChildAt(1)).setText(materials.get(i).getName());
        }
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




}

