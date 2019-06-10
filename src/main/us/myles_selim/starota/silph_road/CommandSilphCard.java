package us.myles_selim.starota.silph_road;

import java.util.ArrayList;
import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.modules.BaseModules;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.silph_road.SilphCard.SilphBadgeData;
import us.myles_selim.starota.silph_road.SilphCard.SilphSocial;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSilphCard extends BotCommand<StarotaServer> {

	public CommandSilphCard() {
		super("silphCard", "Displays a given users Silph Road card.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS,
				Permission.USE_EXTERNAL_EMOJIS);
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("silph");
		return aliases;
	}

	@Override
	public String getGeneralUsage() {
		return "<target>";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		String target = message.getAuthor().get().getUsername();
		if (args.length > 1)
			target = args[1];
		if (StarotaModule.isModuleEnabled(server, BaseModules.PROFILES)
				&& !SilphRoadCardUtils.hasCard(target)) {
			User possibleUser = Starota.findUser(target);
			if (possibleUser != null && server.hasProfile(Starota.findUser(target)))
				target = server.getProfile(possibleUser).getPoGoName();
		}
		if (!SilphRoadCardUtils.hasCard(target)) {
			channel.createMessage("User \"" + target + "\" does not have a Silph Road card.");
			return;
		}
		SilphCard card = SilphRoadCardUtils.getCard(target);
		if (!card.data.is_public.equals("1")) {
			channel.createMessage("User \"" + target + "\"'s Silph Road card is not public.");
			return;
		}
		EmbedBuilder builder = new EmbedBuilder();

		builder.withColor(EnumTeam.valueOf(card.data.team.toUpperCase()).getColor());
		builder.withAuthorName("The Silph Road");
		builder.withAuthorUrl("https://thesilphroad.com/");
		builder.withAuthorIcon(
				"https://assets.thesilphroad.com/img/apple-touch-icons/apple-touch-icon-120x120.png");
		builder.setTitle(card.data.in_game_username + " - " + card.data.title);
		builder.withUrl(SilphRoadCardUtils.getCardURL(card.data.in_game_username));
		builder.withThumbnail(card.data.avatar);
		builder.addField("Level:", Integer.toString(card.data.trainer_level), true);
		builder.addField("XP:", card.data.xp, true);
		String favPokemonString = "";
		for (int id : card.data.top_6_pokemon)
			if (id != 0)
				favPokemonString += EnumPokemon.getPokemon(id).getName() + ", ";
		if (!favPokemonString.isEmpty())
			builder.addField("Top 6 Pokemon:",
					favPokemonString.substring(0, favPokemonString.length() - 2), false);

		builder.addField("Joined:", card.data.joined, true);
		builder.addField("Travelers Met:", Integer.toString(card.data.handshakes), true);
		builder.addField("Meetup Checkins:", Integer.toString(card.data.checkins.length), true);
		builder.addField("Nest Reports:", Integer.toString(card.data.nest_migrations), true);
		builder.addField("Playstyle:", card.data.playstyle + "\n" + card.data.goal + "\nTypically raids "
				+ card.data.raid_average + "x/week", false);

		String socialString = "";
		for (SilphSocial s : card.data.socials)
			socialString += " - " + s.vendor + ": " + s.username + "\n";
		if (!socialString.isEmpty())
			builder.addField("Social:", socialString, false);

		List<String> badgesStrings = new ArrayList<>();
		String newBadgesString = "";
		for (SilphBadgeData bd : card.data.badges) {
			String emojiS = EmojiServerHelper.getEmoji(bd.Badge.slug, bd.Badge.image).toString();
			// System.out.println("new length will be " +
			// (newBadgesString.length() + emojiS.length()));
			if (newBadgesString.length() + emojiS.length() > 1024) {
				badgesStrings.add(newBadgesString);
				newBadgesString = "";
			}
			newBadgesString += emojiS;
		}
		badgesStrings.add(newBadgesString);
		for (int i = 0; i < badgesStrings.size(); i++) {
			String bs = badgesStrings.get(i);
			if (!bs.isEmpty())
				builder.addField("Badges" + (badgesStrings.size() > 1 ? " Part " + (i + 1) + ":" : ":"),
						bs, false);
		}

		builder.withFooterText("Last updated " + card.data.modified);
		channel.createEmbed(builder.build());
	}

}
