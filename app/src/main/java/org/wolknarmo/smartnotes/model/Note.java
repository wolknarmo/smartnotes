package org.wolknarmo.smartnotes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Note implements Serializable {
	public long id;
	public String title;
	public String body;
	public Priority priority;

	public Note(long id, String title, String body, Priority priority) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.priority = priority;
	}

	public Note() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Note note = (Note) obj;
		if (id != note.id)
			return false;
		return true;
	}

	public enum Priority {
		NO_PRIORITY("No priority"),
		LOW("Low priority"),
		MEDIUM("Medium priority"),
		HIGH("High priority");

		public String title;

		Priority(String s) {
			title = s;
		}

		public static List<String> asTitlesList() {
			ArrayList<String> list = new ArrayList<>();
			for (Priority p : values()) {
				list.add(p.title);
			}
			return list;
		}

		public static Priority fromTitle(String title) {
			if (NO_PRIORITY.title.equals(title))
				return NO_PRIORITY;
			if (LOW.title.equals(title))
				return LOW;
			if (MEDIUM.title.equals(title))
				return MEDIUM;
			if (HIGH.title.equals(title))
				return HIGH;
			throw new EnumConstantNotPresentException(Priority.class, title);
		}
	}
}
