package us.myles_selim.starota.trading;

import us.myles_selim.starota.trading.forms.FormSet;
import us.myles_selim.starota.trading.forms.FormSetAlolan;
import us.myles_selim.starota.trading.forms.FormSetArceus;
import us.myles_selim.starota.trading.forms.FormSetBurmyFamily;
import us.myles_selim.starota.trading.forms.FormSetCastform;
import us.myles_selim.starota.trading.forms.FormSetDeoxys;
import us.myles_selim.starota.trading.forms.FormSetGiratina;
import us.myles_selim.starota.trading.forms.FormSetPichuHat;
import us.myles_selim.starota.trading.forms.FormSetPikachuHat;
import us.myles_selim.starota.trading.forms.FormSetRaichuHat;
import us.myles_selim.starota.trading.forms.FormSetRotom;
import us.myles_selim.starota.trading.forms.FormSetShellosFamily;
import us.myles_selim.starota.trading.forms.FormSetSpinda;
import us.myles_selim.starota.trading.forms.FormSetSquirtleSquad;
import us.myles_selim.starota.trading.forms.FormSetUnown;

public enum EnumPokemon {
	// GEN 1
	BULBASAUR,
	IVYSAUR,
	VENUSAUR,
	CHARMANDER,
	CHARMELEON,
	CHARIZARD,
	SQUIRTLE(
			FormSetSquirtleSquad.FORM_SET),
	WARTORTLE(
			FormSetSquirtleSquad.FORM_SET),
	BLASTOISE(
			FormSetSquirtleSquad.FORM_SET),
	CATERPIE,
	METAPOD,
	BUTTERFREE,
	WEEDLE,
	KAKUNA,
	BEEDRILL,
	PIDGEY,
	PIDGEOTTO,
	PIDGEOT,
	RATTATA(
			FormSetAlolan.FORM_SET),
	RATICATE(
			FormSetAlolan.FORM_SET),
	SPEAROW,
	FEAROW,
	EKANS,
	ARBOK,
	PIKACHU(
			FormSetPikachuHat.FORM_SET),
	RAICHU(
			FormSetRaichuHat.FORM_SET),
	SANDSHREW(
			FormSetAlolan.FORM_SET),
	SANDSLASH(
			FormSetAlolan.FORM_SET),
	NIDORAN_F(
			"Nidoran♀",
			EnumGenderPossible.FEMALE),
	NIDORINA(
			EnumGenderPossible.FEMALE),
	NIDOQUEEN(
			EnumGenderPossible.FEMALE),
	NIDORAN_M(
			"Nidoran♂",
			EnumGenderPossible.MALE),
	NIDORINO(
			EnumGenderPossible.MALE),
	NIDOKING(
			EnumGenderPossible.MALE),
	CLEFAIRY,
	CLEFABLE,
	VULPIX(
			FormSetAlolan.FORM_SET),
	NINETAILS(
			FormSetAlolan.FORM_SET),
	JIGGLYPUFF,
	WIGGLYTUFF,
	ZUBAT,
	GOLBAT,
	ODDISH,
	GLOOM,
	VILEPLUME,
	PARAS,
	PARASECT,
	VENONAT,
	VENOMOTH,
	DIGLETT(
			FormSetAlolan.FORM_SET),
	DUGTRIO(
			FormSetAlolan.FORM_SET),
	MEOWTH(
			FormSetAlolan.FORM_SET),
	PERSIAN(
			FormSetAlolan.FORM_SET),
	PSYDUCK,
	GOLDUCK,
	MANKEY,
	PRIMEAPE,
	GROWLITHE,
	ARCANINE,
	POLIWAG,
	POLIWHIRL,
	POLIWRATH,
	ABRA,
	KADABRA,
	ALAKAZAM,
	MACHOP,
	MACHOKE,
	MACHAMP,
	BELLSPROUT,
	WEEPINBELL,
	VICTREEBEL,
	TENTACOOL,
	TENACRUEL,
	GEODUDE(
			FormSetAlolan.FORM_SET),
	GRAVELER(
			FormSetAlolan.FORM_SET),
	GOLEM(
			FormSetAlolan.FORM_SET),
	PONYTA,
	RAPIDASH,
	SOWPOKE,
	SLOWBRO,
	MAGNEMITE(
			EnumGenderPossible.UNKNOWN),
	MAGNETON(
			EnumGenderPossible.UNKNOWN),
	FARFETCHD(
			"Farfetch'd"),
	DODUO,
	DODRIO,
	SEEL,
	DEWGONG,
	GRIMER(
			FormSetAlolan.FORM_SET),
	MUK(
			FormSetAlolan.FORM_SET),
	SHELLDER,
	CLOYSTER,
	GASTLY,
	HAUNTER,
	GENGAR,
	ONIX,
	DROWZEE,
	HYPNO,
	KRABBY,
	KINGLER,
	VOLTORB(
			EnumGenderPossible.UNKNOWN),
	ELECTRODE(
			EnumGenderPossible.UNKNOWN),
	EXEGGCUTE,
	EXEGGUTOR(
			FormSetAlolan.FORM_SET),
	CUBONE,
	MAROWAK(
			FormSetAlolan.FORM_SET),
	HITMONLEE(
			EnumGenderPossible.MALE),
	HITMONCHAN(
			EnumGenderPossible.MALE),
	LICKITUNG,
	KOFFING,
	WEEZING,
	RHYHORN,
	RHYDON,
	CHANSEY(
			EnumGenderPossible.FEMALE),
	TANGELA,
	KANGASKHAN(
			EnumGenderPossible.FEMALE),
	HORSEA,
	SEADRA,
	GOLDEEN,
	SEAKING,
	STARYU(
			EnumGenderPossible.UNKNOWN),
	STARMIE(
			EnumGenderPossible.UNKNOWN),
	MR_MIME(
			"Mr. Mime"),
	SCYTHER,
	JYNX(
			EnumGenderPossible.FEMALE),
	ELECTABUZZ,
	MAGMAR,
	PINSIR,
	TAUROS(
			EnumGenderPossible.MALE),
	MAGIKARP,
	GYARADOS,
	LAPRAS,
	DITTO,
	EEVEE,
	VAPOREON,
	JOLTEON,
	FLAREON,
	PORYGON(
			EnumGenderPossible.UNKNOWN),
	OMANYTE,
	OMASTAR,
	KABUTO,
	KABUTOPS,
	AERODACTYL,
	SNORLAX,
	ARTICUNO(
			EnumGenderPossible.UNKNOWN),
	ZAPDOS(
			EnumGenderPossible.UNKNOWN),
	MOLTRES(
			EnumGenderPossible.UNKNOWN),
	DRATINI,
	DRAGONAIR,
	DRAGONITE,
	MEWTWO(
			EnumGenderPossible.UNKNOWN),
	MEW(
			EnumGenderPossible.UNKNOWN),
	// GEN 2
	CHIKORITA,
	BAYLEEF,
	MEGANIUM,
	CYNDAQUIL,
	QUILAVA,
	TYPHLOSION,
	TOTODILE,
	CROCONAW,
	FERALIGATR,
	SENTRET,
	FURRET,
	HOOTHOOT,
	NOCTOWL,
	LEDYBA,
	LEDIAN,
	SPINARAK,
	ARIADOS,
	CROBAT,
	CHINCHOU,
	LANTURN,
	PICHU(
			FormSetPichuHat.FORM_SET),
	CLEFFA,
	IGGLYBUFF,
	TOGEPI,
	TOGETIC,
	NATU,
	XATU,
	MAREEP,
	FLAAFFY,
	AMPHAROS,
	BELLOSSOM,
	MARILL,
	AZUMARILL,
	SUDOWOODO,
	POLITOED,
	HOPPIP,
	SKIPLOOM,
	JUMPLUFF,
	APIOM,
	SUNKERN,
	SUNFLORA,
	YANMA,
	WOOPER,
	QUAGSIRE,
	ESPEON,
	UMBREON,
	MURKROW,
	SLOWKING,
	MISDREAVUS,
	UNOWN(
			FormSetUnown.FORM_SET,
			EnumGenderPossible.UNKNOWN),
	WOBBUFFET,
	GIRAFARIG,
	PINECO,
	FORRETRESS,
	DUNSPARCE,
	GLIGAR,
	STEELIX,
	SNUBBULL,
	GRANBULL,
	QUILFISH,
	SCIZOR,
	SHUCKLE,
	HERACROSS,
	SNEASEL,
	TEDDIURSA,
	URSARING,
	SLUGMA,
	MAGCARGO,
	SWINUB,
	PINOSWINE,
	CORSOLA,
	REMORIAD,
	OCTILLERY,
	DELIBIRD,
	MANTINE,
	SKARMORY,
	HOUNDOUR,
	HOUNDOOM,
	KINGDRA,
	PHANPY,
	DONPHAN,
	PORYGON2(
			EnumGenderPossible.UNKNOWN),
	STANTLER,
	SMEARGLE,
	TYROGUE(
			EnumGenderPossible.MALE),
	HITMONTOP(
			EnumGenderPossible.MALE),
	SMOOCHUM(
			EnumGenderPossible.FEMALE),
	ELEKID,
	MAGBY,
	MILTANK(
			EnumGenderPossible.FEMALE),
	BLISSEY(
			EnumGenderPossible.FEMALE),
	RAIKOU(
			EnumGenderPossible.UNKNOWN),
	ENTEI(
			EnumGenderPossible.UNKNOWN),
	SOUCUNE(
			EnumGenderPossible.UNKNOWN),
	LARVITAR,
	PUPITAR,
	TYRANITAR,
	LUGIA(
			EnumGenderPossible.UNKNOWN),
	HO_OH(
			"Ho-Oh",
			EnumGenderPossible.UNKNOWN),
	CELEBI,
	// GEN 3
	TREECKO,
	GROVYLE,
	SCEPTILE,
	TORCHIC,
	COMBUSKEN,
	BLAZIKEN,
	MUDKIP,
	MARSHTOMP,
	SWAMPERT,
	POOCHYENA,
	MIGHTYENA,
	ZIGZAGOON,
	LINOONE,
	WURMPLE,
	SILCOON,
	BEAUTIFLY,
	CASCOON,
	DUSTOX,
	LOTAD,
	LOMBRE,
	LUDICOLO,
	SEEDOT,
	NUZLEAF,
	SHIFTRY,
	TAILLOW,
	SWELLOW,
	WINGULL,
	PELIPPER,
	RALTS,
	KIRLIA,
	GARDEVOIR,
	SURSKIT,
	MASQUERAIN,
	SHROOMISH,
	BRELOOM,
	SLAKOTH,
	VIGOROTH,
	SLAKING,
	NINCADA,
	NINJASK,
	SHEDINJA(
			EnumGenderPossible.UNKNOWN),
	WHISMUR,
	LOUDRED,
	EXPLOUD,
	MAKUHITA,
	HARIYAMA,
	AZURILL,
	NOSEPASS,
	SKITTY,
	DELCATTY,
	SABLEYE,
	MAWILE,
	ARON,
	LAIRON,
	AGGRON,
	MEDITITE,
	MEDICHAM,
	ELECTRIKE,
	MANECTRIC,
	PLUSLE,
	MINUN,
	VOLBEAT(
			EnumGenderPossible.MALE),
	ILLUMISE(
			EnumGenderPossible.FEMALE),
	ROSELIA,
	GULPIN,
	SWALOT,
	CARVANHA,
	SHARKPADE,
	WAILMER,
	WAILORD,
	NUMEL,
	CAMERUPT,
	TORKOAL,
	SPOINK,
	GRUMPIG,
	SPINDA(
			FormSetSpinda.FORM_SET),
	TRAPINCH,
	VIBRAVA,
	FLYGON,
	CACNEA,
	CACTURNE,
	SWABLU,
	ALTARIA,
	ZANGOOSE,
	SEVIPER,
	LUNATONE(
			EnumGenderPossible.UNKNOWN),
	SOLROCK(
			EnumGenderPossible.UNKNOWN),
	BARBOACH,
	WHISCASH,
	CORPHISH,
	CRAWDAUNT,
	BALTOY(
			EnumGenderPossible.UNKNOWN),
	CLAYDOL(
			EnumGenderPossible.UNKNOWN),
	LILEEP,
	CRADILY,
	ANORITH,
	ARMALDO,
	FEEBAS,
	MILOTIC,
	CASTFORM(
			FormSetCastform.FORM_SET),
	KECLEON,
	SHUPPET,
	BANETTE,
	DUSKULL,
	DUSCLOPS,
	TROPIUS,
	CHIMECHO,
	ABSOL,
	WYNAUT,
	SNORUNT,
	GLALIE,
	SPHEAL,
	SEALEO,
	WALREIN,
	CLAMPERL,
	HUNTAIL,
	GOREBYSS,
	RELICANTH,
	LUVDISC,
	BAGON,
	SHELGON,
	SALAMENCE,
	BELDUM(
			EnumGenderPossible.UNKNOWN),
	METANG(
			EnumGenderPossible.UNKNOWN),
	METAGROSS(
			EnumGenderPossible.UNKNOWN),
	REGIROCK(
			EnumGenderPossible.UNKNOWN),
	REGICE(
			EnumGenderPossible.UNKNOWN),
	REGISTEEL(
			EnumGenderPossible.UNKNOWN),
	LATIAS(
			EnumGenderPossible.FEMALE),
	LATIOS(
			EnumGenderPossible.MALE),
	KYOGRE(
			EnumGenderPossible.UNKNOWN),
	GROUDON(
			EnumGenderPossible.UNKNOWN),
	RAYQUAZA(
			EnumGenderPossible.UNKNOWN),
	JIRACHI(
			EnumGenderPossible.UNKNOWN),
	DEOXYS(
			FormSetDeoxys.FORM_SET,
			EnumGenderPossible.UNKNOWN),
	// GEN 4
	TURTWIG,
	GROTLE,
	TORTERRA,
	CHIMCHAR,
	MONFERNO,
	INFERNAPE,
	PIPLUP,
	PRINPLUP,
	EMPOLEON,
	STARLY,
	STARAVIA,
	STARAPTOR,
	BIDOOF,
	BIBAREL,
	KRICKETOT,
	KRICKETUNE,
	SHINX,
	LUXIO,
	LUXRAY,
	BUDEW,
	ROSERADE,
	CRANIDOS,
	RAMPARDOS,
	SHIELDON,
	BASTIODON,
	BURMY(
			FormSetBurmyFamily.FORM_SET),
	WORMADAM(
			FormSetBurmyFamily.FORM_SET,
			EnumGenderPossible.FEMALE),
	MOTHIM(
			EnumGenderPossible.MALE),
	COMBEE,
	VESPIQUEN(
			EnumGenderPossible.FEMALE),
	PACHIRISU,
	BUIZEL,
	FLOATZEL,
	CHERUBI,
	CHERRIM, // TODO: Add form if necessary later
	SHELLOS(
			FormSetShellosFamily.FORM_SET),
	GASTRODON(
			FormSetShellosFamily.FORM_SET),
	AMBIPOM,
	DRIFLOON,
	DRIFBLIM,
	BUNEARY,
	LOPUNNY,
	MISMAGIUS,
	HONCHKROW,
	GLAMEOW,
	PURUGLY,
	CHINGLING,
	STUNKY,
	SKUNTANK,
	BRONZOR(
			EnumGenderPossible.UNKNOWN),
	BRONZONG(
			EnumGenderPossible.UNKNOWN),
	BONSLY,
	MIME_JR(
			"Mime Jr."),
	HAPPINY(
			EnumGenderPossible.FEMALE),
	CHATOT,
	SPIRITOMB,
	GIBLE,
	GABITE,
	GARCHOMP,
	MUNCHLAX,
	RIOLU,
	LUCARIO,
	HIPPOPOTAS,
	HIPPOWDON,
	SKORUPI,
	DRAPION,
	CROAGUNK,
	TOXICROAK,
	CARNIVINE,
	GINNEON,
	LUMINEON,
	MANTYKE,
	SNOVER,
	ABOMASNOW,
	WEAVILE,
	MAGNEZONE(
			EnumGenderPossible.UNKNOWN),
	LICKILICKY,
	RHYPERIOR,
	TANGROWTH,
	ELECTIVIRE,
	MAGMORTAR,
	TOGEKISS,
	YANMEGA,
	LEAFEON,
	GLACEON,
	GLISCOR,
	MAMOSWINE,
	PORYGON_Z(
			"Porygon-Z",
			EnumGenderPossible.UNKNOWN),
	GALLADE(
			EnumGenderPossible.MALE),
	PROBOPASS,
	DUSKNOIR,
	FROSLASS(
			EnumGenderPossible.FEMALE),
	ROTOM(
			FormSetRotom.FORM_SET,
			EnumGenderPossible.UNKNOWN),
	UXIE(
			EnumGenderPossible.UNKNOWN),
	MESPRIT(
			EnumGenderPossible.UNKNOWN),
	AZELF(
			EnumGenderPossible.UNKNOWN),
	DIALGA(
			EnumGenderPossible.UNKNOWN),
	PALKIA(
			EnumGenderPossible.UNKNOWN),
	HEATRAN,
	REGIGIGAS(
			EnumGenderPossible.UNKNOWN),
	GIRATINA(
			FormSetGiratina.FORM_SET,
			EnumGenderPossible.UNKNOWN),
	CRESSELIA(
			EnumGenderPossible.FEMALE),
	PHIONE(
			EnumGenderPossible.UNKNOWN),
	MANAPHY(
			EnumGenderPossible.UNKNOWN),
	DARKRAI(
			EnumGenderPossible.UNKNOWN),
	SHAYMIN(
			EnumGenderPossible.UNKNOWN),
	ARCEUS(
			FormSetArceus.FORM_SET,
			EnumGenderPossible.UNKNOWN);

