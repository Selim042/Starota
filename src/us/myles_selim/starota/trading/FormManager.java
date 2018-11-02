package us.myles_selim.starota.trading;

public class FormManager {

	public static final int MAX_DEX = 386;
	public static final int[] EXCLUDED = new int[] { //
			235, 251, // Smeargle
			352, // Kecleon
			366, 367, 368, // Clamperl family
			385, // Jirachi
	};
	public static final int[] FORCE_ENABLE = new int[] { //
			387, 388, 389, 390, 391, 392, 393, 394, 395, // gen 4 starters
			396, 397, 398, 399, 400, 401, 402, // stary, bidoof, kricketot
			403, 404, 405, 417, // shinx & pachirisu
			427, 428, 441, 447, 448, 455, // buneary, chatot, riolu, carnivine
			425, 426, 434, 435, // drifloon & stunky
			487, // giratina
	};
	public static final int[] SHINYABLE = new int[] { //
			1, 2, 3, 4, 5, 6, 7, 8, 9, // gen 1 starters
			10, 11, 12, // caterpie
			25, 26, // pikachu
			29, 30, 31, // nidoran female
			58, 59, // growlithe
			74, 75, 76, // geodude
			88, 89, 90, 91, // grimer & shelder
			94, // gengar
			96, 97, 98, 99, // drowzee & krabby
			126, 127, // magmar, pinsir
			129, 130, // magikarp
			133, 134, 135, 136, // eeveelutions
			138, 139, 140, 141, 142, // fossils
			144, 145, 146, 147, 148, 149, // gen 1 birds & dratini

			152, 153, 154, 155, 156, 157, // gen 2 starters
			172, // pichu
			175, 176, 177, 178, 179, 180, 181, // togepi, natu & mareep
			191, 192, // sunkern
			196, 197, 198, // espeon, umbreon & murkrow
			202, // wobbuffet
			204, 205, // pineco
			209, 210, // snubbul
			228, 229, // houndour
			240, // magby
			246, 247, 248, 249, 250, // larvitar, lugia, ho-oh

			261, 262, // poochyena
			278, 279, // wingull
			296, 297, // makuhita
			302, 303, // sableye & mawile
			304, 305, 306, 307, 308, // aron & meditite
			311, 312, // plusle & minun
			315, // roselia
			320, 321, // wailmer
			333, 334, // swablu
			353, 354, 355, 356, // shuppet & duskull
			359, 360, 361, 362, // absol, whynaut & snorunt
			374, 375, 376, // beldum
			370, // luvdisc
			382, // kyogre

			403, 404, 405, // shinx
			425, 426, // drifloon
	};

	public static boolean isAvailable(EnumPokemon pokemon) {
		return pokemon != null && isAvailable(pokemon.getId());
	}

	public static boolean isAvailable(int id) {
		if (arrCont(FORCE_ENABLE, id) || (id <= MAX_DEX && !arrCont(EXCLUDED, id)))
			return true;
		return false;
	}

	public static boolean isShinyable(EnumPokemon pokemon) {
		return pokemon != null && isShinyable(pokemon.getId());
	}

	public static boolean isShinyable(int id) {
		return arrCont(SHINYABLE, id);
	}

	private static boolean arrCont(int[] ii, int iv) {
		for (int i : ii)
			if (i == iv)
				return true;
		return false;
	}

	@SuppressWarnings("unused")
	private static <T> boolean arrCont(T[] tt, T tv) {
		for (T t : tt)
			if (t != null && t.equals(tv))
				return true;
		return false;
	}

}
