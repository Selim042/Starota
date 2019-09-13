package us.myles_selim.starota.enums;

import java.util.HashMap;
import java.util.Map;

import discord4j.core.object.reaction.ReactionEmoji;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;

public enum EnumTeam {

	// TODO: Replace NO_TEAM URL with a representative image
	NO_TEAM("No team", 0x777777, "http://assets.myles-selim.us/starota/teams/instinct.png", 0),
	INSTINCT("Instinct", 0xF1C40F, "http://assets.myles-selim.us/starota/teams/instinct.png", 3),
	MYSTIC("Mystic", 0x277ECD, "http://assets.myles-selim.us/starota/teams/mystic.png", 1),
	VALOR("Valor", 0x992D22, "http://assets.myles-selim.us/starota/teams/valor.png", 2);

	private static final Map<String, EnumTeam> NAME_MAP = new HashMap<>();

	static {
		for (EnumTeam t : EnumTeam.values())
			NAME_MAP.put(t.getName().toLowerCase(), t);
	}

	public static EnumTeam getTeam(String name) {
		if (!NAME_MAP.containsKey(name.toLowerCase()))
			return null;
		return NAME_MAP.get(name.toLowerCase());
	}

	public static EnumTeam getTeam(String[] names) {
		for (String n : names) {
			EnumTeam ret = getTeam(n);
			if (ret != null)
				return ret;
		}
		return null;
	}

	private String name;
	private int color;
	private String icon;
	private int rdmIndex;

	EnumTeam(String name, int color, String icon, int rdmIndex) {
		this.name = name;
		this.color = color;
		this.icon = icon;
		this.rdmIndex = rdmIndex;
	}

	public String getName() {
		return this.name;
	}

	public int getColor() {
		return this.color;
	}

	public String getIcon() {
		return this.icon;
	}

	public int getRDMIndex() {
		return this.rdmIndex;
	}

	public ReactionEmoji.Custom getEmoji() {
		return EmojiServerHelper.getEmoji(this.name().toLowerCase(), icon);
	}

	public static EnumTeam valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length)
			return null;
		return values()[ordinal];
	}

	public static EnumTeam fromRDMIndex(int index) {
		for (EnumTeam t : values())
			if (index == t.rdmIndex)
				return t;
		return NO_TEAM;
	}

	@Override
	public String toString() {
		return getName();
	}

}
