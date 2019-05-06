package us.myles_selim.starota.webserver.webhooks.reaction_messages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Consumer;

import discord4j.core.DiscordClient;
import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.Category;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.assistants.StarotaAssistants;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;
import us.myles_selim.starota.misc.data_types.RaidBoss;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.modules.BaseModules;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.pokedex.GoHubDatabase;
import us.myles_selim.starota.pokedex.PokedexEntry;
import us.myles_selim.starota.pokedex.PokedexEntry.DexMoveset;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.webserver.webhooks.WebhookRaid;
import us.myles_selim.starota.wrappers.StarotaServer;

public class WebhookRaidReactionMessage extends ReactionMessage {

	// Matches {@link Matches CommandRaid.TOPIC_REGEX}
	private static final String TOPIC_TEMPLATE = "Raid chat the %1$s raid at %2$s\n\n(Key: 0x%3$016X **DO NOT MODIFY**)";
	private static final String CATEGORY_NAME = "Raid Channels";
	private static final SimpleDateFormat ROLE_DATE_FORMAT = new SimpleDateFormat("HHmm");
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm");

	private WebhookRaid raidData;
	private TextChannel channel;
	private Role role;
	private EnumPokemon pokemon;
	private Form form;

	public WebhookRaidReactionMessage(WebhookRaid raidData) {
		this.raidData = raidData;
		this.pokemon = EnumPokemon.getPokemon(raidData.pokemon_id);
		if (this.pokemon != null && this.pokemon.getFormSet() != null)
			this.form = this.pokemon.getFormSet().getForm(raidData.form);
	}

	public WebhookRaid getRaidData() {
		return this.raidData;
	}

	@Override
	public void onSend(StarotaServer server, TextChannel channel, Message msg) {
		msg.addReaction(ReactionEmoji.unicode("✅"));
	}

	@Override
	public void onReactionAdded(StarotaServer server, TextChannel channel, Message msg, Member member,
			ReactionEmoji react) {
		if (react.toString().equals("✅")) {
			handleStarotaAddReaction(server, channel, msg, member, react);
			handleAssistantAddReaction(server, channel, msg, member, react);
		}
	}

	private void handleStarotaAddReaction(StarotaServer server, Channel channel, Message msg, User user,
			ReactionEmoji react) {
		channel = server.getDiscordGuild().getChannelById(channel.getId()).block();
		msg = server.getDiscordGuild().getClient()
				.getMessageById(server.getDiscordGuild().getId(), msg.getId()).block();
		Member member = server.getDiscordGuild().getMemberById(user.getId()).block();
		Guild guild = server.getDiscordGuild();
		String name = getRoleName();

		if (this.channel == null) {
			Category category = null;
			for (Category c : MiscUtils.getCategoryByName(guild, CATEGORY_NAME)) {
				category = c;
				break;
			}
			if (category == null) {
				category = guild.createCategory((c) -> {
					c.setName(CATEGORY_NAME);
					Set<PermissionOverwrite> perms = new HashSet<>();
					perms.add(PermissionOverwrite.forRole(guild.getEveryoneRole().block().getId(),
							PermissionSet.none(), PermissionSet.of(Permission.VIEW_CHANNEL)));
					perms.add(PermissionOverwrite.forMember(Starota.getSelf().getId(),
							PermissionSet.of(Permission.VIEW_CHANNEL), PermissionSet.none()));
					c.setPermissionOverwrites(perms);
				}).block();
			}
			for (GuildChannel c : MiscUtils.getChannelByName(guild, name)) {
				this.channel = (TextChannel) c;
				break;
			}
			// TODO: fix this, not put in category and fix topic
			if (this.channel == null)
				this.channel = guild.createTextChannel((c) -> c.setName(name)).block();
			// this.channel.changeTopic(String.format(TOPIC_TEMPLATE,
			// raidData.getPokemon().getName(),
			// raidData.getGymName(), getMessage().getId().asLong()));
		}
		if (this.role == null) {
			for (Role r : MiscUtils.getRoleByName(guild, name)) {
				this.role = r;
				break;
			}
			if (this.role == null) {
				this.role = guild.createRole((r) -> {
					r.setName(name);
					r.setPermissions(PermissionSet.none());
				}).block();
				this.channel.addRoleOverwrite(this.role.getId(),
						PermissionOverwrite.forRole(this.role.getId(), PermissionSet
								.of(Permission.READ_MESSAGE_HISTORY, Permission.VIEW_CHANNEL),
								PermissionSet.none()));
			}
		}
		member.addRole(this.role.getId());
	}

