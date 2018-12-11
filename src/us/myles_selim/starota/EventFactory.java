package us.myles_selim.starota;

import java.util.List;
import java.util.Map.Entry;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.embed_converter.ExtraField;
import us.myles_selim.starota.lua.LuaUtils;
import us.myles_selim.starota.lua.events.GetProfileEvent;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.profiles.ProfileManager;
import us.myles_selim.starota.profiles.SilphRoadUtils;

public class EventFactory {

	public static GetProfileEvent fireProfileEvent(IGuild server, PlayerProfile profile) {
		GetProfileEvent event = new GetProfileEvent(server, profile);

		event.setColor(profile.getTeam().getColor());
		event.setThumbnail(profile.getTeam().getIcon());

		List<ExtraField> fields = event.getFields();
		fields.add(new ExtraField("Trainer Level:", Integer.toString(profile.getLevel()), true));
		fields.add(new ExtraField("Team:", profile.getTeam().getName(), true));
		if (profile.getRealName() != null)
			fields.add(new ExtraField("Real Name:", profile.getRealName(), true));
		if (profile.getTrainerCode() != -1)
			fields.add(new ExtraField("Trainer Code:", profile.getTrainerCodeString(), true));
		if (!profile.getAlts().isEmpty()) {
			String alts = "";
			for (Entry<String, Long> e : profile.getAlts().entrySet())
				alts += "- **" + e.getKey() + "**: " + ProfileManager.getTrainerCodeString(e.getValue())
						+ "\n";
			fields.add(new ExtraField("Alternate Accounts:", alts, false));
		}
		if (SilphRoadUtils.hasCard(profile.getPoGoName())) {
			fields.add(new ExtraField("Silph Road Card:", SilphRoadUtils.getCard(profile.getPoGoName()),
					false));
			event.setThumbnail(SilphRoadUtils.getCardAvatar(profile.getPoGoName()));
		}
		IUser user = profile.getDiscordUser();
		fields.add(
				new ExtraField("Discord User:", user.getName() + "#" + user.getDiscriminator(), true));
		String patronRoleName = profile.getPatronRoleName();
		if (patronRoleName != null)
			fields.add(new ExtraField("Patron:", patronRoleName, true));
		LuaUtils.handleEvent(event);
		return event;
	}

}
