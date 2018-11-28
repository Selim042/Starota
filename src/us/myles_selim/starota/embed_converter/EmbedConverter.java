package us.myles_selim.starota.embed_converter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.Pair;
import us.myles_selim.starota.embed_converter.annotations.EmbedAuthorIcon;
import us.myles_selim.starota.embed_converter.annotations.EmbedAuthorName;
import us.myles_selim.starota.embed_converter.annotations.EmbedAuthorURL;
import us.myles_selim.starota.embed_converter.annotations.EmbedColor;
import us.myles_selim.starota.embed_converter.annotations.EmbedField;
import us.myles_selim.starota.embed_converter.annotations.EmbedFooterIcon;
import us.myles_selim.starota.embed_converter.annotations.EmbedFooterText;
import us.myles_selim.starota.embed_converter.annotations.EmbedImage;
import us.myles_selim.starota.embed_converter.annotations.EmbedThumbnail;
import us.myles_selim.starota.embed_converter.annotations.EmbedTimestamp;
import us.myles_selim.starota.embed_converter.annotations.EmbedTitle;
import us.myles_selim.starota.embed_converter.annotations.EmbedURL;

public class EmbedConverter {

	public static EmbedObject toEmbed(Object obj) {
		EmbedBuilder builder = new EmbedBuilder();
		Class<?> clazz = obj.getClass();

		if (obj.getClass().isAnnotationPresent(EmbedTitle.class))
			builder.withTitle(getEmbedName(obj, clazz.getAnnotation(EmbedTitle.class).value()));
		if (obj.getClass().isAnnotationPresent(EmbedColor.class)) {
			EmbedColor color = clazz.getAnnotation(EmbedColor.class);
			if (color.value() != -1)
				builder.withColor(color.value());
		}
		if (obj.getClass().isAnnotationPresent(EmbedAuthorName.class)) {
			EmbedAuthorName name = clazz.getAnnotation(EmbedAuthorName.class);
			if (!name.value().isEmpty())
				builder.withAuthorName(getEmbedName(obj, name.value()));
		}
		if (obj.getClass().isAnnotationPresent(EmbedAuthorIcon.class)) {
			EmbedAuthorIcon icon = clazz.getAnnotation(EmbedAuthorIcon.class);
			if (!icon.value().isEmpty())
				builder.withAuthorIcon(getEmbedName(obj, icon.value()));
		}
		if (obj.getClass().isAnnotationPresent(EmbedAuthorURL.class)) {
			EmbedAuthorURL url = clazz.getAnnotation(EmbedAuthorURL.class);
			if (!url.value().isEmpty())
				builder.withAuthorUrl(getEmbedName(obj, url.value()));
		}
		if (obj.getClass().isAnnotationPresent(EmbedThumbnail.class)) {
			EmbedThumbnail thumbnail = clazz.getAnnotation(EmbedThumbnail.class);
			if (!thumbnail.value().isEmpty())
				builder.withThumbnail(getEmbedName(obj, thumbnail.value()));
		}
		if (obj.getClass().isAnnotationPresent(EmbedTimestamp.class)) {
			EmbedTimestamp timestamp = clazz.getAnnotation(EmbedTimestamp.class);
			if (timestamp.value() != -1)
				builder.withTimestamp(timestamp.value());
		}
		if (obj.getClass().isAnnotationPresent(EmbedFooterText.class)) {
			EmbedFooterText footer = clazz.getAnnotation(EmbedFooterText.class);
			if (!footer.value().isEmpty())
				builder.withFooterText(getEmbedName(obj, footer.value()));
		}
		if (obj.getClass().isAnnotationPresent(EmbedFooterIcon.class)) {
			EmbedFooterIcon footer = clazz.getAnnotation(EmbedFooterIcon.class);
			if (!footer.value().isEmpty())
				builder.withFooterIcon(getEmbedName(obj, footer.value()));
		}
		if (obj.getClass().isAnnotationPresent(EmbedImage.class)) {
			EmbedImage image = clazz.getAnnotation(EmbedImage.class);
			if (!image.value().isEmpty())
				builder.withImage(getEmbedName(obj, image.value()));
		}
		if (obj.getClass().isAnnotationPresent(EmbedURL.class)) {
			EmbedURL url = clazz.getAnnotation(EmbedURL.class);
			if (!url.value().isEmpty())
				builder.withUrl(getEmbedName(obj, url.value()));
		}

		@SuppressWarnings("unchecked")
		Pair<EmbedField, ?>[] fields = new Pair[clazz.getDeclaredFields().length
				+ clazz.getDeclaredMethods().length];
		for (Field f : clazz.getDeclaredFields()) {
			if (f.isAnnotationPresent(EmbedField.class)) {
				EmbedField name = f.getAnnotation(EmbedField.class);
				fields[name.order()] = new Pair<>(name, f);
			}
			if (f.isAnnotationPresent(EmbedColor.class)) {
				EmbedColor color = f.getAnnotation(EmbedColor.class);
				if (color.value() == -1) {
					Object val = getFieldValue(obj, f);
					if (val instanceof Integer)
						builder.withColor((Integer) val);
				}
			}
			if (f.isAnnotationPresent(EmbedAuthorName.class)) {
				EmbedAuthorName name = f.getAnnotation(EmbedAuthorName.class);
				if (name.value().isEmpty()) {
					Object val = getFieldValue(obj, f);
					if (val instanceof String)
						builder.withAuthorName((String) val);
				}
			}
			if (f.isAnnotationPresent(EmbedAuthorIcon.class)) {
				EmbedAuthorIcon icon = f.getAnnotation(EmbedAuthorIcon.class);
				if (icon.value().isEmpty()) {
					Object val = getFieldValue(obj, f);
					if (val instanceof String)
						builder.withAuthorIcon((String) val);
				}
			}
			if (f.isAnnotationPresent(EmbedAuthorURL.class)) {
				EmbedAuthorURL url = f.getAnnotation(EmbedAuthorURL.class);
				if (url.value().isEmpty()) {
					Object val = getFieldValue(obj, f);
					if (val instanceof String)
						builder.withAuthorUrl((String) val);
				}
			}
			if (f.isAnnotationPresent(EmbedThumbnail.class)) {
				EmbedThumbnail thumbnail = f.getAnnotation(EmbedThumbnail.class);
				if (thumbnail.value().isEmpty()) {
					Object val = getFieldValue(obj, f);
					if (val instanceof String)
						builder.withThumbnail((String) val);
				}
			}
			if (f.isAnnotationPresent(EmbedTimestamp.class)) {
				EmbedTimestamp timestamp = f.getAnnotation(EmbedTimestamp.class);
				if (timestamp.value() == -1) {
					Object val = getFieldValue(obj, f);
					if (val instanceof Long)
						builder.withTimestamp((Long) val);
				}
			}
			if (f.isAnnotationPresent(EmbedFooterText.class)) {
				EmbedFooterText footer = f.getAnnotation(EmbedFooterText.class);
				if (footer.value().isEmpty()) {
					Object val = getFieldValue(obj, f);
					if (val instanceof String)
						builder.withFooterText((String) val);
				}
			}
			if (f.isAnnotationPresent(EmbedFooterIcon.class)) {
				EmbedFooterIcon footer = f.getAnnotation(EmbedFooterIcon.class);
				if (footer.value().isEmpty()) {
					Object val = getFieldValue(obj, f);
					if (val instanceof String)
						builder.withFooterIcon((String) val);
				}
			}
			if (f.isAnnotationPresent(EmbedImage.class)) {
				EmbedImage image = f.getAnnotation(EmbedImage.class);
				if (image.value().isEmpty()) {
					Object val = getFieldValue(obj, f);
					if (val instanceof String)
						builder.withImage((String) val);
				}
			}
			if (f.isAnnotationPresent(EmbedURL.class)) {
				EmbedURL url = f.getAnnotation(EmbedURL.class);
				if (url.value().isEmpty()) {
					Object val = getFieldValue(obj, f);
					if (val instanceof String)
						builder.withThumbnail((String) val);
				}
			}
		}

		for (Method m : clazz.getDeclaredMethods()) {
			if (m.isAnnotationPresent(EmbedField.class)) {
				EmbedField name = m.getAnnotation(EmbedField.class);
				fields[name.order()] = new Pair<>(name, m);
			}
			if (m.isAnnotationPresent(EmbedColor.class)) {
				EmbedColor color = m.getAnnotation(EmbedColor.class);
				if (color.value() == -1) {
					Object val = getMethodValue(obj, m);
					if (val instanceof Integer)
						builder.withColor((Integer) val);
				}
			}
			if (m.isAnnotationPresent(EmbedAuthorName.class)) {
				EmbedAuthorName name = m.getAnnotation(EmbedAuthorName.class);
				if (name.value().isEmpty()) {
					Object val = getMethodValue(obj, m);
					if (val instanceof String)
						builder.withAuthorName((String) val);
				}
			}
			if (m.isAnnotationPresent(EmbedAuthorIcon.class)) {
				EmbedAuthorIcon icon = m.getAnnotation(EmbedAuthorIcon.class);
				if (icon.value().isEmpty()) {
					Object val = getMethodValue(obj, m);
					if (val instanceof String)
						builder.withAuthorIcon((String) val);
				}
			}
			if (m.isAnnotationPresent(EmbedAuthorURL.class)) {
				EmbedAuthorURL url = m.getAnnotation(EmbedAuthorURL.class);
				if (url.value().isEmpty()) {
					Object val = getMethodValue(obj, m);
					if (val instanceof String)
						builder.withAuthorUrl((String) val);
				}
			}
			if (m.isAnnotationPresent(EmbedThumbnail.class)) {
				EmbedThumbnail thumbnail = m.getAnnotation(EmbedThumbnail.class);
				if (thumbnail.value().isEmpty()) {
					Object val = getMethodValue(obj, m);
					if (val instanceof String)
						builder.withThumbnail((String) val);
				}
			}
			if (m.isAnnotationPresent(EmbedTimestamp.class)) {
				EmbedTimestamp timestamp = m.getAnnotation(EmbedTimestamp.class);
				if (timestamp.value() == -1) {
					Object val = getMethodValue(obj, m);
					if (val instanceof Long)
						builder.withTimestamp((Long) val);
				}
			}
			if (m.isAnnotationPresent(EmbedFooterText.class)) {
				EmbedFooterText footer = m.getAnnotation(EmbedFooterText.class);
				if (footer.value().isEmpty()) {
					Object val = getMethodValue(obj, m);
					if (val instanceof String)
						builder.withFooterText((String) val);
				}
			}
			if (m.isAnnotationPresent(EmbedFooterIcon.class)) {
				EmbedFooterIcon footer = m.getAnnotation(EmbedFooterIcon.class);
				if (footer.value().isEmpty()) {
					Object val = getMethodValue(obj, m);
					if (val instanceof String)
						builder.withFooterIcon((String) val);
				}
			}
			if (m.isAnnotationPresent(EmbedImage.class)) {
				EmbedImage image = m.getAnnotation(EmbedImage.class);
				if (image.value().isEmpty()) {
					Object val = getMethodValue(obj, m);
					if (val instanceof String)
						builder.withImage((String) val);
				}
			}
			if (m.isAnnotationPresent(EmbedURL.class)) {
				EmbedURL url = m.getAnnotation(EmbedURL.class);
				if (url.value().isEmpty()) {
					Object val = getMethodValue(obj, m);
					if (val instanceof String)
						builder.withThumbnail((String) val);
				}
			}
		}

		for (Pair<EmbedField, ?> ef : fields) {
			if (ef == null)
				continue;
			EmbedField name = ef.left;
			Object fm = ef.right;
			Object val = null;
			if (fm instanceof Field)
				val = getFieldValue(obj, (Field) fm);
			if (fm instanceof Method)
				val = getMethodValue(obj, (Method) fm);
			if (val == null)
				continue;
			builder.appendField(name.value(), val.toString(), name.isInline());
		}

		return builder.build();
	}

	private static String getEmbedName(Object obj, String name) {
		for (Field f : obj.getClass().getDeclaredFields()) {
			Object val = getFieldValue(obj, f);
			if (val == null)
				continue;
			name = name.replaceAll("\\%" + f.getName() + "\\%", val.toString());
		}
		for (Method m : obj.getClass().getDeclaredMethods()) {
			Object val = getMethodValue(obj, m);
			if (val == null)
				continue;
			name = name.replaceAll("\\%" + m.getName() + "\\%", val.toString());
		}
		return name;
	}

	private static Object getFieldValue(Object obj, Field field) {
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		Object val = null;
		try {
			val = field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return null;
		}
		field.setAccessible(accessible);
		return val;
	}

	private static Object getMethodValue(Object obj, Method method) {
		if (method.getParameterCount() != 0)
			return null;
		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object val = null;
		try {
			val = method.invoke(obj);
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			return null;
		}
		method.setAccessible(accessible);
		return val;
	}

}
