package org.rookit.dm.genre;

import java.util.Set;

@SuppressWarnings("javadoc")
public interface GenreableSetter<T> {
	
	T addGenre(final Genre genre);
	T setGenres(final Set<Genre> genres);

}
