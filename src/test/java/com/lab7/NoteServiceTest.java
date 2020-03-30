package com.lab7;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public class NoteServiceTest
{
	private NotesStorageMock storage;
	private NotesService service;
	private final Offset<Float> PRECISSION = Offset.offset(0.001f);

	@BeforeEach
	public void Setup()
	{
		storage = new NotesStorageMock();
		service = NotesServiceImpl.createWith(storage);
	}

	@Test
	public void addOnce()
	{
		service.add(Note.of("asdf", 3f));
		assertThat(storage.numberOfAddCalls()).isEqualTo(1);


	}

	@Test
	public void addMultipleTimes()
	{
		service.add(Note.of("asdf", 3f));
		service.add(Note.of("asdf", 3f));
		service.add(Note.of("asdf", 3f));
		service.add(Note.of("asdf", 3f));
		assertThat(storage.numberOfAddCalls()).isEqualTo(4);
	}

	@Test
	public void addMultipleWithDifferentName()
	{
		service.add(Note.of("asdf", 3f));
		service.add(Note.of("adsaaf", 3f));
		service.add(Note.of("asdffff", 5f));
		service.add(Note.of("agggggsdf", 4f));
		assertThat(storage.numberOfAddCalls()).isEqualTo(4);
	}

	@Test
	public void addThenClear()
	{
		service.add(Note.of("asdf", 3f));
		service.add(Note.of("adsaaf", 3f));
		service.add(Note.of("asdffff", 5f));
		service.add(Note.of("agggggsdf", 4f));
		service.clear();
		assertThat(storage.numberOfAddCalls()).isEqualTo(4);
		assertThat(storage.isClear()).isTrue();
	}

	@Test
	public void addThenAverage()
	{
		service.add(Note.of("asdf", 3f));
		service.add(Note.of("asdf", 4.5f));
		service.add(Note.of("asdffff", 5f));
		service.add(Note.of("agggggsdf", 4f));
		storage.setNextOutput(new ArrayList<Note>(Arrays.asList(
				Note.of("asdf", 3f),
				Note.of("asdf", 4.5f))));
		assertThat(service.averageOf("asdf")).isCloseTo(3.75f, PRECISSION);
	}

	@Test
	public void averageWithoutNotes()
	{
		assertThat(service.averageOf("asdf")).isNaN();
	}

	@Test
	public void addNullThenAverage()
	{
		service.add(null);
		assertThat(service.averageOf("asdf")).isNaN();
	}

	@Test
	public void addClearAverage()
	{
		service.add(Note.of("asdf", 3f));
		service.add(Note.of("asdf", 4.5f));
		service.add(Note.of("asdffff", 5f));
		service.add(Note.of("agggggsdf", 4f));
		service.clear();
		assertThat(service.averageOf("asdf")).isNaN();
	}

	@Test
	public void clearNullStorage()
	{
		NotesService newService = NotesServiceImpl.createWith(null);
		Throwable thrown = catchThrowable(newService::clear);
		Assertions.assertThat(thrown).isInstanceOf(NullPointerException.class);
	}

	@Test
	public void getFromNullStorage()
	{
		NotesService newService = NotesServiceImpl.createWith(null);
		Throwable thrown = catchThrowable(() -> newService.averageOf("a"));
		Assertions.assertThat(thrown).isInstanceOf(NullPointerException.class);
	}

	@Test
	public void addToNullStorage()
	{
		NotesService newService = NotesServiceImpl.createWith(null);
		Throwable thrown = catchThrowable(() -> newService.add(Note.of("a", 3f)));
		Assertions.assertThat(thrown).isInstanceOf(NullPointerException.class);
	}
}
