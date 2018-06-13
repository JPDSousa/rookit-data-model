package org.rookit.dm.track.audio;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import org.rookit.api.dm.track.audio.AudioContentFactory;
import org.rookit.api.dm.track.audio.AudioFeatureFactory;

public final class AudioFeatureModule extends AbstractModule {

    private static final Module MODULE = new AudioFeatureModule();

    public static Module getModule() {
        return MODULE;
    }

    private AudioFeatureModule() {}

    @Override
    protected void configure() {
        bind(AudioFeatureFactory.class).to(AudioFeatureFactoryImpl.class).in(Singleton.class);
        bind(AudioContentFactory.class).to(ImmutableAudioContentFactoryImpl.class).in(Singleton.class);
    }
}
