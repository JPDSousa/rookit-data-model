
package org.rookit.dm.genre;

import org.junit.jupiter.api.Test;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.Genreable;
import org.rookit.dm.play.able.AbstractPlayableTest;
import org.rookit.test.AbstractUnitTest;
import org.rookit.test.CollectionOps;

import java.util.Collection;

@SuppressWarnings("javadoc")
public abstract class AbstractGenreableTest<T extends Genreable> extends AbstractPlayableTest<T> {

    private final class GenreCollectionOps implements CollectionOps<Genre> {

        private final T testResource;

        private GenreCollectionOps(final T testResource) {
            this.testResource = testResource;
        }

        @Override
        public void add(final Genre item) {
            this.testResource.addGenre(item);
        }

        @Override
        public void addAll(final Collection<Genre> items) {
            this.testResource.addGenres(items);
        }

        @Override
        public Collection<Genre> get() {
            return this.testResource.getGenres();
        }

        @Override
        public void remove(final Genre item) {
            this.testResource.removeGenre(item);
        }

        @Override
        public void removeAll(final Collection<Genre> items) {
            this.testResource.removeGenres(items);
        }

        @Override
        public void reset() {
            this.testResource.clearGenres();
        }

        @Override
        public void set(final Collection<Genre> items) {
            this.testResource.setGenres(items);
        }
    }

    @SuppressWarnings("synthetic-access")
    @Test
    public void testGenres() {
        AbstractUnitTest.testCollectionOps(new GenreCollectionOps(this.testResource), FACTORY.genres());
    }
}
