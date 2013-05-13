package com.moe.instafitness.activities;

import com.moe.instafitness.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {

	private boolean sexe;
	private boolean objectif;
	private String test;	 

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
					String text = "You should go "+ test +" day per week to the gym. Now let s make a test to see your skills.";
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
					test="2";
				}else if (checkedId==R.id.radioButton2){
					test="3";
				}else if(checkedId== R.id.radioButton3){
					test="5";
				}
				String text = "You should go "+ test +" day per week to the gym. Now let's makes a test to see your skills.";
				if (sexe && objectif){
					message.setText(text); 
					finish.setVisibility(View.VISIBLE);
				}				
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
