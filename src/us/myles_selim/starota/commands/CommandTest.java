package us.myles_selim.starota.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.lib.jse.JsePlatform;
import org.squiddev.cobalt.lib.platform.AbstractResourceManipulator;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.lua.DiscordLib;
import us.myles_selim.starota.lua.StarotaLib;

public class CommandTest extends Command {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		LuaState state = new LuaState(new AbstractResourceManipulator() {

			private boolean hasRun = false;

			@Override
			public InputStream findResource(String filename) {
				if (hasRun)
					return null;
				hasRun = true;
				try {
					return new FileInputStream(new File(filename));
				} catch (FileNotFoundException e) {
					return null;
				}
			}
		});
		state.stdout = System.out;
		String script = "test.lua";

		LuaTable _G = JsePlatform.standardGlobals(state);
		_G.load(state, new DiscordLib(guild));
		_G.load(state, new StarotaLib(guild));
		try {
			_G.get(state, "dofile").checkFunction().call(state, ValueFactory.valueOf(script));
		} catch (LuaError e) {
			RequestBuffer.request(() -> channel.sendMessage("lua error: " + e.getLocalizedMessage()));
			e.printStackTrace();
		}
	}

}
