package us.myles_selim.starota.lua.conversion.discord;

import java.util.List;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.lua.conversion.IConverter;

public class UserConverter implements IConverter {

	@Override
	public LuaValue toLua(LuaState state, Object val) throws LuaError {
		if (!(val instanceof IUser))
			return Constants.NIL;
		IUser user = (IUser) val;
		LuaTable methods = new LuaTable();
		methods.rawset("getName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(user.getName());
			}
		});
		methods.rawset("getDiscriminator", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(user.getDiscriminator());
			}
		});

		methods.rawset("getDisplayName", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (!(arg instanceof LuaUserdata) || !(((LuaUserdata) arg).instance instanceof IGuild))
					throw new LuaError("arg1 must be a server");
				IGuild target = (IGuild) ((LuaUserdata) arg).instance;
				return ValueFactory.valueOf(user.getDisplayName(target));
			}
		});
		methods.rawset("getRoles", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (!(arg instanceof LuaUserdata) || !(((LuaUserdata) arg).instance instanceof IGuild))
					throw new LuaError("arg1 must be a server");
				IGuild target = (IGuild) ((LuaUserdata) arg).instance;
				LuaTable rolesT = new LuaTable();
				List<IRole> roles = user.getRolesForGuild(target);
				for (int i = 0; i < roles.size(); i++)
					rolesT.rawset(i, ConversionHandler.convertToLua(state, roles.get(i)));
				return rolesT;
			}
		});
		methods.rawset("addRole", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg1) throws LuaError {
				if (!(arg1 instanceof LuaUserdata) || !(((LuaUserdata) arg1).instance instanceof IRole))
					throw new LuaError("arg1 must be a role");
				IRole role = (IRole) ((LuaUserdata) arg1).instance;
				IGuild target = role.getGuild();
				if (Starota.getOurUser().getPermissionsForGuild(target)
						.contains(Permissions.MANAGE_ROLES))
					return RequestBuffer.request(() -> {
						try {
							user.addRole(role);
						} catch (DiscordException e) {
							return Constants.FALSE;
						}
						return Constants.TRUE;
					}).get();
				else
					return Constants.FALSE;
			}
		});
		methods.rawset("removeRole", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (!(arg instanceof LuaUserdata) || !(((LuaUserdata) arg).instance instanceof IRole))
					throw new LuaError("arg1 must be a role");
				IRole role = (IRole) ((LuaUserdata) arg).instance;
				IGuild target = role.getGuild();
				if (Starota.getOurUser().getPermissionsForGuild(target)
						.contains(Permissions.MANAGE_ROLES))
					return RequestBuffer.request(() -> {
						try {
							user.removeRole(role);
						} catch (DiscordException e) {
							return Constants.FALSE;
						}
						return Constants.TRUE;
					}).get();
				else
					return Constants.FALSE;
			}
		});
		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(user, mt);
	}

	@Override
	public Object toJava(LuaState state, LuaValue val) throws LuaError {
		// TODO Auto-generated method stub
		return null;
	}

}
