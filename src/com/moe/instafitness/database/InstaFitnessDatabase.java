package com.moe.instafitness.database;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moe.instafitness.libraries.CSVReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InstaFitnessDatabase {
	private static volatile InstaFitnessDatabase instance = null;
	
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
    
    public static InstaFitnessDatabase getInstance(Context context) {
    	if (instance == null) {
        	synchronized (InstaFitnessDatabase.class){
        		if (instance == null) {
        			instance = new InstaFitnessDatabase(context);
                }
        	}
        }
        return instance;
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
	        "repetition INTEGER, " +
	        "type TEXT, " +
	        "material_needed INTEGER);",
	        
	        "CREATE TABLE " + TYPE_TABLE_NAME + " (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE , " +
			"muscle TEXT);",
			
			"CREATE TABLE " + WORKOUT_TABLE_NAME + "_" + TYPE_TABLE_NAME + " (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE , " +
			"id_workout INTEGER NOT NULL, " +
			"id_type INTEGER NOT NULL);",
			
			"CREATE TABLE " + PICTURE_TABLE_NAME + " (" +
	        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE , " +
	        "id_workout INTEGER NOT NULL, " +
	        "path TEXT);", 
	        
	        "CREATE TABLE " + DIFFICULTY_TABLE_NAME + " (" +
	        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE , " +
	        "id_workout INTEGER NOT NULL, " +
	        "note INTEGER, " +
	        "timestamp DATETIME);", 
	        
	        "CREATE TABLE " + PERSONAL_INFO_TABLE_NAME + " (" +
	        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE , " +
	        "name TEXT, " +
	        "surname TEXT, " +
	        "age INTEGER, " +
	        "objectif_date DATETIME, " +
	        "weight FLOAT, " +
	        "how_many_time_week INTEGER);"
		};
    	
    	InstaFitnessOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
            this.getReadableDatabase();
            
            loadCSV();
        }

		@Override
		public void onCreate(SQLiteDatabase db) {
			mDatabase = db;
			for(String i : DATABASE_TABLES_CREATE) {
				 mDatabase.execSQL(i);
			}
		}
		
		private void loadCSV() {
			String index[] = {};
			String next[] = {};
	        //List<String[]> list = new ArrayList<String[]>();

	        try {
	            CSVReader reader = new CSVReader(new InputStreamReader(mHelperContext.getAssets().open("test2.csv")));
	            int i = 0;
	            while(true) {
	            	if(i == 0) {
	            		index = reader.readNext();
	            	}
	                next = reader.readNext();
	                if(next != null) {
	                	Map<String, String> workout = new HashMap<String, String>();
	                	Map<String, String> personalInfo = new HashMap<String, String>();
                        String[] muscles = new String[]{};
	                	for (int j = 0 ; j < index.length ; j++) {
	                		String[] column = index[j].split(":");
	                		if (column[0].equals("workout")) {
	                			workout.put(column[1], next[j]);
	                		} else if (column[0].equals("personal_info")) {
                                personalInfo.put(column[1], next[j]);
	                		} else if (column[0].equals("type")) {
                                muscles = next[j].split(",");
	                		}
	                	}
	                	
	                	// si le workout possede un nom et une description ==> insert
	                	if (!workout.get("name").equals("") && !workout.get("description").equals("")) {
	                		long workoutId = addWorkout(workout);
                            // si le workout a bien été ajouté dans la bdd
                            // et qu'on a les infos personnelles ==> insert
	                		if (workoutId > 0 && !personalInfo.get("how_many_time_week").equals("")) {
                                long personalInfoId = addPersonalInfo(personalInfo);
                            }
                            // si le workout a bien été ajouté dans la bdd
                            // et que qu'on a les types de muscles ==> insert
                            if (workoutId > 0 && muscles.length > 0) {
                                for (String muscle : muscles) {
                                    long typeId = addType(muscle);
                                    // si l'insert c'est bien passé on met a jour la table de liaison
                                    if(typeId > 0) {
                                        addJoinWorkoutType(workoutId, typeId);
                                    }
                                }
                            }
	                	}
	                    //list.add(next);
	                } else {
	                    break;
	                }
	                i++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }

        /**
         * Insert d'un workout
         * @param workout
         * @return workoutId
         */
		public long addWorkout(Map<String, String> workout) {
            ContentValues workoutValues = new ContentValues();
            
            for (Map.Entry<String, String> entry : workout.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                workoutValues.put(key, value);
            }

            return mDatabase.insert(WORKOUT_TABLE_NAME, null, workoutValues);
        }

        /**
         * Insert des informations personnelles
         * @param personalInfo
         * @return personalInfoId
         */
        public long addPersonalInfo(Map<String, String> personalInfo) {
            ContentValues personalInfoValues = new ContentValues();

            for (Map.Entry<String, String> entry : personalInfo.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                personalInfoValues.put(key, value);
            }

            return mDatabase.insert(PERSONAL_INFO_TABLE_NAME, null, personalInfoValues);
        }

        /**
         * Insert du type
         * @param muscle
         * @return muscleId
         */
        public long addType(String muscle) {
            ContentValues typeValues = new ContentValues();
            typeValues.put("muscle", muscle);

            return mDatabase.insert(TYPE_TABLE_NAME, null, typeValues);
        }

        /**
         * Insert dans la table faisant la jointure entre le workout et ses types
         * @param workoutId
         * @param typeId
         * @return
         */
        public long addJoinWorkoutType(long workoutId, long typeId) {
            ContentValues values = new ContentValues();
            values.put("id_workout", (int) workoutId);
            values.put("id_type", (int) typeId);

            return mDatabase.insert(WORKOUT_TABLE_NAME + "_" + TYPE_TABLE_NAME, null, values);
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
