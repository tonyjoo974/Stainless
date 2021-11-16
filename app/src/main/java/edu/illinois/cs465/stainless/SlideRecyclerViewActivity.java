package edu.illinois.cs465.stainless;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import edu.illinois.cs465.stainless.R;
import edu.illinois.cs465.stainless.adapter.InventoryAdapterHave;
import edu.illinois.cs465.stainless.adapter.InventoryAdapterNotHave;
import edu.illinois.cs465.stainless.bean.Inventory;
import edu.illinois.cs465.stainless.SlideRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class SlideRecyclerViewActivity extends AppCompatActivity {

    private SlideRecyclerView recycler_view_list1;
    private SlideRecyclerView recycler_view_list2;
    private List<Inventory> mInventoriesHave;
    private List<Inventory> mInventoriesNotHave;
    private InventoryAdapterHave mInventoryAdapterHave;
    private InventoryAdapterNotHave mInventoryAdapterNotHave;
    private Button btnHave;
    private Button btnNotHave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        btnHave = findViewById(R.id.btnHave);
        btnNotHave = findViewById(R.id.btnNotHave);

        recycler_view_list1 = (SlideRecyclerView) findViewById(R.id.recycler_view_list1);
        recycler_view_list1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        recycler_view_list1.addItemDecoration(itemDecoration);
        mInventoriesHave = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Inventory inventory = new Inventory();
            inventory.setItemDesc("test have " + i);
            mInventoriesHave.add(inventory);
        }
        mInventoryAdapterHave = new InventoryAdapterHave(this, mInventoriesHave);
        recycler_view_list1.setAdapter(mInventoryAdapterHave);
        //have
        mInventoryAdapterHave.setOnDeleteClickListener(new InventoryAdapterHave.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                Inventory inventory = mInventoriesHave.remove(position);
                mInventoriesNotHave.add(inventory);
                mInventoryAdapterHave.notifyDataSetChanged();
                mInventoryAdapterNotHave.notifyDataSetChanged();
                recycler_view_list1.closeMenu();
            }
        });


        recycler_view_list2 = (SlideRecyclerView) findViewById(R.id.recycler_view_list2);
        recycler_view_list2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        recycler_view_list2.addItemDecoration(itemDecoration);
        mInventoriesNotHave = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Inventory inventory = new Inventory();
            inventory.setItemDesc("test Not Have" + i);
            mInventoriesNotHave.add(inventory);
        }
        mInventoryAdapterNotHave = new InventoryAdapterNotHave(this, mInventoriesNotHave);
        recycler_view_list2.setAdapter(mInventoryAdapterNotHave);

        //not have
        mInventoryAdapterNotHave.setOnDeleteClickListener(new InventoryAdapterNotHave.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                Inventory inventory = mInventoriesNotHave.remove(position);
                mInventoriesHave.add(inventory);
                mInventoryAdapterNotHave.notifyDataSetChanged();
                mInventoryAdapterHave.notifyDataSetChanged();
                recycler_view_list2.closeMenu();
            }
        });

        btnHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycler_view_list1.setVisibility(View.VISIBLE);
                recycler_view_list2.setVisibility(View.GONE);
            }
        });

        btnNotHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycler_view_list1.setVisibility(View.GONE);
                recycler_view_list2.setVisibility(View.VISIBLE);
            }
        });
    }
}
