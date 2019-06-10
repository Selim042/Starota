package us.myles_selim.starota.assistants.points;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.misc.utils.ImageHelper;

public class QuestType<P> {

	private static final List<QuestType<?>> FAST_TYPES = new ArrayList<>();
	private static final List<QuestType<?>> SLOW_TYPES = new ArrayList<>();

	public static final QuestTypeNull DO_RAID = new QuestTypeNull("Complete a raid",
			"Complete any tier raid and post your journal screenshot to this channel before this quest expires.",
			false, "http://assets.myles-selim.us/starota/points/raids.png", 5);
	public static final QuestType<EnumPokemon> CATCH_POKEMON = new QuestType<EnumPokemon>("Catch %s",
			"Catch a %s from the wild, raids, or research tasks/breakthroughs and post your journal screenshot to this channel before this quest expires.",
			true, EnumPokemon::getRandomPokemon, ImageHelper::getOfficalArtwork, QuestType::getPoints);
	public static final QuestTypeNull SPIN_STOP = new QuestTypeNull("Spin a PokeStop",
			"Spin any PokeStop photo disc and post your journal screenshot to this channel before this quest expires.",
			true, "http://assets.myles-selim.us/starota/points/pokestop.png", 1);
	public static final QuestType<EnumGiftSendOpen> DO_GIFT = new QuestType<>("%s a gift",
			"%s a gift to a friend and post your journal screenshot to this channel before this quest expires.",
			false,
			(Random rand) -> EnumGiftSendOpen.values()[rand.nextInt(EnumGiftSendOpen.values().length)],
			(e) -> "http://assets.myles-selim.us/starota/points/gift.png",
			(e) -> e == EnumGiftSendOpen.EITHER ? 1 : 3);
	public static final QuestType<EnumPVPWinLose> DO_PVP = new QuestType<>("%s a PvP battle",
			"%s a PvP battle and post your journal screenshot to this channel before this quest expires.",
			false,
			(Random rand) -> EnumPVPWinLose.values()[rand.nextInt(EnumPVPWinLose.values().length)],
			(e) -> "http://assets.myles-selim.us/starota/points/pvp.png",
			(e) -> e == EnumPVPWinLose.EITHER ? 2 : 5);
	public static final QuestTypeNull DO_TRADE = new QuestTypeNull("Trade with a friend",
			"Trade any Pokemon to a friend and post your journal screenshot to this channel before this quest expires.",
			false, "http://assets.myles-selim.us/starota/points/trade.png", 2);
	public static final QuestTypeNull BUDDY_CANDY = new QuestTypeNull("Find a candy with your buddy",
			"Find a candy with your buddy and post your journal screenshot to this channel before this quest expires.",
			false, "http://assets.myles-selim.us/starota/points/candies.png", 3);
	public static final QuestTypeNull HATCH_EGG = new QuestTypeNull("Hatch an egg",
			"Hatch an egg and post your journal screenshot to this channel before this quest expires.",
			false, "http://assets.myles-selim.us/starota/points/eggs.png", 4);

	private static int getPoints(EnumPokemon poke) {
		switch (poke.getStage()) {
		case BASE:
			return (int) (1 * Math.max(poke.getType1().getPointMult(),
					poke.getType2() == null ? 0 : poke.getType2().getPointMult()));
		case MIDDLE:
			return (int) (2 * Math.max(poke.getType1().getPointMult(),
					poke.getType2() == null ? 0 : poke.getType2().getPointMult()));
		case FINAL:
			return (int) (3.5f * Math.max(poke.getType1().getPointMult(),
					poke.getType2() == null ? 0 : poke.getType2().getPointMult()));
		case BABY:
			return (int) (2 * Math.max(poke.getType1().getPointMult(),
					poke.getType2() == null ? 0 : poke.getType2().getPointMult()));
		case LEGEND:
			return 5;
		case MYTHIC:
			return 10;
		default:
			return 2;
		}
	}

	private static final Random RAND = new Random();

	private final String displayName;
	private final String desc;
	private final boolean isFast;
	private final Function<Random, P> gen;
	private final Function<P, String> image;
	private final Function<P, Integer> points;

	private QuestType(String displayName, String desc, boolean isFast, Function<Random, P> gen,
			Function<P, String> image, Function<P, Integer> points) {
		List<QuestType<?>> blah;
		if (isFast)
			blah = FAST_TYPES;
		else
			blah = SLOW_TYPES;
		blah.add(this);

		this.displayName = displayName;
		this.desc = desc;
		this.isFast = isFast;
		this.gen = gen;
		this.image = image;
		this.points = points;
	}

	@SuppressWarnings("unchecked")
	public static <V extends QuestType<P>, P> PointQuest<V, P> getRandomQuest(Random rand,
			boolean isFast, long expTime) {
		V type;
		if (isFast)
			type = (V) FAST_TYPES.get(RAND.nextInt(FAST_TYPES.size()));
		else
			type = (V) SLOW_TYPES.get(RAND.nextInt(SLOW_TYPES.size()));
		return new PointQuest<>(type, type.getRandomParamater(rand), expTime);
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public String getDescription() {
		return this.desc;
	}

	public boolean isFast() {
		return this.isFast;
	}

	public P getRandomParamater(Random rand) {
		if (gen == null)
			return null;
		return gen.apply(rand);
	}

	public String getImage(P param) {
		return image.apply(param);
	}

	public int getPoints(P param) {
		Integer i = points.apply(param);
		if (i == null)
			return 0;
		return i;
	}

	public static enum EnumGiftSendOpen {
		SEND("Send"),
		OPEN("Open"),
		EITHER("Send or open");

		private String displayName;

		EnumGiftSendOpen(String displayName) {
			this.displayName = displayName;
		}

		@Override
		public String toString() {
			return this.displayName;
		}
	}

	public static enum EnumPVPWinLose {
		WIN("Win"),
		LOSE("Lose"),
		EITHER("Win or lose");

		private String displayName;

		EnumPVPWinLose(String displayName) {
			this.displayName = displayName;
		}

		@Override
		public String toString() {
			return this.displayName;
		}
	}

	public static class QuestTypeNull extends QuestType<Void> {

		private QuestTypeNull(String displayName, String desc, boolean isFast, String image,
				int points) {
			super(displayName, desc, isFast, null, (v) -> image, (v) -> points);
		}

		@Override
		public Void getRandomParamater(Random rand) {
			return null;
		}

	}

}
