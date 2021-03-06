package com.moe.instafitness.activities;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.moe.instafitness.R;
import com.moe.instafitness.R.id;
import com.moe.instafitness.database.InstaFitnessDatabase;

public class MainActivity extends Activity implements OnClickListener {

    Button buttonAtHome;
    Button buttonAtGym;
    Button buttonListEx;
    Button buttonProgress;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//        InstaFitnessDatabase instaFitnessDatabase = InstaFitnessDatabase.getInstance(getBaseContext());
        //db test
//        Cursor workouts = instaFitnessDatabase.selectWorkouts();
//
//        while (workouts.moveToNext()) {
//            Log.i("CursorTest", workouts.getString(0));
//        }


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
	
	private void haveProfile() {
        InstaFitnessDatabase instaFitnessDatabase = InstaFitnessDatabase.getInstance(getBaseContext());
        Cursor profil = instaFitnessDatabase.selectProfile();
        if(profil==null){
            Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
            startActivity(intent);
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getBaseContext(), WorkoutListActivity.class);

        switch (v.getId()) {
            case id.buttonListEx:
                startActivity(intent);
                break;
            case id.buttonAtHome:
                intent.putExtra("place","home");
                startActivity(intent);
                break;
            case id.buttonAtGym:
                intent.putExtra("place","gym");
                startActivity(intent);
                break;
            case id.buttonProgress:
                break;
        }
    }
}
