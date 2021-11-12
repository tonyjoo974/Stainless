package edu.illinois.cs465.stainless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Stain> stains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stains = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, stains);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(myAdapter);
    }


}