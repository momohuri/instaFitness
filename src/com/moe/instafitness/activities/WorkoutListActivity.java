package com.moe.instafitness.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.moe.instafitness.R;
import com.moe.instafitness.R.id;
import com.moe.instafitness.adapter.WorkoutAdapter;
import com.moe.instafitness.database.InstaFitnessDatabase;
import com.moe.instafitness.entity.WorkoutClass;
import java.util.ArrayList;

public class WorkoutListActivity extends ListActivity {
	private ListView listViewWorkout;
    private ArrayList<WorkoutClass> myWorkout;
    private WorkoutAdapter adapter;
	private EditText editTextSearch;
    private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_list);
        this.listViewWorkout = (ListView) findViewById(android.R.id.list);
        this.myWorkout = new ArrayList<WorkoutClass>();
        this.editTextSearch = (EditText) findViewById(id.editTextSearch);
        //search don t work

        InstaFitnessDatabase instaFitnessDatabase = InstaFitnessDatabase.getInstance(getBaseContext());
        Cursor allWorkout = instaFitnessDatabase.selectWorkouts();

        this.adapter = new WorkoutAdapter(this, allWorkout) ;


        this.setListAdapter(adapter);
        this.listViewWorkout.setTextFilterEnabled(true);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter = (WorkoutAdapter) listViewWorkout.getAdapter();
               adapter.getFilter().filter(s.toString());
            }
        });
	}


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        this.cursor = ((CursorAdapter)l.getAdapter()).getCursor();
        this.cursor.moveToPosition(position);

        String titre = this.cursor.getString(this.cursor.getColumnIndex("name"));
        Intent intent = new Intent(getBaseContext(), WorkoutActivity.class);
        intent.putExtra("extra",titre);
        this.startActivity(intent);


    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list, menu);
		return true;
	}

}
