package org.rookit.dm.genre;

import java.util.Collection;
import java.util.Set;

import org.mongodb.morphia.annotations.Reference;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.Genreable;
import org.rookit.dm.play.able.AbstractPlayable;

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public abstract class AbstractGenreable extends AbstractPlayable implements Genreable {

	/**
	 * Genres of the album
	 */
	@Reference(idOnly = true)
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
