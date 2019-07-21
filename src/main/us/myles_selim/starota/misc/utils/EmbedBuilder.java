package us.myles_selim.starota.misc.utils;

import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import discord4j.core.spec.EmbedCreateSpec;

/**
 * Used to configure and build a {@link EmbedObject}.
 */
public class EmbedBuilder {

	public static final int FIELD_COUNT_LIMIT = 25;
	public static final int TITLE_LENGTH_LIMIT = 256;
	public static final int FIELD_CONTENT_LIMIT = 1024;
	public static final int DESCRIPTION_CONTENT_LIMIT = 2048;
	public static final int FOOTER_CONTENT_LIMIT = DESCRIPTION_CONTENT_LIMIT;
	public static final int AUTHOR_NAME_LIMIT = 256;
	public static final int MAX_CHAR_LIMIT = 6000;

	private String title;
	private String description;
	private String url;
	private Instant timestamp;
	private int color;
	private FooterObject footer;
	private ImageObject image;
	private ThumbnailObject thumbnail;
	private AuthorObject author;
	private List<EmbedFieldObject> fields = new ArrayList<>();

	private volatile boolean lenient = false;

	/**
	 * Sets whether the builder should sanitize input to prevent errors
	 * automatically.
	 *
	 * @param lenient
	 *            Whether the builder should sanitize input to prevent errors
	 *            automatically.
	 * @return The builder instance.
	 */
	public EmbedBuilder setLenient(boolean lenient) {
		this.lenient = lenient;

		return this;
	}

	/**
	 * Sets the title of the this.
	 *
	 * @param title
	 *            The title.
	 * @return The builder instance.
	 */
	public EmbedBuilder withTitle(String title) {
		if (title != null && title.trim().length() > TITLE_LENGTH_LIMIT)
			if (lenient)
				title = title.substring(0, TITLE_LENGTH_LIMIT);
			else
				throw new IllegalArgumentException(
						"Embed title cannot have more than " + TITLE_LENGTH_LIMIT + " characters");

		throwExceptionForCharacterLimit(title == null ? 4 : title.trim().length());

		this.title = title;
		return this;
	}

	/**
	 * Sets the description of the this.
	 *
	 * @param desc
	 *            The description.
	 * @return The builder instance.
	 */
	public EmbedBuilder withDescription(String desc) {
		if (desc != null && desc.trim().length() > DESCRIPTION_CONTENT_LIMIT) {
			if (lenient)
				desc = desc.substring(0, DESCRIPTION_CONTENT_LIMIT);
			else
				throw new IllegalArgumentException("Embed description cannot have more than "
						+ DESCRIPTION_CONTENT_LIMIT + " characters");
		}

		throwExceptionForCharacterLimit(desc == null ? 4 : desc.trim().length());

		this.description = desc;
		return this;
	}

	/**
	 * Sets the description of the this.
	 *
	 * @param desc
	 *            The description.
	 * @return The builder instance.
	 */
	public EmbedBuilder withDesc(String desc) {
		return withDescription(desc);
	}

	/**
	 * Appends to the description of the this.
	 *
	 * @param desc
	 *            The description.
	 * @return The builder instance.
	 */
	public EmbedBuilder appendDescription(String desc) {
		if (this.description == null)
			this.description = "";
		if (desc != null && (this.description + desc).trim().length() > DESCRIPTION_CONTENT_LIMIT) {
			if (lenient)
				desc = desc.substring(0, DESCRIPTION_CONTENT_LIMIT - this.description.length());
			else
				throw new IllegalArgumentException("Embed description cannot have more than "
						+ DESCRIPTION_CONTENT_LIMIT + " characters");
		}

		throwExceptionForCharacterLimit(desc == null ? 4 : desc.trim().length());

		this.description += desc;
		return this;
	}

	/**
	 * Appends to the description of the this.
	 *
	 * @param desc
	 *            The description.
	 * @return The builder instance.
	 */
	public EmbedBuilder appendDesc(String desc) {
		return appendDescription(desc);
	}

	/**
	 * Sets the timestamp of the this.
	 *
	 * @param instant
	 *            The timestamp.
	 * @return The builder instance.
	 */
	public EmbedBuilder withTimestamp(Instant instant) {
		this.timestamp = instant;
		return this;
	}

	/**
	 * Sets the timestamp of the this.
	 *
	 * @param millis
	 *            The unix timestamp.
	 * @return The builder instance.
	 */
	public EmbedBuilder withTimestamp(long millis) {
		return withTimestamp(Instant.ofEpochMilli(millis));
	}

	/**
	 * Set the color of the this.
	 *
	 * @param color
	 *            The color.
	 * @return The builder instance.
	 */
	public EmbedBuilder withColor(Color color) {
		this.color = color.getRGB();
		return this;
	}

	/**
	 * Set the color of the this.
	 *
	 * @param color
	 *            The color.
	 * @return The builder instance.
	 */
	public EmbedBuilder withColor(int color) {
		return withColor(new Color(color));
	}

