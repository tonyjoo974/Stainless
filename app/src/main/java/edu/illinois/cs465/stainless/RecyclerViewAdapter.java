package edu.illinois.cs465.stainless;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Stain> stains;
    private ArrayList<Stain> temp;
    private List<String> stainList;
    private static int CUTOFF = 60;

    public RecyclerViewAdapter(Context mContext, List<Stain> stains, List<String> concatString) {
        this.mContext = mContext;
        this.stains = stains;
        this.temp = new ArrayList<>();
        this.temp.addAll(stains);
        this.stainList = concatString;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_stains, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.stain_name.setText(stains.get(position).getName());
        holder.img_stain_thumbnail.setImageResource(stains.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // segue to clicked page
                Intent intent = new Intent(mContext, Stain_Activity.class);
                intent.putExtra("StainName", stains.get(position).getName());
                intent.putExtra("Thumbnail", stains.get(position).getThumbnail());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stains.size();
    }

    // Filter search results with FuzzyWuzzy
    // Reference: https://github.com/xdrop/fuzzywuzzy
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filter(String newText) {
        List<ExtractedResult> result;
        List<String> arrOfStr = new ArrayList<>();
        List<String> stainName;
        List<String> extractedNames;

        stains.clear();
        if (newText == null || newText.isEmpty()){
            stains.addAll(temp);
        } else {
            // You can adjust CUTOFF value. Currently set to 60.
            result = FuzzySearch.extractAll(newText, stainList, CUTOFF);
//            System.out.println(result);
            for (ExtractedResult x : result) {
                stainName = Arrays.asList(x.getString().split(" "));
                extractedNames = stainName.subList(0, stainName.size() - 1);
                arrOfStr.add(String.join(" ", extractedNames));
            }
//            System.out.println(arrOfStr);
            for (Stain stain : temp) {
                if (arrOfStr.contains(stain.getName().toLowerCase())){
                    stains.add(stain);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView stain_name;
        ImageView img_stain_thumbnail;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            stain_name = (TextView) itemView.findViewById(R.id.stain_name_id);
            img_stain_thumbnail = (ImageView) itemView.findViewById((R.id.stain_img_id));
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}