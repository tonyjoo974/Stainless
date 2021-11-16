package edu.illinois.cs465.stainless.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import edu.illinois.cs465.stainless.R;
import edu.illinois.cs465.stainless.bean.Inventory;

import java.util.List;


public class InventoryAdapterHave extends BaseRecyclerViewAdapter<Inventory> {

    private OnDeleteClickLister mDeleteClickListener;

    public InventoryAdapterHave(Context context, List<Inventory> data) {
        super(context, data, R.layout.item_inventory);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, Inventory bean, int position) {
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
        ((TextView) holder.getView(R.id.name)).setText(bean.getItemDesc());
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
}
