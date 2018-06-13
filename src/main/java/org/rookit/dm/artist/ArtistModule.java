package org.rookit.dm.artist;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.dm.artist.external.ExternalIdentifiersModule;
import org.rookit.dm.artist.factory.FactoryModule;
import org.rookit.api.dm.artist.factory.GroupArtistFactory;
import org.rookit.api.dm.artist.factory.MusicianFactory;
import org.rookit.dm.artist.name.ArtistNameModule;
import org.rookit.dm.artist.profile.ProfileModule;
import org.rookit.dm.artist.timeline.TimelineModule;

public final class ArtistModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(new ArtistModule(),
            ArtistNameModule.getModule(),
            TimelineModule.getModule(),
            FactoryModule.getModule(),
            ProfileModule.getModule(),
            ExternalIdentifiersModule.getModule());

    public static Module getModule() {
        return MODULE;
    }

    private ArtistModule() {}

    @Override
    protected void configure() {
        bind(GroupArtistFactory.class).to(GroupArtistFactoryImpl.class);
        bind(MusicianFactory.class).to(MusicianFactoryImpl.class);
    }

}
