package us.myles_selim.starota.profiles;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.ebs.IOHelper;
import us.myles_selim.starota.Starota;

public class ProfileManager {

	private static boolean inited = false;
	private static final Map<Long, EBStorage> DATA = new ConcurrentHashMap<>();

	private static final FilenameFilter DAT_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(IOHelper.EBS_EXTENSION);
		}
	};

	public static void init() {
		if (inited)
			return;
		inited = true;

		if (!Starota.DATA_FOLDER.exists())
			Starota.DATA_FOLDER.mkdirs();
		File[] files = new File(Starota.DATA_FOLDER, "profiles").listFiles(DAT_FILTER);
		if (files != null) {
			for (File file : files) {
				String name = file.getName().substring(0,
						file.getName().length() - IOHelper.EBS_EXTENSION.length());
				long serverId = Long.parseLong(name);
				EBStorage profiles = IOHelper.readEBStorage(file);
				DATA.put(serverId, profiles);
			}
		}
	}

	public static void flush() {
		File profiles = new File(Starota.DATA_FOLDER, "profiles");
		if (!profiles.exists())
			profiles.mkdirs();
		for (Entry<Long, EBStorage> entry : DATA.entrySet())
			IOHelper.writeEBStorage(entry.getValue(),
					new File(profiles, entry.getKey() + IOHelper.EBS_EXTENSION));
	}

	public static void setProfile(IGuild server, IUser user, PlayerProfile profile) {
		if (!DATA.containsKey(server.getLongID()))
			DATA.put(server.getLongID(), new EBStorage().registerPrimitives()
					.registerType(new PlayerProfile.DataTypePlayerProfile()));
		EBStorage storage = DATA.get(server.getLongID());
		storage.set(user.getStringID(), profile);
		flush();
	}

	public static List<PlayerProfile> getProfiles(IGuild server) {
		if (!DATA.containsKey(server.getLongID()))
			return null;
		EBStorage storage = DATA.get(server.getLongID());
		List<PlayerProfile> profiles = new LinkedList<>();
		for (String k : storage.getKeys())
			profiles.add(storage.get(k, PlayerProfile.class));
		return Collections.unmodifiableList(profiles);
	}

	public static PlayerProfile getProfile(IGuild server, String pogoName) {
		if (!DATA.containsKey(server.getLongID()))
			return null;
		EBStorage storage = DATA.get(server.getLongID());
		for (String k : storage.getKeys()) {
			PlayerProfile profile = storage.get(k, PlayerProfile.class);
			if (profile.getPoGoName().equalsIgnoreCase(pogoName))
				return profile;
		}
		return null;
	}

	public static PlayerProfile getProfile(IGuild server, IUser user) {
		if (!DATA.containsKey(server.getLongID()))
			return null;
		EBStorage storage = DATA.get(server.getLongID());
		if (storage.containsKey(user.getStringID()))
			return storage.get(user.getStringID(), PlayerProfile.class);
		return null;
	}

	public static boolean hasProfile(IGuild server, IUser user) {
		if (user == null)
			return false;
		if (!DATA.containsKey(server.getLongID()))
			return false;
		EBStorage storage = DATA.get(server.getLongID());
		return storage.containsKey(user.getStringID()) && storage.get(user.getStringID()) != null;
	}

	public static EmbedObject getProfileEmbed(IGuild server, PlayerProfile profile) {
		if (profile == null)
			return null;
		EmbedBuilder builder = new EmbedBuilder();
		builder.withColor(profile.getTeam().getColor());
		builder.appendDesc("**Profile for " + profile.getPoGoName() + "**\n\n");
		IUser user = Starota.getUser(profile.getDiscordId());
		builder.appendDesc("**Trainer Level**: " + profile.getLevel() + "\n");
		builder.appendDesc("**Team**: " + profile.getTeam().getName() + "\n");
		if (user != null) {
			String nickname = user.getNicknameForGuild(server);
			if (nickname != null)
				builder.appendDesc("**Discord User**: " + nickname + " (_" + user.getName() + "#"
						+ user.getDiscriminator() + "_)\n");
			else
				builder.appendDesc(
						"**Discord User**: " + user.getName() + "#" + user.getDiscriminator() + "\n");
		}
		if (profile.getRealName() != null)
			builder.appendDesc("**Real Name**: " + profile.getRealName() + "\n");
		if (profile.getTrainerCode() != -1)
			builder.appendDesc("**Trainer Code**: " + profile.getTrainerCodeString() + "\n");

		Map<String, Long> alts = profile.getAlts();
		if (alts == null || !alts.isEmpty()) {
			builder.appendDesc("**Alternate Accounts**:\n");
			for (Entry<String, Long> e : alts.entrySet())
				builder.appendDesc(
						"- **" + e.getKey() + "**: " + getTrainerCodeString(e.getValue()) + "\n");
		}
		return builder.build();
	}

	public static String getTrainerCodeString(long code) {
		String codeS = String.format("%012d", code);
		return codeS.substring(0, 4) + " " + codeS.substring(4, 8) + " " + codeS.substring(8, 12);
	}

}
