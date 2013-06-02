package com.moe.instafitness.adapter;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moe.instafitness.entity.WorkoutClass;
import com.moe.instafitness.view.Workout;
import com.moe.instafitness.R;

public class WorkoutAdapter extends CursorAdapter {

    LayoutInflater inflater;

    public WorkoutAdapter(Context context, Cursor objects) {
        super(context, objects);
        this.inflater = LayoutInflater.from(context);
    }



    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView t = (TextView) view.findViewById(R.id.titre);
        String st = cursor.getString(cursor.getColumnIndex("workout.name"));

        t.setText(st);

        t = (TextView) view.findViewById(R.id.description);
        t.setText(st);


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.workout_list, parent, false);
        return view;
    }

}
