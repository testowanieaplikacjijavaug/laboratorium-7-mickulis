package com.lab7;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public class NoteTest
{
	private final Offset<Float> PRECISSION = Offset.offset(0.001f);

	@Test
	public void NullName()
	{
		Throwable thrown = catchThrowable(() -> Note.of(null, 3f));
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@ValueSource(floats = { 0f, -1f, 1.999f, 6.0001f })
	public void InvalidNote(float note)
	{
		Throwable thrown = catchThrowable(() -> Note.of("a", note));
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "  ", "\n", " \n \r \t" })
	public void InvalidName(String name)
	{
		Throwable thrown = catchThrowable(() -> Note.of(name, 3f));
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@CsvSource(
			{
			"aaa, aaa, 3, 3",
			"'   a      ', 'a', 3.000000000001, 3",
					"' /n  a  a /t/t  /r  ', '/n  a  a /t/t  /r', 5.99999, 6.0"
			})
	public void InvalidName(String inputName, String expectedOutputName, float inputNote, float expectedOutputNote)
	{
		Note note = Note.of(inputName, inputNote);
		assertThat(note.getName()).isEqualTo(expectedOutputName);
		assertThat(note.getNote()).isCloseTo(expectedOutputNote, PRECISSION);
	}
}
