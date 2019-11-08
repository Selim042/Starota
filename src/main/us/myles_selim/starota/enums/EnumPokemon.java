package us.myles_selim.starota.enums;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.myles_selim.starota.enums.PokemonData.PokemonDataBuilder;
import us.myles_selim.starota.forms.FormManager;
import us.myles_selim.starota.forms.FormSet;

public enum EnumPokemon {

	// GEN 1
	BULBASAUR,
	IVYSAUR,
	VENUSAUR,
	CHARMANDER,
	CHARMELEON,
	CHARIZARD,
	SQUIRTLE,
	WARTORTLE,
	BLASTOISE,
	CATERPIE,
	METAPOD,
	BUTTERFREE,
	WEEDLE,
	KAKUNA,
	BEEDRILL,
	PIDGEY,
	PIDGEOTTO,
	PIDGEOT,
	RATTATA,
	RATICATE,
	SPEAROW,
	FEAROW,
	EKANS,
	ARBOK,
	PIKACHU,
	RAICHU,
	SANDSHREW,
	SANDSLASH,
	NIDORAN_F,
	NIDORINA,
	NIDOQUEEN,
	NIDORAN_M,
	NIDORINO,
	NIDOKING,
	CLEFAIRY,
	CLEFABLE,
	VULPIX,
	NINETALES,
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
	DIGLETT,
	DUGTRIO,
	MEOWTH,
	PERSIAN,
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
	TENTACRUEL,
	GEODUDE,
	GRAVELER,
	GOLEM,
	PONYTA,
	RAPIDASH,
	SLOWPOKE,
	SLOWBRO,
	MAGNEMITE,
	MAGNETON,
	FARFETCHD,
	DODUO,
	DODRIO,
	SEEL,
	DEWGONG,
	GRIMER,
	MUK,
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
	VOLTORB,
	ELECTRODE,
	EXEGGCUTE,
	EXEGGUTOR,
	CUBONE,
	MAROWAK,
	HITMONLEE,
	HITMONCHAN,
	LICKITUNG,
	KOFFING,
	WEEZING,
	RHYHORN,
	RHYDON,
	CHANSEY,
	TANGELA,
	KANGASKHAN,
	HORSEA,
	SEADRA,
	GOLDEEN,
	SEAKING,
	STARYU,
	STARMIE,
	MR_MIME,
	SCYTHER,
	JYNX,
	ELECTABUZZ,
	MAGMAR,
	PINSIR,
	TAUROS,
	MAGIKARP,
	GYARADOS,
	LAPRAS,
	DITTO,
	EEVEE,
	VAPOREON,
	JOLTEON,
	FLAREON,
	PORYGON,
	OMANYTE,
	OMASTAR,
	KABUTO,
	KABUTOPS,
	AERODACTYL,
	SNORLAX,
	ARTICUNO,
	ZAPDOS,
	MOLTRES,
	DRATINI,
	DRAGONAIR,
	DRAGONITE,
	MEWTWO,
	MEW,
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
	PICHU,
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
	AIPOM,
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
	UNOWN,
	WOBBUFFET,
	GIRAFARIG,
	PINECO,
	FORRETRESS,
	DUNSPARCE,
	GLIGAR,
	STEELIX,
	SNUBBULL,
	GRANBULL,
	QWILFISH,
	SCIZOR,
	SHUCKLE,
	HERACROSS,
	SNEASEL,
	TEDDIURSA,
	URSARING,
	SLUGMA,
	MAGCARGO,
	SWINUB,
	PILOSWINE,
	CORSOLA,
	REMORAID,
	OCTILLERY,
	DELIBIRD,
	MANTINE,
	SKARMORY,
	HOUNDOUR,
	HOUNDOOM,
	KINGDRA,
	PHANPY,
	DONPHAN,
	PORYGON2,
	STANTLER,
	SMEARGLE,
	TYROGUE,
	HITMONTOP,
	SMOOCHUM,
	ELEKID,
	MAGBY,
	MILTANK,
	BLISSEY,
	RAIKOU,
	ENTEI,
	SUICUNE,
	LARVITAR,
	PUPITAR,
	TYRANITAR,
	LUGIA,
	HO_OH,
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
	SHEDINJA,
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
	VOLBEAT,
	ILLUMISE,
	ROSELIA,
	GULPIN,
	SWALOT,
	CARVANHA,
	SHARPEDO,
	WAILMER,
	WAILORD,
	NUMEL,
	CAMERUPT,
	TORKOAL,
	SPOINK,
	GRUMPIG,
	SPINDA,
	TRAPINCH,
	VIBRAVA,
	FLYGON,
	CACNEA,
	CACTURNE,
	SWABLU,
	ALTARIA,
	ZANGOOSE,
	SEVIPER,
	LUNATONE,
	SOLROCK,
	BARBOACH,
	WHISCASH,
	CORPHISH,
	CRAWDAUNT,
	BALTOY,
	CLAYDOL,
	LILEEP,
	CRADILY,
	ANORITH,
	ARMALDO,
	FEEBAS,
	MILOTIC,
	CASTFORM,
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
	BELDUM,
	METANG,
	METAGROSS,
	REGIROCK,
	REGICE,
	REGISTEEL,
	LATIAS,
	LATIOS,
	KYOGRE,
	GROUDON,
	RAYQUAZA,
	JIRACHI,
	DEOXYS,
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
	BURMY,
	WORMADAM,
	MOTHIM,
	COMBEE,
	VESPIQUEN,
	PACHIRISU,
	BUIZEL,
	FLOATZEL,
	CHERUBI,
	CHERRIM,
	SHELLOS,
	GASTRODON,
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
	BRONZOR,
	BRONZONG,
	BONSLY,
	MIME_JR,
	HAPPINY,
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
	FINNEON,
	LUMINEON,
	MANTYKE,
	SNOVER,
	ABOMASNOW,
	WEAVILE,
	MAGNEZONE,
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
	PORYGON_Z,
	GALLADE,
	PROBOPASS,
	DUSKNOIR,
	FROSLASS,
	ROTOM,
	UXIE,
	MESPRIT,
	AZELF,
	DIALGA,
	PALKIA,
	HEATRAN,
	REGIGIGAS,
	GIRATINA,
	CRESSELIA,
	PHIONE,
	MANAPHY,
	DARKRAI,
	SHAYMIN,
	ARCEUS,
	// TODO: GEN 5 genders, forms
	VICTINI,
	SNIVY,
	SERVINE,
	SERPERIOR,
	TEPIG,
	PIGNITE,
	EMBOAR,
	OSHAWOTT,
	DEWOTT,
	SAMUROTT,
	PATRAT,
	WATCHOG,
	LILLIPUP,
	HERDIER,
	STOUTLAND,
	PURRLOIN,
	LIEPARD,
	PANSAGE,
	SIMISAGE,
	PANSEAR,
	SIMISEAR,
	PANPOUR,
	SIMIPOUR,
	MUNNA,
	MUSHARNA,
	PIDOVE,
	TRANQUILL,
	UNFEZANT,
	BLITZLE,
	ZEBSTRIKA,
	ROGGENROLA,
	BOLDORE,
	GIGALITH,
	WOOBAT,
	SWOOBAT,
	DRILBUR,
	EXCADRILL,
	AUDINO,
	TIMBURR,
	GURDURR,
	CONKELDURR,
	TYMPOLE,
	PALPITOAD,
	SEISMITOAD,
	THROH,
	SAWK,
	SEWADDLE,
	SWADLOON,
	LEAVANNY,
	VENIPEDE,
	WHIRLIPEDE,
	SCOLIPEDE,
	COTTONEE,
	WHIMSICOTT,
	PETILIL,
	LILLIGANT,
	BASCULIN, // forms
	SANDILE,
	KROKOROK,
	KROOKODILE,
	DARUMAKA,
	DARMANITAN, // forms
	MARACTUS,
	DWEBBLE,
	CRUSTLE,
	SCRAGGY,
	SCRAFTY,
	SIGILYPH,
	YAMASK,
	COFAGRIGUS,
	TIRTOUGA,
	CARRACOSTA,
	ARCHEN,
	ARCHEOPS,
	TRUBBISH,
	GARBODOR,
	ZORUA,
	ZOROARK,
	MINCCINO,
	CINCCINO,
	GOTHITA,
	GOTHORITA,
	GOTHITELLE,
	SOLOSIS,
	DUOSION,
	REUNICLUS,
	DUCKLETT,
	SWANNA,
	VANILLITE,
	VANILLISH,
	VANILLUXE,
	DEERLING, // forms
	SAWSBUCK, // forms
	EMOLGA,
	KARRABLAST,
	ESCAVALIER,
	FOONGUS,
	AMOONGUSS,
	FRILLISH,
	JELLICENT,
	ALOMOMOLA,
	JOLTIK,
	GALVANTULA,
	FERROSEED,
	FERROTHORN,
	KLINK,
	KLANG,
	KLINKLANG,
	TYNAMO,
	EELEKTRIK,
	EELEKTROSS,
	ELGYEM,
	BEHEEYEM,
	LITWICK,
	LAMPENT,
	CHANDELURE,
	AXEW,
	FRAXURE,
	HAXORUS,
	CUBCHOO,
	BEARTIC,
	CRYOGONAL,
	SHELMET,
	ACCELGOR,
	STUNFISK,
	MIENFOO,
	MIENSHAO,
	DRUDDIGON,
	GOLETT,
	GOLURK,
	PAWNIARD,
	BISHARP,
	BOUFFALANT,
	RUFFLET,
	BRAVIARY,
	VULLABY,
	MANDIBUZZ,
	HEATMOR,
	DURANT,
	DEINO,
	ZWEILOUS,
	HYDREIGON,
	LARVESTA,
	VOLCARONA,
	COBALION,
	TERRAKION,
	VIRIZION,
	TORNADUS, // forms
	THUNDURUS, // forms
	RESHIRAM,
	ZEKROM,
	LANDORUS, // forms
	KYUREM,
	KELDEO,
	MELOETTA, // forms
	GENESECT,
	// TODO: GEN 6 genders, forms
	CHESPIN,
	QUILLADIN,
	CHESNAUGHT,
	FENNEKIN,
	BRAIXEN,
	DELPHOX,
	FROAKIE,
	FROGADIER,
	GRENINJA,
	BUNNELBY,
	DIGGERSBY,
	FLETCHLING,
	FLETCHINDER,
	TALONFLAME,
	SCATTERBUG,
	SPEWPA,
	VIVILLON,
	LITLEO,
	PYROAR,
	FLABEBE,
	FLOETTE,
	FLORGES,
	SKIDDO,
	GOGOAT,
	PANCHAM,
	PANGORO,
	FURFROU,
	ESPURR,
	MEOWSTIC,
	HONEDGE,
	DOUBLADE,
	AEGISLASH,
	SPRITZEE,
	AROMATISSE,
	SWIRLIX,
	SLURPUFF,
	INKAY,
	MALAMAR,
	BINACLE,
	BARBARACLE,
	SKRELP,
	DRAGALGE,
	CLAUNCHER,
	CLAWITZER,
	HELIOPTILE,
	HELIOLISK,
	TYRUNT,
	TYRANTRUM,
	AMAURA,
	AURORUS,
	SYLVEON,
	HAWLUCHA,
	DEDENNE,
	CARBINK,
	GOOMY,
	SLIGGOO,
	GOODRA,
	KLEFKI,
	PHANTUMP,
	TREVENANT,
	PUMPKABOO, // forms
	GOURGEIST, // forms
	BERGMITE,
	AVALUGG,
	NOIBAT,
	NOIVERN,
	XERNEAS,
	YVELTAL,
	ZYGARDE,
	DIANCIE,
	HOOPA, // forms
	VOLCANION,
	// TODO: GEN 7 genders, forms
	ROWLET,
	DARTRIX,
	DECIDUEYE,
	LITTEN,
	TORRACAT,
	INCINEROAR,
	POPPLIO,
	BRIONNE,
	PRIMARINA,
	PIKIPEK,
	TRUMBEAK,
	TOUCANNON,
	YUNGOOS,
	GUMSHOOS,
	GRUBBIN,
	CHARJABUG,
	VIKAVOLT,
	CRABRAWLER,
	CRABOMINABLE,
	ORICORIO, // forms
	CUTIEFLY,
	RIBOMBEE,
	ROCKRUFF,
	LYCANROC, // forms
	WISHIWASHI,
	MAREANIE,
	TOXAPEX,
	MUDBRAY,
	MUDSDALE,
	DEWPIDER,
	ARAQUANID,
	FOMANTIS,
	LURANTIS,
	MORELULL,
	SHIINOTIC,
	SALANDIT,
	SALAZZLE,
	STUFFUL,
	BEWEAR,
	BOUNSWEET,
	STEENEE,
	TSAREENA,
	COMFEY,
	ORANGURU,
	PASSIMIAN,
	WIMPOD,
	GOLISOPOD,
	SANDYGAST,
	PALOSSAND,
	PYUKUMUKU,
	TYPE_NULL,
	SILVALLY,
	MINIOR,
	KOMALA,
	TURTONATOR,
	TOGEDEMARU,
	MIMIKYU,
	BRUXISH,
	DRAMPA,
	DHELMISE,
	JANGMO_O,
	HAKAMO_O,
	KOMMO_O,
	TAPU_KOKO,
	TAPU_LELE,
	TAPU_BULU,
	TAPU_FINI,
	COSMOG,
	COSMOEM,
	SOLGALEO,
	LUNALA,
	NIHILEGO,
	BUZZWOLE,
	PHEROMOSA,
	XURKITREE,
	CELESTEELA,
	KARTANA,
	GUZZLORD,
	NECROZMA, // forms
	MAGEARNA, // forms
	MARSHADOW,
	POIPOLE,
	NAGANADEL,
	STAKATAKA,
	BLACEPHALON,
	ZERAORA,
	// MELTAN (gen 7?)
	MELTAN,
	MELMETAL,;

