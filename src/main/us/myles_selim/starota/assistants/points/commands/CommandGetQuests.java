package us.myles_selim.starota.assistants.points.commands;

import java.util.List;
import java.util.Random;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.assistants.points.PointServer;
import us.myles_selim.starota.assistants.points.QuestType;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;

public class CommandGetQuests extends BotCommand<PointServer> {

	public CommandGetQuests() {
		super("getQuests", "Gets the current quests.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("quests");
		return aliases;
	}

	@Override
	public void execute(String[] args, Message message, PointServer server, MessageChannel channel)
			throws CommandException {
		Random rand = new Random();
		// server.setFastQuest(
		// QuestType.getRandomQuest(rand, true,
		// System.currentTimeMillis() + 360000));
		// server.setSlowQuest(
		// QuestType.getRandomQuest(new Random(), false,
		// System.currentTimeMillis() + 360000));
		// boolean doneOne = false;
		// EmbedBuilder builder = new EmbedBuilder();
		// PointQuest<?, ?> fastQuest = server.getFastQuest();
		// if (fastQuest != null && !fastQuest.hasExpired()) {
		// builder.appendDesc(String.format("Fast Quest: %s\n", fastQuest));
		// doneOne = true;
		// }
		// PointQuest<?, ?> slowQuest = server.getSlowQuest();
		// if (slowQuest != null && !slowQuest.hasExpired()) {
		// builder.appendDesc(String.format("Slow Quest: %s\n", slowQuest));
		// doneOne = true;
		// }
		// if (doneOne)
		// channel.createMessage(builder.build());
		// else
		// channel.createMessage(new EmbedBuilder().appendDesc("No quests
		// active").build());
		channel.createEmbed(QuestType
				.getRandomQuest(rand, rand.nextBoolean(), System.currentTimeMillis() + 36000).toEmbed())
				.block();
	}

}
