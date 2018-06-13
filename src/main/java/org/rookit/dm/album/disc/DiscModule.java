package org.rookit.dm.album.disc;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.rookit.api.dm.album.disc.DiscFactory;
import org.rookit.dm.album.config.AlbumConfig;
import org.rookit.dm.album.disc.config.DiscConfig;

@SuppressWarnings("MethodMayBeStatic")
public final class DiscModule extends AbstractModule {

    private static final Module MODULE = new DiscModule();

    public static Module getModule() {
        return MODULE;
    }

    private DiscModule() {}

    @Override
    protected void configure() {
        bind(DiscFactory.class).to(DiscFactoryImpl.class).in(Singleton.class);
        bind(MutableDiscFactory.class).to(MutableDiscFactoryImpl.class).in(Singleton.class);
    }

    @Provides
    DiscConfig getDiscConfig(final AlbumConfig parentConfig) {
        return parentConfig.discConfig();
    }
}