	private void handleAssistantAddReaction(StarotaServer server, TextChannel channel, Message msg,
			Member user, ReactionEmoji react) {
		DiscordClient client = StarotaAssistants.getResponsibleClient(msg);
		channel = (TextChannel) client.getChannelById(channel.getId()).block();
		msg = client.getMessageById(server.getDiscordGuild().getId(), msg.getId()).block();
		user = client.getMemberById(server.getDiscordGuild().getId(), user.getId()).block();

		this.editMessage(channel, msg);
		this.channel.createMessage(user + " was added.");
	}

	@Override
	public void onReactionRemoved(StarotaServer server, TextChannel channel, Message msg, Member member,
			ReactionEmoji react) {
		if (react.toString().equals("✅")) {
			handleStarotaRemoveReaction(server, channel, msg, member, react);
			handleAssistantRemoveReaction(server, channel, msg, member, react);
		}
	}

	private void handleStarotaRemoveReaction(StarotaServer server, TextChannel channel, Message msg,
			Member user, ReactionEmoji react) {
		channel = (TextChannel) server.getDiscordGuild().getChannelById(channel.getId()).block();
		msg = Starota.getClient().getMessageById(server.getDiscordGuild().getId(), msg.getId()).block();
		user = server.getDiscordGuild().getMemberById(user.getId()).block();

		user.removeRole(this.role.getId());
	}

	private void handleAssistantRemoveReaction(StarotaServer server, TextChannel channel, Message msg,
			User user, ReactionEmoji react) {
		DiscordClient client = StarotaAssistants.getResponsibleClient(msg);
		channel = (TextChannel) client.getChannelById(channel.getId()).block();
		msg = client.getMessageById(server.getDiscordGuild().getId(), msg.getId()).block();
		user = client.getMemberById(server.getDiscordGuild().getId(), user.getId()).block();

		this.channel.createMessage(user + " was removed.");
		this.editMessage(channel, msg);
	}

	@Override
	protected Consumer<EmbedCreateSpec> getEmbed(StarotaServer server) {
		// if (raidData != null)
		// return raidData.toEmbed();
		EmbedBuilder builder = new EmbedBuilder();
		PokedexEntry entry = null;
		builder.withColor(RaidBoss.getColor(raidData.level, pokemon));
		if (pokemon != null && StarotaModule.isModuleEnabled(server, BaseModules.POKEDEX))
			entry = GoHubDatabase.getEntry(pokemon,
					form == null ? null
							: (form.getSpritePostfix(pokemon) == null ? form.toString()
									: form.getSpritePostfix(pokemon)));
		if (pokemon != null) {
			String titleString = (form == null ? "" : form + " ") + pokemon + " Raid ";
			// title tier emoji
			// if (boss.getTier() == 6)
			// titleString += EmojiServerHelper.getEmoji(EX_RAID_EMOJI);
			// else {
			// GuildEmoji raidEmoji =
			// EmojiServerHelper.getEmoji(RAID_EMOJI);
			// for (int i = 0; i < boss.getTier(); i++)
			// titleString += raidEmoji;
			// }
			builder.setTitle(titleString + "at " + raidData.gym_name);
			builder.withThumbnail(ImageHelper.getOfficalArtwork(pokemon, raidData.form));
			if (entry != null) {
				String boostedString = "";
				for (EnumWeather w : entry.weatherInfluences)
					boostedString += w.getEmoji();
				builder.appendDesc("**Weather Boosts**: " + boostedString);
			}
		}
		if (pokemon == null)
			builder.appendDesc("");
		else
			builder.appendDesc("\n**Time Left**: " + getTimeRemainingDespawn());
		TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("CST"));
		builder.appendDesc("\n**End Time**: " + TIME_FORMAT.format(new Date(raidData.end)));
		if (channel != null)
			builder.appendDesc("\n**Raid Channel**: " + channel);
		if (role != null)
			builder.appendDesc("\n**Raid Role**: " + role);
		// builder.addField("Time:", time, true);
		// builder.addField("Location:", location, true);

