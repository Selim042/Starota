package us.myles_selim.starota.misc.data_types;

class EmbedObject {

	/**
	 * The title of the embed.
	 */
	public String title;
	/**
	 * The type of the embed.
	 */
	public String type;
	/**
	 * The description of the embed.
	 */
	public String description;
	/**
	 * The URL of the embed.
	 */
	public String url;
	/**
	 * The timestamp of the embed.
	 */
	public String timestamp;
	/**
	 * The side color of the embed.
	 */
	public int color;
	/**
	 * The footer of the embed.
	 */
	public FooterObject footer;
	/**
	 * The image of the embed.
	 */
	public ImageObject image;
	/**
	 * The thumbnail of the embed.
	 */
	public ThumbnailObject thumbnail;
	/**
	 * The video of the embed.
	 */
	public VideoObject video;
	/**
	 * The provider of the embed.
	 */
	public ProviderObject provider;
	/**
	 * The author of the embed.
	 */
	public AuthorObject author;
	/**
	 * The fields of the embed.
	 */
	public EmbedFieldObject[] fields;

	public EmbedObject() {}

	public EmbedObject(String title, String type, String description, String url, String timestamp,
			int color, FooterObject footer, ImageObject image, ThumbnailObject thumbnail,
			VideoObject video, ProviderObject provider, AuthorObject author, EmbedFieldObject[] fields) {
		this.title = title;
		this.type = type;
		this.description = description;
		this.url = url;
		this.timestamp = timestamp;
		this.color = color;
		this.footer = footer;
		this.image = image;
		this.thumbnail = thumbnail;
		this.video = video;
		this.provider = provider;
		this.author = author;
		this.fields = fields;
	}

	public EmbedObject(EmbedObject embed) {
		this.title = embed.title;
		this.type = embed.type;
		this.description = embed.description;
		this.url = embed.url;
		this.timestamp = embed.timestamp;
		this.color = embed.color;
		this.footer = new FooterObject(embed.footer);
		this.image = new ImageObject(embed.image);
		this.thumbnail = new ThumbnailObject(embed.thumbnail);
		this.video = new VideoObject(embed.video);
		this.provider = new ProviderObject(embed.provider);
		this.author = new AuthorObject(embed.author);
		this.fields = new EmbedFieldObject[embed.fields.length];
		for (int i = 0; i < this.fields.length; i++)
			this.fields[i] = new EmbedFieldObject(embed.fields[i]);
	}

	/**
	 * Represents a json thumbnail object.
	 */
	public static class ThumbnailObject {

		/**
		 * The URL of the thumbnail.
		 */
		public String url;
		/**
		 * The proxied URL of the thumbnail.
		 */
		public String proxy_url;
		/**
		 * The height of the thumbnail.
		 */
		public int height;
		/**
		 * The width of the thumbnail.
		 */
		public int width;

		public ThumbnailObject() {}

		public ThumbnailObject(String url, String proxy_url, int height, int width) {
			this.url = url;
			this.proxy_url = proxy_url;
			this.height = height;
			this.width = width;
		}

		public ThumbnailObject(ThumbnailObject obj) {
			this.url = obj.url;
			this.proxy_url = obj.proxy_url;
			this.height = obj.height;
			this.width = obj.width;
		}
	}

	/**
	 * Represents a json video object.
	 */
	public static class VideoObject {

		/**
		 * The URL of the video.
		 */
		public String url;
		/**
		 * The height of the video.
		 */
		public int height;
		/**
		 * The width of the video.
		 */
		public int width;

		public VideoObject() {}

		public VideoObject(String url, int height, int width) {
			this.url = url;

			this.height = height;
			this.width = width;
		}

		public VideoObject(VideoObject obj) {
			this.url = obj.url;

			this.height = obj.height;
			this.width = obj.width;
		}
	}

	/**
	 * Represents a json image object.
	 */
	public static class ImageObject {

		/**
		 * The URL of the image.
		 */
		public String url;
		/**
		 * The proxied URL of the image.
		 */
		public String proxy_url;
		/**
		 * The height of the image.
		 */
		public int height;
		/**
		 * The width of the image.
		 */
		public int width;

		public ImageObject() {}

		public ImageObject(String url, String proxy_url, int height, int width) {
			this.url = url;
			this.proxy_url = proxy_url;
			this.height = height;
			this.width = width;
		}

		public ImageObject(ImageObject obj) {
			this.url = obj.url;
			this.proxy_url = obj.proxy_url;
			this.height = obj.height;
			this.width = obj.width;
		}
	}

	/**
	 * Represents a json provider object.
	 */
	public static class ProviderObject {

		/**
		 * The name of the provider.
		 */
		public String name;
		/**
		 * The URL of the provider.
		 */
		public String url;

		public ProviderObject() {}

		public ProviderObject(String name, String url) {
			this.name = name;
			this.url = url;
		}

		public ProviderObject(ProviderObject obj) {
			this.name = obj.name;
			this.url = obj.url;
		}
	}

	/**
	 * Represents a json author object.
	 */
	public static class AuthorObject {

		/**
		 * The name of the author.
		 */
		public String name;
		/**
		 * The URL of the author.
		 */
		public String url;
		/**
		 * The URL of the author icon.
		 */
		public String icon_url;
		/**
		 * The proxied URL of the author icon.
		 */
		public String proxy_icon_url;

		public AuthorObject() {}

		public AuthorObject(String name, String url, String icon_url, String proxy_icon_url) {
			this.name = name;
			this.url = url;
			this.icon_url = icon_url;
			this.proxy_icon_url = proxy_icon_url;
		}

		public AuthorObject(AuthorObject obj) {
			this.name = obj.name;
			this.url = obj.url;
			this.icon_url = obj.icon_url;
			this.proxy_icon_url = obj.proxy_icon_url;
		}
	}

	/**
	 * Represents a json footer object.
	 */
	public static class FooterObject {

		/**
		 * The text in the footer.
		 */
		public String text;
		/**
		 * The URL of the icon in the footer.
		 */
		public String icon_url;
		/**
		 * The proxied URL of the icon in the footer.
		 */
		public String proxy_icon_url;

		public FooterObject() {}

		public FooterObject(String text, String icon_url, String proxy_icon_url) {
			this.text = text;
			this.icon_url = icon_url;
			this.proxy_icon_url = proxy_icon_url;
		}

		public FooterObject(FooterObject obj) {
			this.text = obj.text;
			this.icon_url = obj.icon_url;
			this.proxy_icon_url = obj.proxy_icon_url;
		}
	}

	/**
	 * Represents a json field object.
	 */
	public static class EmbedFieldObject {

		/**
		 * The name of the field.
		 */
		public String name;
		/**
		 * The content in the field.
		 */
		public String value;
		/**
		 * Whether the field should be displayed inline.
		 */
		public boolean inline;

		public EmbedFieldObject() {}

		public EmbedFieldObject(String name, String value, boolean inline) {
			this.name = name;
			this.value = value;
			this.inline = inline;
		}

		public EmbedFieldObject(EmbedFieldObject obj) {
			this.name = obj.name;
			this.value = obj.value;
			this.inline = obj.inline;
		}
	}

}
