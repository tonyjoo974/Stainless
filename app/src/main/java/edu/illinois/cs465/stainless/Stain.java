package edu.illinois.cs465.stainless;

public class Stain {

    private String Name;
    private String Category;
    private int Thumbnail;

    public Stain(String name, String category, int thumbnail) {
        Name = name;
        Category = category;
        Thumbnail = thumbnail;
    }

    public String getName() {
        return Name;
    }

    public String getCategory() {
        return Category;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
