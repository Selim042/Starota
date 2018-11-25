package us.myles_selim.starota;

import java.util.List;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.GuildLeaveEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IEmbed;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.research.ResearchTracker;
import us.myles_selim.starota.trading.EnumPokemon;
import us.myles_selim.starota.trading.FormManager;

public class EventHandler {

	private static final long RESEARCH_WEBHOOK = 490020347408678922L;
	private static final String SUBMITTED_REGEX = "\\*\\*Submitted by:\\*\\* .*#\\d{4}";

	@EventSubscriber
	public void onMessageRecieved(MessageReceivedEvent event) {
		if (event.getGuild().getLongID() == Starota.SUPPORT_SERVER)
			return;
		if (Starota.DEBUG)
			System.out.println("Channel: " + event.getChannel() + ", Author: " + event.getAuthor()
					+ ", Message: " + event.getMessage());
		// if (JavaCommandHandler.executeCommand(event.getMessage(),
		// event.getGuild(), event.getChannel()))
		// return;
		if (event.getGuild().getLongID() == Starota.PVILLE_SERVER) {
			long authorId = event.getAuthor().getLongID();
			long webhookId = event.getMessage().getWebhookLongID();
			if (webhookId == RESEARCH_WEBHOOK) {
				System.out.println("webhook research");
				IMessage message = event.getMessage();
				List<IEmbed> embeds = message.getEmbeds();
				for (IEmbed embed : embeds) {
					for (String line : embed.getDescription().split("\n")) {
						if (line.matches(SUBMITTED_REGEX)) {
							String trueAuthor = line.substring(18);
							IUser trueAuthorUser = Starota.findUser(event.getGuild().getLongID(),
									trueAuthor);
							if (trueAuthorUser != null) {
								authorId = trueAuthorUser.getLongID();
								System.out.println(
										"true author for \"" + line + "\" is " + trueAuthorUser.getName()
												+ "#" + trueAuthorUser.getDiscriminator());
							} else
								authorId = -1;
						}
					}
				}
			}
			if (authorId != -1)
				ResearchTracker.addPost(event.getGuild().getLongID(), authorId);
		} else
			ResearchTracker.addPost(event.getGuild(), event.getAuthor());
	}

	@EventSubscriber
	public void onServerCreate(GuildCreateEvent event) {
		IGuild server = event.getGuild();
		IUser selimUser = Starota.getUser(Starota.SELIM_USER_ID);
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
					+ Starota.SUPPORT_SERVER_LINK);
			ownerPm.sendMessage(builder.build());
		});
	}

	@EventSubscriber
	public void onServerLeave(GuildLeaveEvent event) {
		ServerOptions.clearOptions(event.getGuild());
	}

	@EventSubscriber
	public void formManagerHandler(MessageReceivedEvent event) {
		if (!(event.getChannel() instanceof IPrivateChannel))
			return;
		IPrivateChannel channel = (IPrivateChannel) event.getChannel();
		// Selim's user id
		if (channel.getRecipient().getLongID() != 134855940938661889L)
			return;
		String message = event.getMessage().getContent();
		if (message.startsWith(".help")) {
			channel.sendMessage(".removeExclude\n" + ".forceEnable\n" + ".addShinyable\n");
			return;
		}
		String[] args = message.split(" ");
		if (args.length != 2)
			return;
		EnumPokemon pokemon = EnumPokemon.getPokemon(args[1]);
		if (pokemon == null)
			pokemon = EnumPokemon.getPokemon(Integer.parseInt(args[1]));
		switch (args[0].toLowerCase()) {
		case ".removeExclude":
			FormManager.removeExcluded(pokemon);
			channel.sendMessage("Removed " + pokemon + " from exclusion");
			break;
		case ".forceEnable":
			FormManager.addForceEnable(pokemon);
			channel.sendMessage("Forced enabled " + pokemon);
			break;
		case ".addShinyable":
			FormManager.addShinyable(pokemon);
			channel.sendMessage("Added " + pokemon + " as shinyable");
			break;
		default:
			break;
		}
	}

}
