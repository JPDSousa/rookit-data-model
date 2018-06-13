package org.rookit.dm.album.disc.config;

import org.rookit.utils.config.Configuration;

import java.util.Optional;

public interface DiscConfig extends Configuration {

    Optional<String> defaultName();

}
