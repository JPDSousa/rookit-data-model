package org.rookit.dm.genre;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.mongodb.morphia.annotations.Reference;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.Genreable;
import org.rookit.dm.play.able.AbstractPlayable;
import org.rookit.utils.VoidUtils;

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public abstract class AbstractGenreable extends AbstractPlayable implements Genreable {

	/**
	 * Genres of the album
	 */
	@Reference(idOnly = true)
	private Set<Genre> genres;
	
	public AbstractGenreable() {
		this.genres = Collections.synchronizedSet(
				Sets.newLinkedHashSetWithExpectedSize(AVERAGE_N_GENRES));
	}
	
	@Override
	public Void addGenre(final Genre genre) {
		VALIDATOR.checkArgument().isNotNull(genre, "genre");
		this.genres.add(genre);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setGenres(final Collection<Genre> genres) {
		VALIDATOR.checkArgument().isNotNull(genres, "genres");
		this.genres = Collections.synchronizedSet(Sets.newLinkedHashSet(genres));
		return VoidUtils.returnVoid();
	}

	@Override
	public Collection<Genre> getAllGenres() {
		return getGenres();
	}

	@Override
	public Collection<Genre> getGenres() {
		return Collections.unmodifiableCollection(this.genres);
	}

	@Override
	public Void addGenres(final Collection<Genre> genres) {
		VALIDATOR.checkArgument().isNotNull(genres, "genres");
		this.genres.addAll(genres);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void removeGenre(final Genre genre) {
		VALIDATOR.checkArgument().isNotNull(genre, "genre");
		this.genres.remove(genre);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void removeGenres(final Collection<Genre> genres) {
		VALIDATOR.checkArgument().isNotNull(genres, "genres");
		this.genres.removeAll(genres);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void clearGenres() {
		this.genres.clear();
		return VoidUtils.returnVoid();
	}

}
