package edu.illinois.cs465.stainless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] spinnerTitles;
    int[] spinnerImages;
    boolean isUserInteracting = false;
    boolean categoryView = false;
    View searchBar = null;
    View recyclerViewObj = null;
    View spinnerObj = null;
    LinearLayout contentSpace = null;

    private void resetView() {
        contentSpace.removeAllViews();
        if (categoryView) {
            contentSpace.addView(spinnerObj);
        } else {
            contentSpace.addView(searchBar);
            contentSpace.addView(recyclerViewObj);
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        isUserInteracting = true;
    }

    List<Stain> stains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stains = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            stains.add(new Stain("Apple", "fruit", R.drawable.apple));
            stains.add(new Stain("Banana", "fruit", R.drawable.banana));
            stains.add(new Stain("Curry", "food", R.drawable.curry));
            stains.add(new Stain("Dyes", "other", R.drawable.dyes));
            stains.add(new Stain("Eye Shadow", "other", R.drawable.eye_shadow));
            stains.add(new Stain("Fabric Dye", "other", R.drawable.fabric_dye));
            stains.add(new Stain("Iodine", "other", R.drawable.iodine));
            stains.add(new Stain("Mustard", "other", R.drawable.mustard));
            stains.add(new Stain("Pudding", "other", R.drawable.pudding));
            stains.add(new Stain("Soft Drinks", "other", R.drawable.soft_drinks));
        }

        this.contentSpace = findViewById(R.id.contentView);
        this.searchBar = getLayoutInflater().inflate(R.layout.search_bar, null);
        this.recyclerViewObj = getLayoutInflater().inflate(R.layout.recycler_view, null);
        this.spinnerObj = getLayoutInflater().inflate(R.layout.spinner, null);

        RecyclerView recyclerView = recyclerViewObj.findViewById(R.id.recyclerView);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, stains);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(myAdapter);

        // Category
        Spinner spinner = spinnerObj.findViewById(R.id.category_spinner);

        CategorySpinnerAdapter customAdapter = new CategorySpinnerAdapter(MainActivity.this, "Food", stains, spinner);
        spinner.setAdapter(customAdapter);

        // Get button
        Button aToZButton = (Button) findViewById(R.id.aToZ_but_id);
        Button categoryButton = (Button) findViewById(R.id.category_but_id);
        aToZButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (categoryView) {
                    categoryView = false;
                    resetView();
                }
            }
        });

        categoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!categoryView) {
                    categoryView = true;
                    resetView();
                }
            }
        });

        resetView();
    }




}