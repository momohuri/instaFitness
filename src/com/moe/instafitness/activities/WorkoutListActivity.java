package com.moe.instafitness.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.moe.instafitness.R;
import com.moe.instafitness.R.id;
import com.moe.instafitness.adapter.WorkoutAdapter;
import com.moe.instafitness.entity.WorkoutClass;

import java.util.ArrayList;
public class WorkoutListActivity extends ListActivity {
	private ListView listViewWorkout;
    ArrayList<WorkoutClass> myWorkout;
    WorkoutAdapter adapter;
	EditText editTextSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_list);
        this.listViewWorkout = (ListView) findViewById(android.R.id.list);
        this.myWorkout = new ArrayList<WorkoutClass>();
        this.editTextSearch = (EditText) findViewById(id.editTextSearch);
        /// test ///
        WorkoutClass test = new WorkoutClass();
        WorkoutClass test2 = new WorkoutClass();
        test.setTitle("salope");
        test.setDescription("ok o k ok  o k k ok k k o k ");
        test2.setTitle("tetetetet");
        test2.setDescription("tetetetete ");
        test2.setImageUI(getResources().getDrawable(R.drawable.ic_launcher));
        test.setImageUI(getResources().getDrawable(R.drawable.ic_launcher));
        myWorkout.add(test);
        myWorkout.add(test2);
        /// end test ///


       this.adapter = new WorkoutAdapter(this, android.R.id.list, this.myWorkout);

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
        WorkoutClass workout = (WorkoutClass) l.getItemAtPosition(position);
        Intent intent = new Intent(getBaseContext(), WorkoutActivity.class);
        intent.putExtra("extra",workout.getTitle());
        this.startActivity(intent);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list, menu);
		return true;
	}

}
