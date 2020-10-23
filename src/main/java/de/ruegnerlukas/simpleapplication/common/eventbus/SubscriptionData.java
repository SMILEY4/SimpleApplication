package de.ruegnerlukas.simpleapplication.common.eventbus;

import de.ruegnerlukas.simpleapplication.common.utils.HashCode;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.TagConditionExpression;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import lombok.Getter;

import java.util.Objects;

public final class SubscriptionData<T> {


	/**
	 * The type of the events to subscribe to. Object.class by default.
	 */
	@Getter
	private final Class<T> type;

	/**
	 * The tag filter applied to the tags of the event
	 */
	@Getter
	private TagConditionExpression filter = Tags.constant(true);

	/**
	 * The thread mode of the subscriber. {@link ThreadMode#POSTING} by default.
	 */
	@Getter
	private ThreadMode threadMode = ThreadMode.POSTING;




	/**
	 * @param type the type of the events to subscribe to
	 */
	private SubscriptionData(final Class<T> type) {
		this.type = type;
	}




	/**
	 * Create a new subscriber meta for a subscriber to events of the given type
	 *
	 * @param type the type of the events to subscribe to
	 * @param <T>  the generic type of the events
	 * @return a new subscriber meta
	 */
	public static <T> SubscriptionData<T> ofType(final Class<T> type) {
		return new SubscriptionData<>(type);
	}




	/**
	 * Create a new subscriber meta for a subscriber to events of the given type and with the given filter
	 *
	 * @param type   the type of the events to subscribe to
	 * @param filter the filter to apply to the tags of published events
	 * @param <T>    the generic type of the events
	 * @return a new subscriber meta
	 */
	public static <T> SubscriptionData<T> ofType(final Class<T> type, final TagConditionExpression filter) {
		return new SubscriptionData<>(type).filter(filter);
	}




	/**
	 * Create a new subscriber meta for a subscriber to events of any type
	 *
	 * @return a new subscriber meta
	 */
	public static SubscriptionData<Object> anyType() {
		return new SubscriptionData<>(Object.class);
	}




	/**
	 * Create a new subscriber meta for a subscriber to events of any type and with the given filter
	 *
	 * @param filter the filter to apply to the tags of published events
	 * @return a new subscriber meta
	 */
	public static SubscriptionData<Object> anyType(final TagConditionExpression filter) {
		return new SubscriptionData<>(Object.class).filter(filter);
	}




	/**
	 * @param filter the filter to apply to the tags of published events
	 * @return this subscriber meta for chaining
	 */
	public SubscriptionData<T> filter(final TagConditionExpression filter) {
		this.filter = filter;
		return this;
	}




	/**
	 * @param threadMode the thread mode of the subscriber
	 * @return this subscriber meta for chaining
	 */
	public SubscriptionData<T> threadMode(final ThreadMode threadMode) {
		this.threadMode = threadMode;
		return this;
	}




	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final SubscriptionData<?> that = (SubscriptionData<?>) o;
		if (!Objects.equals(this.getType(), that.getType())) {
			return false;
		}
		if (!Objects.equals(this.getFilter(), that.getFilter())) {
			return false;
		}
		return getThreadMode() == that.getThreadMode();
	}




	@Override
	public int hashCode() {
		int result = getType() != null ? getType().hashCode() : 0;
		result = HashCode.CONSTANT * result + (getFilter() != null ? getFilter().hashCode() : 0);
		result = HashCode.CONSTANT * result + (getThreadMode() != null ? getThreadMode().hashCode() : 0);
		return result;
	}

}
