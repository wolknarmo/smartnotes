package org.wolknarmo.smartnotes.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.wolknarmo.smartnotes.database.NoteContract;
import org.wolknarmo.smartnotes.database.NoteReaderDbHelper;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository {
	private static NotesRepository INSTANCE = new NotesRepository();
	private SQLiteDatabase db;
	private Note currentNote;

	private NotesRepository() {
	}

	public static NotesRepository getInstance() {
		return INSTANCE;
	}

	public void setDbHelper(NoteReaderDbHelper dbHelper) {
		db = dbHelper.getWritableDatabase();
	}

	public Note saveNote(Note note) {
		if (note.id == -1) {
			return addNewNote(note);
		} else {
			return updateNote(note);
		}
	}

	private Note addNewNote(Note note) {
		ContentValues values = new ContentValues();
		values.put(NoteContract.NoteEntry.COLUMN_NAME_TITLE, note.title);
		values.put(NoteContract.NoteEntry.COLUMN_NAME_BODY, note.body);
		values.put(NoteContract.NoteEntry.COLUMN_NAME_PRIORITY, note.priority.toString());

		note.id = db.insert(NoteContract.NoteEntry.TABLE_NAME, null, values);
		return note;
	}

	private Note updateNote(Note note) {
		ContentValues values = new ContentValues();
		values.put(NoteContract.NoteEntry.COLUMN_NAME_TITLE, note.title);
		values.put(NoteContract.NoteEntry.COLUMN_NAME_BODY, note.body);
		values.put(NoteContract.NoteEntry.COLUMN_NAME_PRIORITY, note.priority.toString());

		String selection = NoteContract.NoteEntry._ID + " LIKE ?";
		String[] selectionArgs = {String.valueOf(note.id)};

		db.update(NoteContract.NoteEntry.TABLE_NAME, values, selection, selectionArgs);
		return note;
	}

	public void deleteNote(Note note) {
		String selection = NoteContract.NoteEntry._ID + " LIKE ?";
		String[] selectionArgs = {String.valueOf(note.id)};
		db.delete(NoteContract.NoteEntry.TABLE_NAME, selection, selectionArgs);
	}

	public Note getCurrentNote() {
		return currentNote;
	}

	public void setCurrentNote(Note note) {
		currentNote = note;
	}

	public List<Note> getNotes() {
		String[] columns = {NoteContract.NoteEntry._ID, NoteContract.NoteEntry.COLUMN_NAME_TITLE, NoteContract.NoteEntry.COLUMN_NAME_BODY, NoteContract.NoteEntry.COLUMN_NAME_PRIORITY};
		Cursor cursor = db.query(NoteContract.NoteEntry.TABLE_NAME, columns, null, null, null, null, null);
		List<Note> notes = new ArrayList<>();
		while (cursor.moveToNext()) {
			long id = cursor.getLong(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry._ID));
			String title = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_TITLE));
			String body = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_BODY));
			String priority = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_PRIORITY));
			notes.add(new Note(id, title, body, Note.Priority.valueOf(priority)));
		}
		cursor.close();
		return notes;
	}
}
