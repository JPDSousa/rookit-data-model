package org.rookit.dm.album;

import com.google.common.base.MoreObjects;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.album.factory.AlbumFactory;
import org.rookit.api.dm.album.key.AlbumKey;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.config.AlbumConfig;
import org.rookit.dm.album.release.MutableRelease;
import org.rookit.dm.album.release.MutableReleaseFactory;
import org.rookit.dm.album.tracks.MutableAlbumTracks;
import org.rookit.dm.album.tracks.MutableAlbumTracksFactory;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

import java.util.Collection;

final class SingleArtistAlbumFactoryImpl implements AlbumFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private final MutableReleaseFactory releaseFactory;
    private final BiStreamFactory<Key> biStreamFactory;
    private final MutableAlbumTracksFactory albumTracksFactory;
    private final MutableEventStatsFactory eventStatsFactory;

    SingleArtistAlbumFactoryImpl(final AlbumConfig config,
                                 final MutableReleaseFactory releaseFactory,
                                 final BiStreamFactory<Key> biStreamFactory,
                                 final MutableAlbumTracksFactory albumTracksFactory,
                                 final MutableEventStatsFactory eventStatsFactory) {
        this.releaseFactory = releaseFactory;
        this.biStreamFactory = biStreamFactory;
        this.albumTracksFactory = albumTracksFactory;
        this.eventStatsFactory = eventStatsFactory;
    }

    private Album create(final String title,
                         final TypeRelease typeRelease,
                         final Collection<Artist> artists) {
        final BiStream biStream = this.biStreamFactory.createEmpty();
        final MutableRelease release = this.releaseFactory.releaseOf(typeRelease);
        final MutableAlbumTracks albumTracks = this.albumTracksFactory.createEmpty();
        return new SingleArtistAlbum(title, release, artists, biStream, albumTracks, this.eventStatsFactory);
    }

    @Override
    public Album create(final AlbumKey key) {
        return create(key.title(), key.releaseType(), key.artists());
    }

    @Override
    public Album createEmpty() {
        return VALIDATOR.handleException().unsupportedOperation("Cannot create an empty SingleArtistAlbum.");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("releaseFactory", this.releaseFactory)
                .add("biStreamFactory", this.biStreamFactory)
                .add("albumTracksFactory", this.albumTracksFactory)
                .toString();
    }
}
