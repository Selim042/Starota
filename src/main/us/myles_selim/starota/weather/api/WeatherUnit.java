package us.myles_selim.starota.weather.api;

public class WeatherUnit {

	protected WeatherUnit() {}

	private double Value;
	private String Unit;
	private int UnitType;

	public double getValue() {
		return Value;
	}

	public String getUnit() {
		return Unit;
	}

	public int getUnitType() {
		return UnitType;
	}

}
