package org.rookit.dm.album;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.api.dm.album.factory.AlbumFactory;
import org.rookit.dm.album.disc.DiscModule;
import org.rookit.dm.album.release.ReleaseModule;
import org.rookit.dm.album.slot.TrackSlotModule;
import org.rookit.dm.album.tracks.AlbumTracksModule;

public final class AlbumModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(TrackSlotModule.getModule(),
            DiscModule.getModule(),
            ReleaseModule.getModule(),
            AlbumTracksModule.getModule(),
            new AlbumModule());

    public static Module getModule() {
        return MODULE;
    }

    private AlbumModule() {}

    @Override
    protected void configure() {
        bind(AlbumFactory.class).to(AlbumFactoryImpl.class);
    }
}
