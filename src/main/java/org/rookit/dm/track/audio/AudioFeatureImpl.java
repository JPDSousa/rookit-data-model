package org.rookit.dm.track.audio;

import static org.rookit.api.dm.track.audio.AudioFeatureFields.ACOUSTIC;
import static org.rookit.api.dm.track.audio.AudioFeatureFields.BPM;
import static org.rookit.api.dm.track.audio.AudioFeatureFields.DANCEABILITY;
import static org.rookit.api.dm.track.audio.AudioFeatureFields.ENERGY;
import static org.rookit.api.dm.track.audio.AudioFeatureFields.INSTRUMENTAL;
import static org.rookit.api.dm.track.audio.AudioFeatureFields.KEY;
import static org.rookit.api.dm.track.audio.AudioFeatureFields.LIVE;
import static org.rookit.api.dm.track.audio.AudioFeatureFields.VALENCE;

import com.google.common.base.MoreObjects;
import com.kekstudio.musictheory.Key;

import java.util.Optional;
import java.util.OptionalDouble;

import org.mongodb.morphia.annotations.Property;
import org.rookit.api.dm.track.audio.AudioFeature;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.VoidUtils;
import org.rookit.utils.log.validator.Validator;
import org.rookit.utils.optional.OptionalBoolean;
import org.rookit.utils.optional.OptionalShort;

class AudioFeatureImpl implements AudioFeature {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    
    private static final short UNINITIALIZED = -1;
    private static final long serialVersionUID = -2259846715669020269L;

    // Audio features
    @Property(BPM)
    private short bpm;

    @Property(KEY)
    private Key trackKey;

    @Property(INSTRUMENTAL)
    private Boolean isInstrumental;

    @Property(LIVE)
    private Boolean isLive;

    @Property(ACOUSTIC)
    private Boolean isAcoustic;

    @Property(DANCEABILITY)
    private double danceability;

    @Property(ENERGY)
    private double energy;

    @Property(VALENCE)
    private double valence;
    
    AudioFeatureImpl() {
        this.bpm = UNINITIALIZED;
        this.danceability = UNINITIALIZED;
        this.energy = UNINITIALIZED;
        this.valence = UNINITIALIZED;
    }

    @Override
    public OptionalShort getBpm() {
        return (this.bpm == UNINITIALIZED)
                ? OptionalShort.empty()
                : OptionalShort.of(this.bpm);
    }

    @Override
    public OptionalDouble getDanceability() {
        return (this.danceability == UNINITIALIZED)
                ? OptionalDouble.empty()
                : OptionalDouble.of(this.danceability);
    }

    @Override
    public OptionalDouble getEnergy() {
        return (this.energy == UNINITIALIZED)
                ? OptionalDouble.empty()
                : OptionalDouble.of(this.energy);
    }
    
    @Override
    public Optional<Key> getTrackKey() {
        return Optional.ofNullable(this.trackKey);
    }
    
    @Override
    public OptionalDouble getValence() {
        return (this.valence == UNINITIALIZED)
                ? OptionalDouble.empty()
                : OptionalDouble.of(this.valence);
    }
    
    @Override
    public OptionalBoolean isAcoustic() {
        return OptionalBoolean.ofNullable(this.isAcoustic);
    }
    
    @Override
    public OptionalBoolean isInstrumental() {
        return OptionalBoolean.ofNullable(this.isInstrumental);
    }

    @Override
    public OptionalBoolean isLive() {
        return OptionalBoolean.ofNullable(this.isLive);
    }
    
    @SuppressWarnings({"UnnecessaryBoxing", "BooleanParameter"})
    @Override
    public Void setAcoustic(final boolean isAcoustic) {
        this.isAcoustic = Boolean.valueOf(isAcoustic);
        return VoidUtils.returnVoid();
    }
    
    @Override
    public Void setBpm(final short bpm) {
        VALIDATOR.checkArgument().isBetween(bpm, RANGE_BPM, "bpm");
        this.bpm = bpm;
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setDanceability(final double danceability) {
        VALIDATOR.checkArgument().isBetween(danceability, RANGE_DANCEABILITY, DANCEABILITY);
        this.danceability = danceability;
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setEnergy(final double energy) {
        VALIDATOR.checkArgument().isBetween(energy, RANGE_ENERGY, ENERGY);
        this.energy = energy;
        return VoidUtils.returnVoid();
    }
    
    @Override
    public Void setTrackKey(final Key trackKey) {
        VALIDATOR.checkArgument().isNotNull(trackKey, KEY);
        this.trackKey = trackKey;
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setValence(final double valence) {
        VALIDATOR.checkArgument().isBetween(valence, RANGE_VALENCE, VALENCE);
        this.valence = valence;
        return VoidUtils.returnVoid();
    }
    
    @SuppressWarnings({"BooleanParameter", "UnnecessaryBoxing"})
    @Override
    public Void setInstrumental(final boolean isInstrumental) {
        this.isInstrumental = Boolean.valueOf(isInstrumental);
        return VoidUtils.returnVoid();
    }

    @SuppressWarnings({"BooleanParameter", "UnnecessaryBoxing"})
    @Override
    public Void setLive(final boolean isLive) {
        this.isLive = Boolean.valueOf(isLive);
        return VoidUtils.returnVoid();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bpm", bpm)
                .add("trackKey", trackKey)
                .add("isInstrumental", isInstrumental)
                .add("isLive", isLive)
                .add("isAcoustic", isAcoustic)
                .add("danceability", danceability)
                .add("energy", energy)
                .add("valence", valence)
                .toString();
    }
}
