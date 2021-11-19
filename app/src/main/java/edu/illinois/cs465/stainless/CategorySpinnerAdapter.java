package edu.illinois.cs465.stainless;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter {
    String categoryName;
    List<Stain> stains;
    int numStainRows;
    Spinner spinner;
    Context mContext;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Method method = null;
            try {
                method = Spinner.class.getDeclaredMethod("onDetachedFromWindow");
                method.setAccessible(true);
                method.invoke(spinner);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }


            Intent intent = new Intent(mContext, Stain_Activity.class);
            int position = (int)v.getTag();
            Log.d("Click", String.valueOf(position));
            intent.putExtra("StainName", stains.get(position).getName());
            intent.putExtra("Thumbnail", stains.get(position).getThumbnail());
            mContext.startActivity(intent);
        }
    };

    private static class ViewHolder {
        ImageView mImage;
        TextView mName;
        RelativeLayout root;
    }

    public CategorySpinnerAdapter(@NonNull Context context, String categoryName, List<Stain> stains, Spinner spinner) {
        super(context, R.layout.category_spinner_row);
        this.categoryName = categoryName;
        this.stains = stains;
        this.numStainRows = (int) Math.ceil((double)this.stains.size() / 3);
        this.spinner = spinner;
        this.mContext = context;
    }

    @Override
    public boolean isEnabled(int position) {
        return position == 0;
    }

    @Override
    public int getCount() {
        return numStainRows + 1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (position == 0) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.category, parent, false);
            TextView title = convertView.findViewById(R.id.categoryName);
            title.setText(this.categoryName);
            title.setTextSize(20);

            convertView.setTag("title");
            return convertView;
        } else {
            position -= 1;
            ViewHolder mViewHolder0 = new ViewHolder();
            ViewHolder mViewHolder1 = new ViewHolder();
            ViewHolder mViewHolder2 = new ViewHolder();

            if (convertView == null || convertView.getTag() != "stains") {
                Log.d("row", String.valueOf(position));
                LayoutInflater mInflater = (LayoutInflater) mContext.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.category_spinner_row, parent, false);

                RelativeLayout stain0 = convertView.findViewById(R.id.stain0);
                RelativeLayout stain1 = convertView.findViewById(R.id.stain1);
                RelativeLayout stain2 = convertView.findViewById(R.id.stain2);
                stain0.setOnClickListener(listener);
                stain1.setOnClickListener(listener);
                stain2.setOnClickListener(listener);
                stain0.setTag(position * 3 + 0);
                stain1.setTag(position * 3 + 1);
                stain2.setTag(position * 3 + 2);

                mViewHolder0.mImage = stain0.findViewById(R.id.stain_img_id);
                mViewHolder0.mName = stain0.findViewById(R.id.stain_name_id);
                mViewHolder0.root = stain0;
                mViewHolder1.mImage = stain1.findViewById(R.id.stain_img_id);
                mViewHolder1.mName = stain1.findViewById(R.id.stain_name_id);
                mViewHolder1.root = stain1;
                mViewHolder2.mImage = stain2.findViewById(R.id.stain_img_id);
                mViewHolder2.mName = stain2.findViewById(R.id.stain_name_id);
                mViewHolder2.root = stain2;
                convertView.setTag(R.id.stain0, mViewHolder0);
                convertView.setTag(R.id.stain1, mViewHolder1);
                convertView.setTag(R.id.stain2, mViewHolder2);
            } else {
                Log.d("row", "exist");
                mViewHolder0 = (ViewHolder) convertView.getTag(R.id.stain0);
                mViewHolder1 = (ViewHolder) convertView.getTag(R.id.stain1);
                mViewHolder2 = (ViewHolder) convertView.getTag(R.id.stain2);
            }
            convertView.setTag("stains");
            ViewHolder[] allHolders = {mViewHolder0, mViewHolder1, mViewHolder2};
            for (int i = 0; i < 3; i++) {
                if (position * 3 + i < this.stains.size()) {
                    allHolders[i].mImage.setImageResource(this.stains.get(position * 3 + i).getThumbnail());
                    allHolders[i].mName.setText(this.stains.get(position * 3 + i).getName());
                } else {
                    ViewGroup stainParent = (ViewGroup) allHolders[i].root.getParent();
                    stainParent.removeView(allHolders[i].root);
                    convertView.setTag("stains-partial");
                }
            }

            return convertView;
        }
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}