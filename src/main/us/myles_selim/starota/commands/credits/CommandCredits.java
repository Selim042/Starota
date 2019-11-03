package us.myles_selim.starota.commands.credits;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.StarotaConstants;

public class CommandCredits extends JavaCommand {

	public CommandCredits() {
		super("credits", "Displays the credits for " + Starota.BOT_NAME + ".");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, MessageChannel channel)
			throws CommandException {
		EmbedBuilder eBuilder = new EmbedBuilder();
		eBuilder.withTitle("Credits for " + Starota.BOT_NAME + " v" + StarotaConstants.VERSION);
		eBuilder.withUrl(Starota.getCreditsURL());

		StringBuilder builder;
		for (Creditable creditable : Starota.getCredits()) {
			builder = new StringBuilder();
			EnumCreditType type = creditable.getType();
			if (creditable instanceof Credit) {
				Credit credit = (Credit) creditable;
				if (type == EnumCreditType.OTHER) {
					if (credit.getLink() == null)
						builder.append(
								String.format("**%s**: %s\n", credit.getTitle(), credit.getName()));
					else
						builder.append(String.format("**%s**: [%s](%s)\n", credit.getTitle(),
								credit.getName(), credit.getLink()));
				} else {
					if (credit.getLink() == null)
						builder.append(
								String.format("**%s**: %s\n", type.getDisplay(), credit.getName()));
					else
						builder.append(String.format("**%s**: [%s](%s)\n", type.getDisplay(),
								credit.getName(), credit.getLink()));
				}
			} else if (creditable instanceof CreditSet) {
				builder.append(String.format("**%s**:\n", type.getDisplay()));
				for (Creditable credit2 : (CreditSet) creditable) {
					if (!(credit2 instanceof Credit))
						continue;
					Credit credit = (Credit) credit2;
					if (type == EnumCreditType.OTHER) {
						if (credit.getLink() == null)
							builder.append(
									String.format("- %s (%s)\n", credit.getName(), credit.getTitle()));
						else
							builder.append(String.format("- [%s](%s) (%s)\n", credit.getName(),
									credit.getLink(), credit.getTitle()));
					} else {
						if (credit.getLink() == null)
							builder.append(String.format("- %s\n", credit.getName()));
						else
							builder.append(
									String.format("- [%s](%s)\n", credit.getName(), credit.getLink()));
					}
				}
			}
			eBuilder.appendDesc(builder.toString());
		}

		// StringBuilder contributors = new StringBuilder();
		// contributors.append("**Contributors**:\n");
		// for (GitHubContributor u : GitHubAPI.getContributors("Selim042",
		// "Starota"))
		// contributors.append(String.format(" - [%s](%s)\n", u.getLogin(),
		// u.getHtmlUrl()));
		// builder.appendDesc(contributors.toString());
		//
		// builder.appendDesc("**Discord4J**:
		// [austinv11](https://github.com/austinv11)\n");
		// builder.appendDesc("**Pokemon Go Database**: [Pokemon Go
		// Hub](https://pokemongohub.net/)\n");
		// builder.appendDesc(
		// "**Raid Boss, Field Research, and Egg Information**: [The Silph
		// Road](https://thesilphroad.com/)\n");
		// builder.appendDesc("**Ditto Information**: [Leek
		// Duck](https://leekduck.com)\n");
		// builder.appendDesc("**Weather**:
		// [AccuWeather](https://accuweather.com/)\n");
		//
		// StringBuilder editors = new StringBuilder();
		// editors.append("**Article Editors**:\n");
		// Guild supportServer = Starota.getSupportServer();
		// for (Member u : MiscUtils.getMembersByRole(supportServer,
		// StarotaConstants.EDITOR_ROLE_ID))
		// editors.append(String.format(" - %s#%s\n", u.getUsername(),
		// u.getDiscriminator()));
		// builder.appendDesc(editors.toString());
		//
		// StringBuilder betaTesters = new StringBuilder();
		// betaTesters.append("**Beta Testers**:\n");
		// Guild testServer =
		// Starota.getGuild(StarotaConstants.BETA_TEST_SERVER);
		// for (Member u : testServer.getMembers().filter(m ->
		// !m.isBot()).collectList().block())
		// betaTesters.append(String.format(" - %s#%s\n", u.getUsername(),
		// u.getDiscriminator()));
		// builder.appendDesc(betaTesters.toString());

		channel.createEmbed(eBuilder.build()).block();
	}

}
