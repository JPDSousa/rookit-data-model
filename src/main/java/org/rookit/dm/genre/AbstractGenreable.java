
package org.rookit.dm.genre;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.Genreable;
import org.rookit.dm.play.able.AbstractPlayable;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.utils.VoidUtils;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("javadoc")
@NotThreadSafe
public abstract class AbstractGenreable extends AbstractPlayable implements Genreable {

    /**
     * Genres of the album
     */
    private Collection<Genre> genres;

    protected AbstractGenreable(final MutableEventStatsFactory eventStatsFactory) {
        super(eventStatsFactory);
        this.genres = Sets.newLinkedHashSet();
    }

    @Override
    public Void addGenre(final Genre genre) {
        VALIDATOR.checkArgument().isNotNull(genre, "genre");
        this.genres.add(genre);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void addGenres(final Collection<Genre> genres) {
        VALIDATOR.checkArgument().isNotNull(genres, "genres");
        this.genres.addAll(genres);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void clearGenres() {
        this.genres.clear();
        return VoidUtils.returnVoid();
    }

    @Override
    public Collection<Genre> allGenres() {
        return getGenres();
    }

    @Override
    public Collection<Genre> getGenres() {
        return Collections.unmodifiableCollection(this.genres);
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
    public Void setGenres(final Collection<Genre> genres) {
        VALIDATOR.checkArgument().isNotNull(genres, "genres");
        this.genres = Sets.newLinkedHashSet(genres);
        return VoidUtils.returnVoid();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("genres", this.genres)
                .toString();
    }
}
