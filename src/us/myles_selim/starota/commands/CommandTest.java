package us.myles_selim.starota.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.compiler.CompileException;
import org.squiddev.cobalt.compiler.LoadState;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.lua.LuaUtils;

public class CommandTest extends JavaCommand {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		LuaState state = LuaUtils.getState(guild);
		state.stdout = System.out;
		String script = "test.lua";
		LuaTable _G = state.getMainThread().getfenv();
		try {
			LoadState.load(state, new FileInputStream(new File(script)), "@" + script, _G).call(state);
		} catch (LuaError | CompileException e) {
			channel.sendMessage("lua error: " + e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
