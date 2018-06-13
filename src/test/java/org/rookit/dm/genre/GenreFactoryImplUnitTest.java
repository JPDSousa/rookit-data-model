package org.rookit.dm.genre;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.GenreFields;
import org.rookit.api.dm.genre.key.GenreKey;
import org.rookit.dm.factory.FactoryTest;
import org.rookit.test.AbstractUnitTest;
import org.rookit.test.categories.UnitTest;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@UnitTest
class GenreFactoryImplUnitTest extends AbstractUnitTest<GenreFactoryImpl>
        implements FactoryTest<GenreFactoryImpl, Genre, GenreKey> {

    private static final Injector INJECTOR = Guice.createInjector();

    @Override
    public GenreFactoryImpl createTestResource() {
        return INJECTOR.getInstance(GenreFactoryImpl.class);
    }

    @Test
    public void testConsistentCreationByName() {
        final String testName = "genre";
        final Genre expected = this.testResource.createGenre(testName);
        final Genre actual = this.testResource.createGenre(testName);

        assertThat(actual)
                .as("One of the created genres")
                .isEqualTo(expected);
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @TestFactory
    public Collection<DynamicTest> testInvalidGenreExceptionNull() {
        return testBlankStringArgument(arg -> this.testResource.createGenre(arg));
    }

    @Test
    public void testCreationByKeyCallsKeyGetName() {
        final GenreKey key = createKey();

        this.testResource.create(key);
        verify(key, times(1)).getName();
    }

    @Test
    @Override
    public void testConsistentCreationByEmpty() {
        assertThatThrownBy(() -> this.testResource.createEmpty())
            .as("Creating an empty genre is not possible")
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void testCreationByMap() {
        final String genreName = "GenreName";
        final Map<String, Object> data = ImmutableMap.of(GenreFields.NAME, genreName);
        final Genre expected = new GenreImpl(genreName);

        assertThat(this.testResource.create(data))
                .as("This is not the expected genre")
                .isEqualTo(expected);
    }

    @Test
    public void testCreationByEmptyMapFails() {
        assertThatThrownBy(() -> this.testResource.create(ImmutableMap.of()))
                .as("Creating a genre fromRelease an empty map")
                .isInstanceOf(RuntimeException.class);
    }

    @Override
    public GenreKey createKey() {
        final GenreKey mockedKey = mock(GenreKey.class);
        when(mockedKey.getName()).thenReturn("AnAwesomeGenre");

        return mockedKey;
    }
}