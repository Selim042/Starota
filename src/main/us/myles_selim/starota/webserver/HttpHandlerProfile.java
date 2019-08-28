package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map.Entry;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.silph_road.SilphCard;
import us.myles_selim.starota.silph_road.SilphCard.SilphBadgeData;
import us.myles_selim.starota.silph_road.SilphCard.SilphSocial;
import us.myles_selim.starota.silph_road.SilphRoadCardUtils;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthUser;
import us.myles_selim.starota.webserver.WebServer.Cookie;
import us.myles_selim.starota.wrappers.StarotaServer;

@SuppressWarnings("restriction")
public class HttpHandlerProfile implements HttpHandler {

	@Override
	public void handle(HttpExchange ex) throws IOException {
		try {
			WebServer.handleBaseGET(ex);
			if (!WebServer.isLoggedIn(ex)) {
				WebServer.return404(ex, "Please login");
				return;
			}
			String path = ex.getRequestURI().toString();
			Cookie tokenCookie = WebServer.getCookies(ex).get("token");
			Snowflake userId;
			if (path.matches("/profile/\\d{18}/?.*?"))
				userId = Snowflake.of(path.substring(9, 9 + 18));
			else if (tokenCookie != null && path.matches("/profile/?.*?")) {
				OAuthUser loggedIn = OAuthUtils.getUser(tokenCookie.value);
				userId = Snowflake.of(loggedIn.id);
			} else {
				WebServer.return404(ex, "Profile not found");
				return;
			}
			Cookie serverCookie = WebServer.getCookies(ex).get("current_server");
			if (serverCookie == null) {
				WebServer.returnServer404(ex, "Please select a server");
				return;
			}
			Snowflake serverId = Snowflake.of(serverCookie.value);
			boolean inGuild = Starota.getClient().getGuilds().any((g) -> g.getId().equals(serverId))
					.block();
			if (!inGuild) {
				WebServer.return404(ex, "Profile not found");
				return;
			}
			Guild guild = Starota.getGuild(serverId);
			boolean findUser = guild.getMembers().any((m) -> m.getId().equals(userId)).block();
			if (!findUser) {
				WebServer.return404(ex, "Profile not found");
				return;
			}
			StarotaServer server = StarotaServer.getServer(guild);
			Member member = guild.getMemberById(userId).block();
			PlayerProfile profile = server.getProfile(member);
			if (profile == null) {
				WebServer.return404(ex, "Profile not found");
				return;
			}

			BufferedReader profTempFile;
			boolean hasSilphCard = profile.getSilphRoadCard() != null;
			if (hasSilphCard)
				profTempFile = new BufferedReader(new FileReader("http/profile_silph.html"));
			else
				profTempFile = new BufferedReader(new FileReader("http/profile.html"));
			StringBuilder profileTempBuilder = new StringBuilder();
			profTempFile.lines().forEach(profileTempBuilder::append);
			profTempFile.close();
			String profileTemp = profileTempBuilder.toString();

			// fill stuff
			profileTemp = WebServer.fillBaseStuff(ex, tokenCookie.value, profileTemp);
			profileTemp = fillProfileInfo(profileTemp, profile, member);
			if (hasSilphCard)
				profileTemp = fillSilphCard(profileTemp,
						SilphRoadCardUtils.getCard(profile.getPoGoName()));

			WebServer.setContentType(ex, "http/profile.html");
			OutputStream response = ex.getResponseBody();
			byte[] out = profileTemp.getBytes("UTF-8");
			ex.sendResponseHeaders(200, out.length);
			response.write(out);
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
			WebServer.returnError(ex, e);
		}
	}

