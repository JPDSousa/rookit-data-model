package org.rookit.dm.album.release;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import org.rookit.api.dm.album.release.ReleaseFactory;

public final class ReleaseModule extends AbstractModule {

    private static final Module MODULE = new ReleaseModule();

    public static Module getModule() {
        return MODULE;
    }

    private ReleaseModule() {}

    @Override
    protected void configure() {
        bind(MutableReleaseFactory.class).to(MutableReleaseFactoryImpl.class).in(Singleton.class);
        bind(ReleaseFactory.class).to(ReleaseFactoryImpl.class).in(Singleton.class);
    }

}
