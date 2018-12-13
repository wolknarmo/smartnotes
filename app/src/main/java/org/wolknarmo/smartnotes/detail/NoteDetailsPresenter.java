package org.wolknarmo.smartnotes.detail;

import org.wolknarmo.smartnotes.model.Note;
import org.wolknarmo.smartnotes.model.NotesRepository;

public class NoteDetailsPresenter {

	private NoteDetailsView view;
	private NotesRepository repository = NotesRepository.getInstance();

	public void attachView(NoteDetailsView view) {
		this.view = view;
	}

	public void detachView() {
		view = null;
	}

	public void onEditNoteClicked() {
		view.showEditNoteView();
	}

	public void onDeleteNoteClicked() {
		Note note = repository.getCurrentNote();
		if (note != null) {
			repository.deleteNote(note);
		}
		repository.setCurrentNote(null);
		view.closeView();
	}

	public void onResetChangesClicked() {
		view.showNote(repository.getCurrentNote());
	}

	public void onSaveNoteClicked(Note note) {
		note.id = repository.getCurrentNote() == null ? -1 : repository.getCurrentNote().id;
		if (note.title.isEmpty()) {
			note.title = "untitled";
		}
		note = repository.saveNote(note);
		repository.setCurrentNote(note);
		view.showNoteDetailsView();
	}

	public void onViewIsReady() {
		view.showNote(repository.getCurrentNote());
	}
}
