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

package org.rookit.dm.utils;

import org.rookit.dm.AbstractRookitModel;
import org.rookit.utils.log.AbstractLogCategory;
import org.rookit.utils.log.LogManager;
import org.rookit.utils.log.validator.AbstractValidator;
import org.rookit.utils.log.validator.Validator;

@SuppressWarnings("javadoc")
public final class DataModelValidator extends AbstractValidator {

    private static final Validator SINGLETON = new DataModelValidator();

    public static Validator getDefault() {
        return SINGLETON;
    }

    private DataModelValidator() {
        super(LogManager.create(new DataModelLogCategory()));
    }

    private static class DataModelLogCategory extends AbstractLogCategory {

        @Override
        public String getName() {
            return "RookitDataModel";
        }

        @Override
        public Package getPackage() {
            return AbstractRookitModel.class.getPackage();
        }
    }
}
