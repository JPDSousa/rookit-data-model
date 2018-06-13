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

import com.google.common.base.MoreObjects;
import org.rookit.api.dm.play.able.EventStats;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.dm.AbstractRookitModel;
import org.rookit.dm.play.able.event.MutableEventStats;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.utils.VoidUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

@SuppressWarnings("javadoc")
public abstract class AbstractPlayable extends AbstractRookitModel implements Playable {

    private final MutableEventStats plays;
    private final MutableEventStats skips;

    private Duration duration;

    protected AbstractPlayable(final MutableEventStatsFactory factory) {
        this.plays = factory.createEmpty();
        this.skips = factory.createEmpty();
    }

    @Override
    public Optional<Duration> duration() {
        return Optional.ofNullable(this.duration);
    }

    @Override
    public Void play() {
        this.plays.touch();
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setDuration(final Duration duration) {
        VALIDATOR.checkArgument().isNotNull(duration, "duration");
        this.duration = duration;
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setLastPlayed(final LocalDate lastPlayed) {
        VALIDATOR.checkArgument().isNotNull(lastPlayed, "lastPlayed");
        this.plays.setLast(lastPlayed);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setLastSkipped(final LocalDate lastSkipped) {
        VALIDATOR.checkArgument().isNotNull(lastSkipped, "lastSkipped");
        this.skips.setLast(lastSkipped);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setPlays(final long plays) {
        VALIDATOR.checkArgument().isPositive(plays, "plays");
        this.plays.setCount(plays);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setSkipped(final long skipped) {
        VALIDATOR.checkArgument().isPositive(skipped, "skipped");
        this.skips.setCount(skipped);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void skip() {
        this.skips.touch();
        return VoidUtils.returnVoid();
    }

    @Override
    public EventStats playStats() {
        return this.plays;
    }

    @Override
    public EventStats skipStats() {
        return this.skips;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("plays", this.plays)
                .add("skips", this.skips)
                .add("duration", this.duration)
                .toString();
    }
}
