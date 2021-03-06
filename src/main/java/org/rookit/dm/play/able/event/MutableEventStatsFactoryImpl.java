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
package org.rookit.dm.play.able.event;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.play.able.EventStats;
import org.rookit.api.dm.play.able.EventStatsFactory;

final class MutableEventStatsFactoryImpl implements MutableEventStatsFactory {

    private final EventStatsFactory factory;
    private final Mapper mapper;

    @Inject
    private MutableEventStatsFactoryImpl(final EventStatsFactory factory, final Mapper mapper) {
        this.factory = factory;
        this.mapper = mapper;
    }

    private MutableEventStats fromEventStats(final EventStats eventStats) {
        if (eventStats instanceof  MutableEventStats) {
            return (MutableEventStats) eventStats;
        }
        final MutableEventStats mutableEventStats = new MutableEventStatsImpl();
        this.mapper.map(eventStats, mutableEventStats);
        return mutableEventStats;
    }

    @Override
    public MutableEventStats create(final Key key) {
        return fromEventStats(this.factory.create(key));
    }

    @Override
    public MutableEventStats createEmpty() {
        return fromEventStats(this.factory.createEmpty());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("factory", factory)
                .add("mapper", mapper)
                .toString();
    }
}
