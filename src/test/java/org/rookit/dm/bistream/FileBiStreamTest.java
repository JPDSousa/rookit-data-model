
package org.rookit.dm.bistream;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.jupiter.api.Test;
import org.rookit.test.AbstractUnitTest;
import org.rookit.test.categories.FastTest;
import org.rookit.test.categories.UnitTest;
import org.rookit.test.mixin.ByteArrayMixin;
import org.rookit.test.preconditions.TestPreconditions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("javadoc")
@FastTest
@UnitTest
public class FileBiStreamTest extends AbstractUnitTest<FileBiStream> implements ByteArrayMixin {

    private static final short FILENAME_LENGTH = 20;
    private static final int CAPACITY = 128;
    private static final RandomStringGenerator RANDOM_STRINGS = new RandomStringGenerator.Builder()
            .build();

    private Path tempFile;

    @SuppressWarnings("CastToConcreteClass")
    @Override
    public FileBiStream createTestResource() {
        try {
            this.tempFile = this.temporaryFolder.createFile(RANDOM_STRINGS.generate(FILENAME_LENGTH)).toPath();
            return new FileBiStream(this.tempFile);
        } catch (final IOException e) {
            return VALIDATOR.handleException().inputOutputException(e);
        }
    }

    @Test
    public final void testClear() throws IOException {
        Files.write(this.tempFile, createRandomArray(CAPACITY));
        this.testResource.clear();

        assertThat(Files.notExists(this.tempFile))
                .as("The remaining size")
                .isTrue();
    }

    @Test
    public final void testRead() throws IOException {
        TestPreconditions.assure().is(this.testResource.isEmpty(), "The test resource must be empty");

        final byte[] expectedContent = createRandomArray(CAPACITY / 2);
        final byte[] actualContent = new byte[CAPACITY / 2];
        Files.write(this.tempFile, expectedContent);

        try (final InputStream input = this.testResource.readFrom()) {
            final int readBytes = input.read(actualContent);
            assertThat(readBytes)
                    .as("The number of bytes read")
                    .isPositive();
            assertThat(actualContent)
                    .as("The content read")
                    .containsExactly(expectedContent);
        }
    }

    @Test
    public final void testWrite() throws IOException {
        TestPreconditions.assure().is(this.testResource.isEmpty(), "The test resource must be empty");

        final byte[] expectedContent = createRandomArray(CAPACITY / 2);

        try (final OutputStream output = this.testResource.writeTo()) {
            output.write(expectedContent);
        }
        final byte[] actualContent = Files.readAllBytes(this.tempFile);
        assertThat(actualContent)
                .as("The content writen")
                .containsExactly(expectedContent);
    }

}
