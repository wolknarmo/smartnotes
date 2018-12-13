package org.wolknarmo.smartnotes.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.wolknarmo.smartnotes.R;
import org.wolknarmo.smartnotes.model.Note;

public class NoteDetailsActivity extends AppCompatActivity implements NoteDetailsView {

	ViewNoteFragment viewNoteFragment = new ViewNoteFragment();
	EditNoteFragment editNoteFragment = new EditNoteFragment();
	NoteDetailsPresenter presenter = new NoteDetailsPresenter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_edit_note);
		presenter.attachView(this);
		if (getActionBar() != null) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		if (!getSupportFragmentManager().getFragments().isEmpty()) {
			Fragment lastFragment = getSupportFragmentManager().getFragments().get(0);
			if (lastFragment instanceof ViewNoteFragment) {
				viewNoteFragment = (ViewNoteFragment) lastFragment;
			} else if (lastFragment instanceof EditNoteFragment) {
				editNoteFragment = (EditNoteFragment) lastFragment;
			}
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment, lastFragment).commit();
		} else {
			if (getIntent().getBooleanExtra("isEdit", false)) {
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment, editNoteFragment).commit();
			} else {
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment, viewNoteFragment).commit();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.detachView();
	}

	@Override
	public void showNoteDetailsView() {
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment, viewNoteFragment).commit();
	}

	@Override
	public void showEditNoteView() {
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment, editNoteFragment).commit();
	}

	@Override
	public void showNote(Note note) {
		if (viewNoteFragment.isAdded())
			viewNoteFragment.showNote(note);
		if (editNoteFragment.isAdded())
			editNoteFragment.showNote(note);
	}

	@Override
	public void closeView() {
		finish();
	}
}