	/**
	 * Set the color of the this.
	 *
	 * @param r
	 *            The red component
	 * @param g
	 *            The green component
	 * @param b
	 *            The blue component
	 * @return The builder instance.
	 */
	public EmbedBuilder withColor(int r, int g, int b) {
		return withColor(new Color(r, g, b));
	}

	/**
	 * Sets the footer text of the this.
	 *
	 * @param footer
	 *            The footer text.
	 * @return The builder instance.
	 */
	public EmbedBuilder withFooterText(String footer) {
		if (this.footer == null)
			this.footer = new FooterObject(null, null, null);

		if (footer.trim().length() > FOOTER_CONTENT_LIMIT) {
			if (lenient)
				footer = footer.substring(0, FOOTER_CONTENT_LIMIT);
			else
				throw new IllegalArgumentException("Embed footer text cannot have more than "
						+ FOOTER_CONTENT_LIMIT + " characters");
		}

		throwExceptionForCharacterLimit(footer.trim().length());

		this.footer.text = footer;
		return this;
	}

	/**
	 * Sets the footer icon. Footer text must be present for the footer icon to
	 * appear.
	 *
	 * @param iconUrl
	 *            The footer icon URL.
	 * @return The builder instance.
	 */
	public EmbedBuilder withFooterIcon(String iconUrl) {
		if (this.footer == null)
			this.footer = new FooterObject(null, null, null);

		this.footer.icon_url = iconUrl;
		return this;
	}

	/**
	 * Sets the image of the this.
	 *
	 * @param imageUrl
	 *            The image URL.
	 * @return The builder instance.
	 */
	public EmbedBuilder withImage(String imageUrl) {
		this.image = new ImageObject(imageUrl, null, 0, 0);
		return this;
	}

	/**
	 * Sets the thumbnail of the this.
	 *
	 * @param url
	 *            The thumbnail URL.
	 * @return The builder instance.
	 */
	public EmbedBuilder withThumbnail(String url) {
		this.thumbnail = new ThumbnailObject(url, null, 0, 0);
		return this;
	}

	/**
	 * Sets the author icon. Author name must be present for the author icon to
	 * appear.
	 *
	 * @param url
	 *            The icon URL.
	 * @return The builder instance.
	 */
	public EmbedBuilder withAuthorIcon(String url) {
		if (this.author == null)
			this.author = new AuthorObject(null, null, null, null);

		this.author.icon_url = url;
		return this;
	}

	/**
	 * Sets the author name.
	 *
	 * @param name
	 *            The author name.
	 * @return The builder instance.
	 */
	public EmbedBuilder withAuthorName(String name) {
		if (this.author == null)
			this.author = new AuthorObject(null, null, null, null);

		if (name.trim().length() > AUTHOR_NAME_LIMIT) {
			if (lenient)
				name = name.substring(0, AUTHOR_NAME_LIMIT);
			else
				throw new IllegalArgumentException(
						"Author name cannot have more than " + AUTHOR_NAME_LIMIT + " characters");
		}

		throwExceptionForCharacterLimit(name.trim().length());

		this.author.name = name;
		return this;
	}

	/**
	 * Sets the author URL. Author name must be present for the URL to work.
	 *
	 * @param url
	 *            The author URL.
	 * @return The builder instance.
	 */
	public EmbedBuilder withAuthorUrl(String url) {
		if (this.author == null)
			this.author = new AuthorObject(null, null, null, null);

		this.author.url = url;
		return this;
	}

	/**
	 * Sets the URL of the this. Title must be present for the URL to work.
	 *
	 * @param url
	 *            The URL.
	 * @return The builder instance.
	 */
	public EmbedBuilder withUrl(String url) {
		this.url = url;
		return this;
	}

	/**
	 * Adds a field to the this.
	 *
	 * @param title
	 *            The title of the field.
	 * @param content
	 *            The content of the field.
	 * @param inline
	 *            Whether the field is inline.
	 * @return The builder instance.
	 *
	 * @throws IllegalArgumentException
	 *             If the title or content is null, empty, or too long. Use
	 *             {@link #setLenient(boolean) leniency} to ignore invalid
	 *             fields instead.
	 */
	public EmbedBuilder appendField(String title, String content, boolean inline) {
		if (((title == null || title.trim().isEmpty())
				|| (content == null || content.trim().isEmpty()))) {
			if (lenient)
				return this;
			throw new IllegalArgumentException("Title or content cannot be null/empty.");
		}

		if (fields.size() >= FIELD_COUNT_LIMIT) {
			if (lenient)
				fields = fields.subList(0, FIELD_COUNT_LIMIT);
			else
				throw new IllegalArgumentException(
						"Embed cannot have more than " + FIELD_COUNT_LIMIT + " fields");
		}

		if (title.length() > TITLE_LENGTH_LIMIT) {
			if (lenient)
				title = title.substring(0, TITLE_LENGTH_LIMIT);
			else
				throw new IllegalArgumentException(
						"Embed field title cannot have more than " + TITLE_LENGTH_LIMIT + " characters");
		}

		if (content.length() > FIELD_CONTENT_LIMIT) {
			if (lenient)
				content = content.substring(0, FIELD_CONTENT_LIMIT);
			else
				throw new IllegalArgumentException("Embed field content cannot have more than "
						+ FIELD_CONTENT_LIMIT + " characters");
		}

		throwExceptionForCharacterLimit(title.trim().length() + content.trim().length());

		fields.add(new EmbedFieldObject(title, content, inline));
		return this;
	}

