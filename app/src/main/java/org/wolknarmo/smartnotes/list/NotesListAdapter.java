package org.wolknarmo.smartnotes.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wolknarmo.smartnotes.R;
import org.wolknarmo.smartnotes.model.Note;

import java.util.ArrayList;
import java.util.List;

class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

	private List<Note> notes = new ArrayList<>();
	private OnItemClickListener listener;

	NotesListAdapter(OnItemClickListener listener) {
		this.listener = listener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		final Note note = notes.get(position);
		holder.title.setText(note.title);
		holder.itemView.setBackgroundColor(getBackgroundColor(holder.itemView.getContext(), note.priority));
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onItemClick(note);
			}
		});
	}

	@Override
	public int getItemCount() {
		return notes.size();
	}

	private int getBackgroundColor(Context context, Note.Priority priority) {
		int color;
		switch (priority) {
			case LOW:
				color = context.getResources().getColor(R.color.priorityLow);
				break;
			case MEDIUM:
				color = context.getResources().getColor(R.color.priorityMedium);
				break;
			case HIGH:
				color = context.getResources().getColor(R.color.priorityHigh);
				break;
			default:
				color = context.getResources().getColor(R.color.priorityNoPriority);
		}
		return color;
	}

	public void setData(List<Note> notes) {
		if (notes == null)
			notes = new ArrayList<>();
		this.notes = notes;
		notifyDataSetChanged();
	}

	interface OnItemClickListener {
		void onItemClick(Note note);
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		TextView title;

		ViewHolder(View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.title);
		}
	}
}
