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

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.rookit.api.dm.artist.Artist;
import org.rookit.dm.AbstractRookitModel;
import org.rookit.dm.album.AbstractAlbum.Disc;
import org.rookit.utils.log.AbstractLogCategory;
import org.rookit.utils.log.LogManager;
import org.rookit.utils.log.Validator;

@SuppressWarnings("javadoc")
public class DataModelValidator extends Validator {
	
	private static final DataModelValidator SINGLETON = new DataModelValidator();
	
	public static DataModelValidator getDefault() {
		return SINGLETON;
	}

	private DataModelValidator() {
		super(LogManager.create(new AbstractLogCategory() {
			
			@Override
			public Package getPackage() {
				return AbstractRookitModel.class.getPackage();
			}
			
			@Override
			public String getName() {
				return "RookitDataModel";
			}
		}));
	}
	
	/**
	 * Validates a disc. This method must be used before accessing a disc.
	 * 
	 * This method will throw an exception ({@link NoSuchDiscException} wrapped in a {@link MlmException}) if:
	 * <ul>
	 * <li>The disc is null
	 * </ul>
	 * 
	 * @param disc disc instance
	 * @param discName disc name that is being accessed
	 * @param albumTitle album title
	 */
	public <T> T checkDiscNotNull(final Disc disc, final String discName, final String albumTitle) {
		return checkArgumentNotNull(disc, "The disc " + discName + " was not found in album " + albumTitle);
	}
	
	public <T> T checkNotIntersecting(final Iterable<Artist> source, final Iterable<Artist> target, final String targetName) {
		checkArgumentNotNull(source, "The artist set cannot be null");
		final Collection<Artist> intersection = CollectionUtils.intersection(source, target);
		
		return checkArgumentEmptyCollection(intersection, "Artists " + intersection.toString() + " are already defined as " + targetName);
	}

	public <T> T checkSumIs(final Collection<Float> values, final int i) {
		final double sum = values.stream()
				.mapToDouble(Float::doubleValue)
				.sum();
		
		return checkArgument(sum != i, "The sum of all values must be " + i);
	}

}