	private String fillProfileInfo(String profileTemp, PlayerProfile profile, Member member) {
		profileTemp = profileTemp.replaceAll("\\{POGO_NAME\\}", profile.getPoGoName())
				.replaceAll("\\{DISCORD_NAME_DISCRIM\\}",
						member.getUsername() + "#" + member.getDiscriminator())
				.replaceAll("\\{POGO_LEVEL\\}", Integer.toString(profile.getLevel()))
				.replaceAll("\\{POGO_TEAM\\}", profile.getTeam().getName())
				.replaceAll("\\{DISCORD_AVATAR\\}", member.getAvatarUrl());

		if (profile.getRealName() != null)
			profileTemp = profileTemp.replaceAll("\\{REAL_NAME\\}", profile.getRealName());
		else
			profileTemp = profileTemp.replaceAll("\\{REAL_NAME\\}", "-");
		if (profile.getTrainerCodeString() != null)
			profileTemp = profileTemp.replaceAll("\\{POGO_TRAINER_CODE\\}",
					profile.getTrainerCodeString());
		else
			profileTemp = profileTemp.replaceAll("\\{POGO_TRAINER_CODE\\}", "-");
		if (profile.getAlts().size() != 0) {
			StringBuilder alts = new StringBuilder();
			for (Entry<String, Long> alt : profile.getAlts().entrySet())
				alts.append(String.format("%s: %s\n", alt.getKey(),
						MiscUtils.getTrainerCodeString(alt.getValue())));
			profileTemp = profileTemp.replaceAll("\\{POGO_ALT_ACCOUNTS\\}", alts.toString());
		} else
			profileTemp = profileTemp.replaceAll("\\{POGO_ALT_ACCOUNTS\\}", "-");
		if (profile.getSilphRoadCard() != null) {
			profileTemp = profileTemp.replaceAll("\\{SILPH_CARD_URL\\}", profile.getSilphRoadCard());
			profileTemp = profileTemp.replaceAll("\\{SILPH_CARD_DISPLAY_URL\\}",
					profile.getSilphRoadCard().replaceAll("https?://", ""));
		} else {
			profileTemp = profileTemp.replaceAll("\\{SILPH_CARD_URL\\}", "#");
			profileTemp = profileTemp.replaceAll("\\{SILPH_CARD_DISPLAY_URL\\}", "-");
		}
		if (profile.getDonorRoleName() != null)
			profileTemp = profileTemp.replaceAll("\\{DONOR_LEVEL\\}", profile.getDonorRoleName());
		else
			profileTemp = profileTemp.replaceAll("\\{DONOR_LEVEL\\}", "-");
		return profileTemp;
	}

	private String fillSilphCard(String profileTemp, SilphCard card) {
		profileTemp = profileTemp.replaceAll("\\{SILPH_CARD_ID\\}", card.data.card_id.toUpperCase());
		profileTemp = profileTemp.replaceAll("\\{SILPH_JOINED_DATE\\}", card.data.joined);
		profileTemp = profileTemp.replaceAll("\\{SILPH_TRAVELERS_MET\\}",
				Integer.toString(card.data.handshakes));
		profileTemp = profileTemp.replaceAll("\\{SILPH_MEETUP_CHECKINS\\}",
				Integer.toString(card.data.checkins.length));
		profileTemp = profileTemp.replaceAll("\\{SILPH_NEST_REPORTS\\}",
				Integer.toString(card.data.nest_migrations));

		StringBuilder social = new StringBuilder();
		for (SilphSocial s : card.data.socials)
			social.append(String.format("%s: %s<br>", s.vendor, s.username));
		profileTemp = profileTemp.replaceAll("\\{SILPH_SOCIAL\\}", social.toString());

		profileTemp = profileTemp.replaceAll("\\{SILPH_PLAYSTYLE\\}",
				String.format("%s<br>%s<br>%s", card.data.playstyle, card.data.goal,
						String.format("Typically raids %sx/week", card.data.raid_average)));

		String topHtml = "<img style=\"width: 40px;height: 40px;\" src=\"%s\" />";
		StringBuilder top6 = new StringBuilder();
		if (card.data.top_6_pokemon == null || card.data.top_6_pokemon.length == 0)
			top6.append("- - - - - -");
		else
			for (int p : card.data.top_6_pokemon) {
				EnumPokemon pp = EnumPokemon.getPokemon(p);
				if (pp != null)
					top6.append(String.format(topHtml, ImageHelper.getOfficalArtwork(pp)));
			}
		profileTemp = profileTemp.replaceAll("\\{SILPH_TOP_6\\}", top6.toString());

		String badgeHtml = "<img style=\"width: 32px;height: 38px;margin: 2px;\" src=\"%s\" />";
		StringBuilder badges = new StringBuilder();
		if (card.data.badges == null || card.data.badges.length == 0)
			badges.append("-");
		else
			for (SilphBadgeData b : card.data.badges)
				badges.append(String.format(badgeHtml, b.Badge.image));
		profileTemp = profileTemp.replaceAll("\\{SILPH_BADGES\\}", badges.toString());

		return profileTemp;
	}

}
