package org.rookit.dm.utils.bistream;

import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings("javadoc")
public interface BiStream {

	InputStream toInput();
	
	OutputStream toOutput();
	
	boolean isEmpty();
}
