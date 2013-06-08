package com.moe.instafitness.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.moe.instafitness.R;
import com.moe.instafitness.R.id;
import com.moe.instafitness.database.InstaFitnessDatabase;

public class WorkoutActivity extends Activity implements View.OnClickListener {
	 Button startCountdown;
     Button toEasy;
     Button Normal;
     Button toHard;
     CountDownTimer  myTimmer;
    Cursor workout;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);
		
	    startCountdown = (Button) findViewById(R.id.startCountdown);
		toEasy = (Button) findViewById(id.toEasy);
		Normal = (Button) findViewById(id.Normal);
		toHard = (Button) findViewById(id.toHard);

        startCountdown.setOnClickListener(this);
        toEasy.setOnClickListener(this);
        Normal.setOnClickListener(this);
        toHard.setOnClickListener(this);
		
		Bundle extra = getIntent().getExtras();

		 if(extra.get("firstTest") != null){
			Toast toast = Toast.makeText(getBaseContext(), extra.get("firstTest")+" test", Toast.LENGTH_SHORT);
			 toast.show();
		 }else{
             InstaFitnessDatabase instaFitnessDatabase = InstaFitnessDatabase.getInstance(getBaseContext());
             String id = extra.getString("exerciseId");
             workout = instaFitnessDatabase.getWorkout(id);
		 }

        final TextView title = (TextView) findViewById(R.id.WorkoutTitle);
        final TextView description  = (TextView) findViewById(R.id.workoutDescription);
        final TextView setsreps = (TextView) findViewById(id.setsReps);
        title.setText(workout.getString(workout.getColumnIndex("name")));
        description.setText(workout.getString(workout.getColumnIndex("description")));
        setsreps.setText("You need to do "+workout.getString(workout.getColumnIndex("sets")) +" sets of "+workout.getString(workout.getColumnIndex("repetition"))+ "repetitions");


        myTimmer = new CountDownTimer(3000, 1000) {
            TextView mTextField = (TextView) findViewById(id.textView2);

            public void onTick(long millisUntilFinished) {
                 mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                toEasy.setVisibility(0);
                Normal.setVisibility(0);
                toHard.setVisibility(0);

                // mTextField.setText("done!");
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(1000);
                Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), uri);
                r.play();

            }
        };

		 @SuppressWarnings("deprecation")
		 Gallery ga = (Gallery)findViewById(R.id.gallery1);
		 ga.setSpacing(2);
		 ga.setAdapter(new ImageAdapter(this.getBaseContext()));


	}
	   public class ImageAdapter extends BaseAdapter {

	        public ImageAdapter(Context c) {
	            mContext = c;
	        }

	        public int getCount() {
	            return mImageIds.length;
	        }

	        public Object getItem(int position) {
	            return position;
	        }

	        public long getItemId(int position) {
	            return position;
	        }

	        public View getView(int position, View convertView, ViewGroup parent) {

	        	ImageView i = new ImageView(mContext);

	            i.setImageResource(mImageIds[position]);
	            i.setScaleType(ImageView.ScaleType.FIT_XY);
	            i.setLayoutParams(new Gallery.LayoutParams(230, 150));
	            return i;
	        }

	        private Context mContext;

	        private Integer[] mImageIds = {
	   			 R.drawable.test1,
				 R.drawable.test2,
				 R.drawable.test3,
				 R.drawable.ic_launcher,
				 R.drawable.ic_launcher	
	        };
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workout, menu);
		return true;
	}

    protected void isFinish(){
        Bundle extra = getIntent().getExtras();
        if(extra.get("firstTest") != null){
            finish();
            Intent intent= (Intent) new Intent(getBaseContext(), WorkoutActivity.class);
            Integer exercise = (Integer) extra.get("firstTest");
            intent.putExtra("firstTest", ++exercise);
            startActivity(intent);
        }else{
            finish();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.toEasy:
                isFinish();
                break;
            case id.Normal:
                isFinish();
                break;
            case id.toHard:
                isFinish();
                break;
            case id.startCountdown:
                myTimmer.start();
                startCountdown.setVisibility(View.GONE);
                break;
        }

    }

}
