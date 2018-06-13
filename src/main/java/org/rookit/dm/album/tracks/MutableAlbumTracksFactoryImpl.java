package org.rookit.dm.album.tracks;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.album.tracks.AlbumTracks;
import org.rookit.api.dm.album.tracks.AlbumTracksFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.disc.MutableDiscFactory;

final class MutableAlbumTracksFactoryImpl implements MutableAlbumTracksFactory {

    private final MutableDiscFactory discFactory;
    private final AlbumTracksFactory albumTracksFactory;
    private final Mapper mapper;

    @Inject
    private MutableAlbumTracksFactoryImpl(final MutableDiscFactory discFactory,
                                          final AlbumTracksFactory albumTracksFactory,
                                          final Mapper mapper) {
        this.discFactory = discFactory;
        this.albumTracksFactory = albumTracksFactory;
        this.mapper = mapper;
    }

    private MutableAlbumTracks fromAlbumTracks(final AlbumTracks albumTracks) {
        if (albumTracks instanceof MutableAlbumTracks) {
            return (MutableAlbumTracks) albumTracks;
        }

        final MutableAlbumTracks mutableAlbumTracks = new MutableAlbumTracksImpl(this.discFactory);
        this.mapper.map(albumTracks, mutableAlbumTracks);
        return mutableAlbumTracks;
    }

    @Override
    public MutableAlbumTracks create(final Key key) {
        return fromAlbumTracks(this.albumTracksFactory.create(key));
    }

    @Override
    public MutableAlbumTracks createEmpty() {
        return fromAlbumTracks(this.albumTracksFactory.createEmpty());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("discFactory", this.discFactory)
                .add("albumTracksFactory", this.albumTracksFactory)
                .add("mapper", this.mapper)
                .toString();
    }
}
