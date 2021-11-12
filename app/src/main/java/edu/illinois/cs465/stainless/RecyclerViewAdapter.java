package edu.illinois.cs465.stainless;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Stain> mData;

    public RecyclerViewAdapter(Context mContext, List<Stain> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_stains, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.stain_name.setText(mData.get(position).getName());
        holder.img_stain_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // segue to clicked page
                Intent intent = new Intent(mContext, Stain_Activity.class);
                intent.putExtra("StainName:", mData.get(position).getName());
                intent.putExtra("Thumbnail:", mData.get(position).getThumbnail());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
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
