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

package org.rookit.dm.play.able;

import org.junit.jupiter.api.Test;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.dm.AbstractRookitTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("javadoc")
public abstract class AbstractPlayableTest<T extends Playable> extends AbstractRookitTest<T> {

    @Test
    public void testPlays() {
        final LocalDate lastPlayed = LocalDate.now().minusDays(3);
        final long initialPlays = this.testResource.getPlays();
        final int testPlays = 5;
        final long testSetPlays = 230293020;
        this.testResource.setLastPlayed(lastPlayed);

        assertThat(initialPlays)
                .isGreaterThanOrEqualTo(0);
        assertThat(this.testResource.getLastPlayed())
                .contains(lastPlayed);

        for (int i = 0; i < testPlays; i++) {
            this.testResource.play();
        }

        assertThat(this.testResource.getLastPlayed())
                .isNotEmpty()
                .get()
                .isNotEqualTo(lastPlayed);

        assertThat(this.testResource.getPlays())
                .isEqualTo(initialPlays + testPlays);

        this.testResource.setSkipped(testSetPlays);
        assertThat(this.testResource.getSkipped())
                .isEqualTo(testSetPlays);
    }

    @Test
    public void testSkip() {
        final LocalDate lastSkipped = LocalDate.now().minusDays(3);
        final long initialSkips = this.testResource.getSkipped();
        final int testSkips = 5;
        final long testSetSkips = 230293020;
        this.testResource.setLastSkipped(lastSkipped);

        assertThat(initialSkips)
                .isGreaterThanOrEqualTo(0);
        assertThat(this.testResource.getLastSkipped())
                .contains(lastSkipped);

        for (int i = 0; i < testSkips; i++) {
            this.testResource.skip();
        }

        assertThat(this.testResource.getLastSkipped())
                .isNotEmpty()
                .get()
                .isNotEqualTo(lastSkipped);

        assertThat(this.testResource.getSkipped())
                .isEqualTo(initialSkips + testSkips);

        this.testResource.setSkipped(testSetSkips);
        assertThat(this.testResource.getSkipped())
                .isEqualTo(testSetSkips);
    }

}
