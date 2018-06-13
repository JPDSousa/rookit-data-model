/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

package org.rookit.dm.genre;

import org.junit.jupiter.api.Test;
import org.rookit.api.dm.genre.Genre;
import org.rookit.dm.play.able.AbstractPlayableTest;
import org.rookit.test.SerializationTest;
import org.rookit.test.categories.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("javadoc")
@UnitTest
public class GenreImplUnitTest extends AbstractPlayableTest<GenreImpl> implements SerializationTest<GenreImpl> {

    @Override
    public GenreImpl createTestResource() {
        return new GenreImpl("GenreName");
    }

    @Test
    public void testCompareTo() {
        testCompareTo(this.testResource);
        testCompareTo(new GenreImpl("AnotherGenreName"));
    }

    @Test
    public void testDescription() {
        final String testDescription = "this is the description";

        this.testResource.setDescription(testDescription);
        assertThat(this.testResource.description())
                .as("Description is not being properly assigned!")
                .isNotEmpty()
                .get()
                .isEqualTo(testDescription);
    }

    @Test
    public void testName() {
        final String name = "theGenre";
        this.testResource = new GenreImpl(name);
        assertThat(this.testResource.name()).as("Name is not being properly assigned!").isEqualTo(name);
    }

    private void testCompareTo(final Genre genre) {
        assertThat(this.testResource.compareTo(genre))
                .isEqualTo(this.testResource.name().compareTo(genre.name()));
    }

    @Override
    public Class<GenreImpl> getTestResourceType() {
        return GenreImpl.class;
    }

}
