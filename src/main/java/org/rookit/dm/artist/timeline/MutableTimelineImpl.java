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
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

import java.time.LocalDate;
import java.util.Optional;

final class MutableTimelineImpl implements MutableTimeline {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private LocalDate beginDate;
    private LocalDate endDate;

    MutableTimelineImpl() {}

    @Override
    public void setBegin(final LocalDate date) {
        VALIDATOR.checkArgument().isNotNull(date, "date");
        final Optional<LocalDate> endOrNone = end();
        if (endOrNone.isPresent()) {
            final LocalDate end = endOrNone.get();
            VALIDATOR.checkArgument().is(end.isAfter(date), "%s is after the end date %s",
                    date, end);
        }
        this.beginDate = date;
    }

    @Override
    public void setEnd(final LocalDate date) {
        VALIDATOR.checkArgument().isNotNull(date, "date");
        final Optional<LocalDate> beginOrNone = begin();
        if (beginOrNone.isPresent()) {
            final LocalDate begin = beginOrNone.get();
            VALIDATOR.checkArgument().is(begin.isBefore(date), "%s is before the begin date %s",
                    date, begin);
        }
        this.endDate = date;
    }

    @Override
    public Optional<LocalDate> begin() {
        return Optional.ofNullable(this.beginDate);
    }

    @Override
    public Optional<LocalDate> end() {
        return Optional.ofNullable(this.endDate);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("beginDate", this.beginDate)
                .add("endDate", this.endDate)
                .toString();
    }
}
