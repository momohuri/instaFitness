package com.moe.instafitness.view;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by Gilaw on 15/05/13.
 */
public class Workout {

    private ImageView imageUI;
    private TextView title;
    private TextView description;

    public ImageView getImageUI() {
        return imageUI;
    }
    public void setImageUI(ImageView imageUI) {
        this.imageUI = imageUI;
    }

    public TextView getTitle() {
        return title;
    }
    public void setTitle(TextView title) {
        this.title = title;
    }
    
    public TextView getDescription(){
        return description;
    }
    public void setDescription(TextView description){
        this.description = description;
    }
}