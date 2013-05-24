package com.moe.instafitness.database.helpers;

import android.provider.BaseColumns;

public final class WorkoutTypeColumns implements BaseColumns{
	private WorkoutTypeColumns () {}
	
	public static final String TABLENAME = WorkoutColumns.TABLENAME+"_"+TypeColumns.TABLENAME;
}