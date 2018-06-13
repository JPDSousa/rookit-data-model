package org.rookit.dm.play;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.rookit.dm.play.able.event.EventStatsModule;
import org.rookit.api.dm.play.factory.DynamicPlaylistFactory;
import org.rookit.dm.play.factory.FactoryModule;
import org.rookit.api.dm.play.factory.StaticPlaylistFactory;

public final class PlaylistModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(new PlaylistModule(),
            FactoryModule.getModule(),
            EventStatsModule.getModule());

    public static Module getModule() {
        return MODULE;
    }

    private PlaylistModule() {}

    @Override
    protected void configure() {
        bind(StaticPlaylistFactory.class).to(StaticPlaylistFactoryImpl.class).in(Singleton.class);
        bind(DynamicPlaylistFactory.class).to(DynamicPlaylistFactoryImpl.class).in(Singleton.class);
    }

}
