package com.moe.instafitness.activities;


import com.moe.instafitness.R;
import com.moe.instafitness.R.id;
import com.moe.instafitness.R.layout;
import com.moe.instafitness.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

    Button buttonAtHome;
    Button buttonAtGym;
    Button buttonListEx;
    Button buttonProgress;





	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		this.haveProfile();

        this.buttonListEx = (Button) this.findViewById(R.id.buttonListEx);
        this.buttonAtGym = (Button) this.findViewById(R.id.buttonAtGym);
        this.buttonAtHome = (Button) this.findViewById(R.id.buttonAtHome);
        this.buttonProgress = (Button) this.findViewById(R.id.buttonProgress);

        this.buttonListEx.setOnClickListener(this);
        this.buttonProgress.setOnClickListener(this);
        this.buttonAtHome.setOnClickListener(this);
        this.buttonAtGym.setOnClickListener(this);
	}
	
	private boolean haveProfile() {  
		Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
		startActivity(intent);
	    return true;
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.buttonListEx:

                Intent intent = new Intent(getBaseContext(), WorkoutListActivity.class);
                intent.putExtra("extra","gym");
                startActivity(intent);
                break;
            case id.buttonAtHome:
                    finish();
                break;
            case id.buttonAtGym:
                break;

            case id.buttonProgress:
                break;
        }
    }
}
