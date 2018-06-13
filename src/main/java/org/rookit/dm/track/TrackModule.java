package org.rookit.dm.track;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.dm.track.artists.TrackArtistsModule;
import org.rookit.dm.track.audio.AudioFeatureModule;
import org.rookit.dm.track.lyrics.LyricsModule;
import org.rookit.dm.track.title.TitleModule;

public final class TrackModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new TrackModule(),
            TitleModule.getModule(),
            LyricsModule.getModule(),
            TrackArtistsModule.getModule(),
            AudioFeatureModule.getModule());

    public static Module getModule() {
        return MODULE;
    }

    private TrackModule() {}

    @Override
    protected void configure() {
        bind(TrackFactory.class).to(TrackFactoryImpl.class);
    }
}
