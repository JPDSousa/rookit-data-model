package org.rookit.dm.artist.external;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import org.rookit.api.dm.artist.external.ExternalIdentifiersFactory;

public final class ExternalIdentifiersModule extends AbstractModule {

    private static final Module MODULE = new ExternalIdentifiersModule();

    public static Module getModule() {
        return MODULE;
    }

    private ExternalIdentifiersModule() {}

    @Override
    protected void configure() {
        bind(ExternalIdentifiersFactory.class).to(ExternalIdentifiersFactoryImpl.class).in(Singleton.class);
        bind(MutableExternalIdentifiersFactory.class).to(MutableExternalIdentifiersFactoryImpl.class)
                .in(Singleton.class);
    }
}
