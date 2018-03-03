package org.rookit.dm.play;

import org.mongodb.morphia.annotations.Entity;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.play.DynamicPlaylist;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.play.TypePlaylist;
import org.rookit.api.dm.play.factory.PlaylistFactory;
import org.rookit.api.dm.track.audio.TrackKey;
import org.rookit.api.dm.track.audio.TrackMode;
import org.rookit.api.storage.DBManager;
import org.rookit.api.storage.queries.TrackQuery;
import org.rookit.api.storage.utils.Order;
import org.rookit.api.storage.utils.Order.TypeOrder;
import org.rookit.utils.VoidUtils;

import com.google.common.base.Optional;

@SuppressWarnings("javadoc")
@Entity("Playlist")
public class DynamicPlaylistImpl extends AbstractPlaylist implements DynamicPlaylist {

	private static final short UNINITIALIZED = -1;
	
	private Boolean onlyPlayable;
	
	// Audio features
	private short bpm;
	private short bpmGap;

	private TrackKey trackKey;

	private TrackMode trackMode;

	private Boolean isInstrumental;

	private Boolean isLive;

	private Boolean isAcoustic;

	private double danceability;
	private float danceabilityGap;

	private double energy;
	private float energyGap;

	private double valence;
	private float valenceGap;
	
	@SuppressWarnings("unused")
	@Deprecated
	private DynamicPlaylistImpl() {
		this(null, null);
	}

	public DynamicPlaylistImpl(final String name, final BiStream picture) {
		super(TypePlaylist.DYNAMIC, name, picture);
		bpm = UNINITIALIZED;
		bpmGap = 10;
		
		danceability = UNINITIALIZED;
		danceabilityGap = 0.2f;
		
		energy = UNINITIALIZED;
		energyGap = 0.2f;
		
		valence = UNINITIALIZED;
		valenceGap = 0.2f;
	}

	@Override
	public Optional<Short> getBPM() {
		return bpm != UNINITIALIZED ? Optional.of(bpm) 
				: Optional.absent();
	}

	@Override
	public Void setBPM(final short bpm) {
		VALIDATOR.checkArgumentBetween(bpm, RANGE_BPM, "bpm");
		this.bpm = bpm;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<TrackKey> getTrackKey() {
		return Optional.fromNullable(this.trackKey);
	}

	@Override
	public Void setTrackKey(final TrackKey trackKey) {
		VALIDATOR.checkArgumentNotNull(trackKey, "track key cannot be null");
		this.trackKey = trackKey;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<TrackMode> getTrackMode() {
		return Optional.fromNullable(this.trackMode);
	}

	@Override
	public Void setTrackMode(final TrackMode trackMode) {
		this.trackMode = trackMode;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<Boolean> isInstrumental() {
		return Optional.fromNullable(this.isInstrumental);
	}

	@Override
	public Void setInstrumental(final boolean isInstrumental) {
		this.isInstrumental = isInstrumental;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<Boolean> isLive() {
		return Optional.fromNullable(this.isLive);
	}

	@Override
	public Void setLive(final boolean isLive) {
		this.isLive = isLive;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<Boolean> isAcoustic() {
		return Optional.fromNullable(this.isAcoustic);
	}

	@Override
	public Void setAcoustic(final boolean isAcoustic) {
		this.isAcoustic = isAcoustic;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<Double> getDanceability() {
		return this.danceability != UNINITIALIZED ? Optional.of(this.danceability)
				: Optional.absent();
	}

	@Override
	public Void setDanceability(final double danceability) {
		this.danceability = danceability;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<Double> getEnergy() {
		return this.energy != UNINITIALIZED ? Optional.of(this.energy)
				: Optional.absent();
	}

	@Override
	public Void setEnergy(final double energy) {
		this.energy = energy;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<Double> getValence() {
		return this.valence != UNINITIALIZED ? Optional.of(this.valence)
				: Optional.absent();
	}

	@Override
	public Void setValence(final double valence) {
		this.valence = valence;
		return VoidUtils.returnVoid();
	}

	private void setValence(final TrackQuery query) {
		getValence()
		.transform(valence -> query.withValence(valence-(valenceGap/2), valence+(valenceGap/2)));
	}

	private void setDanceability(final TrackQuery query) {
		getDanceability()
		.transform(danceability -> query.withDanceability(danceability-(danceabilityGap/2), danceability+(danceabilityGap/2)));
	}

	private void setEnergy(final TrackQuery query) {
		getEnergy()
		.transform(energy -> query.withEnergy(energy-(energyGap/2), energy+(energyGap/2)));
	}

	private void setAcoustic(final TrackQuery query) {
		isAcoustic().transform(query::withAcoustic);
	}

	private void setLive(final TrackQuery query) {
		isLive().transform(query::withLive);
	}

	private void setInstrumental(final TrackQuery query) {
		isInstrumental().transform(query::withInstrumental);
	}

	private void setTrackMode(final TrackQuery query) {
		getTrackMode().transform(query::withTrackMode);
	}

	private void setTrackKey(final TrackQuery query) {
		getTrackKey().transform(query::withTrackKey);
	}

	private void setBpm(final TrackQuery query) {
		getBPM().transform(bpm -> query.withBPM((short) (bpm-(bpmGap/2)), (short) (bpm+(bpmGap/2))));
	}

	@Override
	public StaticPlaylist freeze(final DBManager db, final int limit) {
		final PlaylistFactory playlistFactory = db.getFactories().getPlaylistFactory();
		final StringBuilder frozenName = new StringBuilder(getName())
				.append("_f_")
				.append(System.currentTimeMillis());
		final StaticPlaylist frozenPlaylist = playlistFactory.createStaticPlaylist(
				frozenName.toString());
		applyQuery(db.getTracks()).stream()
		.limit(limit)
		.forEach(frozenPlaylist::add);
		return frozenPlaylist;
	}

	@Override
	public TrackQuery applyQuery(final TrackQuery query) {
		final Order order = query.newOrder();
		order.addField(PLAYS, TypeOrder.DSC);
		setBpm(query);
		setTrackKey(query);
		setTrackMode(query);
		setInstrumental(query);
		setLive(query);
		setAcoustic(query);
		setDanceability(query);
		setEnergy(query);
		setValence(query);
		setOnlyPlayable(query);
		
		// TODO use other props
		// TODO order by plays[DSC], release date[DSC]
		return query.order(order);
	}

	private void setOnlyPlayable(final TrackQuery query) {
		isOnlyPlayable().transform(query::withPath);
	}

	@Override
	public Void setOnlyPlayable(final boolean onlyPlayable) {
		this.onlyPlayable = onlyPlayable;
		return VoidUtils.returnVoid();
	}
	
	@Override
	public Optional<Boolean> isOnlyPlayable() {
		return Optional.fromNullable(this.onlyPlayable);
	}

}
