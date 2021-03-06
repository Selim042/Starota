package us.myles_selim.starota.misc.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import discord4j.core.event.EventDispatcher;
import discord4j.core.event.domain.Event;

public interface EventListener {

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface EventSubscriber {}

	/***
	 * @deprecated Call, don't override
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Deprecated
	public default void setup(EventDispatcher dispatch) {
		for (Method m : getClass().getDeclaredMethods()) {
			if (!m.isAnnotationPresent(EventSubscriber.class))
				continue;
			Class<?>[] paramTypes = m.getParameterTypes();
			if (paramTypes.length == 1 && Event.class.isAssignableFrom(paramTypes[0])) {
				dispatch.on((Class) paramTypes[0]).subscribe((Object obj) -> {
					try {
						m.invoke(this, obj);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						throw new EventHandleException(paramTypes[0], e);
					}
				});
			}
		}
	}

	static class EventHandleException extends RuntimeException {

		private static final long serialVersionUID = 6470933899853379423L;

		private final Class<?> event;

		public EventHandleException(Class<?> event, Exception e) {
			super(e);
			this.event = event;
		}

		@Override
		public String getMessage() {
			Throwable cause = this.getCause();
			return String.format("%s thrown in %s handler", cause.getClass().getName(), event.getName());
		}

	}

}
