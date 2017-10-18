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
import org.apache.logging.log4j.Logger;
import org.rookit.dm.album.AbstractAlbum.Disc;
import org.rookit.dm.artist.Artist;
import org.rookit.utils.log.Errors;
import org.rookit.utils.log.Logs;
import org.rookit.utils.log.Validator;

@SuppressWarnings("javadoc")
public class DataModelValidator extends Validator {
	
	private static final DataModelValidator SINGLETON = new DataModelValidator(Logs.DM.getLogger());
	
	public static DataModelValidator getDefault() {
		return SINGLETON;
	}

	private DataModelValidator(Logger logger) {
		super(logger);
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
	public void checkDiscNotNull(Disc disc, String discName, String albumTitle) {
		checkArgumentNotNull(disc, "The disc " + discName + " was not found in album " + albumTitle);
	}
	
	public void checkValidFeatures(Iterable<Artist> features, Iterable<Artist> mainArtists) {
		checkArgumentNotNull(features, "The feature artists set cannot be null");
		final Collection<Artist> intersection = CollectionUtils.intersection(features, mainArtists);
		checkArgumentEmptyCollection(intersection, "Artists " + intersection.toString() + " are already defined as main artist");
	}
	
	public void handleException(RuntimeException e) {
		Errors.handleException(e, Logs.CORE);
	}

}
