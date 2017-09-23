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
package org.rookit.dm.track;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rookit.dm.track.TypeTrack;

import static org.rookit.dm.track.TypeTrack.*;

@SuppressWarnings("javadoc")
public class TypeTrackTest {
	
	@Test
	public void testTrackClass(){
		for(TypeTrack t : values()){
			assertNotNull(TypeTrack.class.getName()+" "+t.name()+"'s track class is not defined!", t.getTrackClass());
		}
	}
	
	@Test
	public final void testGetByClass() {
		for(TypeTrack t : values()) {
			assertEquals(t, getByClass(t.getTrackClass()));
		}
	}
	
	@Test
	public final void testGetName() {
		for(TypeTrack t : values()) {
			assertNotNull(t.getName());
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testGetByName() {
		assertEquals(ORIGINAL, getByName("ORIGINAL"));
		assertEquals(VERSION, getByName("REMIX"));
		assertEquals(VERSION, getByName("COVER"));
	}

}
