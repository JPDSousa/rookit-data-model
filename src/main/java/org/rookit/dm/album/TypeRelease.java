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
package org.rookit.dm.album;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.rookit.dm.utils.DataModelValidator;

/**
 * @author Joao
 * 
 * This enumeration keeps the possible release types for an album.
 * 
 * <p>The release type is part of an album signature. It assures that there can
 * be two albums from the same set of artists with the same names, as long as
 * they represent different release types. 
 */
public enum TypeRelease {

	/**
	 * Represents the standard release type.
	 * @see <a href="https://en.wikipedia.org/wiki/Album">Wikipedia</a>
	 */
	STUDIO("%s", new String[]{}),
	/**
	 * Represents an EP (Extended Play) Album.
	 * @see <a href="https://en.wikipedia.org/wiki/Extended_play">Wikipedia</a>
	 */
	EP("%s (EP)", new String[]{" (EP)", " EP"}),
	/**
	 * Represents a LP (Long Play) Album.
	 * @see <a href="https://en.wikipedia.org/wiki/LP_record">Wikipedia</a>
	 */
	LP("%s (LP)", new String[]{" (LP)", " LP", " (Vinyl)", " VINYL"}),
	/**
	 * Represents a single.
	 * @see <a href="https://en.wikipedia.org/wiki/Single_(music)">Wikipedia</a>
	 */
	SINGLE("%s (Single)", new String[]{" (Single)", " SINGLE"}),
	/**
	 * Represents a promotional album.
	 * @see <a href="https://en.wikipedia.org/wiki/Promotional_recording">Wikipedia</a>
	 */
	PROMO("%s (Promo)", new String[]{" (Promo)", " PROMO"}),
	/**
	 * Represents an album recorded live.
	 */
	LIVE("%s (Live)", new String[]{" (Live)", " LIVE"}),
	/**
	 * Represents a Best Of (aka Greatest Hits) album.
	 * @see <a href="https://en.wikipedia.org/wiki/Greatest_hits_album">Wikipedia</a>
	 */
	BESTOF("%s (Best Of)", new String[]{" (Best Of)", "Bestof", " BEST OF"}),
	/**
	 * Represents a Remix Album.
	 * @see <a href="https://en.wikipedia.org/wiki/Remix_album">Wikipedia</a>
	 */
	REMIXES("%s (The Remixes)", new String[]{" (The Remixes)", "(Remixes)", " REMIXES"}),
	COVERS("%s (Covers)", new String[]{" (The Covers)", "Cover", " COVERS"}),
	/**
	 * Represents an OST.
	 * @see <a href="https://en.wikipedia.org/wiki/Soundtrack">Wikipedia</a>
	 */
	SOUNDTRACK("%s Soundtrack", new String[]{" (Soundtrack)", " OST", " soundtrack"}),
	/**
	 * Represents a Tribute Album.
	 * @see <a href="https://en.wikipedia.org/wiki/Album#Tribute_or_cover">Wikipedia</a>
	 */
	TRIBUTE("%s (Tribute)", new String[]{"(Tribute)", "Tribute"}),

	/**
	 * Represents a deluxe edition of an album.
	 * @see <a href="https://en.wikipedia.org/wiki/Special_edition#Music">Wikipedia</a>
	 */
	DELUXE("%s (Deluxe Edition)", new String[]{" (Deluxe Edition)", "(Deluxe)", " DELUXE EDITION"}),
	/**
	 * Represents a limited edition album.
	 */
	LIMITED("%s (Limited Edition)", new String[]{" (Limited Edition)", " LIMITED EDITION"}),
	/**
	 * Represents an iTunes special edition.
	 */
	ITUNES("%s (iTunes Edition)", new String[]{" (iTunes Edition)", " iTunes edition"}),
	/**
	 * Repsents a Japanese special edition.
	 */
	JAPANESE("%s (Japanese Edition)", new String[]{" (Japanese Edition)", " Japanese edition"}),
	/**
	 * Represents a German special edition.
	 */
	GERMAN("%s (German Edition)", new String[]{" (German Edition)", " German edition"}),
	/**
	 * Represents a compilation album.
	 * @see <a href="https://en.wikipedia.org/wiki/Compilation_album">Wikipedia</a>
	 */
	COMPILATION("%s (Compilation)", new String[]{" (Compilation Album)", "compilation"}),
	/**
	 * Represents a (declared) rare edition of an album, or a special album that contains
	 * rare material from the artist.
	 */
	RARITIES("%s (Rarities)", new String[]{" (Rarities)", "Rarities"});
	
	private static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	
	private final String nameFormat;
	private final String[] tokens;

	private TypeRelease(final String format, final String[] tokens){
		this.nameFormat = format;
		this.tokens = tokens;
	}

	/**
	 * Returns a formatted version of the album title passed as parameter
	 * with the release type included.
	 * 
	 * @param albumTitle album title
	 * @return album title with the release type included
	 */
	public String getFormattedName(final String albumTitle){
		return String.format(nameFormat, albumTitle);
	}

	/**
	 * Parses and returns the release type from the string passed
	 * as parameter.
	 * @param albumTitle album title
	 * @return the type {@link TypeRelease} extracted from the albumTitle
	 */
	public static TypeRelease getTypeRelease(final String albumTitle){
		return parseAlbumName(albumTitle, STUDIO).getLeft();
	}

	/**
	 * Parses the albumTitle as a pair of {@link TypeRelease} and {@link String}.
	 * 
	 * <p>e.g. <i>Title (The Remixes)</i> will return a pair with the string "Title"
	 * and {@link TypeRelease#REMIXES}.</p>
	 * 
	 * @param albumTitle title to be parsed
	 * @param defaultTypeRelease default type release, in case no matches are found.
	 * @return a {@link Pair} with the album title ({@link String}) and its release type {@link TypeRelease}).
	 */
	public static Pair<TypeRelease, String> parseAlbumName(final String albumTitle, final TypeRelease defaultTypeRelease) {
		Pair<TypeRelease, String> parseResult = Pair.of(defaultTypeRelease, albumTitle);
		int index;
		String name;

		for(TypeRelease type : TypeRelease.values()){
			for(String token : type.tokens){
				if(StringUtils.containsIgnoreCase(albumTitle, token)){
					index = StringUtils.indexOfIgnoreCase(albumTitle, token);
					try {
						name = albumTitle.substring(0, index) + albumTitle.substring(index+token.length());
						name = name.trim();
						parseResult = Pair.of(type, name);
					} catch (NullPointerException | IndexOutOfBoundsException e) {
						VALIDATOR.handleException(e);
					}
					break;
				}
			}
			if(parseResult.getLeft() != defaultTypeRelease){
				break;
			}
		}

		return parseResult;
	}

	@Override
	public String toString() {
		return super.toString().replace("_", " ");
	}
}
