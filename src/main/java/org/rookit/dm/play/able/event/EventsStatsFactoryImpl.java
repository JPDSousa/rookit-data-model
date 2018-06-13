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

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.play.able.EventStats;
import org.rookit.api.dm.play.able.EventStatsFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class EventsStatsFactoryImpl implements EventStatsFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final Logger logger = VALIDATOR.getLogger(EventsStatsFactoryImpl.class);

    @Inject
    private EventsStatsFactoryImpl() {}

    @Override
    public EventStats create(final Key key) {
        logger.info("Creation by key is not supported. Falling back to createEmpty");
        return createEmpty();
    }

    @Override
    public EventStats createEmpty() {
        return new MutableEventStatsImpl();
    }
}
