package us.myles_selim.starota.misc.data_types;

import java.awt.Color;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.misc.data_types.EmbedObject.EmbedFieldObject;

/**
 * Used to configure and build a {@link EmbedObject}.
 */
public class EmbedBuilder {

	/**
	 * The maximum number of fields an embed can contain.
	 */
	public static final int FIELD_COUNT_LIMIT = 25;
	/**
	 * The maximum length of embed and field titles.
	 */
	public static final int TITLE_LENGTH_LIMIT = 256;
	/**
	 * The maximum length of field content.
	 */
	public static final int FIELD_CONTENT_LIMIT = 1024;
	/**
	 * The maximum length of embed descriptions.
	 */
	public static final int DESCRIPTION_CONTENT_LIMIT = 2048;
	/**
	 * The maximum length of footer text.
	 */
	public static final int FOOTER_CONTENT_LIMIT = DESCRIPTION_CONTENT_LIMIT;
	/**
	 * The maximum length of author name.
	 */
	public static final int AUTHOR_NAME_LIMIT = 256;
	/**
	 * The maximum character limit across all visible fields.
	 */
	public static final int MAX_CHAR_LIMIT = 6000;

	/**
	 * The underlying embed object that is modified by the builder.
	 */
	private final EmbedObject embed;
	/**
	 * The fields of the embed.
	 */
	private volatile List<EmbedObject.EmbedFieldObject> fields = new CopyOnWriteArrayList<>();
	/**
	 * The color of the embed.
	 */
	private volatile Color color = new Color(0);

	/**
	 * Whether the builder should sanitize input to prevent errors
	 * automatically.
	 */
	private volatile boolean lenient = false;

	public EmbedBuilder() {
		this.embed = new EmbedObject(null, "rich", null, null, null, 0, null, null, null, null, null,
				null, null);
	}

	public EmbedBuilder(EmbedObject embed) {
		this.embed = new EmbedObject(embed);
		this.embed.fields = new EmbedFieldObject[0];
		this.color = new Color(embed.color);
	}

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

	public EmbedBuilder setTitle(String title) {
		return withTitle(title);
	}

	/**
	 * Sets the title of the embed.
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

		embed.title = title;
		return this;
	}

	/**
	 * Sets the description of the embed.
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

		embed.description = desc;
		return this;
	}

	/**
	 * Sets the description of the embed.
	 *
	 * @param desc
	 *            The description.
	 * @return The builder instance.
	 */
	public EmbedBuilder withDesc(String desc) {
		return withDescription(desc);
	}

	/**
	 * Appends to the description of the embed.
	 *
	 * @param desc
	 *            The description.
	 * @return The builder instance.
	 */
	public EmbedBuilder appendDescription(String desc) {
		if (embed.description == null)
			embed.description = "";
		if (desc != null && (embed.description + desc).trim().length() > DESCRIPTION_CONTENT_LIMIT) {
			if (lenient)
				desc = desc.substring(0, DESCRIPTION_CONTENT_LIMIT - embed.description.length());
			else
				throw new IllegalArgumentException("Embed description cannot have more than "
						+ DESCRIPTION_CONTENT_LIMIT + " characters");
		}

		throwExceptionForCharacterLimit(desc == null ? 4 : desc.trim().length());

		embed.description += desc;
		return this;
	}

	/**
	 * Appends to the description of the embed.
	 *
	 * @param desc
	 *            The description.
	 * @return The builder instance.
	 */
	public EmbedBuilder appendDesc(String desc) {
		return appendDescription(desc);
	}