	private String name;
	private FormSet forms;
	private EnumGenderPossible gender;

	EnumPokemon() {
		name = this.name();
		name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase();
		this.gender = EnumGenderPossible.EITHER;
	}

	EnumPokemon(String name) {
		this.name = name;
		this.gender = EnumGenderPossible.EITHER;
	}

	EnumPokemon(String name, EnumGenderPossible gender) {
		this(name);
		this.gender = gender;
	}

	EnumPokemon(FormSet formSet) {
		this();
		this.forms = formSet;
		this.gender = EnumGenderPossible.EITHER;
	}

	EnumPokemon(FormSet formSet, EnumGenderPossible gender) {
		this(formSet);
		this.gender = gender;
	}

	EnumPokemon(String name, FormSet formSet) {
		this(name);
		this.forms = formSet;
		this.gender = EnumGenderPossible.EITHER;
	}

	EnumPokemon(String name, FormSet formSet, EnumGenderPossible gender) {
		this(name, formSet);
		this.gender = gender;
	}

	EnumPokemon(EnumGenderPossible gender) {
		this();
		this.gender = gender;
	}

	public String getName() {
		return this.name;
	}

	public FormSet getFormSet() {
		return this.forms;
	}

	public EnumGenderPossible getGenderPossible() {
		return this.gender;
	}

