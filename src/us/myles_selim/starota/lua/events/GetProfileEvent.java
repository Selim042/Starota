package us.myles_selim.starota.lua.events;

import java.util.ArrayList;
import java.util.List;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ThreeArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;

import us.myles_selim.starota.embed_converter.ExtraField;
import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class GetProfileEvent extends LuaEvent {

	private final PlayerProfile profile;
	private final List<ExtraField> fields = new ArrayList<>();
	private int color;
	private String thumbnail;

	public GetProfileEvent(StarotaServer server, PlayerProfile profile) {
		super(server);
		this.profile = profile;
	}

	public PlayerProfile getProfile() {
		return this.profile;
	}

	public List<ExtraField> getFields() {
		return this.fields;
	}

	public int getColor() {
		return this.color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public LuaValue toLua(LuaState state) {
		LuaTable methods = new LuaTable();
		methods.rawset("getProfile", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ConversionHandler.convertToLua(state, profile);
			}
		});
		methods.rawset("server",
				ConversionHandler.convertToLua(state, this.getServer().getDiscordGuild()));

		methods.rawset("getFields", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable fieldsL = new LuaTable();
				for (int i = 0; i < fields.size(); i++) {
					ExtraField field = fields.get(i);
					LuaTable tbl = new LuaTable();
					tbl.rawset("title", ValueFactory.valueOf(field.title));
					tbl.rawset("value", ValueFactory.valueOf(field.value));
					tbl.rawset("inline", ValueFactory.valueOf(field.inline));
					fieldsL.rawset(i, tbl);
				}
				return ConversionHandler.convertToLua(state, profile);
			}
		});
		methods.rawset("addField", new ThreeArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg1, LuaValue arg2, LuaValue arg3)
					throws LuaError {
				if (!arg3.checkBoolean())
					throw new LuaError("arg3 must be a boolean");
				ExtraField field = new ExtraField(arg1.toString(), arg2.toString(), arg3.checkBoolean());
				fields.add(field);
				System.out.println("s");
				return Constants.NIL;
			}
		});
		methods.rawset("removeField", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (!arg.isIntExact())
					throw new LuaError("arg1 must be a number");
				int id = arg.toInteger() - 1;
				if (id < 0 || id > fields.size())
					throw new LuaError("arg1 must be a number between 1 and " + (fields.size() - 1));
				fields.remove(arg.toInteger());
				return Constants.NIL;
			}
		});

		methods.rawset("getColor", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(color);
			}
		});
		methods.rawset("setColor", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (!arg.isIntExact())
					throw new LuaError("arg1 must be a number");
				color = arg.toInteger();
				return Constants.NIL;
			}
		});
		methods.rawset("getThumbnail", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(thumbnail);
			}
		});
		methods.rawset("setThumbnail", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				thumbnail = arg.toString();
				return Constants.NIL;
			}
		});

		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(this, mt);
	}

}