	/**
	 * Sets the timestamp of the embed.
	 *
	 * @param instant
	 *            The timestamp.
	 * @return The builder instance.
	 */
	public EmbedBuilder withTimestamp(Instant instant) {
		embed.timestamp = instant.atZone(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		return this;
	}

	/**
	 * Sets the timestamp of the embed.
	 *
	 * @param millis
	 *            The unix timestamp.
	 * @return The builder instance.
	 */
	public EmbedBuilder withTimestamp(long millis) {
		return withTimestamp(Instant.ofEpochMilli(millis));
	}

	/**
	 * Set the color of the embed.
	 *
	 * @param color
	 *            The color.
	 * @return The builder instance.
	 */
	public EmbedBuilder withColor(Color color) {
		this.color = color;
		return this;
	}

	/**
	 * Set the color of the embed.
	 *
	 * @param color
	 *            The color.
	 * @return The builder instance.
	 */
	public EmbedBuilder withColor(int color) {
		return withColor(new Color(color));
	}

	/**
	 * Set the color of the embed.
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
	 * Sets the footer text of the embed.
	 *
	 * @param footer
	 *            The footer text.
	 * @return The builder instance.
	 */
	public EmbedBuilder withFooterText(String footer) {
		if (embed.footer == null)
			embed.footer = new EmbedObject.FooterObject(null, null, null);

		if (footer.trim().length() > FOOTER_CONTENT_LIMIT) {
			if (lenient)
				footer = footer.substring(0, FOOTER_CONTENT_LIMIT);
			else
				throw new IllegalArgumentException("Embed footer text cannot have more than "
						+ FOOTER_CONTENT_LIMIT + " characters");
		}

		throwExceptionForCharacterLimit(footer.trim().length());

		embed.footer.text = footer;
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
		if (embed.footer == null)
			embed.footer = new EmbedObject.FooterObject(null, null, null);

		embed.footer.icon_url = iconUrl;
		return this;
	}

	/**
	 * Sets the image of the embed.
	 *
	 * @param imageUrl
	 *            The image URL.
	 * @return The builder instance.
	 */
	public EmbedBuilder withImage(String imageUrl) {
		embed.image = new EmbedObject.ImageObject(imageUrl, null, 0, 0);
		return this;
	}

	/**
	 * Sets the thumbnail of the embed.
	 *
	 * @param url
	 *            The thumbnail URL.
	 * @return The builder instance.
	 */
	public EmbedBuilder withThumbnail(String url) {
		embed.thumbnail = new EmbedObject.ThumbnailObject(url, null, 0, 0);
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
		if (embed.author == null)
			embed.author = new EmbedObject.AuthorObject(null, null, null, null);

		embed.author.icon_url = url;
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
		if (embed.author == null)
			embed.author = new EmbedObject.AuthorObject(null, null, null, null);

		if (name.trim().length() > AUTHOR_NAME_LIMIT) {
			if (lenient)
				name = name.substring(0, AUTHOR_NAME_LIMIT);
			else
				throw new IllegalArgumentException(
						"Author name cannot have more than " + AUTHOR_NAME_LIMIT + " characters");
		}

		throwExceptionForCharacterLimit(name.trim().length());

		embed.author.name = name;
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
		if (embed.author == null)
			embed.author = new EmbedObject.AuthorObject(null, null, null, null);

		embed.author.url = url;
		return this;
	}

	/**
	 * Sets the URL of the embed. Title must be present for the URL to work.
	 *
	 * @param url
	 *            The URL.
	 * @return The builder instance.
	 */
	public EmbedBuilder withUrl(String url) {
		embed.url = url;
		return this;
	}

	public EmbedBuilder addField(String title, String content, boolean inline) {
		return appendField(title, content, inline);
	}

	/**
	 * Adds a field to the embed.
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

		fields.add(new EmbedObject.EmbedFieldObject(title, content, inline));
		return this;
	}

	/**
	 * Appends a field to the embed.
	 *
	 * @param field
	 *            The field to append.
	 * @return The builder instance.
	 *
	 * @throws IllegalArgumentException
	 *             If the field is null. Use {@link #setLenient(boolean)
	 *             leniency} to ignore invalid fields instead.
	 */
	public EmbedBuilder appendField(EmbedFieldObject field) {
		if (field == null) {
			if (lenient)
				return this;
			else
				throw new IllegalArgumentException("Field can not be null!");
		}
		return appendField(field.name, field.value, field.inline);
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
	public Consumer<EmbedCreateSpec> build() {
		throwExceptionForCharacterLimit(0);

		return (e) -> {
			e.setTitle(embed.title);
			e.setDescription(embed.description == null ? "" : embed.description);
			e.setUrl(embed.url);
			e.setTimestamp(Instant.parse(embed.timestamp));
			e.setColor(color == null ? new Color(embed.color) : new Color(color.getRGB()));
			if (embed.footer != null)
				e.setFooter(embed.footer.text, embed.footer.icon_url);
			e.setImage(embed.image == null ? null : embed.image.url);
			e.setThumbnail(embed.thumbnail == null ? null : embed.thumbnail.url);
			if (embed.author != null)
				e.setAuthor(embed.author.name, embed.author.url, embed.author.icon_url);
			for (EmbedObject.EmbedFieldObject f : fields)
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
		return (embed.title == null ? 0 : embed.title.length())
				+ (embed.description == null ? 0 : embed.description.length())
				+ (embed.footer == null ? 0
						: (embed.footer.text == null ? 0 : embed.footer.text.length()))
				+ (embed.author == null ? 0
						: (embed.author.name == null ? 0 : embed.author.name.length()))
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
	 *            The number of extra characters added to the embed.
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

}