		if (this.role != null) {
			String interestString = "";
			for (User o : MiscUtils.getMemberByRole(channel.getGuild().block(), this.role.getId()))
				interestString += o.getUsername() + "\n";
			if (!interestString.isEmpty())
				builder.addField("Interested:", interestString, false);
		}

		if (entry != null) {
			String cpsString = "";
			cpsString += "**Max CP**: " + entry.CPs.max + "\n";
			cpsString += "**Catch CP**: " + entry.CPs.raidCaptureMin + "-" + entry.CPs.raidCaptureMax
					+ "\n";
			cpsString += "**Boosted Catch CP**: " + entry.CPs.raidCaptureBoostMin + "-"
					+ entry.CPs.raidCaptureBoostMax;
			builder.addField("Important CPs:", cpsString, false);

			// disable counters to embed smaller, less scrolling on small
			// screens
			// String counterString = "";
			// int rank = 1;
			// for (Counter c : entry.getTopCounters())
			// counterString += String.format("#%d %s", rank++, c);
			// if (!counterString.isEmpty())
			// builder.addField("Counters:", counterString, false);
		}

		if (raidData.move_1 != 0 && raidData.move_2 != 0) {
			DexMoveset set = raidData.getMoveset();
			if (entry == null)
				builder.addField("Moveset:", set.toString(), false);
			else
				builder.addField("Moveset:", set.toString(entry), false);
		}

		builder.addField("Directions:",
				String.format(
						"[Google Maps](https://www.google.com/maps/search/?api=1&query=%1$f,%2$f) | "
								+ "[Apple Maps](http://maps.apple.com/?daddr=%1$f,%2$f)",
						raidData.latitude, raidData.longitude),
				false);
		builder.addField("Reaction Usage:",
				"React with ✅ to indicate your interest and be added to the raid channel.", false);
		builder.withImage(String.format(
				"https://api.mapbox.com/styles/v1/mapbox/satellite-streets-v11/static/pin-s+%06X(%2$f,%3$f)/%2$f,%3$f,16.5,0,0/600x300@2x?logo=false&access_token=pk.eyJ1Ijoic2VsaW0wNDIiLCJhIjoiY2pyOXpmM2g1MG16cTQzbndqZXk5dHNndCJ9.vsh20BzsPBgTcBBcKWBqQw",
				raidData.getTeam().getColor(), raidData.longitude, raidData.latitude));
		return builder.build();
	}

	private String getRoleName() {
		Date start = new Date(raidData.start);
		ROLE_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("CST"));
		return (raidData.gym_name.replaceAll(" ", "_") + ROLE_DATE_FORMAT.format(start)).toLowerCase();
	}

	private String getTimeRemainingDespawn() {
		long rem = raidData.getTimeRemainingDespawn();
		int hours = (int) rem / 3600000;
		rem = rem % 3600;
		int mins = (int) rem / 360000;
		rem = rem % 360;
		int seconds = (int) rem / 60000;
		String ret = "";
		boolean cont = false;
		if (hours > 0) {
			ret += hours + " hours, ";
			cont = true;
		}
		if (mins > 0 || cont) {
			ret += mins + " minutes, ";
			cont = true;
		}
		if (seconds > 0 || cont)
			ret += seconds + " seconds, ";
		if (ret.isEmpty())
			return ret;
		return ret.substring(0, ret.length() - 2);
	}

}
