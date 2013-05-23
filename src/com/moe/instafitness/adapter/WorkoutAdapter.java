package com.moe.instafitness.adapter;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moe.instafitness.entity.WorkoutClass;
import com.moe.instafitness.view.Workout;
import com.moe.instafitness.R;

public class WorkoutAdapter extends ArrayAdapter<WorkoutClass> {

    LayoutInflater inflater;

    public WorkoutAdapter(Context context, int textViewResourceId,List<WorkoutClass> objects) {
        super(context, textViewResourceId, objects);
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Workout workout;
        View row = convertView;
        if (row == null) {
            workout = new Workout();
            convertView = this.inflater.inflate(R.layout.list, null);
            workout.setDescription((TextView) convertView.findViewById(R.id.description));
            workout.setImageUI((ImageView) convertView.findViewById(R.id.img));
            workout.setTitle((TextView) convertView.findViewById(R.id.titre));

            convertView.setTag(workout);
        } else {
            workout = (Workout) convertView.getTag();
        }
        workout.getTitle().setText(super.getItem(position).getTitle());

        workout.getImageUI().setImageDrawable(super.getItem(position).getImageUI());

        workout.getDescription().setText(super.getItem(position).getDescription());
        return convertView;
    }
}
