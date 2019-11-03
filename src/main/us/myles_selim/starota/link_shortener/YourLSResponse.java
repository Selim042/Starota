package us.myles_selim.starota.link_shortener;

public class YourLSResponse {

	private YourLSURL url;
	private String status;
	private String message;
	private String title;
	private String shorturl;
	private int statuscCode;

	public YourLSURL getUrl() {
		return url;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public String getTitle() {
		return title;
	}

	public String getShortUrl() {
		return shorturl;
	}

	public int getStatuscCode() {
		return statuscCode;
	}

	public class YourLSURL {

		private String keyword;
		private String url;
		private String title;
		private String date;
		private String ip;

		private YourLSURL() {}

		public String getKeyword() {
			return keyword;
		}

		public String getUrl() {
			return url;
		}

		public String getTitle() {
			return title;
		}

		public String getDate() {
			return date;
		}

		public String getIp() {
			return ip;
		}

	}

}
