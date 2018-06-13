package org.rookit.dm.bistream;

import org.rookit.api.bistream.BiStream;
import org.rookit.api.bistream.BiStreamFactory;

import java.nio.file.Path;

public interface FileBiStreamFactory extends BiStreamFactory<FileBiStreamKey> {

    BiStream create(Path relativePath);

}
