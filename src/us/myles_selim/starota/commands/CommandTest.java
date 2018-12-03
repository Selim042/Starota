package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.modules.StarotaModule;

public class CommandTest extends JavaCommand {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		StarotaModule m1 = new StarotaModule("m1", "m1", new StarotaModule[] { null });
		StarotaModule m2 = new StarotaModule("m2", "m2", m1);
		m1.getDependencies()[0] = m2;
		System.out.println(StarotaModule.isModuleEnabled(guild, m1));
	}

}
