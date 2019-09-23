package us.myles_selim.starota;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.guild.GuildDeleteEvent;
import discord4j.core.event.domain.guild.MemberJoinEvent;
import discord4j.core.event.domain.guild.MemberLeaveEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.role.RoleUpdateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.debug_server.DebugServer;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EventListener;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.wrappers.StarotaServer;

public class EventHandler implements EventListener {

	private Set<ReadyEvent.Guild> guildsWereIn = Collections.emptySet();

	private boolean isInGuildAtStart(Snowflake guildId) {
		guildsWereIn = Collections.emptySet();
		for (ReadyEvent.Guild g : guildsWereIn)
			if (g.getId().asLong() == guildId.asLong()) {
				System.out.println("was in guild at start");
				return true;
			}
		return false;
	}

	@EventSubscriber
	public void onReady(ReadyEvent event) {
		guildsWereIn = event.getGuilds();
	}

	@EventSubscriber
	public void onServerCreate(GuildCreateEvent event) {
		if (isInGuildAtStart(event.getGuild().getId())) {
			System.out.println("skipped stats submission");
			return;
		}
		if (!Starota.FULLY_STARTED)
			return;
		Guild server = event.getGuild();
		if (!server.getMembers().collectList().block().contains(Starota.getOurUser()))
			return;
		Starota.submitStats();
		Starota.updateOwners();
		User selimUser = Starota.getUser(StarotaConstants.SELIM_USER_ID.asLong());
		PrivateChannel selimPm = selimUser.getPrivateChannel().block();
		selimPm.createMessage("Starota was added to the server: " + server.getName()).block();

		Member serverOwner = server.getOwner().block();
		PrivateChannel ownerPm = serverOwner.getPrivateChannel().block();
		EmbedBuilder builder = new EmbedBuilder();
		String ourName = Starota.getOurName(server);
		builder.withTitle("Thanks for using " + ourName + "!");
		builder.appendDesc("If you need any assistance with " + ourName
				+ " or it's features, feel free to join our support server at "
				+ StarotaConstants.SUPPORT_SERVER_LINK);
		ownerPm.createEmbed(builder.build()).block();
		if (!Starota.getOurUser().asMember(server.getId()).block().getBasePermissions().block()
				.contains(Permission.SEND_MESSAGES))
			ownerPm.createMessage(Starota.getOurName(server)
					+ " requires the `SEND_MESSAGES` permission for all command functionality.").block();
	}

	@EventSubscriber
	public void onServerLeave(GuildDeleteEvent event) {
		if (!event.isUnavailable()) {
			StarotaServer.getServer(event.getGuild().get()).clearDataOptions();
			Starota.updateOwners();
		}
	}

	@EventSubscriber
	public void onUserJoin(MemberJoinEvent event) {
		if (event.getGuild().block().getId().equals(StarotaConstants.SUPPORT_SERVER))
			Starota.updateOwners();
	}

	@EventSubscriber
	public void onUserJoin(MemberLeaveEvent event) {
		StarotaServer server = event.getGuild().map((g) -> StarotaServer.getServer(g)).block();
		Snowflake user = event.getUser().getId();
		if (server.hasProfile(user))
			server.deleteProfile(user);
	}

	private static final Map<String, Consumer<String>> CACHES = new HashMap<>();

	static {
		Reflections ref = new Reflections("us.myles_selim.starota", new MethodAnnotationsScanner());
		for (Method cl : ref.getMethodsAnnotatedWith(ClearCache.class)) {
			ClearCache clear = cl.getAnnotation(ClearCache.class);
			CACHES.put(clear.value(), cache -> {
				try {
					cl.invoke(null);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			});
		}
	}

	// update Patreon perms on debug server
	@EventSubscriber
	public void roleChange(RoleUpdateEvent event) {
		if (event.getCurrent().getGuild().block().getId().equals(StarotaConstants.SUPPORT_SERVER))
			DebugServer.update();
	}

}