	/**
	 * Clears the fields in the builder.
	 *
	 * @return The builder instance.
	 */
	public EmbedBuilder clearFields() {
		fields.clear();
		return this;
	}

	/**
	 * Gets the number of fields in the builder.
	 *
	 * @return The number of fields in the builder.
	 */
	public int getFieldCount() {
		return fields.size();
	}

	/**
	 * Builds an embed with the configuration specified by the builder.
	 *
	 * @return An embed with the configuration specified by the builder.
	 */
	public Consumer<? super EmbedCreateSpec> build() {
		generateWarnings();
		throwExceptionForCharacterLimit(0);

		return (e) -> {
			e.setTitle(this.title == null ? "" : this.title);
			e.setDescription(this.description == null ? "" : this.description);
			e.setUrl(this.url == null ? "" : this.url);
			if (this.timestamp != null)
				e.setTimestamp(this.timestamp);
			e.setColor(new Color(this.color));
			if (this.footer != null)
				e.setFooter(this.footer.text, this.footer.icon_url);
			if (this.image != null)
				e.setImage(this.image.url);
			if (this.thumbnail != null)
				e.setThumbnail(this.thumbnail.url);
			if (this.author != null)
				e.setAuthor(this.author.name, this.author.url, this.author.icon_url);
			for (EmbedFieldObject f : fields)
				e.addField(f.name, f.value, f.inline);
		};
	}

	/**
	 * Gets the total number of characters that will be visible in the embed in
	 * the Discord client.
	 *
	 * @return The total number of characters that will be visible in the embed
	 *         in the Discord client.
	 */
	public int getTotalVisibleCharacters() {
		return (this.title == null ? 0 : this.title.length())
				+ (this.description == null ? 0 : this.description.length())
				+ (this.footer == null ? 0 : (this.footer.text == null ? 0 : this.footer.text.length()))
				+ (this.author == null ? 0 : (this.author.name == null ? 0 : this.author.name.length()))
				+ (fields.stream().mapToInt(efo -> (efo.name == null ? 0 : efo.name.length())
						+ (efo.value == null ? 0 : efo.value.length())).sum());
	}

	/**
	 * Gets whether the embed exceeds {@value MAX_CHAR_LIMIT} characters.
	 *
	 * @return Whether the embed exceeds {@value MAX_CHAR_LIMIT} characters.
	 */
	public boolean doesExceedCharacterLimit() {
		return getTotalVisibleCharacters() > MAX_CHAR_LIMIT;
	}

	/**
	 * Throws an exception if the builder is not lenient and the given number of
	 * extra characters would exceed {@value MAX_CHAR_LIMIT} characters.
	 *
	 * @param extra
	 *            The number of extra characters added to the this.
	 */
	private void throwExceptionForCharacterLimit(int extra) {
		if (extra < 0)
			throw new IllegalArgumentException("Extra cannot be negative!");
		if (!lenient && getTotalVisibleCharacters() + extra > MAX_CHAR_LIMIT)
			throw new IllegalArgumentException(
					"Embed " + (extra == 0 ? "exceeds" : "would exceed") + " character limit of "
							+ MAX_CHAR_LIMIT + " (" + (extra == 0 ? "has" : "would have") + " "
							+ (getTotalVisibleCharacters() + extra) + " chars)");
	}

	/**
	 * Logs warnings about the configuration of the embed (if any).
	 */
	private void generateWarnings() {
		if (this.footer != null) {
			// footer warnings
			if (this.footer.icon_url != null && (this.footer.text == null || this.footer.text.isEmpty()))
				System.err.println(
						"Embed object warning - footer icon without footer text - footer icon will not be "
								+ "visible");
		}

		if (this.author != null) {
			if (this.author.name == null || this.author.name.isEmpty()) {
				if (this.author.icon_url != null)
					System.err.println(
							"Embed object warning - author icon without author name - author icon will not be "
									+ "visible");
				if (this.author.url != null)
					System.err.println(
							"Embed object warning - author URL without author name - URL is useless and cannot"
									+ " " + "be clicked");
			}
		}
	}

	private static class ThumbnailObject {

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
	}

	private static class ImageObject {

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
	}

	private static class AuthorObject {

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
	}

	private static class FooterObject {

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
	}

	private static class EmbedFieldObject {

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
	}

}
