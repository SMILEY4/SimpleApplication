package de.ruegnerlukas.simpleapplication.common.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Channel {


	private enum ChannelType {
		/**
		 * Identified by a name.
		 */
		NAME,

		/**
		 * Identified by a type.
		 */
		TYPE;
	}






	/**
	 * Constant value used in hash code generation.
	 */
	private static final int HASH_CONSTANT = 31;

	/**
	 * The identifier of this channel as a string
	 */
	private final String name;

	/**
	 * The identifier of this channel as a type
	 */
	private final Class<?> type;

	/**
	 * The type of this channel / which identifier to use
	 */
	private final ChannelType channelType;




	/**
	 * Returns a {@link Channel} identified by a string.
	 *
	 * @param name the name of the channel
	 * @return the channel
	 */
	public static Channel name(final String name) {
		return new Channel(name, null, ChannelType.NAME);
	}




	/**
	 * Returns a {@link Channel} identified by a type.
	 *
	 * @param type the type of the channel / events in the channel
	 * @return the channel
	 */
	public static Channel type(final Class<?> type) {
		return new Channel(null, type, ChannelType.TYPE);

	}




	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Channel other = (Channel) o;
		if (this.getChannelType() != other.getChannelType()) {
			return false;
		}
		if (getChannelType() == ChannelType.NAME) {
			return getName().equals(other.getName());
		} else {
			return getType().equals(other.getType());
		}
	}




	@Override
	public int hashCode() {
		int result = getName() != null ? getName().hashCode() : 0;
		result = HASH_CONSTANT * result + (getType() != null ? getType().hashCode() : 0);
		result = HASH_CONSTANT * result + getChannelType().hashCode();
		return result;
	}

}
