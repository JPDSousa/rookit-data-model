
package org.rookit.dm.bistream;

import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.Logger;
import org.rookit.api.bistream.BiStream;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

final class FileBiStream implements BiStream {

    private static final long serialVersionUID = -7828271525930497490L;

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    /**
     * Logger for TempFileBiStream.
     */
    private static final Logger logger = VALIDATOR.getLogger(FileBiStream.class);

    private final String path;

    FileBiStream(final Path path) {
        logger.trace("New {} in path {}", FileBiStream.class.getName(), path);
        this.path = path.toString();
    }

    private Path asPath() {
        return Paths.get(this.path);
    }

    @Override
    public void clear() {
        try {
            Files.deleteIfExists(asPath());
        } catch (final IOException e) {
            VALIDATOR.handleException().inputOutputException(e);
        }
    }

    @Override
    public boolean isEmpty() {
        return Files.notExists(asPath())
                || !canRead();
    }
    
    @SuppressWarnings("boxing")
    private boolean canRead() {
        try (final InputStream input = Files.newInputStream(asPath(), StandardOpenOption.CREATE,
                StandardOpenOption.READ)) {
            return input.read() != -1;
        } catch (final IOException e) {
            //noinspection AutoUnboxing
            return VALIDATOR.handleException().inputOutputException(e);
        }
    }

    @Override
    public InputStream readFrom() {
        try {
            return Files.newInputStream(asPath(), StandardOpenOption.CREATE, StandardOpenOption.READ);
        } catch (final IOException e) {
            return VALIDATOR.handleException().inputOutputException(e);
        }
    }

    @Override
    public OutputStream writeTo() {
        try {
            return Files.newOutputStream(asPath());
        } catch (final IOException e) {
            return VALIDATOR.handleException().inputOutputException(e);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("path", this.path)
                .toString();
    }
}
