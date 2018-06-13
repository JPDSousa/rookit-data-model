package org.rookit.dm.bistream;

import org.immutables.value.Value;
import org.rookit.api.dm.key.Key;

import java.nio.file.Path;
import java.util.Optional;

@FunctionalInterface
@Value.Immutable
public interface FileBiStreamKey extends Key {

    Optional<Path> path();
}
