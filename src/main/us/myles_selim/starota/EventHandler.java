package us.myles_selim.starota;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.GuildLeaveEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.impl.events.guild.role.RoleUpdateEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.assistants.StarotaAssistants;
import us.myles_selim.starota.debug_server.DebugServer;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.wrappers.StarotaServer;

public class EventHandler {

	// private static final long RESEARCH_WEBHOOK = 490020347408678922L;
	// private static final String SUBMITTED_REGEX = "\\*\\*Submitted by:\\*\\*
	// .*#\\d{4}";

	@EventSubscriber
	public void onMessageRecieved(MessageReceivedEvent event) {
		if (event.getGuild() == null || event.getGuild().getLongID() == StarotaConstants.SUPPORT_SERVER)
			return;
		if (Starota.DEBUG)
			System.out.println("Channel: " + event.getChannel() + ", Author: " + event.getAuthor()
					+ ", Message: " + event.getMessage());
		// if (JavaCommandHandler.executeCommand(event.getMessage(),
		// event.getGuild(), event.getChannel()))
		// return;
		// if (event.getGuild().getLongID() == Starota.PVILLE_SERVER) {
		// long authorId = event.getAuthor().getLongID();
		// long webhookId = event.getMessage().getWebhookLongID();
		// if (webhookId == RESEARCH_WEBHOOK) {
		// System.out.println("webhook research");
		// IMessage message = event.getMessage();
		// List<IEmbed> embeds = message.getEmbeds();
		// for (IEmbed embed : embeds) {
		// for (String line : embed.getDescription().split("\n")) {
		// if (line.matches(SUBMITTED_REGEX)) {
		// String trueAuthor = line.substring(18);
		// IUser trueAuthorUser = Starota.findUser(event.getGuild().getLongID(),
		// trueAuthor);
		// if (trueAuthorUser != null) {
		// authorId = trueAuthorUser.getLongID();
		// System.out.println(
		// "true author for \"" + line + "\" is " + trueAuthorUser.getName()
		// + "#" + trueAuthorUser.getDiscriminator());
		// } else
		// authorId = -1;
		// }
		// }
		// }
		// }
		// if (authorId != -1)
		// ResearchTracker.addPost(event.getGuild().getLongID(), authorId);
		// }
		// else
		// ResearchTracker.addPost(event.getGuild(), event.getAuthor());
	}

	@EventSubscriber
	public void onServerCreate(GuildCreateEvent event) {
		Starota.submitStats();
		Starota.updateOwners();
		IGuild server = event.getGuild();
		if (!server.getUsers().contains(Starota.getClient().getOurUser()))
			return;
		IUser selimUser = Starota.getUser(StarotaConstants.SELIM_USER_ID);
		RequestBuffer.request(() -> {
			IPrivateChannel selimPm = selimUser.getOrCreatePMChannel();
			selimPm.sendMessage("Starota was added to the server: " + server.getName());
		});
		RequestBuffer.request(() -> {
			IUser serverOwner = server.getOwner();
			IPrivateChannel ownerPm = serverOwner.getOrCreatePMChannel();
			EmbedBuilder builder = new EmbedBuilder();
			String ourName = Starota.getOurName(server);
			builder.withTitle("Thanks for using " + ourName + "!");
			builder.appendDesc("If you need any assistance with " + ourName
					+ " or it's features, feel free to join our support server at "
					+ StarotaConstants.SUPPORT_SERVER_LINK);
			ownerPm.sendMessage(builder.build());
		});
		if (!Starota.getOurUser().getPermissionsForGuild(server).contains(Permissions.SEND_MESSAGES)) {
			IUser serverOwner = server.getOwner();
			IPrivateChannel ownerPm = serverOwner.getOrCreatePMChannel();
			ownerPm.sendMessage(Starota.getOurName(server)
					+ " requires the `SEND_MESSAGES` permission for all command functionality.");
		}
	}

	@EventSubscriber
	public void onServerLeave(GuildLeaveEvent event) {
		StarotaServer.getServer(event.getGuild()).clearDataOptions();
		Starota.updateOwners();
	}

	@EventSubscriber
	public void onUserJoin(UserJoinEvent event) {
		Starota.updateOwners();
		if (StarotaAssistants.isAssistant(event.getUser()))
			StarotaAssistants.setAssistantRole(event.getGuild(), event.getUser());
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

	@EventSubscriber
	public void cacheHandler(MessageReceivedEvent event) {
//		if (!(event.getChannel() instanceof IPrivateChannel))
//			return;
//		IPrivateChannel channel = (IPrivateChannel) event.getChannel();
//		// Selim's user id
//		if (channel.getRecipient().getLongID() != 134855940938661889L)
//			return;
//		String message = event.getMessage().getContent();
//		if (message.startsWith(".help")) {
//			channel.sendMessage(".dumpCache\n" + ".getCaches\n");
//			return;
//		}
//		String[] args = message.split(" ");
//		switch (args[0].toLowerCase()) {
//		case ".getcaches":
//			String out = "";
//			for (String key : CACHES.keySet())
//				out += key + ", ";
//			channel.sendMessage(out);
//			return;
//		case ".dumpcache":
//			if (args.length < 2) {
//				channel.sendMessage("Usage: .dumpCache <cacheName>");
//				return;
//			}
//			if (!CACHES.containsKey(args[1])) {
//				channel.sendMessage("Cache not found");
//				return;
//			}
//			CACHES.get(args[1]).accept(args[1]);
//			channel.sendMessage("Dumped");
//			return;
//		}
	}

	// update Patreon perms on debug server
	@EventSubscriber
	public void roleChange(RoleUpdateEvent event) {
		if (event.getGuild().getLongID() == StarotaConstants.SUPPORT_SERVER)
			DebugServer.update();
	}

}
