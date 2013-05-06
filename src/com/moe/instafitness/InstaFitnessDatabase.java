package com.moe.instafitness;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InstaFitnessDatabase {
	private static final String TAG = "InstaFitnessDatabase";
	
	private static final String DATABASE_NAME = "instafitness";
	private static final String WORKOUT_TABLE_NAME = "workout";
	private static final String TYPE_TABLE_NAME = "type";
	private static final String PICTURE_TABLE_NAME = "picture";
	private static final String DIFFICULTY_TABLE_NAME = "difficulty";
	private static final String PERSONAL_INFO_TABLE_NAME = "personal_info";
	private static final int DATABASE_VERSION = 1;
	
	private final InstaFitnessOpenHelper mDatabaseOpenHelper;
	
	/**
     * Constructor
     * @param context The Context within which to work, used to create the DB
     */
    public InstaFitnessDatabase(Context context) {
        mDatabaseOpenHelper = new InstaFitnessOpenHelper(context);
    }
    
    /**
     * This creates/opens the database.
     */
    private static class InstaFitnessOpenHelper extends SQLiteOpenHelper {
    	
    	private final Context mHelperContext;
        private SQLiteDatabase mDatabase;
        
        private static final String[] DATABASE_TABLES_CREATE = {
	        "CREATE TABLE " + WORKOUT_TABLE_NAME + " (" +
	        "id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL UNIQUE , " +
	        "name TEXT, " +
	        "description TEXT, " +
	        "icon TEXT, " +
	        "difficulty INTEGER, " +
	        "time INTEGER, " +
	        "sets INTEGER, " +
	        "type TEXT, " +
	        "material_needed INTEGER );",
	        
	        "CREATE TABLE " + TYPE_TABLE_NAME + " (" +
			"id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
			"muscle TEXT);",
			
			"CREATE TABLE " + WORKOUT_TABLE_NAME + TYPE_TABLE_NAME + " (" +
			"id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
			"id_workout INTEGER  AUTOINCREMENT  NOT NULL , " +
			"id_type INTEGER  AUTOINCREMENT  NOT NULL);",
			
			"CREATE TABLE " + PICTURE_TABLE_NAME + " (" +
	        "id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL UNIQUE , " +
	        "id_workout INTEGER  AUTOINCREMENT  NOT NULL , " +
	        "path TEXT);", 
	        
	        "CREATE TABLE " + DIFFICULTY_TABLE_NAME + " (" +
	        "id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL UNIQUE , " +
	        "id_workout INTEGER  AUTOINCREMENT  NOT NULL , " +
	        "note INTEGER, " +
	        "timestamp DATETIME);", 
	        
	        "CREATE TABLE " + PERSONAL_INFO_TABLE_NAME + " (" +
	        "id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL UNIQUE , " +
	        "name TEXT, " +
	        "surname TEXT, " +
	        "age INTEGER, " +
	        "objectif_date DATETIME, " +
	        "weight FLOAT, " +
	        "how_many_time_week INTEGER );"
		};
    	
    	InstaFitnessOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

		@Override
		public void onCreate(SQLiteDatabase db) {
			mDatabase = db;
			for(String i : DATABASE_TABLES_CREATE) {
				 mDatabase.execSQL(i);
			}			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
			for(String i : DATABASE_TABLES_CREATE) {
				db.execSQL("DROP TABLE IF EXISTS " + i);
			}
            onCreate(db);
		}
    }

}
