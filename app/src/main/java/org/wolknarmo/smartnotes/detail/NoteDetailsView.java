package org.wolknarmo.smartnotes.detail;

import org.wolknarmo.smartnotes.model.Note;

public interface NoteDetailsView {

	void showNoteDetailsView();

	void showEditNoteView();

	void showNote(Note note);

	void closeView();
}
