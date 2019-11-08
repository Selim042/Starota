package us.myles_selim.starota.permissions;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Pattern;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.permissions.holders.PermissionHolder;

public class PermissionsIO {

	private static final String MODIFIERS = "[-\\+]";
	private static final String HOLDERS = "(@everyone|Discord\\.[A-Z_]+|OWNER|U\\d{18}|R\\d{18})";

	private static final Pattern HOLDER_PATTERN = Pattern
			.compile("(U\\d{18}|R\\d{18}|OWNER|@everyone|Discord\\.[A-Z_]+):");
	private static final Pattern MODIFIER_PATTERN = Pattern.compile(MODIFIERS + "(?= )");
	private static final Pattern PERMISSION_PATTERN = Pattern.compile("(?<=[\\+-] ).+\\..+");
	private static final Pattern COMMENT_PATTERN = Pattern.compile("#\\S*?(?=\\s)");

	private static final Pattern CHANNEL_PATTERN = Pattern.compile("C\\d{18}");
	private static final Pattern OPEN_BRACE_PATTERN = Pattern.compile("\\{");
	private static final Pattern CLOSE_BRACE_PATTERN = Pattern.compile("\\}");

	public static final File PERMISSION_FOLDER = new File(Starota.DATA_FOLDER, "permissions");

	// implement this
	private static PermissionHolder[] getDefaultPermissions() {
		return new PermissionHolder[0];
	}

	public static PermissionHolder[] getPermissionsForGuild(Guild guild) {
		if (guild == null)
			return null;
		PermissionHolder.dumpPerms(guild);
		return getPermissionsFromFile(guild, guild.getId().asString() + ".starota_perms");
	}

	public static PermissionHolder[] getPermissionsFromFile(Guild guild, String permFileName) {
		File permFile = new File(PERMISSION_FOLDER, permFileName);
		if (!permFile.exists())
			return getDefaultPermissions();

		HashMap<String, PermissionHolder> holders = new HashMap<>();
		try {
			boolean lookingForScope = false;
			Snowflake channel = null;
			PermissionHolder holder = null;
			EnumPermissionModifier modifier = null;
			Scanner scanner = new Scanner(new FileReader(permFile));
			while (scanner.hasNext()) {
				if (scanner.hasNext(COMMENT_PATTERN)) {
					scanner.nextLine();
					continue;
				}
				if (scanner.hasNext(CHANNEL_PATTERN)) {
					String channelS = scanner.next(CHANNEL_PATTERN);
					channel = Snowflake.of(channelS.substring(1, channelS.length()));
					lookingForScope = true;
					continue;
				}
				if (scanner.hasNext(OPEN_BRACE_PATTERN)) {
					scanner.next();
					if (lookingForScope)
						lookingForScope = false;
					else {
						scanner.close();
						throw new PermissionErrorException("unexpected {");
					}
					continue;
				}
				if (scanner.hasNext(CLOSE_BRACE_PATTERN)) {
					scanner.next();
					if (!lookingForScope)
						channel = null;
					else {
						scanner.close();
						throw new PermissionErrorException("unexpected }");
					}
					continue;
				}
				if (scanner.hasNext(HOLDER_PATTERN)) {
					if (lookingForScope) {
						String errToken = scanner.next();
						scanner.close();
						throw new PermissionErrorException("expecing {, got \"" + errToken + "\"");
					}
					String holderS = scanner.next(HOLDER_PATTERN);
					holderS = holderS.substring(0, holderS.length() - 1);
					holder = getHolder(guild, holders, holderS);
					continue;
				}
				if (scanner.hasNext(MODIFIER_PATTERN)) {
					if (lookingForScope) {
						String errToken = scanner.next();
						scanner.close();
						throw new PermissionErrorException("expecting {, got \"" + errToken + "\"");
					}
					String modS = scanner.next(MODIFIER_PATTERN);
					modifier = EnumPermissionModifier.fromChars(modS);
					if (modifier == null) {
						scanner.close();
						throw new PermissionErrorException("modifier \"" + modS + "\" not found");
					}
					continue;
				}
				if (scanner.hasNext(PERMISSION_PATTERN)) {
					if (lookingForScope) {
						String errToken = scanner.next();
						scanner.close();
						throw new PermissionErrorException("expecting {, got \"" + errToken + "\"");
					}
					if (modifier == null) {
						String errToken = scanner.next();
						scanner.close();
						throw new PermissionErrorException(
								"expecting modifier, got \"" + errToken + "\"");
					}
					String permission = scanner.next(PERMISSION_PATTERN);
					if (modifier == EnumPermissionModifier.GRANT)
						holder.grantPermission(channel, permission);
					if (modifier == EnumPermissionModifier.REVOKE)
						holder.revokePermission(channel, permission);
					modifier = null;
					continue;
				}
				String errToken = scanner.next();
				scanner.close();
				throw new PermissionErrorException("unknown token \"" + errToken + "\"");
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		PermissionHolder[] ret = new PermissionHolder[holders.size()];
		int i = 0;
		for (Entry<String, PermissionHolder> entry : holders.entrySet())
			ret[i++] = entry.getValue();
		return ret;
	}

	private static PermissionHolder getHolder(Guild guild, Map<String, PermissionHolder> holders,
			String name) {
		if (!holders.containsKey(name))
			holders.put(name, PermissionHolder.getNewHolder(guild, name));
		PermissionHolder holder = holders.get(name);
		if (holder == null)
			throw new PermissionErrorException("no holder found \"" + name + "\"");
		return holder;
	}

	public static void main(String... args) {
		getPermissionsFromFile(null, "481646364716040202.starota_perms");
	}

}
