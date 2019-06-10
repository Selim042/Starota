package us.myles_selim.starota.commands.settings.types;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TimeZone;

import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.commands.settings.Setting;
import us.myles_selim.starota.commands.settings.types.SettingTimeZone.TimeZoneWrapper;

public class SettingTimeZone extends Setting<TimeZoneWrapper> {

	private static final Method GET_TIMEZONE;

	static {
		try {
			GET_TIMEZONE = TimeZone.class.getDeclaredMethod("getTimeZone", String.class, boolean.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
		GET_TIMEZONE.setAccessible(true);
	}

	public SettingTimeZone(String name) {
		super(name, (TimeZoneWrapper) null);
	}

	public SettingTimeZone(String name, String desc) {
		super(name, desc, null);
	}

	public SettingTimeZone(String name, TimeZoneWrapper value) {
		super(name, value);
	}

	public SettingTimeZone(String name, String desc, TimeZoneWrapper value) {
		super(name, desc, value);
	}

	public SettingTimeZone(SettingTimeZone setting) {
		super(setting);
	}

	@Override
	protected TimeZoneWrapper getEmptyValue() {
		return new TimeZoneWrapper(null);
	}

	@Override
	public boolean setValue(String str) {
		try {
			TimeZone tz = (TimeZone) GET_TIMEZONE.invoke(null, str, false);
			if (tz != null) {
				setValue(new TimeZoneWrapper(tz));
				return true;
			}
			return false;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return false;
		}
	}

	@Override
	public Class<TimeZoneWrapper> getType() {
		return TimeZoneWrapper.class;
	}

	@Override
	public void toBytes(Storage stor) {
		if (getValue() == TimeZoneWrapper.NULL_VALUE)
			stor.writeString("null");
		else
			stor.writeString(getValue().getID());
	}

	@Override
	public void fromBytes(Storage stor) {
		String tz = stor.readString();
		if (tz.equals("null"))
			setValue(TimeZoneWrapper.NULL_VALUE);
		else
			setValue(new TimeZoneWrapper(TimeZone.getTimeZone(tz)));
	}

	public static class TimeZoneWrapper {

		public static final TimeZoneWrapper NULL_VALUE = new TimeZoneWrapper(null);

		public final TimeZone tz;

		public TimeZoneWrapper(TimeZone tz) {
			this.tz = tz;
		}

		public String getID() {
			if (tz == null)
				return "null";
			return tz.getID();
		}

		@Override
		public String toString() {
			return this.tz == null ? "null" : this.tz.getDisplayName();
		}

	}

}
