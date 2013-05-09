package com.moe.instafitness.activities;

import java.util.ArrayList;
import java.util.HashMap;

import com.moe.instafitness.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;


public class WorkoutListActivity extends Activity {
	private ListView maListViewPerso;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_list);
		
	      maListViewPerso = (ListView) findViewById(R.id.listviewperso);
	        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
	        HashMap<String, String> map;
	        map = new HashMap<String, String>();
	  	  map.put("titre","hello");
          map.put("description", "Editeur de texte");
          map.put("img", String.valueOf(R.drawable.ic_launcher));
          listItem.add(map);
          SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.list,
                  new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
    
           //On attribut à notre listView l'adapter que l'on vient de créer
           maListViewPerso.setAdapter(mSchedule);
           

//           //Enfin on met un écouteur d'évènement sur notre listView
           maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
   			@Override
   			@SuppressWarnings("unchecked")
            	public void onItemClick(AdapterView<?> a, View v, int position, long id) {   			
           		HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
          		
           		Intent intent = new Intent(getBaseContext(), WorkoutActivity.class);
            	intent.putExtra("extra",map.get("titre"));
            	startActivity(intent);    
           	
           	}
            });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list, menu);
		return true;
	}

}
