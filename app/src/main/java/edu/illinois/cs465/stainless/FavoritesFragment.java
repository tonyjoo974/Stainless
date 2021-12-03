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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavoritesFragment extends Fragment {
    View view;
    View searchbar = null;
    View recyclerViewObj = null;
    Context mContext;
    RecyclerFavoritesViewAdapter myAdapter;
    LinearLayout contentSpace = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);
        mContext = inflater.getContext();

        this.recyclerViewObj = getLayoutInflater().inflate(R.layout.recycler_view, null);

        RecyclerView recyclerView = this.recyclerViewObj.findViewById(R.id.recyclerView);

        this.myAdapter = new RecyclerFavoritesViewAdapter(mContext);
        recyclerView.setAdapter(this.myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));


        this.contentSpace = view.findViewById(R.id.contentView);
        this.contentSpace.removeAllViews();

        this.contentSpace.addView(this.recyclerViewObj);
        return view;
    }

}
