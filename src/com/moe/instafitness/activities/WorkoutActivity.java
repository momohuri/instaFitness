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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WorkoutActivity extends Activity implements View.OnClickListener {
	 Button startCountdown;
     Button toEasy;
     Button Normal;
     Button toHard;
     CountDownTimer  myTimmer;
     Cursor workout;
     String workoutId;
    InstaFitnessDatabase instaFitnessDatabase = InstaFitnessDatabase.getInstance(getBaseContext());

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);
		

		toEasy = (Button) findViewById(id.toEasy);
		Normal = (Button) findViewById(id.Normal);
		toHard = (Button) findViewById(id.toHard);


        toEasy.setOnClickListener(this);
        Normal.setOnClickListener(this);
        toHard.setOnClickListener(this);
		
		Bundle extra = getIntent().getExtras();


		 if(extra.get("firstTest") != null){
			Toast toast = Toast.makeText(getBaseContext(), extra.get("grade").toString(), Toast.LENGTH_SHORT);
			 toast.show();
             workoutId = extra.get("firstTest").toString();
             workout = instaFitnessDatabase.getWorkout(workoutId);
		 }else{
             workoutId = extra.getString("exerciseId");
             workout = instaFitnessDatabase.getWorkout(workoutId);
		 }

        final TextView title = (TextView) findViewById(R.id.WorkoutTitle);
        final TextView description  = (TextView) findViewById(R.id.workoutDescription);

        title.setText(workout.getString(workout.getColumnIndex("name")));
        description.setSingleLine(false);
        description.setText(workout.getString(workout.getColumnIndex("description")).replace("/n", "\n\n"));

        //if theire is a duration do a timmer :
        if(!"".equals(workout.getString(workout.getColumnIndex("duration")))){
            startCountdown = (Button) findViewById(R.id.startCountdown);
            startCountdown.setOnClickListener(this);


            toEasy.setVisibility(View.GONE);
            Normal.setVisibility(View.GONE);
            toHard.setVisibility(View.GONE);
            startCountdown.setVisibility(0);

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
        }else{
            final TextView setsreps = (TextView) findViewById(id.setsReps);
            setsreps.setText("You need to do "+workout.getString(workout.getColumnIndex("sets")) +" sets of "+workout.getString(workout.getColumnIndex("repetition"))+ "repetitions");
        }


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
     //when the exercise is finish, we want to know if we are on the first test or random one
    protected void isFinish(Integer difficulty){
        Bundle extra = getIntent().getExtras();
        if(extra.get("firstTest") != null){
            finish();

            Integer exercise = (Integer) extra.get("firstTest");
            //if first test is finish or not
            if(exercise==6){
                Intent intent= (Intent) new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("evaluationFinish",true);
                Map<String, String> personalInfo  = new HashMap<String, String>();
                personalInfo.put("grade",extra.get("grade").toString());
                instaFitnessDatabase.updatePersonalInfo(personalInfo);
                startActivity(intent);
                Toast toast = Toast.makeText(getBaseContext(),"Your evaluation is finished, you can now start a workout", Toast.LENGTH_SHORT);
                toast.show();
            } else{
                Intent intent= (Intent) new Intent(getBaseContext(), WorkoutActivity.class);
                intent.putExtra("firstTest", ++exercise);
                Integer grade = (Integer) extra.get("grade");
                intent.putExtra("grade", grade + difficulty);
                startActivity(intent);
            }

        }else{
            if(difficulty!=0){
                Cursor personalinfo =  instaFitnessDatabase.selectProfile();
                double grade = Integer.valueOf(personalinfo.getString(personalinfo.getColumnIndex("grade")));
                if(difficulty==-1){
                     grade = grade-0.1;
                }else{
                    grade = grade+0.1;
                }
                Map<String, String> personalInfo  = new HashMap<String, String>();
                personalInfo.put("grade", String.valueOf(grade));
                instaFitnessDatabase.updatePersonalInfo(personalInfo);
//                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE , " +
//                        "id_workout INTEGER NOT NULL, " +
//                        "note INTEGER, " +
//                        "timestamp DATETIME);",
                Map<String, String> difficultyInfo =  new HashMap<String,String>();
                difficultyInfo.put("id_workout",workoutId);
                difficultyInfo.put("note", String.valueOf(difficulty));
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date today = Calendar.getInstance().getTime();
                String reportDate = df.format(today);
                difficultyInfo.put("timestamp",reportDate);
                instaFitnessDatabase.insertDifficulty(difficultyInfo);

            }
            finish();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.toEasy:
                isFinish(1);
                break;
            case id.Normal:
                isFinish(0);
                break;
            case id.toHard:
                isFinish(-1);
                break;
            case id.startCountdown:
                myTimmer.start();
                startCountdown.setVisibility(View.GONE);
                break;
        }

    }

}
