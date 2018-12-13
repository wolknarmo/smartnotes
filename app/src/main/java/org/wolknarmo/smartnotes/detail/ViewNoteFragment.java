package org.wolknarmo.smartnotes.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wolknarmo.smartnotes.R;
import org.wolknarmo.smartnotes.model.Note;

public class ViewNoteFragment extends Fragment {

	NoteDetailsPresenter presenter = new NoteDetailsPresenter();
	TextView title;
	TextView body;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.view_note_fragment, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		title = view.findViewById(R.id.title);
		body = view.findViewById(R.id.body);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		presenter.attachView((NoteDetailsView) getActivity());
		presenter.onViewIsReady();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		presenter.detachView();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.view_note, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.edit_note:
				presenter.onEditNoteClicked();
				return true;
			case R.id.delete_note:
				presenter.onDeleteNoteClicked();
				return true;
		}
		return false;
	}

	public void showNote(Note note) {
		if (note != null) {
			title.setText(note.title);
			body.setText(note.body);
		}
	}
}
