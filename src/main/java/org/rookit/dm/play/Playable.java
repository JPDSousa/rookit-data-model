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
package org.rookit.dm.play;

import java.time.LocalDate;

@SuppressWarnings("javadoc")
public interface Playable {
	
	String PLAYS = "plays";
	String LAST_PLAYED = "last_played";
	String SKIPPED = "skipped";
	String LAST_SKIPPED = "last_skipped";
	String DURATION = "duration";

	public long getPlays();
	public void play();
	public void setPlays(long plays);
	
	public LocalDate getLastPlayed();
	public void setLastPlayed(LocalDate lastPlayed);
	
	public long getSkipped();
	public void skip();
	public void setSkipped(long skipped);
	
	public LocalDate getLastSkipped();
	public void setLastSkipped(LocalDate lastSkipped);
	
	void setDuration(long duration);
	long getDuration();
}
