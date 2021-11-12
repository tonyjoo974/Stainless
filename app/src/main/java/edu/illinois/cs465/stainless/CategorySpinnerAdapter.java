package edu.illinois.cs465.stainless;

import android.content.Context;
import android.content.Intent;
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

public class CategorySpinnerAdapter extends ArrayAdapter {
    String categoryName;
    String[] stainNames;
    int[] stainImages;
    int numStainRows;
    Spinner spinner;
    Context mContext;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, ((TextView)v.findViewById(R.id.tvName)).getText(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, MainActivity.class);
            mContext.startActivity(intent);

            Method method = null;
            try {
                method = Spinner.class.getDeclaredMethod("onDetachedFromWindow");
                method.setAccessible(true);
                method.invoke(spinner);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    };

    private static class ViewHolder {
        ImageView mImage;
        TextView mName;
    }

    public CategorySpinnerAdapter(@NonNull Context context, String[] names, int[] images, Spinner spinner) {
        super(context, R.layout.category_spinner_row);
        this.categoryName = names[0];
        this.stainNames = Arrays.copyOfRange(names, 1, names.length);
        this.numStainRows = (int) Math.ceil((double)this.stainNames.length / 3);
        this.stainImages = images;
        this.spinner = spinner;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return numStainRows + 1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (position == 0) {
            Log.d("0", String.valueOf(position));
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.category, parent, false);
            TextView title = convertView.findViewById(R.id.categoryName);
            title.setText(this.categoryName);

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

                mViewHolder0.mImage = stain0.findViewById(R.id.ivImg);
                mViewHolder0.mName = stain0.findViewById(R.id.tvName);
                mViewHolder1.mImage = stain1.findViewById(R.id.ivImg);
                mViewHolder1.mName = stain1.findViewById(R.id.tvName);
                mViewHolder2.mImage = stain2.findViewById(R.id.ivImg);
                mViewHolder2.mName = stain2.findViewById(R.id.tvName);
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
                if (position * 3 + i < this.stainNames.length) {
                    allHolders[i].mImage.setImageResource(this.stainImages[position * 3 + i]);
                    allHolders[i].mName.setText(this.stainNames[position * 3 + i]);
                } else {
                    ViewGroup stainParent = (ViewGroup) allHolders[i].mImage.getParent();
                    stainParent.removeView(allHolders[i].mImage);
                    stainParent.removeView(allHolders[i].mName);
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
