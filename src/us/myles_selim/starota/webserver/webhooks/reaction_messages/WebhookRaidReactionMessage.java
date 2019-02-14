package us.myles_selim.starota.webserver.webhooks.reaction_messages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.TimeZone;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.ICategory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.ImageHelper;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.modules.BaseModules;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.pokedex.GoHubDatabase;
import us.myles_selim.starota.pokedex.PokedexEntry;
import us.myles_selim.starota.pokedex.PokedexEntry.DexMoveset;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.silph_road.SilphRoadData.RaidBoss;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.webserver.webhooks.WebhookRaid;
import us.myles_selim.starota.wrappers.StarotaServer;

public class WebhookRaidReactionMessage extends ReactionMessage {

	private static final String CATEGORY_NAME = "Raid Channels";
	private static final SimpleDateFormat ROLE_DATE_FORMAT = new SimpleDateFormat("HHmm");
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm");

	private WebhookRaid raidData;
	private IChannel channel;
	private IRole role;
	private EnumPokemon pokemon;
	private Form form;

	public WebhookRaidReactionMessage(WebhookRaid raidData) {
		this.raidData = raidData;
		this.pokemon = EnumPokemon.getPokemon(raidData.pokemon_id);
		if (this.pokemon != null && this.pokemon.getFormSet() != null)
			this.form = this.pokemon.getFormSet().getForm(raidData.form);
	}

	@Override
	public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
		RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of("✅")));
	}

	@Override
	public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		if (react.getEmoji().getName().equals("✅")) {
			user.addRole(this.role);
			this.channel.sendMessage(user + " was added.");
			IGuild guild = server.getDiscordGuild();
			String name = getRoleName();
			if (this.channel == null) {
				ICategory category = null;
				for (ICategory c : guild.getCategoriesByName(CATEGORY_NAME)) {
					category = c;
					break;
				}
				if (category == null) {
					category = guild.createCategory(CATEGORY_NAME);
					category.overrideRolePermissions(guild.getEveryoneRole(),
							EnumSet.noneOf(Permissions.class), EnumSet.of(Permissions.READ_MESSAGES));
					category.overrideUserPermissions(
							Starota.getOurUser(), EnumSet.of(Permissions.MANAGE_CHANNEL,
									Permissions.MANAGE_PERMISSIONS, Permissions.READ_MESSAGES),
							EnumSet.noneOf(Permissions.class));
				}
				System.out.println(name);
				this.channel = category.createChannel(name);
			}
			if (this.role == null) {
				this.role = guild.createRole();
				this.role.changePermissions(EnumSet.noneOf(Permissions.class));
				this.role.changeName(name);
				this.channel.overrideRolePermissions(this.role,
						EnumSet.of(Permissions.READ_MESSAGE_HISTORY, Permissions.READ_MESSAGES),
						EnumSet.noneOf(Permissions.class));
			}
		}
		this.editMessage(channel, msg);
	}

	@Override
	public void onReactionRemoved(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		if (react.getEmoji().getName().equals("✅")) {
			user.removeRole(this.role);
			this.channel.sendMessage(user + " was removed.");
		}
		this.editMessage(channel, msg);
	}

	@Override
	protected EmbedObject getEmbed(StarotaServer server) {
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
			// if (boss.getTier() == 6)
			// titleString += EmojiServerHelper.getEmoji(EX_RAID_EMOJI);
			// else {
			// IEmoji raidEmoji =
			// EmojiServerHelper.getEmoji(RAID_EMOJI);
			// for (int i = 0; i < boss.getTier(); i++)
			// titleString += raidEmoji;
			// }
			builder.withTitle(titleString + "at " + raidData.gym_name);
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
		// builder.appendField("Time:", time, true);
		// builder.appendField("Location:", location, true);

		if (this.role != null) {
			String interestString = "";
			for (IUser o : channel.getGuild().getUsersByRole(this.role))
				interestString += o.getName() + "\n";
			if (!interestString.isEmpty())
				builder.appendField("Interested:", interestString, false);
		}

		if (entry != null) {
			String cpsString = "";
			cpsString += "**Max CP**: " + entry.CPs.max + "\n";
			cpsString += "**Catch CP**: " + entry.CPs.raidCaptureMin + "-" + entry.CPs.raidCaptureMax
					+ "\n";
			cpsString += "**Boosted Catch CP**: " + entry.CPs.raidCaptureBoostMin + "-"
					+ entry.CPs.raidCaptureBoostMax;
			builder.appendField("Important CPs:", cpsString, false);

			// disable counters to embed smaller, less scrolling on small
			// screens
			// String counterString = "";
			// int rank = 1;
			// for (Counter c : entry.getTopCounters())
			// counterString += String.format("#%d %s", rank++, c);
			// if (!counterString.isEmpty())
			// builder.appendField("Counters:", counterString, false);
		}

		if (raidData.move_1 != 0 && raidData.move_2 != 0) {
			DexMoveset set = raidData.getMoveset();
			if (entry == null)
				builder.appendField("Moveset:", set.toString(), false);
			else
				builder.appendField("Moveset:", set.toString(entry), false);
		}

		builder.appendField("Directions:",
				String.format(
						"[Google Maps](https://www.google.com/maps/search/?api=1&query=%1$f,%2$f) | "
								+ "[Apple Maps](http://maps.apple.com/?daddr=%1$f,%2$f)",
						raidData.latitude, raidData.longitude),
				false);
		builder.appendField("Reaction Usage:",
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
