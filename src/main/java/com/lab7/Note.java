package com.lab7;

import java.io.Serializable;
import com.google.common.base.Preconditions;

public class Note implements Serializable {

	private final String name;
	private final float note;

	public static Note of(final String name, final float note) {
		return new Note(name, note);
	}
	public String getName() {
		return name;
	}
	public float getNote() {
		return note;
	}
	private Note(String name, final float note) {
		Preconditions.checkArgument(name != null, "Imię ucznia nie może być null");
		name = name.trim();
		Preconditions.checkArgument(!name.trim().isEmpty(), "Imię ucznia nie może być puste");
		Preconditions.checkArgument(note >= 2.0f && note <= 6.0f, "Niewłaściwa ocena");

		this.name = name;
		this.note = note;
	}
}