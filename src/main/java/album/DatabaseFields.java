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
package album;

/**
 * Interface that stores the database fields metadata for Albums.
 * @author Joao
 *
 */
public interface DatabaseFields {
		
	/**
	 * Database field name for the album type
	 */
	String TYPE = "type";

	/**
	 * Database field name for the album title
	 */
	String TITLE = "title";

	/**
	 * Database name for the release type
	 */
	String RELEASE_TYPE = "release_type";

	/**
	 * Database name for the album artists
	 */
	String ARTISTS = "artists";

	/**
	 * Database name for the release date
	 */
	String RELEASE_DATE = "release_date";

	/**
	 * Database name for the album cover
	 */
	String COVER = "cover";

	/**
	 * Database name for the album tracks
	 */
	String TRACKS = "tracks";

	/**
	 * Database name for the album discs
	 */
	String DISCS = "discs";
}
