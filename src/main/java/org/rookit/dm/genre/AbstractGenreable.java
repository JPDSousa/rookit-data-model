package org.rookit.dm.genre;

import java.util.Collection;
import java.util.Set;

import org.rookit.dm.play.AbstractPlayable;
import org.smof.annnotations.SmofArray;
import org.smof.parsers.SmofType;

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public abstract class AbstractGenreable extends AbstractPlayable implements Genreable {

	/**
	 * Genres of the album
	 */
	@SmofArray(name = GENRES, type = SmofType.OBJECT)
	private Set<Genre> genres;
	
	public AbstractGenreable() {
		genres = Sets.newLinkedHashSetWithExpectedSize(AVERAGE_N_GENRES);
	}
	
	@Override
	public Void addGenre(Genre genre) {
		VALIDATOR.checkArgumentNotNull(genre, "Cannot add a null genre");
		genres.add(genre);
		return null;
	}

	@Override
	public Void setGenres(Set<Genre> genres) {
		VALIDATOR.checkArgumentNotNull(genres, "The genre set cannot be null");
		this.genres = genres;
		return null;
	}

	@Override
	public Collection<Genre> getAllGenres() {
		return getGenres();
	}

	@Override
	public Collection<Genre> getGenres() {
		return genres;
	}

}
