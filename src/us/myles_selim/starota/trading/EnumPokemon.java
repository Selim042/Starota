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
	BULBASAUR(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	IVYSAUR(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	VENUSAUR(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	CHARMANDER(EnumPokemonType.FIRE),
	CHARMELEON(EnumPokemonType.FIRE),
	CHARIZARD(EnumPokemonType.FIRE, EnumPokemonType.FLYING),
	SQUIRTLE(EnumPokemonType.WATER, FormSetSquirtleSquad.FORM_SET),
	WARTORTLE(EnumPokemonType.WATER, FormSetSquirtleSquad.FORM_SET),
	BLASTOISE(EnumPokemonType.WATER, FormSetSquirtleSquad.FORM_SET),
	CATERPIE(EnumPokemonType.BUG),
	METAPOD(EnumPokemonType.BUG),
	BUTTERFREE(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	WEEDLE(EnumPokemonType.BUG, EnumPokemonType.POISON),
	KAKUNA(EnumPokemonType.BUG, EnumPokemonType.POISON),
	BEEDRILL(EnumPokemonType.BUG, EnumPokemonType.POISON),
	PIDGEY(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	PIDGEOTTO(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	PIDGEOT(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	RATTATA(EnumPokemonType.NORMAL, FormSetAlolan.FORM_SET),
	RATICATE(EnumPokemonType.NORMAL, FormSetAlolan.FORM_SET),
	SPEAROW(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	FEAROW(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	EKANS(EnumPokemonType.POISON),
	ARBOK(EnumPokemonType.POISON),
	PIKACHU(EnumPokemonType.ELECTRIC, FormSetPikachuHat.FORM_SET),
	RAICHU(EnumPokemonType.ELECTRIC, FormSetRaichuHat.FORM_SET),
	SANDSHREW(EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	SANDSLASH(EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	NIDORAN_F(EnumPokemonType.POISON, "Nidoran♀", EnumGenderPossible.FEMALE),
	NIDORINA(EnumPokemonType.POISON, EnumGenderPossible.FEMALE),
	NIDOQUEEN(EnumPokemonType.POISON, EnumPokemonType.GROUND, EnumGenderPossible.FEMALE),
	NIDORAN_M(EnumPokemonType.POISON, "Nidoran♂", EnumGenderPossible.MALE),
	NIDORINO(EnumPokemonType.POISON, EnumGenderPossible.MALE),
	NIDOKING(EnumPokemonType.POISON, EnumPokemonType.GROUND, EnumGenderPossible.MALE),
	CLEFAIRY(EnumPokemonType.FAIRY),
	CLEFABLE(EnumPokemonType.FAIRY),
	VULPIX(EnumPokemonType.FIRE, FormSetAlolan.FORM_SET),
	NINETALES(EnumPokemonType.FIRE, FormSetAlolan.FORM_SET),
	JIGGLYPUFF(EnumPokemonType.NORMAL, EnumPokemonType.FAIRY),
	WIGGLYTUFF(EnumPokemonType.NORMAL, EnumPokemonType.FAIRY),
	ZUBAT(EnumPokemonType.POISON, EnumPokemonType.FLYING),
	GOLBAT(EnumPokemonType.POISON, EnumPokemonType.FLYING),
	ODDISH(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	GLOOM(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	VILEPLUME(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	PARAS(EnumPokemonType.BUG, EnumPokemonType.GRASS),
	PARASECT(EnumPokemonType.BUG, EnumPokemonType.GRASS),
	VENONAT(EnumPokemonType.BUG, EnumPokemonType.POISON),
	VENOMOTH(EnumPokemonType.BUG, EnumPokemonType.POISON),
	DIGLETT(EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	DUGTRIO(EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	MEOWTH(EnumPokemonType.NORMAL, FormSetAlolan.FORM_SET),
	PERSIAN(EnumPokemonType.NORMAL, FormSetAlolan.FORM_SET),
	PSYDUCK(EnumPokemonType.WATER),
	GOLDUCK(EnumPokemonType.WATER),
	MANKEY(EnumPokemonType.FIGHTING),
	PRIMEAPE(EnumPokemonType.FIGHTING),
	GROWLITHE(EnumPokemonType.FIRE),
	ARCANINE(EnumPokemonType.FIRE),
	POLIWAG(EnumPokemonType.WATER),
	POLIWHIRL(EnumPokemonType.WATER),
	POLIWRATH(EnumPokemonType.WATER, EnumPokemonType.FIGHTING),
	ABRA(EnumPokemonType.PSYCHIC),
	KADABRA(EnumPokemonType.PSYCHIC),
	ALAKAZAM(EnumPokemonType.PSYCHIC),
	MACHOP(EnumPokemonType.FIGHTING),
	MACHOKE(EnumPokemonType.FIGHTING),
	MACHAMP(EnumPokemonType.FIGHTING),
	BELLSPROUT(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	WEEPINBELL(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	VICTREEBEL(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	TENTACOOL(EnumPokemonType.WATER, EnumPokemonType.POISON),
	TENACRUEL(EnumPokemonType.WATER, EnumPokemonType.POISON),
	GEODUDE(EnumPokemonType.ROCK, EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	GRAVELER(EnumPokemonType.ROCK, EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	GOLEM(EnumPokemonType.ROCK, EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	PONYTA(EnumPokemonType.FIRE),
	RAPIDASH(EnumPokemonType.FIRE),
	SOWPOKE(EnumPokemonType.WATER, EnumPokemonType.PSYCHIC),
	SLOWBRO(EnumPokemonType.WATER, EnumPokemonType.PSYCHIC),
	MAGNEMITE(EnumPokemonType.ELECTRIC, EnumPokemonType.STEEL, EnumGenderPossible.UNKNOWN),
	MAGNETON(EnumPokemonType.ELECTRIC, EnumPokemonType.STEEL, EnumGenderPossible.UNKNOWN),
	FARFETCHD(EnumPokemonType.NORMAL, EnumPokemonType.FLYING, "Farfetch'd"),
	DODUO(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	DODRIO(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	SEEL(EnumPokemonType.WATER),
	DEWGONG(EnumPokemonType.WATER, EnumPokemonType.ICE),
	GRIMER(EnumPokemonType.POISON, FormSetAlolan.FORM_SET),
	MUK(EnumPokemonType.POISON, FormSetAlolan.FORM_SET),
	SHELLDER(EnumPokemonType.WATER),
	CLOYSTER(EnumPokemonType.WATER, EnumPokemonType.ICE),
	GASTLY(EnumPokemonType.GHOST, EnumPokemonType.POISON),
	HAUNTER(EnumPokemonType.GHOST, EnumPokemonType.POISON),
	GENGAR(EnumPokemonType.GHOST, EnumPokemonType.POISON),
	ONIX(EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	DROWZEE(EnumPokemonType.PSYCHIC),
	HYPNO(EnumPokemonType.PSYCHIC),
	KRABBY(EnumPokemonType.WATER),
	KINGLER(EnumPokemonType.WATER),
	VOLTORB(EnumPokemonType.ELECTRIC, EnumGenderPossible.UNKNOWN),
	ELECTRODE(EnumPokemonType.ELECTRIC, EnumGenderPossible.UNKNOWN),
	EXEGGCUTE(EnumPokemonType.GRASS, EnumPokemonType.PSYCHIC),
	EXEGGUTOR(EnumPokemonType.GRASS, EnumPokemonType.PSYCHIC, FormSetAlolan.FORM_SET),
	CUBONE(EnumPokemonType.GROUND),
	MAROWAK(EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	HITMONLEE(EnumPokemonType.FIGHTING, EnumGenderPossible.MALE),
	HITMONCHAN(EnumPokemonType.FIGHTING, EnumGenderPossible.MALE),
	LICKITUNG(EnumPokemonType.NORMAL),
	KOFFING(EnumPokemonType.POISON),
	WEEZING(EnumPokemonType.POISON),
	RHYHORN(EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	RHYDON(EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	CHANSEY(EnumPokemonType.NORMAL, EnumGenderPossible.FEMALE),
	TANGELA(EnumPokemonType.GRASS),
	KANGASKHAN(EnumPokemonType.NORMAL, EnumGenderPossible.FEMALE),
	HORSEA(EnumPokemonType.WATER),
	SEADRA(EnumPokemonType.WATER),
	GOLDEEN(EnumPokemonType.WATER),
	SEAKING(EnumPokemonType.WATER),
	STARYU(EnumPokemonType.WATER, EnumGenderPossible.UNKNOWN),
	STARMIE(EnumPokemonType.WATER, EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	MR_MIME(EnumPokemonType.PSYCHIC, "Mr. Mime"),
	SCYTHER(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	JYNX(EnumPokemonType.ICE, EnumPokemonType.PSYCHIC, EnumGenderPossible.FEMALE),
	ELECTABUZZ(EnumPokemonType.ELECTRIC),
	MAGMAR(EnumPokemonType.FIRE),
	PINSIR(EnumPokemonType.BUG),
	TAUROS(EnumPokemonType.NORMAL, EnumGenderPossible.MALE),
	MAGIKARP(EnumPokemonType.WATER),
	GYARADOS(EnumPokemonType.WATER, EnumPokemonType.FLYING),
	LAPRAS(EnumPokemonType.WATER, EnumPokemonType.ICE),
	DITTO(EnumPokemonType.NORMAL),
	EEVEE(EnumPokemonType.NORMAL),
	VAPOREON(EnumPokemonType.WATER),
	JOLTEON(EnumPokemonType.ELECTRIC),
	FLAREON(EnumPokemonType.FIRE),
	PORYGON(EnumPokemonType.NORMAL, EnumGenderPossible.UNKNOWN),
	OMANYTE(EnumPokemonType.ROCK, EnumPokemonType.WATER),
	OMASTAR(EnumPokemonType.ROCK, EnumPokemonType.WATER),
	KABUTO(EnumPokemonType.ROCK, EnumPokemonType.WATER),
	KABUTOPS(EnumPokemonType.ROCK, EnumPokemonType.WATER),
	AERODACTYL(EnumPokemonType.ROCK, EnumPokemonType.FLYING),
	SNORLAX(EnumPokemonType.NORMAL),
	ARTICUNO(EnumPokemonType.ICE, EnumPokemonType.FLYING, EnumGenderPossible.UNKNOWN),
	ZAPDOS(EnumPokemonType.ICE, EnumPokemonType.FLYING, EnumGenderPossible.UNKNOWN),
	MOLTRES(EnumPokemonType.ICE, EnumPokemonType.FLYING, EnumGenderPossible.UNKNOWN),
	DRATINI(EnumPokemonType.DRAGON),
	DRAGONAIR(EnumPokemonType.DRAGON),
	DRAGONITE(EnumPokemonType.DRAGON, EnumPokemonType.FLYING),
	MEWTWO(EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	MEW(EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	// GEN 2
	CHIKORITA(EnumPokemonType.GRASS),
	BAYLEEF(EnumPokemonType.GRASS),
	MEGANIUM(EnumPokemonType.GRASS),
	CYNDAQUIL(EnumPokemonType.FIRE),
	QUILAVA(EnumPokemonType.FIRE),
	TYPHLOSION(EnumPokemonType.FIRE),
	TOTODILE(EnumPokemonType.WATER),
	CROCONAW(EnumPokemonType.WATER),
	FERALIGATR(EnumPokemonType.WATER),
	SENTRET(EnumPokemonType.NORMAL),
	FURRET(EnumPokemonType.NORMAL),
	HOOTHOOT(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	NOCTOWL(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	LEDYBA(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	LEDIAN(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	SPINARAK(EnumPokemonType.BUG, EnumPokemonType.POISON),
	ARIADOS(EnumPokemonType.BUG, EnumPokemonType.POISON),
	CROBAT(EnumPokemonType.POISON, EnumPokemonType.FLYING),
	CHINCHOU(EnumPokemonType.WATER, EnumPokemonType.ELECTRIC),
	LANTURN(EnumPokemonType.WATER, EnumPokemonType.ELECTRIC),
	PICHU(EnumPokemonType.ELECTRIC, FormSetPichuHat.FORM_SET),
	CLEFFA(EnumPokemonType.FAIRY),
	IGGLYBUFF(EnumPokemonType.NORMAL, EnumPokemonType.FAIRY),
	TOGEPI(EnumPokemonType.FAIRY),
	TOGETIC(EnumPokemonType.FAIRY, EnumPokemonType.FLYING),
	NATU(EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING),
	XATU(EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING),
	MAREEP(EnumPokemonType.ELECTRIC),
	FLAAFFY(EnumPokemonType.ELECTRIC),
	AMPHAROS(EnumPokemonType.ELECTRIC),
	BELLOSSOM(EnumPokemonType.GRASS),
	MARILL(EnumPokemonType.WATER, EnumPokemonType.FAIRY),
	AZUMARILL(EnumPokemonType.WATER, EnumPokemonType.FAIRY),
	SUDOWOODO(EnumPokemonType.ROCK),
	POLITOED(EnumPokemonType.WATER),
	HOPPIP(EnumPokemonType.GRASS, EnumPokemonType.FLYING),
	SKIPLOOM(EnumPokemonType.GRASS, EnumPokemonType.FLYING),
	JUMPLUFF(EnumPokemonType.GRASS, EnumPokemonType.FLYING),
	APIOM(EnumPokemonType.NORMAL),
	SUNKERN(EnumPokemonType.GRASS),
	SUNFLORA(EnumPokemonType.GRASS),
	YANMA(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	WOOPER(EnumPokemonType.WATER, EnumPokemonType.GROUND),
	QUAGSIRE(EnumPokemonType.WATER, EnumPokemonType.GROUND),
	ESPEON(EnumPokemonType.PSYCHIC),
	UMBREON(EnumPokemonType.DARK),
	MURKROW(EnumPokemonType.DARK, EnumPokemonType.FLYING),
	SLOWKING(EnumPokemonType.WATER, EnumPokemonType.PSYCHIC),
	MISDREAVUS(EnumPokemonType.GHOST),
	UNOWN(EnumPokemonType.PSYCHIC, FormSetUnown.FORM_SET, EnumGenderPossible.UNKNOWN),
	WOBBUFFET(EnumPokemonType.PSYCHIC),
	GIRAFARIG(EnumPokemonType.PSYCHIC),
	PINECO(EnumPokemonType.BUG),
	FORRETRESS(EnumPokemonType.BUG, EnumPokemonType.STEEL),
	DUNSPARCE(EnumPokemonType.NORMAL),
	GLIGAR(EnumPokemonType.GROUND, EnumPokemonType.FLYING),
	STEELIX(EnumPokemonType.STEEL, EnumPokemonType.GROUND),
	SNUBBULL(EnumPokemonType.FAIRY),
	GRANBULL(EnumPokemonType.FAIRY),
	QUILFISH(EnumPokemonType.WATER, EnumPokemonType.POISON),
	SCIZOR(EnumPokemonType.BUG, EnumPokemonType.STEEL),
	SHUCKLE(EnumPokemonType.BUG, EnumPokemonType.ROCK),
	HERACROSS(EnumPokemonType.BUG, EnumPokemonType.FIGHTING),
	SNEASEL(EnumPokemonType.DARK, EnumPokemonType.ICE),
	TEDDIURSA(EnumPokemonType.NORMAL),
	URSARING(EnumPokemonType.NORMAL),
	SLUGMA(EnumPokemonType.FIRE),
	MAGCARGO(EnumPokemonType.FIRE, EnumPokemonType.ROCK),
	SWINUB(EnumPokemonType.ICE, EnumPokemonType.GROUND),
	PINOSWINE(EnumPokemonType.ICE, EnumPokemonType.GROUND),
	CORSOLA(EnumPokemonType.WATER, EnumPokemonType.ROCK),
	REMORIAD(EnumPokemonType.WATER),
	OCTILLERY(EnumPokemonType.WATER),
	DELIBIRD(EnumPokemonType.ICE, EnumPokemonType.FLYING),
	MANTINE(EnumPokemonType.WATER, EnumPokemonType.FLYING),
	SKARMORY(EnumPokemonType.STEEL, EnumPokemonType.FLYING),
	HOUNDOUR(EnumPokemonType.DARK, EnumPokemonType.FIRE),
	HOUNDOOM(EnumPokemonType.DARK, EnumPokemonType.FIRE),
	KINGDRA(EnumPokemonType.WATER, EnumPokemonType.DRAGON),
	PHANPY(EnumPokemonType.GROUND),
	DONPHAN(EnumPokemonType.GROUND),
	PORYGON2(EnumPokemonType.NORMAL, EnumGenderPossible.UNKNOWN),
	STANTLER(EnumPokemonType.NORMAL),
	SMEARGLE(EnumPokemonType.NORMAL),
	TYROGUE(EnumPokemonType.FIGHTING, EnumGenderPossible.MALE),
	HITMONTOP(EnumPokemonType.FIGHTING, EnumGenderPossible.MALE),
	SMOOCHUM(EnumPokemonType.ICE, EnumPokemonType.PSYCHIC, EnumGenderPossible.FEMALE),
	ELEKID(EnumPokemonType.ELECTRIC),
	MAGBY(EnumPokemonType.FIRE),
	MILTANK(EnumPokemonType.NORMAL, EnumGenderPossible.FEMALE),
	BLISSEY(EnumPokemonType.NORMAL, EnumGenderPossible.FEMALE),
	RAIKOU(EnumPokemonType.ELECTRIC, EnumGenderPossible.UNKNOWN),
	ENTEI(EnumPokemonType.FIRE, EnumGenderPossible.UNKNOWN),
	SUICUNE(EnumPokemonType.WATER, EnumGenderPossible.UNKNOWN),
	LARVITAR(EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	PUPITAR(EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	TYRANITAR(EnumPokemonType.ROCK, EnumPokemonType.DARK),
	LUGIA(EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING, EnumGenderPossible.UNKNOWN),
	HO_OH(EnumPokemonType.WATER, EnumPokemonType.FLYING, "Ho-Oh", EnumGenderPossible.UNKNOWN),
	CELEBI(EnumPokemonType.PSYCHIC, EnumPokemonType.GRASS),
	// GEN 3
	TREECKO(EnumPokemonType.GRASS),
	GROVYLE(EnumPokemonType.GRASS),
	SCEPTILE(EnumPokemonType.GRASS),
	TORCHIC(EnumPokemonType.FIRE),
	COMBUSKEN(EnumPokemonType.FIRE, EnumPokemonType.FIGHTING),
	BLAZIKEN(EnumPokemonType.FIRE, EnumPokemonType.FIGHTING),
	MUDKIP(EnumPokemonType.WATER),
	MARSHTOMP(EnumPokemonType.WATER, EnumPokemonType.GROUND),
	SWAMPERT(EnumPokemonType.WATER, EnumPokemonType.GROUND),
	POOCHYENA(EnumPokemonType.DARK),
	MIGHTYENA(EnumPokemonType.DARK),
	ZIGZAGOON(EnumPokemonType.NORMAL),
	LINOONE(EnumPokemonType.NORMAL),
	WURMPLE(EnumPokemonType.BUG),
	SILCOON(EnumPokemonType.BUG),
	BEAUTIFLY(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	CASCOON(EnumPokemonType.BUG),
	DUSTOX(EnumPokemonType.BUG, EnumPokemonType.POISON),
	LOTAD(EnumPokemonType.WATER, EnumPokemonType.GRASS),
	LOMBRE(EnumPokemonType.WATER, EnumPokemonType.GRASS),
	LUDICOLO(EnumPokemonType.WATER, EnumPokemonType.GRASS),
	SEEDOT(EnumPokemonType.GRASS),
	NUZLEAF(EnumPokemonType.GRASS, EnumPokemonType.DARK),
	SHIFTRY(EnumPokemonType.GRASS, EnumPokemonType.DARK),
	TAILLOW(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	SWELLOW(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	WINGULL(EnumPokemonType.WATER, EnumPokemonType.FLYING),
	PELIPPER(EnumPokemonType.WATER, EnumPokemonType.FLYING),
	RALTS(EnumPokemonType.PSYCHIC, EnumPokemonType.FAIRY),
	KIRLIA(EnumPokemonType.PSYCHIC, EnumPokemonType.FAIRY),
	GARDEVOIR(EnumPokemonType.PSYCHIC, EnumPokemonType.FAIRY),
	SURSKIT(EnumPokemonType.BUG, EnumPokemonType.WATER),
	MASQUERAIN(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	SHROOMISH(EnumPokemonType.GRASS),
	BRELOOM(EnumPokemonType.GRASS),
	SLAKOTH(EnumPokemonType.NORMAL),
	VIGOROTH(EnumPokemonType.NORMAL),
	SLAKING(EnumPokemonType.NORMAL),
	NINCADA(EnumPokemonType.BUG, EnumPokemonType.GROUND),
	NINJASK(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	SHEDINJA(EnumPokemonType.BUG, EnumPokemonType.GHOST, EnumGenderPossible.UNKNOWN),
	WHISMUR(EnumPokemonType.NORMAL),
	LOUDRED(EnumPokemonType.NORMAL),
	EXPLOUD(EnumPokemonType.NORMAL),
	MAKUHITA(EnumPokemonType.FIGHTING),
	HARIYAMA(EnumPokemonType.FIGHTING),
	AZURILL(EnumPokemonType.NORMAL, EnumPokemonType.FAIRY),
	NOSEPASS(EnumPokemonType.ROCK),
	SKITTY(EnumPokemonType.NORMAL),
	DELCATTY(EnumPokemonType.NORMAL),
	SABLEYE(EnumPokemonType.DARK, EnumPokemonType.GHOST),
	MAWILE(EnumPokemonType.STEEL, EnumPokemonType.FAIRY),
	ARON(EnumPokemonType.STEEL, EnumPokemonType.ROCK),
	LAIRON(EnumPokemonType.STEEL, EnumPokemonType.ROCK),
	AGGRON(EnumPokemonType.STEEL, EnumPokemonType.ROCK),
	MEDITITE(EnumPokemonType.FIGHTING, EnumPokemonType.PSYCHIC),
	MEDICHAM(EnumPokemonType.FIGHTING, EnumPokemonType.PSYCHIC),
	ELECTRIKE(EnumPokemonType.ELECTRIC),
	MANECTRIC(EnumPokemonType.ELECTRIC),
	PLUSLE(EnumPokemonType.ELECTRIC),
	MINUN(EnumPokemonType.ELECTRIC),
	VOLBEAT(EnumPokemonType.BUG, EnumGenderPossible.MALE),
	ILLUMISE(EnumPokemonType.BUG, EnumGenderPossible.FEMALE),
	ROSELIA(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	GULPIN(EnumPokemonType.POISON),
	SWALOT(EnumPokemonType.POISON),
	CARVANHA(EnumPokemonType.WATER, EnumPokemonType.DARK),
	SHARKPADE(EnumPokemonType.WATER, EnumPokemonType.DARK),
	WAILMER(EnumPokemonType.WATER),
	WAILORD(EnumPokemonType.WATER),
	NUMEL(EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	CAMERUPT(EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	TORKOAL(EnumPokemonType.ROCK),
	SPOINK(EnumPokemonType.PSYCHIC),
	GRUMPIG(EnumPokemonType.PSYCHIC),
	SPINDA(EnumPokemonType.NORMAL, FormSetSpinda.FORM_SET),
	TRAPINCH(EnumPokemonType.GROUND),
	VIBRAVA(EnumPokemonType.GROUND, EnumPokemonType.DRAGON),
	FLYGON(EnumPokemonType.GROUND, EnumPokemonType.DRAGON),
	CACNEA(EnumPokemonType.GRASS),
	CACTURNE(EnumPokemonType.GRASS, EnumPokemonType.DARK),
	SWABLU(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	ALTARIA(EnumPokemonType.DRAGON, EnumPokemonType.FLYING),
	ZANGOOSE(EnumPokemonType.NORMAL),
	SEVIPER(EnumPokemonType.POISON),
	LUNATONE(EnumPokemonType.ROCK, EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	SOLROCK(EnumPokemonType.ROCK, EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	BARBOACH(EnumPokemonType.WATER, EnumPokemonType.GROUND),
	WHISCASH(EnumPokemonType.WATER, EnumPokemonType.GROUND),
	CORPHISH(EnumPokemonType.WATER),
	CRAWDAUNT(EnumPokemonType.WATER, EnumPokemonType.DARK),
	BALTOY(EnumPokemonType.GROUND, EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	CLAYDOL(EnumPokemonType.GROUND, EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	LILEEP(EnumPokemonType.ROCK, EnumPokemonType.GRASS),
	CRADILY(EnumPokemonType.ROCK, EnumPokemonType.GRASS),
	ANORITH(EnumPokemonType.ROCK, EnumPokemonType.BUG),
	ARMALDO(EnumPokemonType.ROCK, EnumPokemonType.BUG),
	FEEBAS(EnumPokemonType.WATER),
	MILOTIC(EnumPokemonType.WATER),
	CASTFORM(EnumPokemonType.NORMAL, FormSetCastform.FORM_SET),
	KECLEON(EnumPokemonType.NORMAL),
	SHUPPET(EnumPokemonType.GHOST),
	BANETTE(EnumPokemonType.GHOST),
	DUSKULL(EnumPokemonType.GHOST),
	DUSCLOPS(EnumPokemonType.GHOST),
	TROPIUS(EnumPokemonType.GRASS, EnumPokemonType.FLYING),
	CHIMECHO(EnumPokemonType.PSYCHIC),
	ABSOL(EnumPokemonType.DARK),
	WYNAUT(EnumPokemonType.PSYCHIC),
	SNORUNT(EnumPokemonType.ICE),
	GLALIE(EnumPokemonType.ICE),
	SPHEAL(EnumPokemonType.ICE, EnumPokemonType.WATER),
	SEALEO(EnumPokemonType.ICE, EnumPokemonType.WATER),
	WALREIN(EnumPokemonType.ICE, EnumPokemonType.WATER),
	CLAMPERL(EnumPokemonType.WATER),
	HUNTAIL(EnumPokemonType.WATER),
	GOREBYSS(EnumPokemonType.WATER),
	RELICANTH(EnumPokemonType.WATER, EnumPokemonType.ROCK),
	LUVDISC(EnumPokemonType.WATER),
	BAGON(EnumPokemonType.DRAGON),
	SHELGON(EnumPokemonType.DRAGON),
	SALAMENCE(EnumPokemonType.DRAGON, EnumPokemonType.FLYING),
	BELDUM(EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	METANG(EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	METAGROSS(EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	REGIROCK(EnumPokemonType.ROCK, EnumGenderPossible.UNKNOWN),
	REGICE(EnumPokemonType.ICE, EnumGenderPossible.UNKNOWN),
	REGISTEEL(EnumPokemonType.STEEL, EnumGenderPossible.UNKNOWN),
	LATIAS(EnumPokemonType.DRAGON, EnumPokemonType.PSYCHIC, EnumGenderPossible.FEMALE),
	LATIOS(EnumPokemonType.DRAGON, EnumPokemonType.PSYCHIC, EnumGenderPossible.MALE),
	KYOGRE(EnumPokemonType.WATER, EnumGenderPossible.UNKNOWN),
	GROUDON(EnumPokemonType.GROUND, EnumGenderPossible.UNKNOWN),
	RAYQUAZA(EnumPokemonType.DRAGON, EnumPokemonType.FLYING, EnumGenderPossible.UNKNOWN),
	JIRACHI(EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	DEOXYS(EnumPokemonType.PSYCHIC, FormSetDeoxys.FORM_SET, EnumGenderPossible.UNKNOWN),
	// GEN 4
	TURTWIG(EnumPokemonType.GRASS),
	GROTLE(EnumPokemonType.GRASS),
	TORTERRA(EnumPokemonType.GRASS, EnumPokemonType.GROUND),
	CHIMCHAR(EnumPokemonType.FIRE),
	MONFERNO(EnumPokemonType.FIRE, EnumPokemonType.FIGHTING),
	INFERNAPE(EnumPokemonType.FIRE, EnumPokemonType.FIGHTING),
	PIPLUP(EnumPokemonType.WATER),
	PRINPLUP(EnumPokemonType.WATER),
	EMPOLEON(EnumPokemonType.WATER, EnumPokemonType.STEEL),
	STARLY(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	STARAVIA(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	STARAPTOR(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	BIDOOF(EnumPokemonType.NORMAL),
	BIBAREL(EnumPokemonType.NORMAL, EnumPokemonType.WATER),
	KRICKETOT(EnumPokemonType.BUG),
	KRICKETUNE(EnumPokemonType.BUG),
	SHINX(EnumPokemonType.ELECTRIC),
	LUXIO(EnumPokemonType.ELECTRIC),
	LUXRAY(EnumPokemonType.ELECTRIC),
	BUDEW(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	ROSERADE(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	CRANIDOS(EnumPokemonType.ROCK),
	RAMPARDOS(EnumPokemonType.ROCK),
	SHIELDON(EnumPokemonType.ROCK, EnumPokemonType.STEEL),
	BASTIODON(EnumPokemonType.ROCK, EnumPokemonType.STEEL),
	BURMY(EnumPokemonType.BUG, FormSetBurmyFamily.FORM_SET),
	WORMADAM(	EnumPokemonType.BUG,
				EnumPokemonType.GRASS,
				FormSetBurmyFamily.FORM_SET,
				EnumGenderPossible.FEMALE),
	MOTHIM(EnumPokemonType.BUG, EnumPokemonType.FLYING, EnumGenderPossible.MALE),
	COMBEE(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	VESPIQUEN(EnumPokemonType.BUG, EnumPokemonType.FLYING, EnumGenderPossible.FEMALE),
	PACHIRISU(EnumPokemonType.ELECTRIC),
	BUIZEL(EnumPokemonType.WATER),
	FLOATZEL(EnumPokemonType.WATER),
	CHERUBI(EnumPokemonType.GRASS),
	CHERRIM(EnumPokemonType.GRASS), // TODO: Add form if necessary later
	SHELLOS(EnumPokemonType.WATER, EnumPokemonType.GROUND, FormSetShellosFamily.FORM_SET),
	GASTRODON(EnumPokemonType.WATER, EnumPokemonType.GROUND, FormSetShellosFamily.FORM_SET),
	AMBIPOM(EnumPokemonType.NORMAL),
	DRIFLOON(EnumPokemonType.GHOST, EnumPokemonType.FLYING),
	DRIFBLIM(EnumPokemonType.GHOST, EnumPokemonType.FLYING),
	BUNEARY(EnumPokemonType.NORMAL),
	LOPUNNY(EnumPokemonType.NORMAL),
	MISMAGIUS(EnumPokemonType.GHOST),
	HONCHKROW(EnumPokemonType.DARK, EnumPokemonType.FLYING),
	GLAMEOW(EnumPokemonType.NORMAL),
	PURUGLY(EnumPokemonType.NORMAL),
	CHINGLING(EnumPokemonType.PSYCHIC),
	STUNKY(EnumPokemonType.POISON, EnumPokemonType.DARK),
	SKUNTANK(EnumPokemonType.POISON, EnumPokemonType.DARK),
	BRONZOR(EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	BRONZONG(EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	BONSLY(EnumPokemonType.ROCK),
	MIME_JR(EnumPokemonType.PSYCHIC, EnumPokemonType.FAIRY, "Mime Jr."),
	HAPPINY(EnumPokemonType.NORMAL, EnumGenderPossible.FEMALE),
	CHATOT(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	SPIRITOMB(EnumPokemonType.GHOST, EnumPokemonType.DARK),
	GIBLE(EnumPokemonType.DRAGON, EnumPokemonType.GROUND),
	GABITE(EnumPokemonType.DRAGON, EnumPokemonType.GROUND),
	GARCHOMP(EnumPokemonType.DRAGON, EnumPokemonType.GROUND),
	MUNCHLAX(EnumPokemonType.NORMAL),
	RIOLU(EnumPokemonType.FIGHTING),
	LUCARIO(EnumPokemonType.FIGHTING, EnumPokemonType.STEEL),
	HIPPOPOTAS(EnumPokemonType.GROUND),
	HIPPOWDON(EnumPokemonType.GROUND),
	SKORUPI(EnumPokemonType.POISON, EnumPokemonType.BUG),
	DRAPION(EnumPokemonType.POISON, EnumPokemonType.DARK),
	CROAGUNK(EnumPokemonType.DRAGON, EnumPokemonType.FIGHTING),
	TOXICROAK(EnumPokemonType.DRAGON, EnumPokemonType.FIGHTING),
	CARNIVINE(EnumPokemonType.GRASS),
	GINNEON(EnumPokemonType.WATER),
	LUMINEON(EnumPokemonType.WATER),
	MANTYKE(EnumPokemonType.WATER, EnumPokemonType.FLYING),
	SNOVER(EnumPokemonType.GRASS, EnumPokemonType.ICE),
	ABOMASNOW(EnumPokemonType.GRASS, EnumPokemonType.ICE),
	WEAVILE(EnumPokemonType.DARK, EnumPokemonType.ICE),
	MAGNEZONE(EnumPokemonType.ELECTRIC, EnumPokemonType.STEEL, EnumGenderPossible.UNKNOWN),
	LICKILICKY(EnumPokemonType.NORMAL),
	RHYPERIOR(EnumPokemonType.GROUND, EnumPokemonType.ROCK),
	TANGROWTH(EnumPokemonType.GRASS),
	ELECTIVIRE(EnumPokemonType.ELECTRIC),
	MAGMORTAR(EnumPokemonType.FIRE),
	TOGEKISS(EnumPokemonType.FAIRY, EnumPokemonType.FLYING),
	YANMEGA(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	LEAFEON(EnumPokemonType.GRASS),
	GLACEON(EnumPokemonType.ICE),
	GLISCOR(EnumPokemonType.GROUND, EnumPokemonType.FLYING),
	MAMOSWINE(EnumPokemonType.ICE, EnumPokemonType.GROUND),
	PORYGON_Z(EnumPokemonType.NORMAL, "Porygon-Z", EnumGenderPossible.UNKNOWN),
	GALLADE(EnumPokemonType.PSYCHIC, EnumPokemonType.FIGHTING, EnumGenderPossible.MALE),
	PROBOPASS(EnumPokemonType.ROCK, EnumPokemonType.STEEL),
	DUSKNOIR(EnumPokemonType.GHOST),
	FROSLASS(EnumPokemonType.ICE, EnumPokemonType.GHOST, EnumGenderPossible.FEMALE),
	ROTOM(	EnumPokemonType.ELECTRIC,
			EnumPokemonType.GHOST,
			FormSetRotom.FORM_SET,
			EnumGenderPossible.UNKNOWN),
	UXIE(EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	MESPRIT(EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	AZELF(EnumPokemonType.PSYCHIC, EnumGenderPossible.UNKNOWN),
	DIALGA(EnumPokemonType.STEEL, EnumPokemonType.DRAGON, EnumGenderPossible.UNKNOWN),
	PALKIA(EnumPokemonType.WATER, EnumPokemonType.DRAGON, EnumGenderPossible.UNKNOWN),
	HEATRAN(EnumPokemonType.FIRE, EnumPokemonType.STEEL),
	REGIGIGAS(EnumPokemonType.NORMAL, EnumGenderPossible.UNKNOWN),
	GIRATINA(	EnumPokemonType.GHOST,
				EnumPokemonType.DRAGON,
				FormSetGiratina.FORM_SET,
				EnumGenderPossible.UNKNOWN),
	CRESSELIA(EnumPokemonType.PSYCHIC, EnumGenderPossible.FEMALE),
	PHIONE(EnumPokemonType.WATER, EnumGenderPossible.UNKNOWN),
	MANAPHY(EnumPokemonType.WATER, EnumGenderPossible.UNKNOWN),
	DARKRAI(EnumPokemonType.DARK, EnumGenderPossible.UNKNOWN),
	SHAYMIN(EnumPokemonType.GRASS, EnumGenderPossible.UNKNOWN),
	ARCEUS(EnumPokemonType.NORMAL, FormSetArceus.FORM_SET, EnumGenderPossible.UNKNOWN);

	private String name;
	private EnumPokemonType type1;
	private EnumPokemonType type2;
	private FormSet forms;
	private EnumGenderPossible gender;

	EnumPokemon(EnumPokemonType type1) {
		this(type1, (EnumPokemonType) null);
	}

	EnumPokemon(EnumPokemonType type1, String name) {
		this(type1, (EnumPokemonType) null, name);
	}

	EnumPokemon(EnumPokemonType type1, String name, EnumGenderPossible gender) {
		this(type1, (EnumPokemonType) null, name, gender);
	}

	EnumPokemon(EnumPokemonType type1, FormSet formSet) {
		this(type1, (EnumPokemonType) null, formSet);
	}

	EnumPokemon(EnumPokemonType type1, FormSet formSet, EnumGenderPossible gender) {
		this(type1, (EnumPokemonType) null, formSet, gender);
	}

	EnumPokemon(EnumPokemonType type1, String name, FormSet formSet) {
		this(type1, (EnumPokemonType) null, name, formSet);
	}

	EnumPokemon(EnumPokemonType type1, String name, FormSet formSet, EnumGenderPossible gender) {
		this(type1, (EnumPokemonType) null, name, formSet);
	}

	EnumPokemon(EnumPokemonType type1, EnumGenderPossible gender) {
		this(type1, (EnumPokemonType) null, gender);
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2) {
		name = this.name();
		name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase();
		this.type1 = type1;
		this.type2 = type2;
		this.gender = EnumGenderPossible.EITHER;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, String name) {
		this.name = name;
		this.type1 = type1;
		this.type2 = type2;
		this.gender = EnumGenderPossible.EITHER;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, String name, EnumGenderPossible gender) {
		this(type1, type2, name);
		this.gender = gender;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, FormSet formSet) {
		this(type1, type2);
		this.forms = formSet;
		this.gender = EnumGenderPossible.EITHER;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, FormSet formSet,
			EnumGenderPossible gender) {
		this(type1, type2, formSet);
		this.gender = gender;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, String name, FormSet formSet) {
		this(type1, type2, name);
		this.forms = formSet;
		this.gender = EnumGenderPossible.EITHER;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, String name, FormSet formSet,
			EnumGenderPossible gender) {
		this(type1, type2, name, formSet);
		this.gender = gender;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, EnumGenderPossible gender) {
		this(type1, type2);
		this.gender = gender;
	}

	public String getName() {
		return this.name;
	}

	public EnumPokemonType getType1() {
		return this.type1;
	}

	public EnumPokemonType getType2() {
		return this.type2;
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
		id--;
		if (id < 0 || id > values().length - 1)
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
		for (EnumPokemon p : EnumPokemon.values()) {
			// for (int i = 0; i < FormManager.MAX_DEX; i++) {
			// EnumPokemon p = EnumPokemon.values()[i];
			int genderMult = p.getGenderPossible() == EnumGenderPossible.EITHER ? 2 : 1;
			int shinyMult = FormManager.isShinyable(p) ? 2 : 1;
			space += ((p.getFormSet() == null ? 1 : p.getFormSet().getNumForms()) * genderMult
					* shinyMult);
		}
		System.out.println("You need " + space + " bag space to hold all forms");

		// for (EnumPokemon p : EnumPokemon.values())
		// if (FormManager.isShinyable(p.getId()))
		// System.out.println(p + " is shinyable");
	}
}
