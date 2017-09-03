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

import static org.rookit.dm.genre.DatabaseFields.*;

import org.rookit.dm.utils.CoreValidator;
import org.smof.annnotations.SmofBuilder;
import org.smof.annnotations.SmofParam;

@SuppressWarnings("javadoc")
public class GenreFactory {

	private static final CoreValidator VALIDATOR = CoreValidator.getDefault();
	
	private static GenreFactory singleton;
	
	public static GenreFactory getDefault() {
		if(singleton == null) {
			singleton = new GenreFactory();
		}
		
		return singleton;
	}
		
	private GenreFactory() {}
	
	@SmofBuilder
	public Genre createGenre(@SmofParam(name = NAME) String name) {
		VALIDATOR.checkArgumentStringNotEmpty(name, "A genre must have a non-null non-empty name");
		return new DefaultGenre(name);
	}
}