package org.rookit.dm.bistream;

import com.google.inject.*;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.key.Key;

import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("MethodMayBeStatic")
public class BiStreamModule extends AbstractModule {

    @SuppressWarnings("AnonymousInnerClass")
    private static final TypeLiteral<BiStreamFactory<Key>> BISTREAM_FACTORY_LITERAL =
            new TypeLiteral<BiStreamFactory<Key>>() {
                // intentionally empty
            };

    private static final Module MODULE = new BiStreamModule();

    public static Module getModule() {
        return MODULE;
    }

    @Override
    protected void configure() {
        bind(BISTREAM_FACTORY_LITERAL).to(GenericFileBiStreamFactoryAdapter.class).in(Singleton.class);
        bind(FileBiStreamFactory.class).to(FileBiStreamFactoryImpl.class).in(Singleton.class);
    }

    @Provides
    @FileStorage
    Path getFileStoragePath(final FileStorageConfig fileStorageConfig) {
        return Paths.get(fileStorageConfig.getPath());
    }

}
