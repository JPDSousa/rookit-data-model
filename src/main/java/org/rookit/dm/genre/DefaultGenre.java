/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.dm.genre;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.storage.DBManager;
import org.rookit.api.storage.queries.TrackQuery;
import org.rookit.api.storage.utils.Order;
import org.rookit.api.storage.utils.Order.TypeOrder;
import org.rookit.dm.play.able.AbstractPlayable;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.VoidUtils;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Generated;
import com.google.common.base.MoreObjects;

@Entity("Genre")
class DefaultGenre extends AbstractPlayable implements Genre {

	private static final String NO_DESCRIPTION = StringUtils.EMPTY;

	private static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	
	@Indexed(options = @IndexOptions(unique = true))
	private final String name;
	
	private String description;
	
	@SuppressWarnings("unused")
	@Deprecated
	private DefaultGenre() {
		this(NO_DESCRIPTION);
	}

	DefaultGenre(final String name) {
		this.name = name;
		resetDescription();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.ofNullable(this.description)
				.filter(description -> description != NO_DESCRIPTION);
	}

	@Override
	public Void setDescription(final String description) {
		VALIDATOR.checkArgument().isNotEmpty(description, "description");
		this.description = description;
		return VoidUtils.returnVoid();
	}
	
	@Override
	public Void resetDescription() {
		this.description = NO_DESCRIPTION;
		return VoidUtils.returnVoid();
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public String toString() {
		return MoreObjects.toStringHelper(this).add("super", super.toString()).add("name", name)
				.add("description", description).toString();
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public int hashCode() {
		return Objects.hash(super.hashCode(), name, description);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public boolean equals(Object object) {
		if (object instanceof DefaultGenre) {
			if (!super.equals(object))
				return false;
			DefaultGenre that = (DefaultGenre) object;
			return Objects.equals(this.name, that.name) && Objects.equals(this.description, that.description);
		}
		return false;
	}

	@Override
	public StaticPlaylist freeze(final DBManager db, final int limit) {
		final StaticPlaylist playlist = db.getFactories()
				.getPlaylistFactory()
				.createStaticPlaylist(getName());
		final Order order = db.newOrder();
		order.addField(PLAYS, TypeOrder.DSC);
		final TrackQuery query = db.getTracks()
				.withGenre(this)
				.order(order);
		query.stream().limit(limit).forEach(playlist::addTrack);
		return playlist;
	}
	
}
