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
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

import java.time.LocalDate;
import java.util.Optional;

final class MutableEventStatsImpl implements MutableEventStats {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private long count;
    private LocalDate date;

    MutableEventStatsImpl() {
        this.count = 0;
    }

    @Override
    public void setCount(final long count) {
        VALIDATOR.checkArgument().isNotNegative(count, "count");
        this.count = count;
    }

    @Override
    public void touch() {
        this.count++;
        this.date = LocalDate.now();
    }

    @Override
    public void setLast(final LocalDate last) {
        VALIDATOR.checkArgument().isNotFuture(last, "last");
        this.date = last;
    }

    @Override
    public long count() {
        return this.count;
    }

    @Override
    public Optional<LocalDate> lastDate() {
        return Optional.ofNullable(this.date);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("count", this.count)
                .add("date", this.date)
                .toString();
    }
}
