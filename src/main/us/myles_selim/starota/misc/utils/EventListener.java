package us.myles_selim.starota.misc.utils;

import discord4j.core.event.domain.Event;

public interface EventListener<T extends Event> {

	Class<T> getEventType();

	void execute(T event);

}
