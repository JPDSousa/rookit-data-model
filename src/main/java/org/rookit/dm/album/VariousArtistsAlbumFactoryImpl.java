package org.rookit.dm.album;

import com.google.common.base.MoreObjects;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.album.factory.AlbumFactory;
import org.rookit.api.dm.album.key.AlbumKey;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.release.MutableRelease;
import org.rookit.dm.album.release.MutableReleaseFactory;
import org.rookit.dm.album.tracks.MutableAlbumTracks;
import org.rookit.dm.album.tracks.MutableAlbumTracksFactory;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class VariousArtistsAlbumFactoryImpl implements AlbumFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private final MutableReleaseFactory releaseFactory;
    private final BiStreamFactory<Key> biStreamFactory;
    private final MutableAlbumTracksFactory albumTracksFactory;
    private final MutableEventStatsFactory eventStatsFactory;

    VariousArtistsAlbumFactoryImpl(final MutableReleaseFactory releaseFactory,
                                   final BiStreamFactory<Key> biStreamFactory,
                                   final MutableAlbumTracksFactory albumTracksFactory,
                                   final MutableEventStatsFactory eventStatsFactory) {
        this.releaseFactory = releaseFactory;
        this.biStreamFactory = biStreamFactory;
        this.albumTracksFactory = albumTracksFactory;
        this.eventStatsFactory = eventStatsFactory;
    }

    private Album create(final String title, final TypeRelease typeRelease) {
        final BiStream biStream = this.biStreamFactory.createEmpty();
        final MutableAlbumTracks albumTracks = this.albumTracksFactory.createEmpty();
        final MutableRelease release = this.releaseFactory.releaseOf(typeRelease);

        return new VariousArtistsAlbum(title, release, biStream, albumTracks, this.eventStatsFactory);
    }

    @Override
    public Album create(final AlbumKey key) {
        return create(key.title(), key.releaseType());
    }

    @Override
    public Album createEmpty() {
        return VALIDATOR.handleException().unsupportedOperation("Cannot create an empty VariousArtistsAlbum");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("releaseFactory", this.releaseFactory)
                .add("biStreamFactory", this.biStreamFactory)
                .add("albumTracksFactory", this.albumTracksFactory)
                .add("eventStatsFactory", this.eventStatsFactory)
                .toString();
    }
}
