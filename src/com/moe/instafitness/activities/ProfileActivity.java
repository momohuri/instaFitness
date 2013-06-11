package com.moe.instafitness.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.moe.instafitness.R;
import com.moe.instafitness.database.InstaFitnessDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends Activity {

	private boolean sexe;
	private boolean objectif;
	private String nbrPerWeek;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		sexe = false;
		objectif = false;
		
		final TextView message = (TextView) findViewById(R.id.message);
		final Button finish = (Button) findViewById(R.id.finish);
		
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sexe);        
	    radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				sexe = true;
				if (sexe && objectif){
					String text = "You should go "+ nbrPerWeek +" day per week to the gym. Now let s make a nbrPerWeek to see your skills.";
					message.setText(text); 
					finish.setVisibility(View.VISIBLE);
                }
			}
		});
	
		    
		RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.objectif);        
	    radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				objectif = true;
				if(checkedId==R.id.radioButton1){
					nbrPerWeek ="2";
				}else if (checkedId==R.id.radioButton2){
					nbrPerWeek ="3";
				}else if(checkedId== R.id.radioButton3){
					nbrPerWeek ="5";
				}
				String text = "You should go "+ nbrPerWeek +" day per week to the gym. Now let's makes a nbrPerWeek to see your skills.";
				if (sexe && objectif){
					message.setText(text); 
					finish.setVisibility(View.VISIBLE);
				}				
			}
		});
	    
	    finish.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO info in db

                EditText name = (EditText) findViewById(R.id.inputName);



                InstaFitnessDatabase instaFitnessDatabase = InstaFitnessDatabase.getInstance(getBaseContext());


                Map<String, String> personalInfo  = new HashMap<String, String>();
                personalInfo.put("name",name.getText().toString());
                personalInfo.put("how_many_time_week", nbrPerWeek);



                instaFitnessDatabase.insertPersonalInfo(personalInfo);


				Intent intent= (Intent) new Intent(getBaseContext(), WorkoutActivity.class);
				intent.putExtra("firstTest", 1);
				startActivity(intent);
			}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
