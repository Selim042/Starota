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
import us.myles_selim.starota.trading.forms.FormSet;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class Tradeboard {

	private static boolean inited = false;
	private static final Map<Long, EBList<TradeboardPost>> DATA = new ConcurrentHashMap<>();

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
				EBList<TradeboardPost> profiles = IOHelper.readEBList(file);
				DATA.put(serverId, profiles);
			}
		}
	}

	public static void flush() {
		File profiles = new File(Starota.DATA_FOLDER, "tradeboard");
		if (!profiles.exists())
			profiles.mkdirs();
		for (Entry<Long, EBList<TradeboardPost>> entry : DATA.entrySet())
			IOHelper.writeEBList(entry.getValue(),
					new File(profiles, entry.getKey() + IOHelper.EBS_LIST_EXTENSION));
	}

	public static void flush(IGuild server) {
		File profiles = new File(Starota.DATA_FOLDER, "tradeboard");
		if (!profiles.exists())
			profiles.mkdirs();
		IOHelper.writeEBList(DATA.get(server.getLongID()),
				new File(profiles, server.getLongID() + IOHelper.EBS_LIST_EXTENSION));
	}

	public static void addPost(IGuild server, PlayerProfile profile, TradeboardPost post) {
		if (!DATA.containsKey(server.getLongID()))
			DATA.put(server.getLongID(), new EBList<>(new TradeboardPost()));
		EBList<TradeboardPost> posts = DATA.get(server.getLongID());
		posts.add(post);
		flush();
	}

	public static List<TradeboardPost> getPosts(IGuild server) {
		if (!DATA.containsKey(server.getLongID()))
			return null;
		return DATA.get(server.getLongID()).values();
	}

	public static List<TradeboardPost> findPosts(boolean lookingFor, IGuild server,
			EnumPokemon pokemon) {
		return findPosts(lookingFor, server, pokemon, false);
	}

	public static List<TradeboardPost> findPosts(boolean lookingFor, IGuild server, EnumPokemon pokemon,
			Form form) {
		return findPosts(lookingFor, server, pokemon, form, false);
	}

	public static List<TradeboardPost> findPosts(boolean lookingFor, IGuild server, EnumPokemon pokemon,
			boolean shiny) {
		FormSet fSet = pokemon.getFormSet();
		return findPosts(lookingFor, server, pokemon, fSet == null ? null : fSet.getDefaultForm(),
				shiny);
	}

	public static List<TradeboardPost> findPosts(boolean lookingFor, IGuild server, EnumPokemon pokemon,
			Form form, boolean shiny) {
		List<TradeboardPost> allPosts = getPosts(server);
		if (allPosts == null)
			return Collections.emptyList();
		List<TradeboardPost> matching = new LinkedList<>();
		FormSet fSet = pokemon.getFormSet();
		for (TradeboardPost p : allPosts)
			if (p.isLookingFor() != lookingFor && p.getPokemon().equals(pokemon)
					&& (fSet == null || p.getForm() == null || form.equals(p.getForm()))
					&& (!shiny || p.isShiny() == shiny))
				matching.add(p);
		return matching;
	}

	public static List<TradeboardPost> findPosts(IGuild server, EnumPokemon pokemon) {
		return findPosts(server, pokemon, false);
	}

	public static List<TradeboardPost> findPosts(IGuild server, EnumPokemon pokemon, Form form) {
		return findPosts(server, pokemon, form, false);
	}

	public static List<TradeboardPost> findPosts(IGuild server, EnumPokemon pokemon, boolean shiny) {
		FormSet fSet = pokemon.getFormSet();
		return findPosts(server, pokemon, fSet == null ? null : fSet.getDefaultForm(), shiny);
	}

	public static List<TradeboardPost> findPosts(IGuild server, EnumPokemon pokemon, Form form,
			boolean shiny) {
		List<TradeboardPost> allPosts = getPosts(server);
		List<TradeboardPost> matching = new LinkedList<>();
		for (TradeboardPost p : allPosts)
			if (p.getPokemon().equals(pokemon) && (form == null || p.getForm() == form)
					&& (!shiny || p.isShiny() == shiny))
				matching.add(p);
		return matching;
	}

	public static TradeboardPost newPost(IGuild server, boolean lookingFor, long owner,
			EnumPokemon pokemon) {
		return newPost(server, lookingFor, owner, pokemon, null, false, EnumGenderPossible.EITHER);
	}

	public static TradeboardPost newPost(IGuild server, boolean lookingFor, long owner,
			EnumPokemon pokemon, boolean shiny) {
		return newPost(server, lookingFor, owner, pokemon, null, shiny, EnumGenderPossible.EITHER);
	}

	public static TradeboardPost newPost(IGuild server, boolean lookingFor, long owner,
			EnumPokemon pokemon, Form form) {
		return newPost(server, lookingFor, owner, pokemon, form, false, EnumGenderPossible.EITHER);
	}

	public static TradeboardPost newPost(IGuild server, boolean lookingFor, long owner,
			EnumPokemon pokemon, Form form, boolean shiny, EnumGenderPossible gender) {
		int nextPostId = 1;
		if (ServerOptions.hasKey(server, TRADE_ID_KEY))
			nextPostId = (int) ServerOptions.getValue(server, TRADE_ID_KEY);
		TradeboardPost post = new TradeboardPost(nextPostId, lookingFor, owner, pokemon, form, shiny,
				gender);
		ServerOptions.setValue(server, TRADE_ID_KEY, nextPostId + 1);
		if (!DATA.containsKey(server.getLongID()))
			DATA.put(server.getLongID(), new EBList<>(new TradeboardPost()));
		DATA.get(server.getLongID()).add(post);
		flush(server);
		return post;
	}

	public static TradeboardPost getPost(IGuild server, int id) {
		if (!DATA.containsKey(server.getLongID()) || id == -1)
			return null;
		for (TradeboardPost t : DATA.get(server.getLongID()).values())
			if (t != null && t.getId() == id)
				return t;
		return null;
	}

	public static List<TradeboardPost> getPosts(IGuild server, IUser user) {
		if (!DATA.containsKey(server.getLongID()))
			return Collections.emptyList();
		List<TradeboardPost> posts = new LinkedList<>();
		for (TradeboardPost t : DATA.get(server.getLongID()).values())
			if (t != null && t.getOwner() == user.getLongID())
				posts.add(t);
		return Collections.unmodifiableList(posts);
	}

	public static TradeboardPost removePost(IGuild server, int id) {
		if (!DATA.containsKey(server.getLongID()))
			return null;
		TradeboardPost post = null;
		EBList<TradeboardPost> posts = DATA.get(server.getLongID());
		for (TradeboardPost t : posts.values())
			if (t != null && t.getId() == id) {
				post = t;
				posts.remove(t);
				break;
			}
		if (post != null)
			flush(server);
		return post;
	}

	public static EmbedObject getPostEmbed(IGuild server, TradeboardPost post) {
		if (post == null)
			return null;
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Tradeboard Post #" + String.format("%04d", post.getId()) + "\n\n");
		builder.appendField("Trade Type:",
				"Poster " + (post.isLookingFor() ? "is looking for" : "currently has"), false);
		IUser user = Starota.getUser(post.getOwner());
		if (user != null) {
			String nickname = user.getNicknameForGuild(server);
			if (nickname != null)
				builder.appendField("Discord User:",
						nickname + " (_" + user.getName() + "#" + user.getDiscriminator() + "_)", true);
			else
				builder.appendField("Discord User:", user.getName() + "#" + user.getDiscriminator(),
						true);
		}
		PlayerProfile profile = ProfileManager.getProfile(server, user);
		if (profile != null && profile.getTrainerCode() != -1)
			builder.appendField("Trainer Code:", profile.getTrainerCodeString(), true);

		EnumPokemon pokemon = post.getPokemon();
		// builder.withThumbnail("http://starota.myles-selim.us/assets/pokemon/"
		// + String.format("%03d", pokemon.getId()) + "-" +
		// pokemon.getName().toLowerCase()
		// + (post.isShiny() ? "-shiny" : "") + "@3x.png");

		Form form = post.getForm();
		if (form == null && pokemon.getFormSet() != null)
			form = pokemon.getDefaultForm();
		String formName = form == null ? "" : form.getSpritePostfix(pokemon);
		if (formName == null)
			formName = "";
		if (!formName.isEmpty())
			formName = '-' + formName;
		builder.withThumbnail("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
				+ (post.isShiny() ? "shiny/" : "") + pokemon.getId() + formName + ".png");
		// System.out.println("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
		// + (post.isShiny() ? "shiny/" : "") + pokemon.getId() + formName +
		// ".png");

		// builder.withThumbnail(
		// "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png");

		builder.withAuthorIcon(user.getAvatarURL());
		builder.withAuthorName(user.getDisplayName(server));
		if (form != null)
			builder.withColor(form.getType1(pokemon).getColor());
		else
			builder.withColor(pokemon.getType1().getColor());

		builder.appendField("Pokemon:", pokemon.getName(), false);
		if (form != null)
			builder.appendField("Form:", form.toString(), true);
		if (FormManager.isShinyable(pokemon) || (form != null && form.canBeShiny(pokemon)))
			builder.appendField("Shiny:", "" + post.isShiny(), true);
		// if (post.getGender() != EnumGenderPossible.UNKNOWN)
		// builder.appendField("Gender:", post.getGender().toString(), true);

		builder.withFooterText("Trade posted");
		builder.withTimestamp(post.getTimePosted());
		return builder.build();
	}

}
