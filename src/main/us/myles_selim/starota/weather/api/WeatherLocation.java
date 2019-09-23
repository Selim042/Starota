package us.myles_selim.starota.weather.api;

public class WeatherLocation {

	protected WeatherLocation() {}

	private int Version;
	private String Key;
	private String Type;
	private int Rank;

	private String LocalizedName;
	private String EnglishName;
	private String PrimaryPostalCode;

	private WeatherRegion Region;
	private WeatherRegion Country;
	private WeatherAdminArea AdministrativeArea;

	private WeatherTimezone TimeZone;
	private WeatherGeoPosition GeoPosition;

	private boolean IsAlias;

	private WeatherSupplementAdminArea[] SupplementalAdminAreas;

	private String[] DataSets;

	public String getNameWithAreas() {
		StringBuilder out = new StringBuilder(getLocalizedName());
		if (getAdministrativeArea() != null)
			out.append(", " + getAdministrativeArea().getLocalizedName());
		out.append(", " + getCountry().getLocalizedName());
		return out.toString();
	}

	public int getVersion() {
		return Version;
	}

	public String getKey() {
		return Key;
	}

	public String getType() {
		return Type;
	}

	public int getRank() {
		return Rank;
	}

	public String getLocalizedName() {
		return LocalizedName;
	}

	public String getEnglishName() {
		return EnglishName;
	}

	public String getPrimaryPostalCode() {
		return PrimaryPostalCode;
	}

	public WeatherRegion getRegion() {
		return Region;
	}

	public WeatherRegion getCountry() {
		return Country;
	}

	public WeatherAdminArea getAdministrativeArea() {
		return AdministrativeArea;
	}

	public WeatherTimezone getTimeZone() {
		return TimeZone;
	}

	public WeatherGeoPosition getGeoPosition() {
		return GeoPosition;
	}

	public boolean isIsAlias() {
		return IsAlias;
	}

	public WeatherSupplementAdminArea[] getSupplementalAdminAreas() {
		return SupplementalAdminAreas;
	}

	public String[] getDataSets() {
		return DataSets;
	}

}
