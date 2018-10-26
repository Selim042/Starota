package us.myles_selim.starota.trading;

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
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.IOHelper;
import us.myles_selim.starota.ServerOptions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.profiles.ProfileManager;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class Tradeboard {

	private static boolean inited = false;
	private static final Map<Long, EBList<TradeboardPost, TradeboardPost>> DATA = new ConcurrentHashMap<>();

	public static final String TRADE_ID_KEY = "trade_id";

	private static final FilenameFilter DAT_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(IOHelper.EBS_LIST_EXTENSION);
		}
	};

	public static void init() {
		if (inited)
			return;
		inited = true;

		if (!Starota.DATA_FOLDER.exists())
			Starota.DATA_FOLDER.mkdirs();
		File[] files = new File(Starota.DATA_FOLDER, "tradeboard").listFiles(DAT_FILTER);
		if (files != null) {
			for (File file : files) {
				String name = file.getName().substring(0,
						file.getName().length() - IOHelper.EBS_LIST_EXTENSION.length());
				long serverId = Long.parseLong(name);
				EBList<TradeboardPost, TradeboardPost> profiles = IOHelper.readEBList(file);
				DATA.put(serverId, profiles);
			}
		}
	}

	public static void flush() {
		File profiles = new File(Starota.DATA_FOLDER, "tradeboard");
		if (!profiles.exists())
			profiles.mkdirs();
		for (Entry<Long, EBList<TradeboardPost, TradeboardPost>> entry : DATA.entrySet())
			IOHelper.writeEBList(entry.getValue(),
					new File(profiles, entry.getKey() + IOHelper.EBS_EXTENSION));
	}

	public static void addPost(IGuild server, PlayerProfile profile, TradeboardPost post) {
		if (!DATA.containsKey(server.getLongID()))
			DATA.put(server.getLongID(), new EBList<>(new TradeboardPost()));
		EBList<TradeboardPost, TradeboardPost> posts = DATA.get(server.getLongID());
		posts.add(post);
		flush();
	}

	public static List<TradeboardPost> getPosts(IGuild server) {
		if (!DATA.containsKey(server.getLongID()))
			return null;
		return Collections.unmodifiableList(DATA.get(server.getLongID()));
	}

	public static List<TradeboardPost> findPosts(IGuild server, EnumPokemon pokemon) {
		List<TradeboardPost> allPosts = getPosts(server);
		List<TradeboardPost> matching = new LinkedList<>();
		for (TradeboardPost p : allPosts)
			if (p.getPokemon().equals(pokemon))
				matching.add(p);
		return matching;
	}

	public static TradeboardPost newPost(IGuild server, boolean lookingFor, long owner,
			EnumPokemon pokemon) {
		return newPost(server, lookingFor, owner, pokemon, null, false);
	}

	public static TradeboardPost newPost(IGuild server, boolean lookingFor, long owner,
			EnumPokemon pokemon, boolean shiny) {
		return newPost(server, lookingFor, owner, pokemon, null, shiny);
	}

	public static TradeboardPost newPost(IGuild server, boolean lookingFor, long owner,
			EnumPokemon pokemon, Form form) {
		return newPost(server, lookingFor, owner, pokemon, form, false);
	}

	public static TradeboardPost newPost(IGuild server, boolean lookingFor, long owner,
			EnumPokemon pokemon, Form form, boolean shiny) {
		int nextPostId = 1;
		if (ServerOptions.hasKey(server, TRADE_ID_KEY))
			nextPostId = (int) ServerOptions.getValue(server, TRADE_ID_KEY);
		TradeboardPost post = new TradeboardPost(nextPostId, lookingFor, owner, pokemon, form, shiny);
		ServerOptions.setValue(server, TRADE_ID_KEY, nextPostId + 1);
		if (!DATA.containsKey(server.getLongID()))
			DATA.put(server.getLongID(), new EBList<>(new TradeboardPost()));
		DATA.get(server.getLongID()).add(post);
		flush();
		return post;
	}

	public static TradeboardPost getPost(IGuild server, int id) {
		if (!DATA.containsKey(server.getLongID()))
			return null;
		for (TradeboardPost t : DATA.get(server.getLongID()))
			if (t != null && t.getId() == id)
				return t;
		return null;
	}

	public static List<TradeboardPost> getPosts(IGuild server, IUser user) {
		if (!DATA.containsKey(server.getLongID()))
			return Collections.emptyList();
		List<TradeboardPost> posts = new LinkedList<>();
		for (TradeboardPost t : DATA.get(server.getLongID()))
			if (t != null && t.getOwner() == user.getLongID())
				posts.add(t);
		return Collections.unmodifiableList(posts);
	}

	public static EmbedObject getPostEmbed(IGuild server, TradeboardPost post) {
		if (post == null)
			return null;
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDesc("**Tradeboard Post** #" + String.format("%04d", post.getId()) + "\n\n");
		builder.appendDesc("**Trade Type**: Poster "
				+ (post.isLookingFor() ? "is looking for" : "currently has") + "\n");
		IUser user = Starota.getUser(post.getOwner());
		if (user != null) {
			String nickname = user.getNicknameForGuild(server);
			if (nickname != null)
				builder.appendDesc("**Discord User**: " + nickname + " (_" + user.getName() + "#"
						+ user.getDiscriminator() + "_)\n");
			else
				builder.appendDesc(
						"**Discord User**: " + user.getName() + "#" + user.getDiscriminator() + "\n");
		}
		PlayerProfile profile = ProfileManager.getProfile(server, user);
		if (profile != null && profile.getTrainerCode() != -1)
			builder.appendDesc("**Trainer Code**: " + profile.getTrainerCodeString() + "\n");

		builder.appendDesc("**Pokemon**: " + post.getPokemon() + "\n");
		if (post.getForm() != null)
			builder.appendDesc("**Form**: " + post.getForm() + "\n");
		builder.appendDesc("**Shiny**: " + post.isShiny() + "\n");
		return builder.build();
	}

}
