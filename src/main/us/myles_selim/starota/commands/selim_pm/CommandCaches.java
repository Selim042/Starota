package us.myles_selim.starota.commands.selim_pm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import discord4j.command.util.CommandException;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;

public class CommandCaches extends JavaCommand {

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

	public CommandCaches() {
		super("dumpCache", "Clears API caches.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("getCaches");
		return aliases;
	}

	@Override
	public String getGeneralUsage() {
		return "<cache>";
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, MessageChannel channel)
			throws CommandException {
		switch (args[0].toLowerCase()) {
		case "getcaches":
			String out = "";
			for (String key : CACHES.keySet())
				out += key + ", ";
			channel.createMessage(out);
			return;
		case "dumpcache":
			if (args.length < 2) {
				this.sendUsage(PrimaryCommandHandler.DEFAULT_PREFIX, channel);
				return;
			}
			if (!CACHES.containsKey(args[1])) {
				channel.createMessage("Cache not found");
				return;
			}
			CACHES.get(args[1]).accept(args[1]);
			channel.createMessage("Dumped");
			return;
		}
	}

}
