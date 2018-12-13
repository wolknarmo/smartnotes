package org.wolknarmo.smartnotes.list;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.wolknarmo.smartnotes.R;
import org.wolknarmo.smartnotes.database.NoteReaderDbHelper;
import org.wolknarmo.smartnotes.model.Note;
import org.wolknarmo.smartnotes.model.NotesRepository;

import java.util.List;

public class NotesListActivity extends AppCompatActivity implements NotesListView {

	RecyclerView notesList;
	NotesListPresenter presenter = new NotesListPresenter();
	NotesListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes_list);
		NotesRepository.getInstance().setDbHelper(new NoteReaderDbHelper(getApplicationContext()));
		presenter.attachView(this);
		notesList = findViewById(R.id.notes_list);
		notesList.setLayoutManager(new LinearLayoutManager(this));
		adapter = new NotesListAdapter(new NotesListAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(Note note) {
				presenter.onNoteClicked(note);
			}
		});
		notesList.setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.detachView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		presenter.updateNotesList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.notes_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.add_note:
				presenter.onAddNoteClicked();
				return true;
		}
		return false;
	}

	@Override
	public void showNotes(List<Note> notes) {
		adapter.setData(notes);
	}

	@Override
	public Context getContext() {
		return this;
	}
}
