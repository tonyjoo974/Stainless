package edu.illinois.cs465.stainless;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs465.stainless.adapter.InventoryAdapterHave;
import edu.illinois.cs465.stainless.adapter.InventoryAdapterNotHave;
import edu.illinois.cs465.stainless.bean.Inventory;

public class InventoryFragment extends Fragment {
    View view;
    private SlideRecyclerView recycler_view_list1;
    private SlideRecyclerView recycler_view_list2;
    private List<Inventory> mInventoriesHave;
    private List<Inventory> mInventoriesNotHave;
    private InventoryAdapterHave mInventoryAdapterHave;
    private InventoryAdapterNotHave mInventoryAdapterNotHave;
    private Button btnHave;
    private Button btnNotHave;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_recyclerview, container, false);
        mContext = inflater.getContext();

        btnHave = view.findViewById(R.id.btnHave);
        btnNotHave = view.findViewById(R.id.btnNotHave);

        recycler_view_list1 = (SlideRecyclerView) view.findViewById(R.id.recycler_view_list1);
        recycler_view_list1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.divider_inset));
        recycler_view_list1.addItemDecoration(itemDecoration);
        mInventoriesHave = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Inventory inventory = new Inventory();
            inventory.setItemDesc("test have " + i);
            mInventoriesHave.add(inventory);
        }
        mInventoryAdapterHave = new InventoryAdapterHave(mContext, mInventoriesHave);
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


        recycler_view_list2 = (SlideRecyclerView) view.findViewById(R.id.recycler_view_list2);
        recycler_view_list2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.divider_inset));
        recycler_view_list2.addItemDecoration(itemDecoration);
        mInventoriesNotHave = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Inventory inventory = new Inventory();
            inventory.setItemDesc("test Not Have" + i);
            mInventoriesNotHave.add(inventory);
        }
        mInventoryAdapterNotHave = new InventoryAdapterNotHave(mContext, mInventoriesNotHave);
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
        return view;
    }
}
