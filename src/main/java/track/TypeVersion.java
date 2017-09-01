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
package track;

@SuppressWarnings("javadoc")
public enum TypeVersion {
	
	EXTENDED("Extended", new String[]{"extended"}),
	VIDEO("Video Clip", new String[]{"video"}),
	PREVIEW("Preview", new String[]{"preview"}),
	INSTRUMENTAL("Instrumental", new String[]{"instrumental"}),
	INTERLUDE("Interlude", new String[]{"interlude"}),
	BONUS("Bonus Track", new String[]{"bonus"}),
	RADIO("Radio Edit", new String[]{"radio edit"}),
	ACOUSTIC("Acoustic Version", new String[]{"acoustic"}),
	ALTERNATIVE("Alternative Version", new String[]{"alternative"}),
	DEMO("Demo Version", new String[]{"demo"}),
	SECOND("Second Version", new String[]{"second"}),
	LIVE("Live", new String[]{"(live", " live"}),
	REMIX("Remix", new String[]{"remix", "flip", "edition", "edit", "reboot"}),
	COVER("Cover", new String[]{"cover"});
	
	private final String name;
	private final String[] tokens;
	
	private TypeVersion(String name, String[] tokens){
		this.name = name;
		this.tokens = tokens;
	}

	public String getName() {
		return name;
	}

	public String[] getTokens() {
		return tokens;
	}
	
	@Override
	public String toString(){
		return getName();
	}

	public static TypeVersion parse(String token){
		TypeVersion type = null;
		
		for(TypeVersion t : values()){
			for(String tt : t.getTokens()){
				if(tt.equalsIgnoreCase(token)){
					type = t;
					break;
				}
			}
			if(type != null){
				break;
			}
		}
		return type;
	}
}
