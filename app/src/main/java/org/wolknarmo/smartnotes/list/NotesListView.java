package org.wolknarmo.smartnotes.list;

import android.content.Context;

import org.wolknarmo.smartnotes.model.Note;

import java.util.List;

public interface NotesListView {

	void showNotes(List<Note> notes);

	Context getContext();
}
