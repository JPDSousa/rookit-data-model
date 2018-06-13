
package org.rookit.dm.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Modules;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.AlbumModule;
import org.rookit.dm.album.factory.AlbumBiStream;
import org.rookit.dm.artist.ArtistModule;
import org.rookit.dm.artist.factory.ArtistBiStream;
import org.rookit.dm.bistream.BiStreamModule;
import org.rookit.dm.genre.GenreModule;
import org.rookit.dm.play.PlaylistModule;
import org.rookit.dm.play.factory.PlaylistBiStream;
import org.rookit.dm.track.TrackModule;
import org.rookit.dm.track.factory.TrackBiStream;

@SuppressWarnings("javadoc")
public final class RookitDataModelModule extends AbstractModule {

    @SuppressWarnings("AnonymousInnerClass")
    private static final TypeLiteral<BiStreamFactory<Key>> BISTREAM_FACTORY_LITERAL =
            new TypeLiteral<BiStreamFactory<Key>>() {
                // intentionally empty
            };

    private static final Module MODULE = Modules.combine(
            new RookitDataModelModule(),
            AlbumModule.getModule(),
            ArtistModule.getModule(),
            GenreModule.getModule(),
            PlaylistModule.getModule(),
            TrackModule.getModule(),
            BiStreamModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private RookitDataModelModule() {}

    @Override
    protected void configure() {
        bindArtistRelatedFactories();
        bindAlbumRelatedFactories();
        bindTrackRelatedFactories();
        bindPlaylistRelatedFactories();
    }

    private void bindPlaylistRelatedFactories() {
        // playlist
        bind(BISTREAM_FACTORY_LITERAL)
                .annotatedWith(PlaylistBiStream.class)
                .to(com.google.inject.Key.get(BISTREAM_FACTORY_LITERAL));
    }

    private void bindAlbumRelatedFactories() {
        // album
        bind(BISTREAM_FACTORY_LITERAL)
                .annotatedWith(AlbumBiStream.class)
                .to(com.google.inject.Key.get(BISTREAM_FACTORY_LITERAL));
    }

    private void bindArtistRelatedFactories() {
        // artist
        bind(BISTREAM_FACTORY_LITERAL)
                .annotatedWith(ArtistBiStream.class)
                .to(com.google.inject.Key.get(BISTREAM_FACTORY_LITERAL));
    }

    private void bindTrackRelatedFactories() {
        // track
        bind(BISTREAM_FACTORY_LITERAL)
                .annotatedWith(TrackBiStream.class)
                .to(com.google.inject.Key.get(BISTREAM_FACTORY_LITERAL));
    }

}
