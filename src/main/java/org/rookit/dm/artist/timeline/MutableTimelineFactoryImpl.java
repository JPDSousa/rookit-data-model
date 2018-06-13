/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
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
package org.rookit.dm.artist.timeline;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.artist.timeline.Timeline;
import org.rookit.api.dm.artist.timeline.TimelineFactory;
import org.rookit.api.dm.key.Key;

import java.time.LocalDate;

final class MutableTimelineFactoryImpl implements MutableTimelineFactory {

    private final Mapper mapper;
    private final TimelineFactory factory;

    @Inject
    private MutableTimelineFactoryImpl(final Mapper mapper, final TimelineFactory factory) {
        this.mapper = mapper;
        this.factory = factory;
    }

    private MutableTimeline fromTimeline(final Timeline timeline) {
        if (timeline instanceof  MutableTimeline) {
            return (MutableTimeline) timeline;
        }
        final MutableTimeline mutableTimeline = new MutableTimelineImpl();
        this.mapper.map(timeline, mutableTimeline);
        return mutableTimeline;
    }

    @Override
    public MutableTimeline startAt(final LocalDate date) {
        return fromTimeline(this.factory.startAt(date));
    }

    @Override
    public MutableTimeline endAt(final LocalDate date) {
        return fromTimeline(this.factory.endAt(date));
    }

    @Override
    public MutableTimeline between(final LocalDate begin, final LocalDate end) {
        return fromTimeline(this.factory.between(begin, end));
    }

    @Override
    public MutableTimeline create(final Key key) {
        return fromTimeline(this.factory.create(key));
    }

    @Override
    public MutableTimeline createEmpty() {
        return fromTimeline(this.factory.createEmpty());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mapper", mapper)
                .add("factory", factory)
                .toString();
    }
}
