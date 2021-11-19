package edu.illinois.cs465.stainless;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener{
    View view;
    boolean categoryView = false;
    View searchBar = null;
    View recyclerViewObj = null;
    View spinnerFood = null;
    View spinnerFruit = null;
    View spinnerOther = null;
    LinearLayout contentSpace = null;
    List<Stain> stains;
    List<Material> materials;
    List<String> stainList;
    RecyclerViewAdapter myAdapter;
    SearchView editSearch;
    String prevUserInput;
    Context mContext;

    private void resetView() {
        contentSpace.removeAllViews();
        if (categoryView) {
            contentSpace.addView(spinnerFood);
            contentSpace.addView(spinnerFruit);
            contentSpace.addView(spinnerOther);
        } else {
            contentSpace.addView(searchBar);
            contentSpace.addView(recyclerViewObj);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = inflater.getContext();

        this.stains = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            stains.add(new Stain("Apple", "fruit", R.drawable.apple));
            stains.add(new Stain("Banana", "fruit", R.drawable.banana));
            stains.add(new Stain("Curry", "food", R.drawable.curry));
            stains.add(new Stain("Dyes", "other", R.drawable.dyes));
            stains.add(new Stain("Eye Shadow", "other", R.drawable.eye_shadow));
            stains.add(new Stain("Fabric Dye", "other", R.drawable.fabric_dye));
            stains.add(new Stain("Iodine", "other", R.drawable.iodine));
            stains.add(new Stain("Mustard", "food", R.drawable.mustard));
            stains.add(new Stain("Pudding", "food", R.drawable.pudding));
            stains.add(new Stain("Soft Drinks", "beverage", R.drawable.soft_drinks));
        }

        // Pre-processing for fuzzy search
        this.stainList = new ArrayList<>();
        for (Stain stain : stains) {
            this.stainList.add(stain.getName().toLowerCase() + " " + stain.getCategory().toLowerCase());
        }

        this.contentSpace = view.findViewById(R.id.contentView);
        this.searchBar = getLayoutInflater().inflate(R.layout.search_bar, null);
        this.recyclerViewObj = getLayoutInflater().inflate(R.layout.recycler_view, null);
        this.spinnerFood = getLayoutInflater().inflate(R.layout.spinner, null);
        this.spinnerFruit = getLayoutInflater().inflate(R.layout.spinner, null);
        this.spinnerOther = getLayoutInflater().inflate(R.layout.spinner, null);

        // Locate the RecyclerView in recycler_view.xml
        RecyclerView recyclerView = recyclerViewObj.findViewById(R.id.recyclerView);
        // Pass the stains to RecyclerViewAdapter Class
        this.myAdapter = new RecyclerViewAdapter(mContext, stains, stainList);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        // Bind the Adapter to RecyclerView
        recyclerView.setAdapter(this.myAdapter);

        // Category
        Spinner spinnerFoodView = spinnerFood.findViewById(R.id.category_spinner);
        Spinner spinnerFruitView = spinnerFruit.findViewById(R.id.category_spinner);
        Spinner spinnerOtherView = spinnerOther.findViewById(R.id.category_spinner);

        List<Stain> foodStains = new ArrayList<>(stains);
        foodStains.removeIf(stain -> !stain.getCategory().equals("food"));
        List<Stain> fruitStains = new ArrayList<>(stains);
        fruitStains.removeIf(stain -> !stain.getCategory().equals("fruit"));
        List<Stain> otherStains = new ArrayList<>(stains);
        otherStains.removeIf(stain -> !stain.getCategory().equals("other"));

        CategorySpinnerAdapter customAdapterFood = new CategorySpinnerAdapter(mContext, String.format(Locale.getDefault(), "%s (%d)", "Food", foodStains.size()), foodStains, spinnerFoodView);
        spinnerFoodView.setAdapter(customAdapterFood);
        CategorySpinnerAdapter customAdapterFruit = new CategorySpinnerAdapter(mContext, String.format(Locale.getDefault(), "%s (%d)", "Fruit", fruitStains.size()), fruitStains, spinnerFoodView);
        spinnerFruitView.setAdapter(customAdapterFruit);
        CategorySpinnerAdapter customAdapterOther = new CategorySpinnerAdapter(mContext, String.format(Locale.getDefault(), "%s (%d)", "Other", otherStains.size()), otherStains, spinnerFoodView);
        spinnerOtherView.setAdapter(customAdapterOther);

        // Get button
        Button aToZButton = (Button) view.findViewById(R.id.aToZ_but_id);
        Button categoryButton = (Button) view.findViewById(R.id.category_but_id);
        ImageView noteButton = view.findViewById(R.id.login_button);

        aToZButton.setOnClickListener(v -> {
            if (categoryView) {
                categoryView = false;
                resetView();
                // retrieve previous search text
                onQueryTextChange(this.prevUserInput);
            }
        });

        categoryButton.setOnClickListener(v -> {
            if (!categoryView) {
                categoryView = true;
                resetView();
                // set search text to null to show all stains
                myAdapter.filter("");
            }
        });

        noteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NoteScreenActivity.class);
                mContext.startActivity(intent);
            }
        });

        resetView();

        // Search bar initialization
        editSearch = this.searchBar.findViewById(R.id.search);
        editSearch.setOnQueryTextListener(this);

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onQueryTextChange(String newText) {
        this.prevUserInput = newText;
        this.myAdapter.filter(newText);
        return false;
    }
}
