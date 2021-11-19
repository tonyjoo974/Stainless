package edu.illinois.cs465.stainless.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.illinois.cs465.stainless.Material;
import edu.illinois.cs465.stainless.R;

import java.util.List;


public class InventoryAdapterNotHave extends BaseRecyclerViewAdapter<Material> {

    private OnDeleteClickLister mDeleteClickListener;

    public InventoryAdapterNotHave(Context context, List<Material> data) {
        super(context, data, R.layout.item_inventory_not);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, Material material, int position) {
        View view = holder.getView(R.id.tv_delete);
        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        ((TextView) holder.getView(R.id.name)).setText(material.getName());
        ((ImageView) holder.getView(R.id.icon)).setImageResource(material.getThumbnail());
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
}