	public int getId() {
		return ordinal() + 1;
	}

	public boolean isTradable() {
		switch (this) {
		case MEW:
		case CELEBI:
		case JIRACHI:
		case DEOXYS:
		case MANAPHY:
		case PHIONE:
		case DARKRAI:
		case SHAYMIN:
			return false;
		default:
			return true;
		}
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public static EnumPokemon getPokemon(int id) {
		if (id < 1 || id > values().length)
			return null;
		return values()[id];
	}

	public static EnumPokemon getPokemon(String name) {
		for (EnumPokemon p : values())
			if (p.name.equalsIgnoreCase(name))
				return p;
		return null;
	}

	public static void main(String... args) {
		System.out.println("Number of entered Pokemon: " + EnumPokemon.values().length);
		int alolan = 0;
		for (EnumPokemon p : EnumPokemon.values())
			if (p.forms != null && p.forms == FormSetAlolan.FORM_SET)
				alolan++;
		System.out.println("Number of Alolan forms: " + alolan);
		System.out.println("Number of Unown forms: " + UNOWN.forms.getNumForms());
		System.out.println("Number of Arceus forms: " + ARCEUS.forms.getNumForms());

		int space = 0;
		for (int i = 0; i < 387; i++) {
			EnumPokemon p = EnumPokemon.values()[i];
			int genderMult = p.getGenderPossible() == EnumGenderPossible.EITHER ? 2 : 1;
			int shinyMult = FormManager.isShinyable(i) ? 2 : 1;
			space += ((p.getFormSet() == null ? 1 : p.getFormSet().getNumForms()) * genderMult
					* shinyMult);
		}
		System.out.println("You need " + space + " bag space to hold all forms");

		for (EnumPokemon p : EnumPokemon.values())
			if (FormManager.isShinyable(p.getId()))
				System.out.println(p + " is shinyable");
	}
}
