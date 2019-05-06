package us.myles_selim.starota;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.guild.GuildDeleteEvent;
import discord4j.core.event.domain.guild.MemberJoinEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import us.myles_selim.starota.assistants.StarotaAssistants;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.wrappers.StarotaServer;

public class EventHandler {

	public void onMessageRecieved(MessageCreateEvent event) {
		if (event.getGuild() == null
				|| event.getGuild().block().getId().equals(StarotaConstants.SUPPORT_SERVER))
			return;
		if (Starota.DEBUG)
			System.out.println("Channel: " + event.getMessage().getChannel() + ", Author: "
					+ event.getMessage().getAuthor() + ", Message: " + event.getMessage());
	}

	public void onServerCreate(GuildCreateEvent event) {
		Starota.submitStats();
		Starota.updateOwners();
		Guild server = event.getGuild();
		if (!server.getMembers().collectList().block().contains(Starota.getSelf()))
			return;
		User selimUser = Starota.getUser(StarotaConstants.SELIM_USER_ID.asLong());
		PrivateChannel selimPm = selimUser.getPrivateChannel().block();
		selimPm.createMessage("Starota was added to the server: " + server.getName());

		User serverOwner = server.getOwner().block();
		PrivateChannel ownerPm = serverOwner.getPrivateChannel().block();
		ownerPm.createEmbed((e) -> {
			String ourName = Starota.getOurName(server);
			e.setTitle("Thanks for using " + ourName + "!");
			e.setDescription("If you need any assistance with " + ourName
					+ " or it's features, feel free to join our support server at "
					+ StarotaConstants.SUPPORT_SERVER_LINK);
		});
		if (!Starota.getSelf().asMember(server.getId()).block().getBasePermissions().block()
				.contains(Permission.SEND_MESSAGES))
			ownerPm.createMessage(Starota.getOurName(server)
					+ " requires the `SEND_MESSAGES` permission for all command functionality.");
	}

	public void onServerLeave(GuildDeleteEvent event) {
		StarotaServer.getServer(event.getGuild().get()).clearDataOptions();
		Starota.updateOwners();
	}

	public void onUserJoin(MemberJoinEvent event) {
		Starota.updateOwners();
		if (StarotaAssistants.isAssistant(event.getMember()))
			StarotaAssistants.setAssistantRole(event.getGuild().block(), event.getMember());
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

}
