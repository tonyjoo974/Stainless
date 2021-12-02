package edu.illinois.cs465.stainless.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.illinois.cs465.stainless.BottomSheetFragment;
import edu.illinois.cs465.stainless.Material;
import edu.illinois.cs465.stainless.Stain_Activity;

/**
 * RecyclerView adapter基类
 * 封装了数据集合以及ItemView的点击事件回调,同时暴露 {@link #onBindData(RecyclerViewHolder, Object, int)}
 * 用于数据与view绑定
 */

abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<T> mData;
    private int mLayoutId;

    private OnItemClickListener mListener;

    BaseRecyclerViewAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mData = data;
        this.mLayoutId = layoutId;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.itemView.setTag(position);
        T bean = mData.get(position);
        onBindData(holder, bean, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send data to BottomSheetFragment and Trigger Modal View
                Material clickedMaterial = (Material) bean;
                Bundle data = new Bundle();
                data.putString("materialName", clickedMaterial.getName());
                data.putInt("thumbnail", clickedMaterial.getThumbnail());
                if (clickedMaterial.isInStock()) {
                    data.putString("stockStatus", "In Stock");
                } else {
                    data.putString("stockStatus", "Out Of Stock");
                }
                BottomSheetFragment dialog = BottomSheetFragment.newInstance();
                dialog.show(((FragmentActivity)mContext).getSupportFragmentManager(), "modal_activity");
                dialog.setArguments(data);
                dialog.disablePeek();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(this, v, (Integer) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    /**
     * 数据绑定，由实现类实现
     *
     * @param holder   The reference of the all view within the item.
     * @param bean     The data bean related to the position.
     * @param position The position to bind data.
     */
    protected abstract void onBindData(RecyclerViewHolder holder, T bean, int position);

    /**
     * item点击监听器
     */
    public interface OnItemClickListener {
        /**
         * item点击回调
         *
         * @param adapter  The Adapter where the click happened.
         * @param v        The view that was clicked.
         * @param position The position of the view in the adapter.
         */
        void onItemClick(RecyclerView.Adapter adapter, View v, int position);
    }
}
