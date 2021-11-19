package edu.illinois.cs465.stainless;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs465.stainless.adapter.InventoryAdapterHave;
import edu.illinois.cs465.stainless.adapter.InventoryAdapterNotHave;

public class InventoryFragment extends Fragment {
    View view;
    private SlideRecyclerView recycler_view_list1;
    private SlideRecyclerView recycler_view_list2;
    private List<Material> mInventoriesHave;
    private List<Material> mInventoriesNotHave;
    private InventoryAdapterHave mInventoryAdapterHave;
    private InventoryAdapterNotHave mInventoryAdapterNotHave;
    private Button btnHave;
    private Button btnNotHave;
    Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_recyclerview, container, false);
        mContext = inflater.getContext();

        ArrayList<Material> materials = new ArrayList<>();
        // isInStock will only be initialized ONCE. See setIsInStock().
        for (int i = 0; i < 1; i++) {
            materials.add(new Material("White Vinegar", R.drawable.white_vinegar, mContext));
            materials.add(new Material("Rubbing alcohol", R.drawable.rubbing_alcohol, mContext, true));
            materials.add(new Material("Enzyme presoak", R.drawable.enzyme_presoak, mContext));
            materials.add(new Material("Chlorine bleach or oxygen bleach", R.drawable.bleach, mContext, true));
//            materials.add(new Material("Eye Shadow", R.drawable.eye_shadow, mContext));
//            materials.add(new Material("Fabric Dye", R.drawable.fabric_dye, mContext, true));
//            materials.add(new Material("Iodine", R.drawable.iodine, mContext));
//            materials.add(new Material("Mustard", R.drawable.mustard, mContext, true));
//            materials.add(new Material("Pudding", R.drawable.pudding, mContext));
//            materials.add(new Material("Soft Drinks", R.drawable.soft_drinks, mContext, true));
        }

        btnHave = view.findViewById(R.id.btnHave);
        btnNotHave = view.findViewById(R.id.btnNotHave);

        recycler_view_list1 = (SlideRecyclerView) view.findViewById(R.id.recycler_view_list1);
        recycler_view_list1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.divider_inset));
        recycler_view_list1.addItemDecoration(itemDecoration);
        mInventoriesHave = new ArrayList<>(materials);
        mInventoriesHave.removeIf(material -> !material.isInStock());
//        for (int i = 0; i < 5; i++) {
//            Material inventory = new Inventory();
//            inventory.setItemDesc("test have " + i);
//            mInventoriesHave.add(inventory);
//        }

        mInventoryAdapterHave = new InventoryAdapterHave(mContext, mInventoriesHave);
        recycler_view_list1.setAdapter(mInventoryAdapterHave);
        //have
        mInventoryAdapterHave.setOnDeleteClickListener(new InventoryAdapterHave.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                Material material = mInventoriesHave.remove(position);
                mInventoriesNotHave.add(material);
                material.setIsInStock(false, true);
                mInventoryAdapterHave.notifyDataSetChanged();
                mInventoryAdapterNotHave.notifyDataSetChanged();
                recycler_view_list1.closeMenu();
            }
        });


        recycler_view_list2 = (SlideRecyclerView) view.findViewById(R.id.recycler_view_list2);
        recycler_view_list2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.divider_inset));
        recycler_view_list2.addItemDecoration(itemDecoration);
        mInventoriesNotHave = new ArrayList<>(materials);
        mInventoriesNotHave.removeIf(material -> material.isInStock());
//        for (int i = 0; i < 5; i++) {
//            Inventory inventory = new Inventory();
//            inventory.setItemDesc("test Not Have" + i);
//            mInventoriesNotHave.add(inventory);
//        }

        mInventoryAdapterNotHave = new InventoryAdapterNotHave(mContext, mInventoriesNotHave);
        recycler_view_list2.setAdapter(mInventoryAdapterNotHave);

        //not have
        mInventoryAdapterNotHave.setOnDeleteClickListener(new InventoryAdapterNotHave.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                Material material = mInventoriesNotHave.remove(position);
                mInventoriesHave.add(material);
                material.setIsInStock(true, true);
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
