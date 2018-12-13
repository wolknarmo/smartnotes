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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.wolknarmo.smartnotes.R;
import org.wolknarmo.smartnotes.model.Note;

public class EditNoteFragment extends Fragment {

	NoteDetailsPresenter presenter = new NoteDetailsPresenter();
	EditText title;
	EditText body;
	Spinner priority;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.edit_note_fragment, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		title = view.findViewById(R.id.title);
		body = view.findViewById(R.id.body);
		priority = view.findViewById(R.id.priority);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		presenter.attachView((NoteDetailsView) getActivity());
		if (getActivity() != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.priority_spinner_item, R.id.title, Note.Priority.asTitlesList());
			priority.setAdapter(adapter);
		}
		presenter.onViewIsReady();
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null) {
			title.setText(savedInstanceState.getString("title"));
			body.setText(savedInstanceState.getString("body"));
			priority.setSelection(savedInstanceState.getInt("priority"));
		}
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("title", title.getText().toString());
		outState.putString("body", body.getText().toString());
		outState.putInt("priority", priority.getSelectedItemPosition());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		presenter.detachView();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.edit_note, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.save_note:
				presenter.onSaveNoteClicked(getData());
				return true;
			case R.id.reset_changes:
				presenter.onResetChangesClicked();
				return true;
			case R.id.delete_note:
				presenter.onDeleteNoteClicked();
				return true;
		}
		return false;
	}

	public Note getData() {
		Note note = new Note();
		note.title = title.getText().toString();
		note.body = body.getText().toString();
		note.priority = Note.Priority.fromTitle((String) priority.getSelectedItem());
		return note;
	}

	public void showNote(Note note) {
		if (note != null) {
			title.setText(note.title);
			body.setText(note.body);
			priority.setSelection(note.priority.ordinal());
		} else {
			title.setText("");
			body.setText("");
			priority.setSelection(0);
		}
	}
}
