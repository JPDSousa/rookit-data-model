package org.rookit.dm.album.tracks;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;
import org.rookit.api.dm.album.disc.Disc;
import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.album.tracks.AlbumTrackSlotsAdapter;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.album.disc.MutableDisc;
import org.rookit.dm.album.disc.MutableDiscFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.*;
import java.util.stream.Stream;

import static org.rookit.api.dm.album.AlbumFields.DISCS;
import static org.rookit.api.dm.album.AlbumFields.TRACKS;

@Embedded
@NotThreadSafe
final class MutableAlbumTracksImpl implements MutableAlbumTracks {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final Logger logger = VALIDATOR.getLogger(MutableAlbumTracksImpl.class);

    private static final long serialVersionUID = 3567307258980403147L;

    /**
     * Map of discs, containing the discs of the album Key ({@link String}) -
     * official of the disc Value ({@link Disc}}Disc) - disc object
     */
    @Embedded(DISCS)
    private final Map<String, MutableDisc> discs;

    @Property(TRACKS)
    private int tracks;

    private final transient MutableDiscFactory discFactory;

    MutableAlbumTracksImpl(final MutableDiscFactory discFactory) {
        this.discs = Maps.newHashMap();
        this.tracks = 0;
        this.discFactory = discFactory;
    }

    @Override
    public AlbumTrackSlotsAdapter asSlots() {
        return new AlbumTrackSlotsAdapterImpl(this.discs, this.tracks);
    }

    @Override
    public Optional<Disc> disc(final String discName) {
        return Optional.ofNullable(this.discs.get(discName));
    }

    @Override
    public boolean contains(final String discName) {
        return false;
    }

    @Override
    public boolean contains(final Track track) {
        VALIDATOR.checkArgument().isNotNull(track, "track");

        return this.discs.values().stream()
                .anyMatch(disc -> disc.contains(track));
    }

    @Override
    public Collection<Disc> getDiscs() {
        return Collections.unmodifiableCollection(this.discs.values());
    }

    @Override
    public Stream<Track> stream() {
        return this.discs.values().stream()
                .flatMap(Disc::stream);
    }

    @Override
    public int size() {
        return this.tracks;
    }

    @Override
    public void clearTracks() {
        this.discs.values().forEach(MutableDisc::clear);
        this.tracks = 0;
    }

    @Override
    public void removeTrack(final Track track) {
        VALIDATOR.checkArgument().isNotNull(track, "track");
        final int removedTracks = this.discs.values()
                .stream()
                .mapToInt(disc -> disc.remove(track))
                .sum();
        this.tracks-=removedTracks;
    }

    @Override
    public void removeTrack(final int number, final String disc) {
        VALIDATOR.checkArgument().isPositive(number, "primitive");
        VALIDATOR.checkArgument().isNotEmpty(disc, "disc");

        final Track removedTrack = getDisc(disc, false).remove(number);
        if (Objects.nonNull(removedTrack)) {
            this.tracks--;
        }
    }

    @Override
    public void relocate(final String discName, final int number, final String newDiscName, final int newNumber) {
        VALIDATOR.checkArgument().isNotEmpty(discName, "discName");
        VALIDATOR.checkArgument().isNotEmpty(newDiscName, "newDiscName");
        // discName and newDiscName are validated by disc()
        final MutableDisc oldDisc = getDisc(discName, false);
        if (Objects.equals(discName, newDiscName)) {
            oldDisc.relocate(number, newNumber);
            return;
        }

        final MutableDisc newDisc = getDisc(newDiscName, true);
        VALIDATOR.checkState().isNotContainedIn(newNumber, newDisc.asTrackMap(),
                "trackNumber", "disc");
        final Track track = oldDisc.remove(number);
        VALIDATOR.checkState().isNotNull(track, "there is no track in [%s|%s] to relocate");

        if (!newDisc.putIfAbsent(track, newNumber)) {
            logger.warn("A track was already contained in {}:{}. Reverting remove operation on {}:{}",
                    newDisc, newNumber, oldDisc, number);
            oldDisc.putIfAbsent(track, number);
        }
    }

    @Override
    public void addTrackSlot(final TrackSlot slot) {
        VALIDATOR.checkArgument().isNotNull(slot, "slot");

        final Optional<Track> trackOrNone = slot.track();
        if (trackOrNone.isPresent()) {
            addTrack(trackOrNone.get(), slot.number(), slot.discName());
        } else {
            logger.info("No track found for slot {}. Ignoring request to add a track", slot);
        }
    }

    @Override
    public void addTrack(final Track track, final int number, final String discName) {
        VALIDATOR.checkArgument().isNotNull(track, "track");
        VALIDATOR.checkArgument().isNotEmpty(discName, "discName");

        getDisc(discName, true).putIfAbsent(track, number);
    }

    @Override
    public void addTrackLast(final Track track, final String discName) {
        VALIDATOR.checkArgument().isNotNull(track, "track");
        VALIDATOR.checkArgument().isNotEmpty(discName, "discName");

        getDisc(discName, true).putNextEmpty(track);
    }

    private MutableDisc getDisc(final String discName, final boolean create) {
        MutableDisc disc = this.discs.get(discName);
        if (!create) {
            VALIDATOR.checkState()
                    .is(Objects.nonNull(disc), "The disc %s was not found in container: %s",
                            discName, this);
        } else if (Objects.isNull(disc)) {
            disc = this.discFactory.create(discName);
            this.discs.put(discName, disc);
        }
        return disc;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("discs", this.discs)
                .add("tracks", this.tracks)
                .add("discFactory", this.discFactory)
                .toString();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if ((object == null) || (getClass() != object.getClass())) return false;
        final MutableAlbumTracksImpl otherAlbumTracks = (MutableAlbumTracksImpl) object;
        return com.google.common.base.Objects.equal(this.discs, otherAlbumTracks.discs);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.discs);
    }
}
