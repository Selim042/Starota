package us.myles_selim.starota.weather.api;

public class WeatherCeiling {

	protected WeatherCeiling() {}

	private int Value;
	private String Unit;
	private int UnitType;

	public int getValue() {
		return Value;
	}

	public String getUnit() {
		return Unit;
	}

	public int getUnitType() {
		return UnitType;
	}

}
