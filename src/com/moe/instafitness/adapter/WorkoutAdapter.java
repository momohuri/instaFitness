package com.moe.instafitness.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.moe.instafitness.R;


public class WorkoutAdapter extends CursorAdapter {

    private Cursor mCursor;
    private Context mContext;
    private final LayoutInflater mInflater;

    public WorkoutAdapter(Context context, Cursor objects) {
        super(context, objects);
        mInflater=LayoutInflater.from(context);
        mContext=context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView t = (TextView) view.findViewById(R.id.titre);
        String st = cursor.getString(cursor.getColumnIndex("name"));
        t.setText(st);

        st = cursor.getString(cursor.getColumnIndex("type"));
        t = (TextView) view.findViewById(R.id.description);
        t.setText(st);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.list, parent, false);
        return view;
    }

}
