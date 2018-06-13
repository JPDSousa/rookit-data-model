package org.rookit.dm.bistream;

import org.immutables.value.Value;

@FunctionalInterface
@Value.Immutable
public interface FileStorageConfig {

    String getPath();
}
