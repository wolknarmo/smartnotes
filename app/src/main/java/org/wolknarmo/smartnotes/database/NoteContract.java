package org.wolknarmo.smartnotes.database;

import android.provider.BaseColumns;

public final class NoteContract {

	private NoteContract() {
	}

	public static class NoteEntry implements BaseColumns {
		public static final String TABLE_NAME = "notes";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_BODY = "body";
		public static final String COLUMN_NAME_PRIORITY = "priority";
	}
}