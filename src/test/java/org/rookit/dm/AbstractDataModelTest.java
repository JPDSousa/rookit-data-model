
package org.rookit.dm;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.rookit.dm.guice.RookitDataModelModule;
import org.rookit.dm.test.DataModelTestFactory;
import org.rookit.test.AbstractUnitTest;
import org.rookit.utils.resource.Resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("javadoc")
public abstract class AbstractDataModelTest<T> extends AbstractUnitTest<T> {

    protected static final Injector INJECTOR;
    protected static final DataModelTestFactory FACTORY;

    static {
        // TODO create a JUnit extension with this logic
        final Path fileStorageDirectory = Resources.RESOURCES_TEST.resolve("fileStorageDirectory");

        if(Files.notExists(fileStorageDirectory)) {
            try {
                Files.createDirectories(fileStorageDirectory);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }

        INJECTOR = Guice.createInjector(RookitDataModelModule.getModule(),
                DataModelTestFactory.getModule());
        FACTORY = INJECTOR.getInstance(DataModelTestFactory.class);
    }

}
