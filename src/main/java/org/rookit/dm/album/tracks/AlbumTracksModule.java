package org.rookit.dm.album.tracks;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import org.rookit.api.dm.album.tracks.AlbumTracksFactory;

public final class AlbumTracksModule extends AbstractModule {

    private static final Module MODULE = new AlbumTracksModule();

    public static Module getModule() {
        return MODULE;
    }

    private AlbumTracksModule() {}

    @Override
    protected void configure() {
        bind(AlbumTracksFactory.class).to(AlbumTracksFactoryImpl.class).in(Singleton.class);
        bind(MutableAlbumTracksFactory.class).to(MutableAlbumTracksFactoryImpl.class).in(Singleton.class);
    }
}
