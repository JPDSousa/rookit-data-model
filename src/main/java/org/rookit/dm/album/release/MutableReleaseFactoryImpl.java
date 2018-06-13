package org.rookit.dm.album.release;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.album.release.Release;
import org.rookit.api.dm.album.release.ReleaseFactory;
import org.rookit.api.dm.key.Key;

import java.time.LocalDate;

final class MutableReleaseFactoryImpl implements MutableReleaseFactory {

    private final ReleaseFactory releaseFactory;
    private final Mapper mapper;

    @Inject
    private MutableReleaseFactoryImpl(final ReleaseFactory releaseFactory, final Mapper mapper) {
        this.releaseFactory = releaseFactory;
        this.mapper = mapper;
    }

    @SuppressWarnings("FeatureEnvy") // due to being an adapter
    private MutableRelease fromRelease(final Release release) {
        if (release instanceof MutableRelease) {
            return (MutableRelease) release;
        }
        final MutableRelease mutableRelease = new MutableReleaseImpl(release.type());
        this.mapper.map(release, mutableRelease);
        return mutableRelease;
    }

    @Override
    public MutableRelease releaseOf(final TypeRelease release) {
        return fromRelease(this.releaseFactory.releaseOf(release));
    }

    @Override
    public MutableRelease released(final TypeRelease type, final LocalDate date) {
        return fromRelease(this.releaseFactory.released(type, date));
    }

    @Override
    public MutableRelease releasedToday(final TypeRelease release) {
        return fromRelease(this.releaseFactory.releasedToday(release));
    }

    @Override
    public MutableRelease create(final Key key) {
        return fromRelease(this.releaseFactory.create(key));
    }

    @Override
    public MutableRelease createEmpty() {
        return fromRelease(this.releaseFactory.createEmpty());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("releaseFactory", this.releaseFactory)
                .add("mapper", this.mapper)
                .toString();
    }
}
