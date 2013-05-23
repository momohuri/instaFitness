package com.moe.instafitness.activities;

import com.moe.instafitness.R;

import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkoutActivity extends Activity {
	 
	

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);
		
		final Button button1 = (Button) findViewById(R.id.button1);
		final Button button2 = (Button) findViewById(R.id.button2);
		final Button button3 = (Button) findViewById(R.id.button3);
		final Button button4 = (Button) findViewById(R.id.button4);
		
		Bundle extra = getIntent().getExtras();
		final TextView text = (TextView) findViewById(R.id.textView1);   		
		 text.setText(extra.getString("extra"));

		 @SuppressWarnings("deprecation")
		Gallery ga = (Gallery)findViewById(R.id.gallery1);
		 ga.setSpacing(2);
		 ga.setAdapter(new ImageAdapter(this.getBaseContext()));
		 
		 final CountDownTimer  myTimmer = new CountDownTimer(3000, 1000) {
			final TextView mTextField = (TextView) findViewById(R.id.textView2);   

			public void onTick(long millisUntilFinished) {
				mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
		     }

		     public void onFinish() {	    	
		    	 
		    	 button2.setVisibility(0);
		    	 button3.setVisibility(0);
		    	 button4.setVisibility(0);
		    	 
		         mTextField.setText("done!");
		         Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				 v.vibrate(1000);
				 Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				 Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), uri);
			     r.play();

		     }
		  };
		  
		  
		  button1.setOnClickListener(new View.OnClickListener() {        	
	            public void onClick(View v) {     
	            	myTimmer.start();
	            	button1.setVisibility(View.GONE);
	            }
	        });
		  
		final Intent intent = new Intent(getBaseContext(), WorkoutListActivity.class);
      	 
		  button2.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {			
				//todo mettre en base
				startActivity(intent);   
			}
		});
		  button3.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View arg0) {			
					//todo mettre en base
					startActivity(intent);   
				}
			});
		  button4.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View arg0) {			
					//todo mettre en base
					startActivity(intent);   
				}
			});
		 
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
	            i.setLayoutParams(new Gallery.LayoutParams(136, 88));
	            return i;
	        }

	        private Context mContext;

	        private Integer[] mImageIds = {
	   			 R.drawable.ic_launcher,
				 R.drawable.ic_launcher,
				 R.drawable.ic_launcher,
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

}
