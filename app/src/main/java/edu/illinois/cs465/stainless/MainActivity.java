package edu.illinois.cs465.stainless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    String[] spinnerTitles;
    int[] spinnerImages;
    boolean isUserInteracting = false;
    boolean categoryView = false;
    View searchBar = null;
    View recyclerViewObj = null;
    View spinnerObj = null;
    LinearLayout contentSpace = null;
    List<Stain> stains;
    RecyclerViewAdapter myAdapter;
    SearchView editSearch;
    String prevUserInput;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.stains = new ArrayList<>();
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

        // Locate the RecyclerView in recycler_view.xml
        RecyclerView recyclerView = recyclerViewObj.findViewById(R.id.recyclerView);
        // Pass the stains to RecyclerViewAdapter Class
        this.myAdapter = new RecyclerViewAdapter(this, stains);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        // Bind the Adapter to RecyclerView
        recyclerView.setAdapter(this.myAdapter);

        // Category
        Spinner spinner = spinnerObj.findViewById(R.id.category_spinner);

        CategorySpinnerAdapter customAdapter = new CategorySpinnerAdapter(MainActivity.this, "Food", stains, spinner);
        spinner.setAdapter(customAdapter);

        // Get button
        Button aToZButton = (Button) findViewById(R.id.aToZ_but_id);
        Button categoryButton = (Button) findViewById(R.id.category_but_id);
        aToZButton.setOnClickListener(v -> {
            if (categoryView) {
                categoryView = false;
                resetView();
                // retrieve previous search text
                onQueryTextChange(this.prevUserInput);
            }
        });

        categoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!categoryView) {
                    categoryView = true;
                    resetView();
                    // set search text to null to show all stains
                    myAdapter.filter("");
                }
            }
        });
        resetView();

        // Search bar initialization
        editSearch = findViewById(R.id.search);
        editSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        this.prevUserInput = newText;
        this.myAdapter.filter(newText);
        return false;
    }
}