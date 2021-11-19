package edu.illinois.cs465.stainless;

import android.content.Context;
import android.util.Log;

public class Material {

    private String name;
    private int thumbnail;
    private Context context;

    public Material(String name, int thumbnail, Context context) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.context = context;
    }

    public Material(String name, int thumbnail, Context context, boolean isInStock) {
        this(name, thumbnail, context);
        setIsInStock(isInStock, false);
    }

    public String getName() {
        return name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumbnail(int thumbnail) { this.thumbnail = thumbnail;}

    public boolean isInStock() {
        return context.getSharedPreferences("STOCK", Context.MODE_PRIVATE)
                .getBoolean(getName(), false);
    }

    public void setIsInStock(boolean isInStock, boolean overwrite) {
        if (overwrite || !context.getSharedPreferences("STOCK", Context.MODE_PRIVATE).contains(getName())) {
            Log.d("setIsInStock", getName());
            context.getSharedPreferences("STOCK", Context.MODE_PRIVATE).edit()
                    .putBoolean(getName(), isInStock).apply();
        }
    }
}
