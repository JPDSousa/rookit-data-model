package org.rookit.dm.utils.bistream;

import org.rookit.dm.utils.factory.RookitFactory;

@SuppressWarnings("javadoc")
public interface BiStreamFoF {

	RookitFactory<BiStream> getBiStreamFactory();
	
	void setBiStreamFactory(RookitFactory<BiStream> factory);
}
