package us.myles_selim.starota.weather.api;

public class WeatherTimezone {

	protected WeatherTimezone() {}

	private String Code;
	private String Name;
	private float GmtOffset;
	private boolean IsDaylightSaving;
	private String NextOffsetChange;

	public String getCode() {
		return Code;
	}

	public String getName() {
		return Name;
	}

	public float getGmtOffset() {
		return GmtOffset;
	}

	public boolean isIsDaylightSaving() {
		return IsDaylightSaving;
	}

	public String getNextOffsetChange() {
		return NextOffsetChange;
	}

}