	private static final Map<EnumPokemon, PokemonData> DATA_LOOKUP = new HashMap<>();

	static {
		// gen 1
		DATA_LOOKUP.put(EnumPokemon.BULBASAUR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BULBASAUR)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.IVYSAUR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.IVYSAUR)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.VENUSAUR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VENUSAUR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.CHARMANDER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHARMANDER)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.CHARMELEON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHARMELEON)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.CHARIZARD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHARIZARD)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.SQUIRTLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SQUIRTLE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.WARTORTLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WARTORTLE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.BLASTOISE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BLASTOISE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.CATERPIE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CATERPIE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.METAPOD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.METAPOD)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.BUTTERFREE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BUTTERFREE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.WEEDLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WEEDLE).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.BUG).withType2(EnumPokemonType.POISON)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.KAKUNA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KAKUNA)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.BEEDRILL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BEEDRILL)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.PIDGEY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PIDGEY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.PIDGEOTTO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PIDGEOTTO)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.PIDGEOT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PIDGEOT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.RATTATA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RATTATA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.RATICATE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RATICATE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.SPEAROW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SPEAROW)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.FEAROW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FEAROW)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.EKANS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.EKANS).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.ARBOK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ARBOK).withStage(EnumPokemonStage.FINAL)
						.withType1(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.PIKACHU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PIKACHU)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.RAICHU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RAICHU)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.SANDSHREW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SANDSHREW)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.SANDSLASH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SANDSLASH)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.NIDORAN_F,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NIDORAN_F).withName("Nidoran♀")
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.POISON)
						.withGender(EnumGender.FEMALE).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.NIDORINA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NIDORINA)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.POISON)
						.withGender(EnumGender.FEMALE).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.NIDOQUEEN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NIDOQUEEN)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.FEMALE)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.NIDORAN_M,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NIDORAN_M).withName("Nidoran♂")
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.POISON)
						.withGender(EnumGender.MALE).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.NIDORINO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NIDORINO)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.POISON)
						.withGender(EnumGender.MALE).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.NIDOKING,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NIDOKING)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.MALE).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.CLEFAIRY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CLEFAIRY)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.CLEFABLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CLEFABLE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.VULPIX,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VULPIX).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.NINETALES,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NINETALES)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.JIGGLYPUFF,
				new PokemonDataBuilder().withPokemon(EnumPokemon.JIGGLYPUFF)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.WIGGLYTUFF,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WIGGLYTUFF)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.ZUBAT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ZUBAT).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.POISON).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.GOLBAT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOLBAT)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.ODDISH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ODDISH).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GRASS).withType2(EnumPokemonType.POISON)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.GLOOM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GLOOM)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.VILEPLUME,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VILEPLUME)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.PARAS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PARAS).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.BUG).withType2(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.PARASECT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PARASECT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.VENONAT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VENONAT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.VENOMOTH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VENOMOTH)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.DIGLETT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DIGLETT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.DUGTRIO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DUGTRIO)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MEOWTH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MEOWTH).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.PERSIAN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PERSIAN)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.PSYDUCK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PSYDUCK)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.GOLDUCK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOLDUCK)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MANKEY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MANKEY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.PRIMEAPE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PRIMEAPE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.GROWLITHE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GROWLITHE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.ARCANINE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ARCANINE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.POLIWAG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.POLIWAG)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.POLIWHIRL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.POLIWHIRL)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(1).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.POLIWRATH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.POLIWRATH)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.ABRA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ABRA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.KADABRA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KADABRA)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.ALAKAZAM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ALAKAZAM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MACHOP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MACHOP).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MACHOKE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MACHOKE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MACHAMP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MACHAMP)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.BELLSPROUT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BELLSPROUT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.WEEPINBELL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WEEPINBELL)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.VICTREEBEL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VICTREEBEL)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.TENTACOOL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TENTACOOL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.TENTACRUEL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TENTACRUEL)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.GEODUDE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GEODUDE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.GRAVELER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GRAVELER)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.GOLEM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOLEM).withStage(EnumPokemonStage.FINAL)
						.withType1(EnumPokemonType.ROCK).withType2(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.PONYTA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PONYTA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.RAPIDASH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RAPIDASH)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.SLOWPOKE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SLOWPOKE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(1).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.SLOWBRO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SLOWBRO)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MAGNEMITE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAGNEMITE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ELECTRIC)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.UNKNOWN)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MAGNETON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAGNETON)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.ELECTRIC)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.UNKNOWN)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.FARFETCHD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FARFETCHD).withName("Farfetch'd")
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(1).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.DODUO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DODUO).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.DODRIO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DODRIO)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.SEEL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SEEL).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.DEWGONG, new PokemonDataBuilder().withPokemon(EnumPokemon.DEWGONG)
				.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
				.withType2(EnumPokemonType.ICE).withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.GRIMER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GRIMER).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MUK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MUK).withStage(EnumPokemonStage.FINAL)
						.withType1(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.SHELLDER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHELLDER)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.CLOYSTER, new PokemonDataBuilder().withPokemon(EnumPokemon.CLOYSTER)
				.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
				.withType2(EnumPokemonType.ICE).withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.GASTLY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GASTLY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GHOST).withType2(EnumPokemonType.POISON)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.HAUNTER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HAUNTER)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.GENGAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GENGAR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.ONIX,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ONIX).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ROCK).withType2(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(1).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.DROWZEE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DROWZEE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.HYPNO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HYPNO).withStage(EnumPokemonStage.FINAL)
						.withType1(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.KRABBY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KRABBY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.KINGLER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KINGLER)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.VOLTORB,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VOLTORB)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.UNKNOWN).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.ELECTRODE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ELECTRODE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.UNKNOWN).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.EXEGGCUTE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.EXEGGCUTE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.EXEGGUTOR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.EXEGGUTOR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.CUBONE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CUBONE).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MAROWAK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAROWAK)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.HITMONLEE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HITMONLEE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.MALE).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.HITMONCHAN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HITMONCHAN)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.MALE).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.LICKITUNG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LICKITUNG)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(1).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.KOFFING,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KOFFING)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.POISON)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.WEEZING,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WEEZING)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.POISON)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.RHYHORN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RHYHORN)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.RHYDON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RHYDON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(1).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.CHANSEY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHANSEY)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.FEMALE).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.TANGELA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TANGELA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(1).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.KANGASKHAN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KANGASKHAN)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.FEMALE).withGeneration(1).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.HORSEA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HORSEA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SEADRA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SEADRA)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(1).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.GOLDEEN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOLDEEN)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.SEAKING,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SEAKING)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.STARYU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STARYU).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.WATER).withGender(EnumGender.UNKNOWN)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.STARMIE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STARMIE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.UNKNOWN)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MR_MIME,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MR_MIME).withName("Mr. Mime")
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(1).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.SCYTHER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SCYTHER)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(1).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.JYNX,
				new PokemonDataBuilder().withPokemon(EnumPokemon.JYNX).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ICE).withType2(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.FEMALE).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.ELECTABUZZ,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ELECTABUZZ)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(1).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.MAGMAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAGMAR).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(1)
						.evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.PINSIR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PINSIR).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.BUG).withGender(EnumGender.EITHER).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.TAUROS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TAUROS).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withGender(EnumGender.MALE).withGeneration(1)
						.isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.MAGIKARP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAGIKARP)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.GYARADOS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GYARADOS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.LAPRAS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LAPRAS).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.WATER).withType2(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.DITTO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DITTO).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.EEVEE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.EEVEE).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.VAPOREON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VAPOREON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.JOLTEON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.JOLTEON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.FLAREON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FLAREON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.PORYGON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PORYGON)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.UNKNOWN).withGeneration(1).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.OMANYTE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.OMANYTE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.OMASTAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.OMASTAR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.KABUTO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KABUTO).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ROCK).withType2(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.KABUTOPS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KABUTOPS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(1)
						.build());
		DATA_LOOKUP.put(EnumPokemon.AERODACTYL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AERODACTYL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.SNORLAX,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SNORLAX)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.ARTICUNO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ARTICUNO)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.ICE)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.UNKNOWN)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.ZAPDOS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ZAPDOS)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.ELECTRIC)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.UNKNOWN)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MOLTRES,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MOLTRES)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.UNKNOWN)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.DRATINI,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DRATINI)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.DRAGONAIR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DRAGONAIR)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.DRAGONITE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DRAGONITE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DRAGON)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MEWTWO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MEWTWO)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.UNKNOWN).withGeneration(1).build());
		DATA_LOOKUP.put(EnumPokemon.MEW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MEW).withStage(EnumPokemonStage.MYTHIC)
						.withType1(EnumPokemonType.PSYCHIC).withGender(EnumGender.UNKNOWN)
						.withGeneration(1).build());

		// gen 2
		DATA_LOOKUP.put(EnumPokemon.CHIKORITA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHIKORITA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.BAYLEEF,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BAYLEEF)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.MEGANIUM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MEGANIUM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.CYNDAQUIL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CYNDAQUIL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.QUILAVA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.QUILAVA)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.TYPHLOSION,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TYPHLOSION)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.TOTODILE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TOTODILE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.CROCONAW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CROCONAW)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.FERALIGATR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FERALIGATR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.SENTRET,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SENTRET)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.FURRET,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FURRET)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.HOOTHOOT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HOOTHOOT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.NOCTOWL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NOCTOWL)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.LEDYBA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LEDYBA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.BUG).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.LEDIAN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LEDIAN)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.SPINARAK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SPINARAK)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.ARIADOS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ARIADOS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.CROBAT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CROBAT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.CHINCHOU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHINCHOU)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.LANTURN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LANTURN)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.PICHU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PICHU).withStage(EnumPokemonStage.BABY)
						.withType1(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.CLEFFA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CLEFFA).withStage(EnumPokemonStage.BABY)
						.withType1(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.IGGLYBUFF,
				new PokemonDataBuilder().withPokemon(EnumPokemon.IGGLYBUFF)
						.withStage(EnumPokemonStage.BABY).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.TOGEPI,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TOGEPI).withStage(EnumPokemonStage.BABY)
						.withType1(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.TOGETIC,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TOGETIC)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FAIRY)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(2).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.NATU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NATU).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.PSYCHIC).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.XATU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.XATU).withStage(EnumPokemonStage.FINAL)
						.withType1(EnumPokemonType.PSYCHIC).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.MAREEP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAREEP).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.FLAAFFY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FLAAFFY)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.AMPHAROS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AMPHAROS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.BELLOSSOM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BELLOSSOM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.MARILL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MARILL).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.WATER).withType2(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.AZUMARILL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AZUMARILL)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SUDOWOODO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SUDOWOODO)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.POLITOED,
				new PokemonDataBuilder().withPokemon(EnumPokemon.POLITOED)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.HOPPIP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HOPPIP).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GRASS).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.SKIPLOOM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SKIPLOOM)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.JUMPLUFF,
				new PokemonDataBuilder().withPokemon(EnumPokemon.JUMPLUFF)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.AIPOM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AIPOM).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(2).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.SUNKERN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SUNKERN)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(2).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.SUNFLORA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SUNFLORA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.YANMA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.YANMA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.BUG).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(2).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.WOOPER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WOOPER).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.WATER).withType2(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.QUAGSIRE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.QUAGSIRE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.ESPEON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ESPEON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.UMBREON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.UMBREON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DARK)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.MURKROW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MURKROW)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(2).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.SLOWKING,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SLOWKING)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.MISDREAVUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MISDREAVUS)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GHOST)
						.withGender(EnumGender.EITHER).withGeneration(2).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.UNOWN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.UNOWN).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.PSYCHIC).withGender(EnumGender.UNKNOWN)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.WOBBUFFET,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WOBBUFFET)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.GIRAFARIG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GIRAFARIG)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.PINECO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PINECO).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.BUG).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.FORRETRESS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FORRETRESS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.DUNSPARCE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DUNSPARCE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.GLIGAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GLIGAR).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GROUND).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(2).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.STEELIX,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STEELIX)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.SNUBBULL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SNUBBULL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.GRANBULL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GRANBULL)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.QWILFISH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.QWILFISH)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.SCIZOR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SCIZOR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SHUCKLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHUCKLE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.ROCK).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.HERACROSS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HERACROSS)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(2).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.SNEASEL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SNEASEL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.ICE).withGender(EnumGender.EITHER).withGeneration(2)
						.evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.TEDDIURSA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TEDDIURSA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.URSARING,
				new PokemonDataBuilder().withPokemon(EnumPokemon.URSARING)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.SLUGMA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SLUGMA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.MAGCARGO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAGCARGO)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.ROCK).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SWINUB,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SWINUB).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ICE).withType2(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.PILOSWINE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PILOSWINE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.ICE)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.CORSOLA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CORSOLA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.ROCK).withGender(EnumGender.EITHER).withGeneration(2)
						.isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.REMORAID,
				new PokemonDataBuilder().withPokemon(EnumPokemon.REMORAID)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.OCTILLERY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.OCTILLERY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.DELIBIRD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DELIBIRD)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ICE)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.MANTINE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MANTINE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.SKARMORY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SKARMORY)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.HOUNDOUR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HOUNDOUR)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.HOUNDOOM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HOUNDOOM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.KINGDRA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KINGDRA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.PHANPY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PHANPY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.DONPHAN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DONPHAN)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.PORYGON2,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PORYGON2)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.UNKNOWN).withGeneration(2).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.STANTLER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STANTLER)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.SMEARGLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SMEARGLE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.TYROGUE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TYROGUE)
						.withStage(EnumPokemonStage.BABY).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.MALE).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.HITMONTOP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HITMONTOP)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.MALE).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.SMOOCHUM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SMOOCHUM)
						.withStage(EnumPokemonStage.BABY).withType1(EnumPokemonType.ICE)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.FEMALE)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.ELEKID,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ELEKID).withStage(EnumPokemonStage.BABY)
						.withType1(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.MAGBY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAGBY).withStage(EnumPokemonStage.BABY)
						.withType1(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.MILTANK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MILTANK)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.FEMALE).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.BLISSEY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BLISSEY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.FEMALE).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.RAIKOU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RAIKOU)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.UNKNOWN).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.ENTEI,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ENTEI)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.UNKNOWN).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.SUICUNE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SUICUNE)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.UNKNOWN).withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.LARVITAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LARVITAR)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.PUPITAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PUPITAR)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.TYRANITAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TYRANITAR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(2)
						.build());
		DATA_LOOKUP.put(EnumPokemon.LUGIA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LUGIA)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.UNKNOWN)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.HO_OH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HO_OH).withName("Ho-Oh")
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.UNKNOWN)
						.withGeneration(2).build());
		DATA_LOOKUP.put(EnumPokemon.CELEBI,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CELEBI)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(2)
						.build());

		// gen 3
		DATA_LOOKUP.put(EnumPokemon.TREECKO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TREECKO)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.GROVYLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GROVYLE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SCEPTILE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SCEPTILE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.TORCHIC,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TORCHIC)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.COMBUSKEN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.COMBUSKEN)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.isStarter(true).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.BLAZIKEN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BLAZIKEN)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.isStarter(true).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.MUDKIP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MUDKIP).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.WATER).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.MARSHTOMP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MARSHTOMP)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SWAMPERT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SWAMPERT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.POOCHYENA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.POOCHYENA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.DARK)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.MIGHTYENA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MIGHTYENA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DARK)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.ZIGZAGOON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ZIGZAGOON)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.LINOONE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LINOONE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.WURMPLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WURMPLE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SILCOON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SILCOON)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.BEAUTIFLY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BEAUTIFLY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.CASCOON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CASCOON)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.DUSTOX,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DUSTOX)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.LOTAD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LOTAD).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.WATER).withType2(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.LOMBRE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LOMBRE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.LUDICOLO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LUDICOLO)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SEEDOT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SEEDOT).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.NUZLEAF,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NUZLEAF)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SHIFTRY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHIFTRY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.TAILLOW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TAILLOW)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SWELLOW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SWELLOW)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.WINGULL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WINGULL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.PELIPPER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PELIPPER)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.RALTS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RALTS).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.PSYCHIC).withType2(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.KIRLIA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KIRLIA)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(3)
						.evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.GARDEVOIR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GARDEVOIR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SURSKIT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SURSKIT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.MASQUERAIN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MASQUERAIN)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SHROOMISH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHROOMISH)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.BRELOOM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BRELOOM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SLAKOTH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SLAKOTH)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.VIGOROTH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VIGOROTH)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SLAKING,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SLAKING)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.NINCADA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NINCADA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.NINJASK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NINJASK)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SHEDINJA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHEDINJA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.UNKNOWN)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.WHISMUR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WHISMUR)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.LOUDRED,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LOUDRED)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.EXPLOUD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.EXPLOUD)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.MAKUHITA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAKUHITA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.HARIYAMA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HARIYAMA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.AZURILL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AZURILL)
						.withStage(EnumPokemonStage.BABY).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.NOSEPASS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NOSEPASS)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SKITTY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SKITTY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.DELCATTY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DELCATTY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SABLEYE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SABLEYE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.MAWILE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAWILE).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.STEEL).withType2(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.ARON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ARON).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.STEEL).withType2(EnumPokemonType.ROCK)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.LAIRON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LAIRON)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.ROCK).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.AGGRON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AGGRON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.ROCK).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.MEDITITE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MEDITITE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIGHTING)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.MEDICHAM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MEDICHAM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIGHTING)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.ELECTRIKE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ELECTRIKE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.MANECTRIC,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MANECTRIC)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.PLUSLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PLUSLE).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.MINUN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MINUN).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.VOLBEAT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VOLBEAT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.MALE).withGeneration(3).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.ILLUMISE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ILLUMISE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.FEMALE).withGeneration(3).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.ROSELIA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ROSELIA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(3).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.GULPIN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GULPIN).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SWALOT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SWALOT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.POISON)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.CARVANHA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CARVANHA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SHARPEDO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHARPEDO)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.WAILMER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WAILMER)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.WAILORD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WAILORD)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.NUMEL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NUMEL).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ROCK).withType2(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.CAMERUPT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CAMERUPT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.TORKOAL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TORKOAL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withGender(EnumGender.EITHER).withGeneration(3).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.SPOINK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SPOINK).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.GRUMPIG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GRUMPIG)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SPINDA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SPINDA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.TRAPINCH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TRAPINCH)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.VIBRAVA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VIBRAVA)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GROUND)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.FLYGON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FLYGON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.CACNEA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CACNEA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.CACTURNE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CACTURNE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SWABLU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SWABLU).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.ALTARIA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ALTARIA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DRAGON)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.ZANGOOSE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ZANGOOSE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(3).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.SEVIPER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SEVIPER)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.POISON)
						.withGender(EnumGender.EITHER).withGeneration(3).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.LUNATONE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LUNATONE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.UNKNOWN)
						.withGeneration(3).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.SOLROCK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SOLROCK)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.UNKNOWN)
						.withGeneration(3).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.BARBOACH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BARBOACH)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.WHISCASH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WHISCASH)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.CORPHISH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CORPHISH)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.CRAWDAUNT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CRAWDAUNT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.BALTOY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BALTOY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GROUND).withType2(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.UNKNOWN).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.CLAYDOL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CLAYDOL)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.UNKNOWN)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.LILEEP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LILEEP).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ROCK).withType2(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.CRADILY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CRADILY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.ANORITH, new PokemonDataBuilder().withPokemon(EnumPokemon.ANORITH)
				.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
				.withType2(EnumPokemonType.BUG).withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.ARMALDO, new PokemonDataBuilder().withPokemon(EnumPokemon.ARMALDO)
				.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
				.withType2(EnumPokemonType.BUG).withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.FEEBAS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FEEBAS).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.MILOTIC,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MILOTIC)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.CASTFORM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CASTFORM)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.KECLEON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KECLEON)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SHUPPET,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHUPPET)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GHOST)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.BANETTE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BANETTE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GHOST)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.DUSKULL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DUSKULL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GHOST)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.DUSCLOPS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DUSCLOPS)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GHOST)
						.withGender(EnumGender.EITHER).withGeneration(3).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.TROPIUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TROPIUS)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(3).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.CHIMECHO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHIMECHO)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.ABSOL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ABSOL).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.WYNAUT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WYNAUT).withStage(EnumPokemonStage.BABY)
						.withType1(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SNORUNT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SNORUNT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(3).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.GLALIE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GLALIE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SPHEAL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SPHEAL).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ICE).withType2(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SEALEO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SEALEO)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.ICE)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.WALREIN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WALREIN)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ICE)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.CLAMPERL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CLAMPERL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.HUNTAIL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HUNTAIL)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.GOREBYSS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOREBYSS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.RELICANTH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RELICANTH)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.ROCK).withGender(EnumGender.EITHER).withGeneration(3)
						.isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.LUVDISC,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LUVDISC)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.BAGON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BAGON).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SHELGON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHELGON)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.SALAMENCE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SALAMENCE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DRAGON)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.BELDUM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BELDUM).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.STEEL).withType2(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.UNKNOWN).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.METANG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.METANG)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.UNKNOWN)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.METAGROSS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.METAGROSS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.UNKNOWN)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.REGIROCK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.REGIROCK)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.ROCK)
						.withGender(EnumGender.UNKNOWN).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.REGICE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.REGICE)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.ICE)
						.withGender(EnumGender.UNKNOWN).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.REGISTEEL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.REGISTEEL)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.STEEL)
						.withGender(EnumGender.UNKNOWN).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.LATIAS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LATIAS)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.DRAGON)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.FEMALE)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.LATIOS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LATIOS)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.DRAGON)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.MALE).withGeneration(3)
						.build());
		DATA_LOOKUP.put(EnumPokemon.KYOGRE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KYOGRE)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.UNKNOWN).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.GROUDON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GROUDON)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.UNKNOWN).withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.RAYQUAZA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RAYQUAZA)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.DRAGON)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.UNKNOWN)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.JIRACHI,
				new PokemonDataBuilder().withPokemon(EnumPokemon.JIRACHI)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.UNKNOWN)
						.withGeneration(3).build());
		DATA_LOOKUP.put(EnumPokemon.DEOXYS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DEOXYS)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.UNKNOWN).withGeneration(3).build());

		// gen 4
		DATA_LOOKUP.put(EnumPokemon.TURTWIG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TURTWIG)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.GROTLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GROTLE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.TORTERRA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TORTERRA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.CHIMCHAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHIMCHAR)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.MONFERNO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MONFERNO)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.isStarter(true).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.INFERNAPE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.INFERNAPE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.isStarter(true).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.PIPLUP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PIPLUP).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.WATER).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.PRINPLUP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PRINPLUP)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.EMPOLEON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.EMPOLEON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.STARLY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STARLY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.STARAVIA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STARAVIA)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.STARAPTOR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STARAPTOR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.BIDOOF,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BIDOOF).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.BIBAREL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BIBAREL)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.KRICKETOT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KRICKETOT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.KRICKETUNE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KRICKETUNE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.SHINX,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHINX).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.LUXIO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LUXIO)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.LUXRAY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LUXRAY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.BUDEW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BUDEW).withStage(EnumPokemonStage.BABY)
						.withType1(EnumPokemonType.GRASS).withType2(EnumPokemonType.POISON)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.ROSERADE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ROSERADE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.CRANIDOS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CRANIDOS)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.RAMPARDOS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RAMPARDOS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.SHIELDON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHIELDON)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.BASTIODON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BASTIODON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.BURMY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BURMY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.BUG).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.WORMADAM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WORMADAM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.FEMALE).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.MOTHIM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MOTHIM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.MALE).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.COMBEE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.COMBEE).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.BUG).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.VESPIQUEN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VESPIQUEN)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.FEMALE)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.PACHIRISU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PACHIRISU)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(4).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.BUIZEL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BUIZEL).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.FLOATZEL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FLOATZEL)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.CHERUBI,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHERUBI)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.CHERRIM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHERRIM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.SHELLOS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHELLOS)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(4).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.GASTRODON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GASTRODON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(4).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.AMBIPOM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AMBIPOM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.DRIFLOON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DRIFLOON)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.DRIFBLIM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DRIFBLIM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.BUNEARY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BUNEARY)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.LOPUNNY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LOPUNNY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.MISMAGIUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MISMAGIUS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GHOST)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.HONCHKROW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HONCHKROW)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.GLAMEOW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GLAMEOW)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.PURUGLY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PURUGLY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.CHINGLING,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHINGLING)
						.withStage(EnumPokemonStage.BABY).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.STUNKY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STUNKY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.POISON).withType2(EnumPokemonType.DARK)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.SKUNTANK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SKUNTANK)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.BRONZOR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BRONZOR)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.UNKNOWN)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.BRONZONG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BRONZONG)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.UNKNOWN)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.BONSLY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BONSLY).withStage(EnumPokemonStage.BABY)
						.withType1(EnumPokemonType.ROCK).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.MIME_JR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MIME_JR).withName("Mime Jr.")
						.withStage(EnumPokemonStage.BABY).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.HAPPINY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HAPPINY)
						.withStage(EnumPokemonStage.BABY).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.FEMALE).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.CHATOT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHATOT).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(4).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.SPIRITOMB,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SPIRITOMB)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.GIBLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GIBLE).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.DRAGON).withType2(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.GABITE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GABITE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.DRAGON)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.GARCHOMP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GARCHOMP)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DRAGON)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.MUNCHLAX,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MUNCHLAX)
						.withStage(EnumPokemonStage.BABY).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.RIOLU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RIOLU).withStage(EnumPokemonStage.BABY)
						.withType1(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.LUCARIO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LUCARIO)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIGHTING)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.HIPPOPOTAS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HIPPOPOTAS)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.HIPPOWDON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HIPPOWDON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.SKORUPI, new PokemonDataBuilder().withPokemon(EnumPokemon.SKORUPI)
				.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.POISON)
				.withType2(EnumPokemonType.BUG).withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.DRAPION,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DRAPION)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.CROAGUNK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CROAGUNK)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.TOXICROAK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TOXICROAK)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.CARNIVINE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CARNIVINE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(4).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.FINNEON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FINNEON)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.LUMINEON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LUMINEON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.MANTYKE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MANTYKE)
						.withStage(EnumPokemonStage.BABY).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.SNOVER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SNOVER).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GRASS).withType2(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.ABOMASNOW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ABOMASNOW)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.ICE).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.WEAVILE, new PokemonDataBuilder().withPokemon(EnumPokemon.WEAVILE)
				.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DARK)
				.withType2(EnumPokemonType.ICE).withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.MAGNEZONE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAGNEZONE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ELECTRIC)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.UNKNOWN)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.LICKILICKY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LICKILICKY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.RHYPERIOR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RHYPERIOR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withType2(EnumPokemonType.ROCK).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.TANGROWTH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TANGROWTH)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.ELECTIVIRE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ELECTIVIRE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.MAGMORTAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAGMORTAR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.TOGEKISS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TOGEKISS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FAIRY)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.YANMEGA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.YANMEGA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.LEAFEON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LEAFEON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.GLACEON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GLACEON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.GLISCOR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GLISCOR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.MAMOSWINE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAMOSWINE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ICE)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(4).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.PORYGON_Z,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PORYGON_Z).withName("Porygon-Z")
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.UNKNOWN).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.GALLADE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GALLADE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.MALE)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.PROBOPASS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PROBOPASS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.DUSKNOIR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DUSKNOIR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GHOST)
						.withGender(EnumGender.EITHER).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.FROSLASS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FROSLASS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ICE)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.FEMALE).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.ROTOM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ROTOM).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ELECTRIC).withType2(EnumPokemonType.GHOST)
						.withGender(EnumGender.UNKNOWN).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.UXIE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.UXIE).withStage(EnumPokemonStage.LEGEND)
						.withType1(EnumPokemonType.PSYCHIC).withGender(EnumGender.UNKNOWN)
						.withGeneration(4).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.MESPRIT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MESPRIT)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.UNKNOWN).withGeneration(4).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.AZELF,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AZELF)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.UNKNOWN).withGeneration(4).isRegional(true).build());
		DATA_LOOKUP.put(EnumPokemon.DIALGA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DIALGA)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.UNKNOWN)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.PALKIA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PALKIA)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.UNKNOWN)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.HEATRAN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HEATRAN)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(4)
						.build());
		DATA_LOOKUP.put(EnumPokemon.REGIGIGAS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.REGIGIGAS)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.UNKNOWN).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.GIRATINA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GIRATINA)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.UNKNOWN)
						.withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.CRESSELIA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CRESSELIA)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.FEMALE).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.PHIONE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PHIONE)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.UNKNOWN).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.MANAPHY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MANAPHY)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.UNKNOWN).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.DARKRAI,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DARKRAI)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.DARK)
						.withGender(EnumGender.UNKNOWN).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.SHAYMIN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHAYMIN)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.UNKNOWN).withGeneration(4).build());
		DATA_LOOKUP.put(EnumPokemon.ARCEUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ARCEUS)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.UNKNOWN).withGeneration(4).build());

		// gen 5
		DATA_LOOKUP.put(EnumPokemon.VICTINI,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VICTINI)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SNIVY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SNIVY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SERVINE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SERVINE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SERPERIOR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SERPERIOR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.TEPIG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TEPIG).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.PIGNITE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PIGNITE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.isStarter(true).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.EMBOAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.EMBOAR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.isStarter(true).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.OSHAWOTT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.OSHAWOTT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.DEWOTT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DEWOTT)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SAMUROTT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SAMUROTT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.PATRAT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PATRAT).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.WATCHOG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WATCHOG)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.LILLIPUP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LILLIPUP)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.HERDIER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HERDIER)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.STOUTLAND,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STOUTLAND)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.PURRLOIN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PURRLOIN)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.DARK)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.LIEPARD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LIEPARD)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DARK)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.PANSAGE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PANSAGE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(5).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.SIMISAGE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SIMISAGE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.PANSEAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PANSEAR)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).withGeneration(5).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.SIMISEAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SIMISEAR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.PANPOUR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PANPOUR)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(5).evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.SIMIPOUR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SIMIPOUR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.MUNNA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MUNNA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.MUSHARNA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MUSHARNA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.PIDOVE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PIDOVE).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.TRANQUILL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TRANQUILL)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.UNFEZANT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.UNFEZANT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.BLITZLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BLITZLE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.ZEBSTRIKA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ZEBSTRIKA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.ROGGENROLA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ROGGENROLA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.BOLDORE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BOLDORE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.ROCK)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.GIGALITH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GIGALITH)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.WOOBAT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WOOBAT).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.PSYCHIC).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SWOOBAT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SWOOBAT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.DRILBUR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DRILBUR)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.EXCADRILL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.EXCADRILL)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.AUDINO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AUDINO).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.TIMBURR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TIMBURR)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.GURDURR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GURDURR)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.CONKELDURR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CONKELDURR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.TYMPOLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TYMPOLE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.PALPITOAD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PALPITOAD)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SEISMITOAD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SEISMITOAD)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.THROH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.THROH).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SAWK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SAWK).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SEWADDLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SEWADDLE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SWADLOON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SWADLOON)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.LEAVANNY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LEAVANNY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.VENIPEDE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VENIPEDE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.WHIRLIPEDE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WHIRLIPEDE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SCOLIPEDE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SCOLIPEDE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.COTTONEE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.COTTONEE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.WHIMSICOTT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WHIMSICOTT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.PETILIL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PETILIL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.LILLIGANT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LILLIGANT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.BASCULIN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BASCULIN)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SANDILE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SANDILE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GROUND)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.KROKOROK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KROKOROK)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GROUND)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.KROOKODILE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KROOKODILE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.DARUMAKA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DARUMAKA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.DARMANITAN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DARMANITAN)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.MARACTUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MARACTUS)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.DWEBBLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DWEBBLE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.ROCK).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.CRUSTLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CRUSTLE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.ROCK).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SCRAGGY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SCRAGGY)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SCRAFTY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SCRAFTY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SIGILYPH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SIGILYPH)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.YAMASK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.YAMASK).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.COFAGRIGUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.COFAGRIGUS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GHOST)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.TIRTOUGA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TIRTOUGA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.ROCK).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.CARRACOSTA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CARRACOSTA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.ROCK).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.ARCHEN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ARCHEN).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ROCK).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.ARCHEOPS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ARCHEOPS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.TRUBBISH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TRUBBISH)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.POISON)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.GARBODOR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GARBODOR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.POISON)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.ZORUA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ZORUA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.ZOROARK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ZOROARK)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DARK)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.MINCCINO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MINCCINO)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.CINCCINO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CINCCINO)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.GOTHITA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOTHITA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.GOTHORITA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOTHORITA)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.GOTHITELLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOTHITELLE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SOLOSIS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SOLOSIS)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.DUOSION,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DUOSION)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.REUNICLUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.REUNICLUS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.DUCKLETT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DUCKLETT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SWANNA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SWANNA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.VANILLITE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VANILLITE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.VANILLISH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VANILLISH)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.VANILLUXE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VANILLUXE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.DEERLING,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DEERLING)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SAWSBUCK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SAWSBUCK)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.EMOLGA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.EMOLGA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ELECTRIC).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.KARRABLAST,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KARRABLAST)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.ESCAVALIER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ESCAVALIER)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.FOONGUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FOONGUS)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.AMOONGUSS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AMOONGUSS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.FRILLISH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FRILLISH)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.JELLICENT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.JELLICENT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.ALOMOMOLA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ALOMOMOLA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.JOLTIK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.JOLTIK).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.BUG).withType2(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.GALVANTULA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GALVANTULA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.FERROSEED,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FERROSEED)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.FERROTHORN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FERROTHORN)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.KLINK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KLINK).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.KLANG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KLANG)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.STEEL)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.KLINKLANG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KLINKLANG)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.STEEL)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.TYNAMO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TYNAMO).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.EELEKTRIK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.EELEKTRIK)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.EELEKTROSS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.EELEKTROSS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.ELGYEM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ELGYEM).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.BEHEEYEM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BEHEEYEM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.LITWICK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LITWICK)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.LAMPENT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LAMPENT)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(5)
						.evolvesWithItem(true).build());
		DATA_LOOKUP.put(EnumPokemon.CHANDELURE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHANDELURE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.AXEW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AXEW).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.FRAXURE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FRAXURE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.HAXORUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HAXORUS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.CUBCHOO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CUBCHOO)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.BEARTIC,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BEARTIC)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.CRYOGONAL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CRYOGONAL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.SHELMET,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHELMET)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.ACCELGOR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ACCELGOR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.STUNFISK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STUNFISK)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GROUND)
						.withType2(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.MIENFOO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MIENFOO)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.MIENSHAO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MIENSHAO)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.DRUDDIGON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DRUDDIGON)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.GOLETT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOLETT).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GROUND).withType2(EnumPokemonType.GHOST)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.GOLURK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOLURK)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.PAWNIARD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PAWNIARD)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.BISHARP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BISHARP)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.BOUFFALANT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BOUFFALANT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.RUFFLET,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RUFFLET)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.BRAVIARY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BRAVIARY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.VULLABY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VULLABY)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.MANDIBUZZ,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MANDIBUZZ)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.HEATMOR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HEATMOR)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.DURANT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DURANT).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.BUG).withType2(EnumPokemonType.STEEL)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.DEINO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DEINO).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.DARK).withType2(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.ZWEILOUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ZWEILOUS)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.HYDREIGON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HYDREIGON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.LARVESTA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LARVESTA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.VOLCARONA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VOLCARONA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.COBALION,
				new PokemonDataBuilder().withPokemon(EnumPokemon.COBALION)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.TERRAKION,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TERRAKION)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.VIRIZION,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VIRIZION)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.TORNADUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TORNADUS)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.THUNDURUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.THUNDURUS)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.ELECTRIC)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.RESHIRAM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RESHIRAM)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.DRAGON)
						.withType2(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(5)
						.build());
		DATA_LOOKUP.put(EnumPokemon.ZEKROM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ZEKROM)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.DRAGON)
						.withType2(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.LANDORUS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LANDORUS)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.GROUND)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.KYUREM, new PokemonDataBuilder().withPokemon(EnumPokemon.KYUREM)
				.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.DRAGON)
				.withType2(EnumPokemonType.ICE).withGender(EnumGender.EITHER).withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.KELDEO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KELDEO)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.MELOETTA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MELOETTA)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(5).build());
		DATA_LOOKUP.put(EnumPokemon.GENESECT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GENESECT)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(5)
						.build());

		// gen 6
		DATA_LOOKUP.put(EnumPokemon.CHESPIN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHESPIN)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.QUILLADIN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.QUILLADIN)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.CHESNAUGHT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHESNAUGHT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.isStarter(true).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.FENNEKIN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FENNEKIN)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.BRAIXEN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BRAIXEN)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.DELPHOX,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DELPHOX)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.FROAKIE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FROAKIE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.FROGADIER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FROGADIER)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.GRENINJA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GRENINJA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.BUNNELBY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BUNNELBY)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.DIGGERSBY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DIGGERSBY)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.FLETCHLING,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FLETCHLING)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.FLETCHINDER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FLETCHINDER)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.TALONFLAME,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TALONFLAME)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.SCATTERBUG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SCATTERBUG)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.SPEWPA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SPEWPA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.VIVILLON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VIVILLON)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.LITLEO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LITLEO).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FIRE).withType2(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.PYROAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PYROAR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.FLABEBE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FLABEBE).withName("Flabébé")
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.FLOETTE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FLOETTE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.FLORGES,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FLORGES)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.SKIDDO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SKIDDO).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.GOGOAT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOGOAT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.PANCHAM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PANCHAM)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.PANGORO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PANGORO)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIGHTING)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.FURFROU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FURFROU)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.ESPURR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ESPURR).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.MEOWSTIC,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MEOWSTIC)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.HONEDGE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HONEDGE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.DOUBLADE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DOUBLADE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.AEGISLASH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AEGISLASH)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SPRITZEE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SPRITZEE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.AROMATISSE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AROMATISSE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.SWIRLIX,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SWIRLIX)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.SLURPUFF,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SLURPUFF)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.INKAY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.INKAY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.DARK).withType2(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.MALAMAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MALAMAR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.BINACLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BINACLE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.BARBARACLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BARBARACLE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SKRELP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SKRELP).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.POISON).withType2(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.DRAGALGE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DRAGALGE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.CLAUNCHER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CLAUNCHER)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.CLAWITZER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CLAWITZER)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.HELIOPTILE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HELIOPTILE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ELECTRIC)
						.withType2(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.HELIOLISK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HELIOLISK)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ELECTRIC)
						.withType2(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.TYRUNT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TYRUNT).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ROCK).withType2(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.TYRANTRUM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TYRANTRUM)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.AMAURA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AMAURA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ROCK).withType2(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.AURORUS, new PokemonDataBuilder().withPokemon(EnumPokemon.AURORUS)
				.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
				.withType2(EnumPokemonType.ICE).withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.SYLVEON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SYLVEON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.HAWLUCHA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HAWLUCHA)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIGHTING)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.DEDENNE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DEDENNE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ELECTRIC)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.CARBINK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CARBINK)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.GOOMY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOOMY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.SLIGGOO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SLIGGOO)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.GOODRA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOODRA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.KLEFKI,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KLEFKI).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.STEEL).withType2(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.PHANTUMP,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PHANTUMP)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.TREVENANT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TREVENANT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.PUMPKABOO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PUMPKABOO)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.GOURGEIST,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOURGEIST)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.BERGMITE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BERGMITE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.AVALUGG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.AVALUGG)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ICE)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.NOIBAT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NOIBAT).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FLYING).withType2(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.NOIVERN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NOIVERN)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FLYING)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.XERNEAS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.XERNEAS)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.FAIRY)
						.withGender(EnumGender.EITHER).withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.YVELTAL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.YVELTAL)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.ZYGARDE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ZYGARDE)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.DRAGON)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(6).build());
		DATA_LOOKUP.put(EnumPokemon.DIANCIE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DIANCIE)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.HOOPA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HOOPA)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).withGeneration(6)
						.build());
		DATA_LOOKUP.put(EnumPokemon.VOLCANION,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VOLCANION)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(6)
						.build());

		// gen 7
		DATA_LOOKUP.put(EnumPokemon.ROWLET,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ROWLET).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.GRASS).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.DARTRIX,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DARTRIX)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.DECIDUEYE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DECIDUEYE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.LITTEN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LITTEN).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.TORRACAT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TORRACAT)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.FIRE)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.INCINEROAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.INCINEROAR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.DARK).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.POPPLIO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.POPPLIO)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.BRIONNE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BRIONNE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).isStarter(true).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.PRIMARINA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PRIMARINA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).isStarter(true)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.PIKIPEK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PIKIPEK)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.TRUMBEAK,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TRUMBEAK)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.TOUCANNON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TOUCANNON)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.YUNGOOS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.YUNGOOS)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.GUMSHOOS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GUMSHOOS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.GRUBBIN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GRUBBIN)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.CHARJABUG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CHARJABUG)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.VIKAVOLT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.VIKAVOLT)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.ELECTRIC).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.CRABRAWLER,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CRABRAWLER)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.CRABOMINABLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CRABOMINABLE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.FIGHTING)
						.withType2(EnumPokemonType.ICE).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.ORICORIO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ORICORIO)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.CUTIEFLY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CUTIEFLY)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.RIBOMBEE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.RIBOMBEE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.ROCKRUFF,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ROCKRUFF)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ROCK)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.LYCANROC,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LYCANROC)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.ROCK)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.WISHIWASHI,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WISHIWASHI)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.MAREANIE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAREANIE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.TOXAPEX,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TOXAPEX)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.MUDBRAY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MUDBRAY)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.MUDSDALE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MUDSDALE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GROUND)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.DEWPIDER, new PokemonDataBuilder().withPokemon(EnumPokemon.DEWPIDER)
				.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
				.withType2(EnumPokemonType.BUG).withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.ARAQUANID,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ARAQUANID)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.BUG).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.FOMANTIS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.FOMANTIS)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.LURANTIS,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LURANTIS)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.MORELULL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MORELULL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SHIINOTIC,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SHIINOTIC)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SALANDIT,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SALANDIT)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SALAZZLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SALAZZLE)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.FIRE).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.STUFFUL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STUFFUL)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.BEWEAR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BEWEAR)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.BOUNSWEET,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BOUNSWEET)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.STEENEE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STEENEE)
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.TSAREENA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TSAREENA)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GRASS)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.COMFEY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.COMFEY).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.ORANGURU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ORANGURU)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.NORMAL)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.PASSIMIAN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PASSIMIAN)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIGHTING)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.WIMPOD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.WIMPOD).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.BUG).withType2(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.GOLISOPOD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GOLISOPOD)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.WATER).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.SANDYGAST,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SANDYGAST)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.PALOSSAND,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PALOSSAND)
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.GROUND).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.PYUKUMUKU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PYUKUMUKU)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.TYPE_NULL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TYPE_NULL).withName("Type: Null")
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.SILVALLY,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SILVALLY)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.NORMAL)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.MINIOR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MINIOR).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.ROCK).withType2(EnumPokemonType.FLYING)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.KOMALA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KOMALA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.TURTONATOR,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TURTONATOR)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.TOGEDEMARU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TOGEDEMARU)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.ELECTRIC)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.MIMIKYU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MIMIKYU)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.BRUXISH,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BRUXISH)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.PSYCHIC).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.DRAMPA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DRAMPA).withStage(EnumPokemonStage.BASE)
						.withType1(EnumPokemonType.NORMAL).withType2(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.DHELMISE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.DHELMISE)
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.GHOST)
						.withType2(EnumPokemonType.GRASS).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.JANGMO_O,
				new PokemonDataBuilder().withPokemon(EnumPokemon.JANGMO_O).withName("Jangmo-o")
						.withStage(EnumPokemonStage.BASE).withType1(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.HAKAMO_O,
				new PokemonDataBuilder().withPokemon(EnumPokemon.HAKAMO_O).withName("Hakamo-o")
						.withStage(EnumPokemonStage.MIDDLE).withType1(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.KOMMO_O,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KOMMO_O).withName("Kommo-o")
						.withStage(EnumPokemonStage.FINAL).withType1(EnumPokemonType.DRAGON)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.TAPU_KOKO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TAPU_KOKO).withName("Tapu Koko")
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.ELECTRIC)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.TAPU_LELE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TAPU_LELE).withName("Tapu Lele")
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.TAPU_BULU,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TAPU_BULU).withName("Tapu Bulu")
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.TAPU_FINI,
				new PokemonDataBuilder().withPokemon(EnumPokemon.TAPU_FINI).withName("Tapu Fini")
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.WATER)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.COSMOG,
				new PokemonDataBuilder().withPokemon(EnumPokemon.COSMOG)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.COSMOEM,
				new PokemonDataBuilder().withPokemon(EnumPokemon.COSMOEM)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.SOLGALEO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.SOLGALEO)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.LUNALA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.LUNALA)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.PSYCHIC)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.NIHILEGO,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NIHILEGO)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.POISON).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.BUZZWOLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BUZZWOLE)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.PHEROMOSA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.PHEROMOSA)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.BUG)
						.withType2(EnumPokemonType.FIGHTING).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.XURKITREE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.XURKITREE)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.CELESTEELA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.CELESTEELA)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.FLYING).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.KARTANA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.KARTANA)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.GRASS)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.GUZZLORD,
				new PokemonDataBuilder().withPokemon(EnumPokemon.GUZZLORD)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.DARK)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.NECROZMA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NECROZMA)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.PSYCHIC)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.MAGEARNA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MAGEARNA)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.STEEL)
						.withType2(EnumPokemonType.FAIRY).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.MARSHADOW,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MARSHADOW)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.FIGHTING)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.POIPOLE,
				new PokemonDataBuilder().withPokemon(EnumPokemon.POIPOLE)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.POISON)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.NAGANADEL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.NAGANADEL)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.POISON)
						.withType2(EnumPokemonType.DRAGON).withGender(EnumGender.EITHER)
						.withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.STAKATAKA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.STAKATAKA)
						.withStage(EnumPokemonStage.LEGEND).withType1(EnumPokemonType.ROCK)
						.withType2(EnumPokemonType.STEEL).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.BLACEPHALON,
				new PokemonDataBuilder().withPokemon(EnumPokemon.BLACEPHALON)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.FIRE)
						.withType2(EnumPokemonType.GHOST).withGender(EnumGender.EITHER).withGeneration(7)
						.build());
		DATA_LOOKUP.put(EnumPokemon.ZERAORA,
				new PokemonDataBuilder().withPokemon(EnumPokemon.ZERAORA)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.ELECTRIC)
						.withGender(EnumGender.EITHER).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.MELTAN,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MELTAN)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.STEEL)
						.withGender(EnumGender.UNKNOWN).withGeneration(7).build());
		DATA_LOOKUP.put(EnumPokemon.MELMETAL,
				new PokemonDataBuilder().withPokemon(EnumPokemon.MELMETAL)
						.withStage(EnumPokemonStage.MYTHIC).withType1(EnumPokemonType.STEEL)
						.withGender(EnumGender.UNKNOWN).withGeneration(7).build());
	}

	public static PokemonData getData(EnumPokemon pokemon) {
		return DATA_LOOKUP.get(pokemon);
	}

	public static EnumPokemon getPokemon(int id) {
		id--;
		if (id < 0 || id > values().length - 1)
			return null;
		return values()[id];
	}

	public static EnumPokemon getPokemon(String name) {
		if (name == null)
			return null;
		if (name.matches("[0-9]+")) {
			int id = Integer.parseInt(name);
			return getPokemon(id);
		}
		name = name.replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9]", "");
		if (name.toLowerCase().matches("farfetch.d"))
			return EnumPokemon.FARFETCHD;
		for (EnumPokemon p : values()) {
			PokemonData data = getData(p);
			List<String> names = new ArrayList<>();
			names.add(data.getName().toLowerCase());
			names.add(p.name().toLowerCase());
			names.add(data.getName().replaceAll("_", "").toLowerCase());
			names.add(p.name().replaceAll("_", "").toLowerCase());
			if (names.contains(name.toLowerCase()))
				return p;
		}
		return null;
	}

	public PokemonData getData() {
		return getData(this);
	}

	public FormSet getFormSet() {
		return FormManager.getForms(this);
	}

	// generate new static from old data
	public static void main(String... args) {
		PrintStream file;
		try {
			file = new PrintStream("EnumPokemonNew.txt");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		int prevGen = 0;
		for (EnumPokemon pokemon : EnumPokemon.values()) {
			PokemonData data = EnumPokemon.getData(pokemon);
			if (prevGen != data.getGeneration()) {
				prevGen = data.getGeneration();
				file.println("\n// gen " + prevGen);
			}
			file.print("DATA_LOOKUP.put(EnumPokemon." + pokemon.name() + ", new PokemonDataBuilder()");
			file.print(".withPokemon(EnumPokemon." + pokemon.name().toUpperCase() + ")");

			String defaultName = pokemon.name();
			defaultName = defaultName.substring(0, 1)
					+ defaultName.substring(1, defaultName.length()).toLowerCase();
			if (!data.getName().equals(defaultName))
				file.print(".withName(\"" + data.getName() + "\")");

			file.print(".withStage(EnumPokemonStage." + data.getStage() + ")");
			file.print(".withType1(EnumPokemonType." + data.getType1().name().toUpperCase() + ")");
			if (data.getType2() != null)
				file.print(".withType2(EnumPokemonType." + data.getType2().name().toUpperCase() + ")");
			file.print(".withGender(EnumGender." + data.getGenderPossible().name().toUpperCase() + ")");
			// family
			if (data.isStarter())
				file.print(".isStarter(" + data.isStarter() + ")");
			file.print(".withGeneration(" + data.getGeneration() + ")");
			if (data.evolvesWithItem())
				file.print(".evolvesWithItem(" + data.evolvesWithItem() + ")");
			if (data.isRegional())
				file.print(".isRegional(" + data.isRegional() + ")");

			file.println(".build());");
		}
		file.close();
	}

}
