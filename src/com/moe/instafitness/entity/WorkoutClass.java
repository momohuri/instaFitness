package com.moe.instafitness.entity;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class WorkoutClass {
    private Drawable imageUI;
    private String title;
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUI(Drawable imageUI) {
        this.imageUI = imageUI;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {

        return description;
    }

    public Drawable getImageUI() {
        return imageUI;
    }

    public String getTitle() {
        return title;
    }
}
