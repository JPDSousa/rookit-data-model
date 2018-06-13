package org.rookit.dm.album.slot;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.rookit.api.dm.album.slot.TrackSlotFactory;

public final class TrackSlotModule extends AbstractModule {

    private static final Module MODULE = new TrackSlotModule();

    public static Module getModule() {
        return MODULE;
    }

    private TrackSlotModule() {}

    @Override
    protected void configure() {
        bind(TrackSlotFactory.class).to(ImmutableTrackSlotFactory.class);
    }
}
