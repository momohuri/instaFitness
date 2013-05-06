package com.moe.instafitness;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class Workout extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);
		
		Bundle extra = getIntent().getExtras();
		final TextView text = (TextView) findViewById(R.id.textView1);   		
		 text.setText(extra.getString("extra"));

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
