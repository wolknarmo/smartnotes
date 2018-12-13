package org.wolknarmo.smartnotes.list;

import android.content.Intent;

import org.wolknarmo.smartnotes.model.Note;
import org.wolknarmo.smartnotes.model.NotesRepository;
import org.wolknarmo.smartnotes.detail.NoteDetailsActivity;

public class NotesListPresenter {

	private NotesListView view;
	private NotesRepository repository = NotesRepository.getInstance();

	public void attachView(NotesListView view) {
		this.view = view;
	}

	public void detachView() {
		view = null;
	}

	public void onNoteClicked(Note note) {
		repository.setCurrentNote(note);
		Intent intent = new Intent(view.getContext(), NoteDetailsActivity.class);
		intent.putExtra("isEdit", false);
		view.getContext().startActivity(intent);
	}

	public void onAddNoteClicked() {
		repository.setCurrentNote(null);
		Intent intent = new Intent(view.getContext(), NoteDetailsActivity.class);
		intent.putExtra("isEdit", true);
		view.getContext().startActivity(intent);
	}

	public void updateNotesList() {
		view.showNotes(repository.getNotes());
	}

}
