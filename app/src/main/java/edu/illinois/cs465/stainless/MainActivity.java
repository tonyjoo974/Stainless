package edu.illinois.cs465.stainless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    Fragment curr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        // Check if app is just opening up
        Bundle extras = getIntent().getExtras();
        Log.d("123", String.valueOf(extras));
        if (extras == null) {
            Intent myIntent = new Intent(this, LoadingScreenActivity.class);
            startActivity(myIntent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        // Begin with Home Screen
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        // Instantiate BottomNavigation
        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
    }


    // Switch tabs when bottom navigation selected
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment;
        if (item.getItemId() == R.id.nav_inventory) {
            selectedFragment = new InventoryFragment();
        } else if (item.getItemId() == R.id.nav_favorites) {
            selectedFragment = new FavoritesFragment();
        } else {
            selectedFragment = new HomeFragment();
        }
        curr = selectedFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    }
    public void onResume() {

        if(curr == null) {
            super.onResume();
            return;

        }
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(curr);
        ft.attach(curr);
        ft.commit();

        super.onResume();

    }
    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        if(curr == null) {
            super.onBackPressed();  // optional depending on your needs
            return;

        }
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(curr);
        ft.attach(curr);
        ft.commit();
        super.onBackPressed();  // optional depending on your needs

    }

}