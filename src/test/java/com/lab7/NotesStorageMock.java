package com.lab7;

import java.util.ArrayList;
import java.util.List;

public class NotesStorageMock implements NotesStorage
{

	private List<Note> _nextOutput = new ArrayList<>();
	private boolean clear = false;

	private List<Note> addCalls = new ArrayList<Note>();
	private int getAllNotesCalls = 0;
	private int clearCalls = 0;

	public void setNextOutput(List<Note> output)
	{
		_nextOutput = output;
		clear = false;
	}

	public int numberOfAddCalls()
	{
		return addCalls.size();
	}

	public void verifyNumberOfClearCalls(int number)
	{
		if(number != clearCalls)
			throw new AssertionError(
					"Expected " + number + " calls of <clear()> method, " +
							"but it was called " + clearCalls + " times.");
	}

	public void verifyNumberOfGetCalls(int number)
	{
		if(number != getAllNotesCalls)
			throw new AssertionError(
					"Expected " + number + " calls of <getAllNotesOf(String name)> method, " +
							"but it was called " + getAllNotesCalls + " times.");
	}

	public boolean isClear()
	{
		return clear;
	}

	@Override
	public void add(Note note)
	{
		addCalls.add(note);
		clear = false;
	}

	@Override
	public List<Note> getAllNotesOf(String name)
	{
		getAllNotesCalls++;
		if(clear)
			return new ArrayList<>();
		return _nextOutput;
	}

	@Override
	public void clear()
	{
		clearCalls++;
		clear = true;
	}
}
