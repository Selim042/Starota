package us.myles_selim.starota.commands;

import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaValue;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.lua.LuaUtils;
import us.myles_selim.starota.lua.conversion.ConversionHandler;

public class CommandTest extends JavaCommand {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		LuaState state = LuaUtils.getState(guild);
		LuaValue msgL = ConversionHandler.convertToLua(state, message);
		channel.sendMessage("" + msgL);
		try {
			System.out.println(msgL.get(state, "getAuthor").checkFunction().call(state));
		} catch (LuaError e) {
			e.printStackTrace();
		}
		// state.stdout = System.out;
		// String script = "test.lua";
		// LuaTable _G = state.getMainThread().getfenv();
		// try {
		// LoadState.load(state, new FileInputStream(new File(script)), "@" +
		// script, _G).call(state);
		// } catch (LuaError | CompileException e) {
		// channel.sendMessage("lua error: " + e.getLocalizedMessage());
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// state.stdout = null;
	}

}
