
package org.rookit.dm.bistream;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.bistream.BiStream;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@SuppressWarnings("javadoc")
final class FileBiStreamFactoryImpl implements FileBiStreamFactory {

    private static final String ROOT = "FileBiStream";
    private static final String PREFIX = ROOT + '_';
    private static final String SUFFIX = ".tmp";

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    /**
     * Logger for BufferBiStreamFactory.
     */
    private static final Logger logger = VALIDATOR.getLogger(FileBiStreamFactoryImpl.class);
    
    private final Path directory;

    @Inject
    private FileBiStreamFactoryImpl(@FileStorage final Path varDir) {
        try {
            this.directory = Files.createDirectories(varDir.resolve(ROOT));
        } catch (final IOException e) {
            //noinspection ProhibitedExceptionThrown
            throw (RuntimeException) VALIDATOR.handleException().inputOutputException(e);
        }
    }

    @Override
    public BiStream create(final FileBiStreamKey key) {
        final Optional<Path> pathOrNone = key.path();

        if (pathOrNone.isPresent()) {
            return create(pathOrNone.get());
        }

        logger.debug("No path provided in {}. Using temporary file.", key);
        return createEmpty();
    }

    @Override
    public BiStream createEmpty() {
        try {
            return createWithAbsolutePath(Files.createTempFile(this.directory, PREFIX, SUFFIX));
        } catch (final IOException e) {
            return VALIDATOR.handleException().inputOutputException(e);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("directory", this.directory)
                .toString();
    }

    @Override
    public BiStream create(final Path relativePath) {
        return createWithAbsolutePath(this.directory.resolve(relativePath));
    }

    private BiStream createWithAbsolutePath(final Path absolutePath) {
        return new FileBiStream(absolutePath);
    }
}
