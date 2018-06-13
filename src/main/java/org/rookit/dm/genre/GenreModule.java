package org.rookit.dm.genre;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.rookit.api.dm.genre.factory.GenreFactory;

public class GenreModule extends AbstractModule {

    private static final Module MODULE = new GenreModule();

    public static Module getModule() {
        return MODULE;
    }

    @Override
    protected void configure() {
        bind(GenreFactory.class).to(GenreFactoryImpl.class);
    }
}
