package edu.illinois.cs465.stainless;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Solution_Activity_Furniture extends AppCompatActivity {
    View modalViewObj = null;
    ArrayList<Material> materials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_furniture);


        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // mock material
        materials = new ArrayList<>();
        materials.add(new Material("White Vinegar", R.drawable.white_vinegar, this));
        materials.add(new Material("Rubbing alcohol", R.drawable.rubbing_alcohol, this, true));
        materials.add(new Material("Enzyme presoak", R.drawable.enzyme_presoak, this));

        ArrayList<LinearLayout> materialLayouts = new ArrayList();
        materialLayouts.add(findViewById(R.id.material1));
        materialLayouts.add(findViewById(R.id.material2));
        materialLayouts.add(findViewById(R.id.material3));

        for (int i = 0; i < 3; i++) {
            ((ImageView) materialLayouts.get(i).getChildAt(0)).setImageResource(materials.get(i).getThumbnail());
            ((TextView) materialLayouts.get(i).getChildAt(1)).setText(materials.get(i).getName());
        }

        LinearLayout material1 = findViewById(R.id.material1);
        LinearLayout material2 = findViewById(R.id.material2);
        LinearLayout material3 = findViewById(R.id.material3);

        material1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passDataToBottomSheet(0);
            }
        });

        material2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passDataToBottomSheet(1);
            }
        });

        material3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passDataToBottomSheet(2);
            }
        });
    }

    public void passDataToBottomSheet(int position){
        BottomSheetFragment dialog = BottomSheetFragment.newInstance();
        dialog.show(getSupportFragmentManager(), "modal_activity");
        Bundle data = new Bundle();
        data.putString("materialName", materials.get(position).getName());
        data.putInt("thumbnail", materials.get(position).getThumbnail());
        if (materials.get(position).isInStock()) {
            data.putString("stockStatus", "In Stock");
        } else {
            data.putString("stockStatus", "Out Of Stock");
        }
        dialog.setArguments(data);
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

