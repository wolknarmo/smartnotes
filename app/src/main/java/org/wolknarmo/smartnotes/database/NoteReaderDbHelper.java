package org.wolknarmo.smartnotes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteReaderDbHelper extends SQLiteOpenHelper {

	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + NoteContract.NoteEntry.TABLE_NAME + " (" +
			NoteContract.NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			NoteContract.NoteEntry.COLUMN_NAME_TITLE + " TEXT," +
			NoteContract.NoteEntry.COLUMN_NAME_BODY + " TEXT," +
			NoteContract.NoteEntry.COLUMN_NAME_PRIORITY + " TEXT)";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + NoteContract.NoteEntry.TABLE_NAME;

	public NoteReaderDbHelper(Context context) {
		super(context.getApplicationContext(), "NoteReader.db", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
}
