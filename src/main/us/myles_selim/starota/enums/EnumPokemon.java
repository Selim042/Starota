package us.myles_selim.starota.enums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.trading.forms.FormSet;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.trading.forms.FormSetAlolan;
import us.myles_selim.starota.trading.forms.FormSetArceus;
import us.myles_selim.starota.trading.forms.FormSetBurmyFamily;
import us.myles_selim.starota.trading.forms.FormSetCastform;
import us.myles_selim.starota.trading.forms.FormSetDeoxys;
import us.myles_selim.starota.trading.forms.FormSetEevee;
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
	NIDORAN_F(EnumPokemonType.POISON, "Nidoran♀", EnumGender.FEMALE),
	NIDORINA(EnumPokemonType.POISON, EnumGender.FEMALE),
	NIDOQUEEN(EnumPokemonType.POISON, EnumPokemonType.GROUND, EnumGender.FEMALE),
	NIDORAN_M(EnumPokemonType.POISON, "Nidoran♂", EnumGender.MALE),
	NIDORINO(EnumPokemonType.POISON, EnumGender.MALE),
	NIDOKING(EnumPokemonType.POISON, EnumPokemonType.GROUND, EnumGender.MALE),
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
	SLOWPOKE(EnumPokemonType.WATER, EnumPokemonType.PSYCHIC),
	SLOWBRO(EnumPokemonType.WATER, EnumPokemonType.PSYCHIC),
	MAGNEMITE(EnumPokemonType.ELECTRIC, EnumPokemonType.STEEL, EnumGender.UNKNOWN),
	MAGNETON(EnumPokemonType.ELECTRIC, EnumPokemonType.STEEL, EnumGender.UNKNOWN),
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
	VOLTORB(EnumPokemonType.ELECTRIC, EnumGender.UNKNOWN),
	ELECTRODE(EnumPokemonType.ELECTRIC, EnumGender.UNKNOWN),
	EXEGGCUTE(EnumPokemonType.GRASS, EnumPokemonType.PSYCHIC),
	EXEGGUTOR(EnumPokemonType.GRASS, EnumPokemonType.PSYCHIC, FormSetAlolan.FORM_SET),
	CUBONE(EnumPokemonType.GROUND),
	MAROWAK(EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	HITMONLEE(EnumPokemonType.FIGHTING, EnumGender.MALE),
	HITMONCHAN(EnumPokemonType.FIGHTING, EnumGender.MALE),
	LICKITUNG(EnumPokemonType.NORMAL),
	KOFFING(EnumPokemonType.POISON),
	WEEZING(EnumPokemonType.POISON),
	RHYHORN(EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	RHYDON(EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	CHANSEY(EnumPokemonType.NORMAL, EnumGender.FEMALE),
	TANGELA(EnumPokemonType.GRASS),
	KANGASKHAN(EnumPokemonType.NORMAL, EnumGender.FEMALE),
	HORSEA(EnumPokemonType.WATER),
	SEADRA(EnumPokemonType.WATER),
	GOLDEEN(EnumPokemonType.WATER),
	SEAKING(EnumPokemonType.WATER),
	STARYU(EnumPokemonType.WATER, EnumGender.UNKNOWN),
	STARMIE(EnumPokemonType.WATER, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	MR_MIME(EnumPokemonType.PSYCHIC, "Mr. Mime"),
	SCYTHER(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	JYNX(EnumPokemonType.ICE, EnumPokemonType.PSYCHIC, EnumGender.FEMALE),
	ELECTABUZZ(EnumPokemonType.ELECTRIC),
	MAGMAR(EnumPokemonType.FIRE),
	PINSIR(EnumPokemonType.BUG),
	TAUROS(EnumPokemonType.NORMAL, EnumGender.MALE),
	MAGIKARP(EnumPokemonType.WATER),
	GYARADOS(EnumPokemonType.WATER, EnumPokemonType.FLYING),
	LAPRAS(EnumPokemonType.WATER, EnumPokemonType.ICE),
	DITTO(EnumPokemonType.NORMAL),
	EEVEE(EnumPokemonType.NORMAL, FormSetEevee.FORM_SET),
	VAPOREON(EnumPokemonType.WATER, FormSetEevee.FORM_SET),
	JOLTEON(EnumPokemonType.ELECTRIC, FormSetEevee.FORM_SET),
	FLAREON(EnumPokemonType.FIRE, FormSetEevee.FORM_SET),
	PORYGON(EnumPokemonType.NORMAL, EnumGender.UNKNOWN),
	OMANYTE(EnumPokemonType.ROCK, EnumPokemonType.WATER),
	OMASTAR(EnumPokemonType.ROCK, EnumPokemonType.WATER),
	KABUTO(EnumPokemonType.ROCK, EnumPokemonType.WATER),
	KABUTOPS(EnumPokemonType.ROCK, EnumPokemonType.WATER),
	AERODACTYL(EnumPokemonType.ROCK, EnumPokemonType.FLYING),
	SNORLAX(EnumPokemonType.NORMAL),
	ARTICUNO(EnumPokemonType.ICE, EnumPokemonType.FLYING, EnumGender.UNKNOWN),
	ZAPDOS(EnumPokemonType.ELECTRIC, EnumPokemonType.FLYING, EnumGender.UNKNOWN),
	MOLTRES(EnumPokemonType.FIRE, EnumPokemonType.FLYING, EnumGender.UNKNOWN),
	DRATINI(EnumPokemonType.DRAGON),
	DRAGONAIR(EnumPokemonType.DRAGON),
	DRAGONITE(EnumPokemonType.DRAGON, EnumPokemonType.FLYING),
	MEWTWO(EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	MEW(EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
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
	AIPOM(EnumPokemonType.NORMAL),
	SUNKERN(EnumPokemonType.GRASS),
	SUNFLORA(EnumPokemonType.GRASS),
	YANMA(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	WOOPER(EnumPokemonType.WATER, EnumPokemonType.GROUND),
	QUAGSIRE(EnumPokemonType.WATER, EnumPokemonType.GROUND),
	ESPEON(EnumPokemonType.PSYCHIC, FormSetEevee.FORM_SET),
	UMBREON(EnumPokemonType.DARK, FormSetEevee.FORM_SET),
	MURKROW(EnumPokemonType.DARK, EnumPokemonType.FLYING),
	SLOWKING(EnumPokemonType.WATER, EnumPokemonType.PSYCHIC),
	MISDREAVUS(EnumPokemonType.GHOST),
	UNOWN(EnumPokemonType.PSYCHIC, FormSetUnown.FORM_SET, EnumGender.UNKNOWN),
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
	PILOSWINE(EnumPokemonType.ICE, EnumPokemonType.GROUND),
	CORSOLA(EnumPokemonType.WATER, EnumPokemonType.ROCK),
	REMORAID(EnumPokemonType.WATER),
	OCTILLERY(EnumPokemonType.WATER),
	DELIBIRD(EnumPokemonType.ICE, EnumPokemonType.FLYING),
	MANTINE(EnumPokemonType.WATER, EnumPokemonType.FLYING),
	SKARMORY(EnumPokemonType.STEEL, EnumPokemonType.FLYING),
	HOUNDOUR(EnumPokemonType.DARK, EnumPokemonType.FIRE),
	HOUNDOOM(EnumPokemonType.DARK, EnumPokemonType.FIRE),
	KINGDRA(EnumPokemonType.WATER, EnumPokemonType.DRAGON),
	PHANPY(EnumPokemonType.GROUND),
	DONPHAN(EnumPokemonType.GROUND),
	PORYGON2(EnumPokemonType.NORMAL, EnumGender.UNKNOWN),
	STANTLER(EnumPokemonType.NORMAL),
	SMEARGLE(EnumPokemonType.NORMAL),
	TYROGUE(EnumPokemonType.FIGHTING, EnumGender.MALE),
	HITMONTOP(EnumPokemonType.FIGHTING, EnumGender.MALE),
	SMOOCHUM(EnumPokemonType.ICE, EnumPokemonType.PSYCHIC, EnumGender.FEMALE),
	ELEKID(EnumPokemonType.ELECTRIC),
	MAGBY(EnumPokemonType.FIRE),
	MILTANK(EnumPokemonType.NORMAL, EnumGender.FEMALE),
	BLISSEY(EnumPokemonType.NORMAL, EnumGender.FEMALE),
	RAIKOU(EnumPokemonType.ELECTRIC, EnumGender.UNKNOWN),
	ENTEI(EnumPokemonType.FIRE, EnumGender.UNKNOWN),
	SUICUNE(EnumPokemonType.WATER, EnumGender.UNKNOWN),
	LARVITAR(EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	PUPITAR(EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	TYRANITAR(EnumPokemonType.ROCK, EnumPokemonType.DARK),
	LUGIA(EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING, EnumGender.UNKNOWN),
	HO_OH(EnumPokemonType.WATER, EnumPokemonType.FLYING, "Ho-Oh", EnumGender.UNKNOWN),
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
	SHEDINJA(EnumPokemonType.BUG, EnumPokemonType.GHOST, EnumGender.UNKNOWN),
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
	VOLBEAT(EnumPokemonType.BUG, EnumGender.MALE),
	ILLUMISE(EnumPokemonType.BUG, EnumGender.FEMALE),
	ROSELIA(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	GULPIN(EnumPokemonType.POISON),
	SWALOT(EnumPokemonType.POISON),
	CARVANHA(EnumPokemonType.WATER, EnumPokemonType.DARK),
	SHARPEDO(EnumPokemonType.WATER, EnumPokemonType.DARK),
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
	LUNATONE(EnumPokemonType.ROCK, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	SOLROCK(EnumPokemonType.ROCK, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	BARBOACH(EnumPokemonType.WATER, EnumPokemonType.GROUND),
	WHISCASH(EnumPokemonType.WATER, EnumPokemonType.GROUND),
	CORPHISH(EnumPokemonType.WATER),
	CRAWDAUNT(EnumPokemonType.WATER, EnumPokemonType.DARK),
	BALTOY(EnumPokemonType.GROUND, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	CLAYDOL(EnumPokemonType.GROUND, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
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
	BELDUM(EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	METANG(EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	METAGROSS(EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	REGIROCK(EnumPokemonType.ROCK, EnumGender.UNKNOWN),
	REGICE(EnumPokemonType.ICE, EnumGender.UNKNOWN),
	REGISTEEL(EnumPokemonType.STEEL, EnumGender.UNKNOWN),
	LATIAS(EnumPokemonType.DRAGON, EnumPokemonType.PSYCHIC, EnumGender.FEMALE),
	LATIOS(EnumPokemonType.DRAGON, EnumPokemonType.PSYCHIC, EnumGender.MALE),
	KYOGRE(EnumPokemonType.WATER, EnumGender.UNKNOWN),
	GROUDON(EnumPokemonType.GROUND, EnumGender.UNKNOWN),
	RAYQUAZA(EnumPokemonType.DRAGON, EnumPokemonType.FLYING, EnumGender.UNKNOWN),
	JIRACHI(EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	DEOXYS(EnumPokemonType.PSYCHIC, FormSetDeoxys.FORM_SET, EnumGender.UNKNOWN),
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
	WORMADAM(EnumPokemonType.BUG, EnumPokemonType.GRASS, FormSetBurmyFamily.FORM_SET, EnumGender.FEMALE),
	MOTHIM(EnumPokemonType.BUG, EnumPokemonType.FLYING, EnumGender.MALE),
	COMBEE(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	VESPIQUEN(EnumPokemonType.BUG, EnumPokemonType.FLYING, EnumGender.FEMALE),
	PACHIRISU(EnumPokemonType.ELECTRIC),
	BUIZEL(EnumPokemonType.WATER),
	FLOATZEL(EnumPokemonType.WATER),
	CHERUBI(EnumPokemonType.GRASS),
	CHERRIM(EnumPokemonType.GRASS), // TODO: Add form if necessary later
	SHELLOS(EnumPokemonType.WATER, FormSetShellosFamily.FORM_SET),
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
	BRONZOR(EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	BRONZONG(EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	BONSLY(EnumPokemonType.ROCK),
	MIME_JR(EnumPokemonType.PSYCHIC, EnumPokemonType.FAIRY, "Mime Jr."),
	HAPPINY(EnumPokemonType.NORMAL, EnumGender.FEMALE),
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
	CROAGUNK(EnumPokemonType.POISON, EnumPokemonType.FIGHTING),
	TOXICROAK(EnumPokemonType.POISON, EnumPokemonType.FIGHTING),
	CARNIVINE(EnumPokemonType.GRASS),
	FINNEON(EnumPokemonType.WATER),
	LUMINEON(EnumPokemonType.WATER),
	MANTYKE(EnumPokemonType.WATER, EnumPokemonType.FLYING),
	SNOVER(EnumPokemonType.GRASS, EnumPokemonType.ICE),
	ABOMASNOW(EnumPokemonType.GRASS, EnumPokemonType.ICE),
	WEAVILE(EnumPokemonType.DARK, EnumPokemonType.ICE),
	MAGNEZONE(EnumPokemonType.ELECTRIC, EnumPokemonType.STEEL, EnumGender.UNKNOWN),
	LICKILICKY(EnumPokemonType.NORMAL),
	RHYPERIOR(EnumPokemonType.GROUND, EnumPokemonType.ROCK),
	TANGROWTH(EnumPokemonType.GRASS),
	ELECTIVIRE(EnumPokemonType.ELECTRIC),
	MAGMORTAR(EnumPokemonType.FIRE),
	TOGEKISS(EnumPokemonType.FAIRY, EnumPokemonType.FLYING),
	YANMEGA(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	LEAFEON(EnumPokemonType.GRASS, FormSetEevee.FORM_SET),
	GLACEON(EnumPokemonType.ICE, FormSetEevee.FORM_SET),
	GLISCOR(EnumPokemonType.GROUND, EnumPokemonType.FLYING),
	MAMOSWINE(EnumPokemonType.ICE, EnumPokemonType.GROUND),
	PORYGON_Z(EnumPokemonType.NORMAL, "Porygon-Z", EnumGender.UNKNOWN),
	GALLADE(EnumPokemonType.PSYCHIC, EnumPokemonType.FIGHTING, EnumGender.MALE),
	PROBOPASS(EnumPokemonType.ROCK, EnumPokemonType.STEEL),
	DUSKNOIR(EnumPokemonType.GHOST),
	FROSLASS(EnumPokemonType.ICE, EnumPokemonType.GHOST, EnumGender.FEMALE),
	ROTOM(EnumPokemonType.ELECTRIC, EnumPokemonType.GHOST, FormSetRotom.FORM_SET, EnumGender.UNKNOWN),
	UXIE(EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	MESPRIT(EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	AZELF(EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	DIALGA(EnumPokemonType.STEEL, EnumPokemonType.DRAGON, EnumGender.UNKNOWN),
	PALKIA(EnumPokemonType.WATER, EnumPokemonType.DRAGON, EnumGender.UNKNOWN),
	HEATRAN(EnumPokemonType.FIRE, EnumPokemonType.STEEL),
	REGIGIGAS(EnumPokemonType.NORMAL, EnumGender.UNKNOWN),
	GIRATINA(	EnumPokemonType.GHOST,
				EnumPokemonType.DRAGON,
				FormSetGiratina.FORM_SET,
				EnumGender.UNKNOWN),
	CRESSELIA(EnumPokemonType.PSYCHIC, EnumGender.FEMALE),
	PHIONE(EnumPokemonType.WATER, EnumGender.UNKNOWN),
	MANAPHY(EnumPokemonType.WATER, EnumGender.UNKNOWN),
	DARKRAI(EnumPokemonType.DARK, EnumGender.UNKNOWN),
	SHAYMIN(EnumPokemonType.GRASS, EnumGender.UNKNOWN),
	ARCEUS(EnumPokemonType.NORMAL, FormSetArceus.FORM_SET, EnumGender.UNKNOWN),
	// TODO: GEN 5 genders, forms
	VICTINI(EnumPokemonType.PSYCHIC, EnumPokemonType.FIRE),
	SNIVY(EnumPokemonType.GRASS),
	SERVINE(EnumPokemonType.GRASS),
	SERPERIOR(EnumPokemonType.GRASS),
	TEPIG(EnumPokemonType.FIRE),
	PIGNITE(EnumPokemonType.FIRE, EnumPokemonType.FIGHTING),
	EMBOAR(EnumPokemonType.FIRE, EnumPokemonType.FIGHTING),
	OSHAWOTT(EnumPokemonType.WATER),
	DEWOTT(EnumPokemonType.WATER),
	SAMUROTT(EnumPokemonType.WATER),
	PARTAR(EnumPokemonType.NORMAL),
	WATCHOG(EnumPokemonType.NORMAL),
	LILLIPUP(EnumPokemonType.NORMAL),
	HERDIER(EnumPokemonType.NORMAL),
	STOUTLAND(EnumPokemonType.NORMAL),
	PURRLOIN(EnumPokemonType.DARK),
	LIEPARD(EnumPokemonType.DARK),
	PANSAGE(EnumPokemonType.GRASS),
	SIMISAGE(EnumPokemonType.GRASS),
	PANSEAR(EnumPokemonType.FIRE),
	SIMISEAR(EnumPokemonType.FIRE),
	PANPOUR(EnumPokemonType.WATER),
	SIMIPOUR(EnumPokemonType.WATER),
	MUNNA(EnumPokemonType.PSYCHIC),
	MASHARNA(EnumPokemonType.PSYCHIC),
	PIDOVE(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	TRAQUILL(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	UNFEZANT(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	BLITZLE(EnumPokemonType.ELECTRIC),
	ZEBSTRIKA(EnumPokemonType.ELECTRIC),
	ROGGENROLA(EnumPokemonType.ROCK),
	BOLDORE(EnumPokemonType.ROCK),
	GIGALITH(EnumPokemonType.ROCK),
	WOOBAT(EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING),
	SWOOBAT(EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING),
	DRILBUR(EnumPokemonType.GROUND),
	EXCADRILL(EnumPokemonType.GROUND, EnumPokemonType.STEEL),
	AUDINO(EnumPokemonType.NORMAL),
	TIMBURR(EnumPokemonType.FIGHTING),
	GURDURR(EnumPokemonType.FIGHTING),
	CONKELDURR(EnumPokemonType.FIGHTING),
	TYMPOLE(EnumPokemonType.WATER),
	PALPITOAD(EnumPokemonType.WATER, EnumPokemonType.GROUND),
	SEISMITOAD(EnumPokemonType.WATER, EnumPokemonType.GROUND),
	THROH(EnumPokemonType.FIGHTING),
	SAWK(EnumPokemonType.FIGHTING),
	SEWADDLE(EnumPokemonType.BUG, EnumPokemonType.GRASS),
	SWADLOON(EnumPokemonType.BUG, EnumPokemonType.GRASS),
	LEAVANNY(EnumPokemonType.BUG, EnumPokemonType.GRASS),
	VENIPEDE(EnumPokemonType.BUG, EnumPokemonType.POISON),
	WHIRLIPEDE(EnumPokemonType.BUG, EnumPokemonType.POISON),
	SCOLIPEDE(EnumPokemonType.BUG, EnumPokemonType.POISON),
	COTTONEE(EnumPokemonType.GRASS, EnumPokemonType.FAIRY),
	WHIMSICOTT(EnumPokemonType.GRASS, EnumPokemonType.FAIRY),
	PETILIL(EnumPokemonType.GRASS),
	LILLIGANT(EnumPokemonType.GRASS),
	BASCULIN(EnumPokemonType.WATER), // forms
	SANDILE(EnumPokemonType.GROUND, EnumPokemonType.DARK),
	KROKOROK(EnumPokemonType.GROUND, EnumPokemonType.DARK),
	KROOKODILE(EnumPokemonType.GROUND, EnumPokemonType.DARK),
	DARUMAKA(EnumPokemonType.FIRE),
	DARMANITAN(EnumPokemonType.FIRE), // forms
	MARACTUS(EnumPokemonType.GRASS),
	DWEBBLE(EnumPokemonType.BUG, EnumPokemonType.ROCK),
	CRUSTLE(EnumPokemonType.BUG, EnumPokemonType.ROCK),
	SCRAGGY(EnumPokemonType.DARK, EnumPokemonType.FIGHTING),
	SCRAFTY(EnumPokemonType.DARK, EnumPokemonType.FIGHTING),
	SIGILYPH(EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING),
	YANMASK(EnumPokemonType.GHOST),
	COFAGRIGUS(EnumPokemonType.GHOST),
	TIRTOUGA(EnumPokemonType.WATER, EnumPokemonType.ROCK),
	CARRACOSTA(EnumPokemonType.WATER, EnumPokemonType.ROCK),
	ARCHEN(EnumPokemonType.ROCK, EnumPokemonType.FLYING),
	ARCHEOPS(EnumPokemonType.ROCK, EnumPokemonType.FLYING),
	TRUBBISH(EnumPokemonType.POISON),
	GARBODOR(EnumPokemonType.POISON),
	ZORUA(EnumPokemonType.DARK),
	ZOROARK(EnumPokemonType.DARK),
	MINCCINO(EnumPokemonType.NORMAL),
	CINCCINO(EnumPokemonType.NORMAL),
	GOTHITA(EnumPokemonType.PSYCHIC),
	GOTHORITA(EnumPokemonType.PSYCHIC),
	GOTHITELLE(EnumPokemonType.PSYCHIC),
	SOLOSIS(EnumPokemonType.PSYCHIC),
	DUOSION(EnumPokemonType.PSYCHIC),
	REUNICLUS(EnumPokemonType.PSYCHIC),
	DUCKLETT(EnumPokemonType.WATER, EnumPokemonType.FLYING),
	SWANNA(EnumPokemonType.WATER, EnumPokemonType.FLYING),
	VANILLITE(EnumPokemonType.ICE),
	VANILLISH(EnumPokemonType.ICE),
	VANILLUXE(EnumPokemonType.ICE),
	DEERLING(EnumPokemonType.NORMAL, EnumPokemonType.GRASS), // forms
	SAWSBUCK(EnumPokemonType.NORMAL, EnumPokemonType.GRASS), // forms
	EMOLGA(EnumPokemonType.ELECTRIC, EnumPokemonType.FLYING),
	KARRABLAST(EnumPokemonType.BUG),
	ESCAVALIER(EnumPokemonType.BUG, EnumPokemonType.STEEL),
	FOONGUS(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	AMOONGUSS(EnumPokemonType.GRASS, EnumPokemonType.POISON),
	FRILLISH(EnumPokemonType.WATER, EnumPokemonType.GHOST),
	JELLICENT(EnumPokemonType.WATER, EnumPokemonType.GHOST),
	ALOMOMOLA(EnumPokemonType.WATER),
	JOLTIK(EnumPokemonType.BUG, EnumPokemonType.ELECTRIC),
	GALVANTULA(EnumPokemonType.BUG, EnumPokemonType.ELECTRIC),
	FERROSEED(EnumPokemonType.GRASS, EnumPokemonType.STEEL),
	FERROTHORN(EnumPokemonType.GRASS, EnumPokemonType.STEEL),
	KLINK(EnumPokemonType.STEEL),
	KLANG(EnumPokemonType.STEEL),
	KLINKLANG(EnumPokemonType.STEEL),
	TYNAMO(EnumPokemonType.ELECTRIC),
	EELETRIK(EnumPokemonType.ELECTRIC),
	EELEKTROSS(EnumPokemonType.ELECTRIC),
	ELGYEM(EnumPokemonType.PSYCHIC),
	BEHEEYM(EnumPokemonType.PSYCHIC),
	LITWICK(EnumPokemonType.GHOST, EnumPokemonType.FIRE),
	LAMPENT(EnumPokemonType.GHOST, EnumPokemonType.FIRE),
	CHANDELURE(EnumPokemonType.GHOST, EnumPokemonType.FIRE),
	AXEW(EnumPokemonType.DRAGON),
	FRAXURE(EnumPokemonType.DRAGON),
	HAXORUS(EnumPokemonType.DRAGON),
	CUBCHOO(EnumPokemonType.ICE),
	BEARTIC(EnumPokemonType.ICE),
	CRYOGONAL(EnumPokemonType.ICE),
	SHELMET(EnumPokemonType.BUG),
	ACCELGOR(EnumPokemonType.BUG),
	STUNFISK(EnumPokemonType.GROUND, EnumPokemonType.ELECTRIC),
	MIENFOO(EnumPokemonType.FIGHTING),
	MIENSHAO(EnumPokemonType.FIGHTING),
	DRUDDIGON(EnumPokemonType.DRAGON),
	GOLETT(EnumPokemonType.GROUND, EnumPokemonType.GHOST),
	GOLURK(EnumPokemonType.GROUND, EnumPokemonType.GHOST),
	PAWNIARD(EnumPokemonType.DARK, EnumPokemonType.STEEL),
	BISHARP(EnumPokemonType.DARK, EnumPokemonType.STEEL),
	BOUFFALANT(EnumPokemonType.NORMAL),
	RUFFLET(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	BRAVIARY(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	VULLABY(EnumPokemonType.DARK, EnumPokemonType.FLYING),
	MANDIBUZZ(EnumPokemonType.DARK, EnumPokemonType.FLYING),
	HEATMOR(EnumPokemonType.FIRE),
	DURANT(EnumPokemonType.BUG, EnumPokemonType.STEEL),
	DEINO(EnumPokemonType.DARK, EnumPokemonType.DRAGON),
	ZWEILOUS(EnumPokemonType.DARK, EnumPokemonType.DRAGON),
	HYDREIGON(EnumPokemonType.DARK, EnumPokemonType.DRAGON),
	LARVESTA(EnumPokemonType.BUG, EnumPokemonType.FIRE),
	VOLCARONA(EnumPokemonType.BUG, EnumPokemonType.FIRE),
	COBALION(EnumPokemonType.STEEL, EnumPokemonType.FIGHTING),
	TERRAKION(EnumPokemonType.ROCK, EnumPokemonType.FIGHTING),
	VIRIZION(EnumPokemonType.GRASS, EnumPokemonType.FIGHTING),
	TORNADUS(EnumPokemonType.FLYING), // forms
	THUNDURUS(EnumPokemonType.ELECTRIC, EnumPokemonType.FLYING), // forms
	RESHIRAM(EnumPokemonType.DRAGON, EnumPokemonType.FIRE),
	ZEKROM(EnumPokemonType.DRAGON, EnumPokemonType.ELECTRIC),
	LANDORUS(EnumPokemonType.GROUND, EnumPokemonType.FLYING), // forms
	KYUREM(EnumPokemonType.DRAGON, EnumPokemonType.ICE),
	KELDEO(EnumPokemonType.WATER, EnumPokemonType.FIGHTING),
	MELOETTA(EnumPokemonType.NORMAL, EnumPokemonType.PSYCHIC), // forms
	GENESECT(EnumPokemonType.BUG, EnumPokemonType.STEEL),
	// TODO: GEN 6 genders, forms
	CHESPIN(EnumPokemonType.GRASS),
	QUILLADIN(EnumPokemonType.GRASS),
	CHESNAUGHT(EnumPokemonType.GRASS, EnumPokemonType.FIGHTING),
	FENNEKIN(EnumPokemonType.FIRE),
	BRAIXEN(EnumPokemonType.FIRE),
	DELPHOX(EnumPokemonType.FIRE),
	FROAKIE(EnumPokemonType.WATER),
	FROGADIER(EnumPokemonType.WATER),
	GRENINJA(EnumPokemonType.WATER, EnumPokemonType.DARK),
	BUNNELBY(EnumPokemonType.NORMAL),
	DIGGERSBY(EnumPokemonType.NORMAL, EnumPokemonType.GROUND),
	FLETCHLING(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	FLETCHINDER(EnumPokemonType.FIRE, EnumPokemonType.FLYING),
	TALONFLAME(EnumPokemonType.FIRE, EnumPokemonType.FLYING),
	SCATTERBUG(EnumPokemonType.BUG),
	SPEWPA(EnumPokemonType.BUG),
	VIVILLON(EnumPokemonType.BUG, EnumPokemonType.FLYING),
	LITEO(EnumPokemonType.FIRE, EnumPokemonType.NORMAL),
	PYROAR(EnumPokemonType.FIRE, EnumPokemonType.NORMAL),
	FLABEBE(EnumPokemonType.FAIRY, "Flabébé"),
	FLOETTE(EnumPokemonType.FAIRY),
	FLORGES(EnumPokemonType.FAIRY),
	SKIDDO(EnumPokemonType.GRASS),
	GOGOAT(EnumPokemonType.GRASS),
	PANCHAM(EnumPokemonType.FIGHTING),
	PANGORO(EnumPokemonType.FIGHTING, EnumPokemonType.DARK),
	FURFROU(EnumPokemonType.NORMAL),
	ESPURR(EnumPokemonType.PSYCHIC),
	MEOWSTIC(EnumPokemonType.PSYCHIC),
	HONEDGE(EnumPokemonType.STEEL, EnumPokemonType.GHOST),
	DOUBLATE(EnumPokemonType.STEEL, EnumPokemonType.GHOST),
	AEGISLASH(EnumPokemonType.STEEL, EnumPokemonType.GHOST),
	SPRITZEE(EnumPokemonType.FAIRY),
	AROMATISSE(EnumPokemonType.FAIRY),
	SWIRLIX(EnumPokemonType.FAIRY),
	SLURPUFF(EnumPokemonType.FAIRY),
	INKAY(EnumPokemonType.DARK, EnumPokemonType.PSYCHIC),
	MALMAR(EnumPokemonType.DARK, EnumPokemonType.PSYCHIC),
	BINACLE(EnumPokemonType.ROCK, EnumPokemonType.WATER),
	BARBARACLE(EnumPokemonType.ROCK, EnumPokemonType.WATER),
	SKRELP(EnumPokemonType.POISON, EnumPokemonType.WATER),
	DRAGALGE(EnumPokemonType.POISON, EnumPokemonType.DRAGON),
	CLAUNCHER(EnumPokemonType.WATER),
	CLAWITZER(EnumPokemonType.WATER),
	HELIOPTILE(EnumPokemonType.ELECTRIC, EnumPokemonType.NORMAL),
	HELIOLISK(EnumPokemonType.ELECTRIC, EnumPokemonType.NORMAL),
	TYRUNT(EnumPokemonType.ROCK, EnumPokemonType.DRAGON),
	TYRANTRUM(EnumPokemonType.ROCK, EnumPokemonType.DRAGON),
	AMAURA(EnumPokemonType.ROCK, EnumPokemonType.ICE),
	AURORUS(EnumPokemonType.ROCK, EnumPokemonType.ICE),
	SYLVEON(EnumPokemonType.FAIRY, FormSetEevee.FORM_SET),
	HAWLUNCHA(EnumPokemonType.FIGHTING, EnumPokemonType.FLYING),
	DEDENNE(EnumPokemonType.ELECTRIC, EnumPokemonType.FAIRY),
	CARBINK(EnumPokemonType.ROCK, EnumPokemonType.FAIRY),
	GOOMY(EnumPokemonType.DRAGON),
	SLIGGOO(EnumPokemonType.DRAGON),
	GOODRA(EnumPokemonType.DRAGON),
	KLEFKI(EnumPokemonType.STEEL, EnumPokemonType.FAIRY),
	PHANTUMP(EnumPokemonType.GHOST, EnumPokemonType.GRASS),
	TREVENANT(EnumPokemonType.GHOST, EnumPokemonType.GRASS),
	PUMPKABOO(EnumPokemonType.GHOST, EnumPokemonType.GRASS), // forms
	GOURGEIST(EnumPokemonType.GHOST, EnumPokemonType.GRASS), // forms
	BERGMITE(EnumPokemonType.ICE),
	AVALUGG(EnumPokemonType.ICE),
	NOIBAT(EnumPokemonType.FLYING, EnumPokemonType.DRAGON),
	NOIVERN(EnumPokemonType.FLYING, EnumPokemonType.DRAGON),
	XERNEAS(EnumPokemonType.FAIRY),
	YVELTAL(EnumPokemonType.DARK, EnumPokemonType.FLYING),
	ZYGARDE(EnumPokemonType.DRAGON, EnumPokemonType.GROUND),
	DIANCIE(EnumPokemonType.ROCK, EnumPokemonType.FAIRY),
	HOOPA(EnumPokemonType.PSYCHIC, EnumPokemonType.GHOST), // forms
	VOLCANION(EnumPokemonType.FIRE, EnumPokemonType.WATER),
	// TODO: GEN 7 genders, forms
	ROWLET(EnumPokemonType.GRASS, EnumPokemonType.FLYING),
	DARTRIX(EnumPokemonType.GRASS, EnumPokemonType.FLYING),
	DECIDUEYE(EnumPokemonType.GRASS, EnumPokemonType.GHOST),
	LITTEN(EnumPokemonType.FIRE),
	TORRACAT(EnumPokemonType.FIRE),
	INCINEROAR(EnumPokemonType.FIRE, EnumPokemonType.DARK),
	POPPLIO(EnumPokemonType.WATER),
	BRIONNE(EnumPokemonType.WATER),
	PRIMARINA(EnumPokemonType.WATER, EnumPokemonType.FAIRY),
	PIKIPEK(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	TRUMBEAK(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	TOUCANNON(EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	YUNGOOS(EnumPokemonType.NORMAL),
	GUMSHOOS(EnumPokemonType.NORMAL),
	GRUBBIN(EnumPokemonType.BUG),
	CHARJABUG(EnumPokemonType.BUG, EnumPokemonType.ELECTRIC),
	VIKAVOLT(EnumPokemonType.BUG, EnumPokemonType.ELECTRIC),
	CRABRAWLER(EnumPokemonType.FIGHTING),
	CRABOMINABLE(EnumPokemonType.FIGHTING, EnumPokemonType.ICE),
	ORICORIO(EnumPokemonType.FIRE, EnumPokemonType.FLYING), // forms
	CUTIEFLY(EnumPokemonType.BUG, EnumPokemonType.FAIRY),
	RIBOMBEE(EnumPokemonType.BUG, EnumPokemonType.FAIRY),
	ROCKRUFF(EnumPokemonType.ROCK),
	LYCANROC(EnumPokemonType.ROCK), // forms
	WISHIWASHI(EnumPokemonType.WATER),
	MAREANIE(EnumPokemonType.POISON, EnumPokemonType.WATER),
	TOXAPEX(EnumPokemonType.POISON, EnumPokemonType.WATER),
	MUDBRAY(EnumPokemonType.GROUND),
	MUDSDALE(EnumPokemonType.GROUND),
	DEWPIDER(EnumPokemonType.WATER, EnumPokemonType.BUG),
	ARAQUANID(EnumPokemonType.WATER, EnumPokemonType.BUG),
	FORMANTIS(EnumPokemonType.GRASS),
	LURANTIS(EnumPokemonType.GRASS),
	MORELULL(EnumPokemonType.GRASS, EnumPokemonType.FAIRY),
	SHIINOTIC(EnumPokemonType.GRASS, EnumPokemonType.FAIRY),
	SALANDIT(EnumPokemonType.POISON, EnumPokemonType.FIRE),
	SALAZZLE(EnumPokemonType.POISON, EnumPokemonType.FIRE),
	STUFFUL(EnumPokemonType.NORMAL, EnumPokemonType.FIGHTING),
	BEWEAR(EnumPokemonType.NORMAL, EnumPokemonType.FIGHTING),
	BOUNSWEET(EnumPokemonType.GRASS),
	STEENEE(EnumPokemonType.GRASS),
	TSAREENA(EnumPokemonType.GRASS),
	COMFEY(EnumPokemonType.FAIRY),
	ORANGURU(EnumPokemonType.NORMAL, EnumPokemonType.PSYCHIC),
	PASSIMIAN(EnumPokemonType.FIGHTING),
	WIMPOD(EnumPokemonType.BUG, EnumPokemonType.WATER),
	GOLISOPOD(EnumPokemonType.BUG, EnumPokemonType.WATER),
	SANDYGAST(EnumPokemonType.GHOST, EnumPokemonType.GROUND),
	PALOSSAND(EnumPokemonType.GHOST, EnumPokemonType.GROUND),
	PYUKUMUKU(EnumPokemonType.WATER),
	TYPE_NULL(EnumPokemonType.NORMAL, "Type: Null"),
	SILVALLY(EnumPokemonType.NORMAL),
	MINIOR(EnumPokemonType.ROCK, EnumPokemonType.FLYING),
	KOMALA(EnumPokemonType.NORMAL),
	TURTONATOR(EnumPokemonType.FIRE, EnumPokemonType.DRAGON),
	TOGEDEMARU(EnumPokemonType.ELECTRIC, EnumPokemonType.STEEL),
	MIMIKYU(EnumPokemonType.GHOST, EnumPokemonType.FAIRY),
	BRUXISH(EnumPokemonType.WATER, EnumPokemonType.PSYCHIC),
	DRAMPA(EnumPokemonType.NORMAL, EnumPokemonType.DRAGON),
	DHELMISE(EnumPokemonType.GHOST, EnumPokemonType.GRASS),
	JANGMO_O(EnumPokemonType.DRAGON, "Jangmo-o"),
	HAKAMO_O(EnumPokemonType.DRAGON, "Hakamo-o"),
	KOMMO_O(EnumPokemonType.DRAGON, "Kommo-o"),
	TAPU_KOKO(EnumPokemonType.ELECTRIC, EnumPokemonType.FAIRY, "Tapu Koko"),
	TAPU_LELE(EnumPokemonType.PSYCHIC, EnumPokemonType.FAIRY, "Tapu Lele"),
	TAPU_BULU(EnumPokemonType.GRASS, EnumPokemonType.FAIRY, "Tapu Bulu"),
	TAPU_FINI(EnumPokemonType.WATER, EnumPokemonType.FAIRY, "Tapu Fini"),
	COSMOG(EnumPokemonType.PSYCHIC),
	COSMOEM(EnumPokemonType.PSYCHIC),
	SOLGALEO(EnumPokemonType.PSYCHIC, EnumPokemonType.STEEL),
	LUNALA(EnumPokemonType.PSYCHIC, EnumPokemonType.GHOST),
	NIHILEGO(EnumPokemonType.ROCK, EnumPokemonType.POISON),
	BUZZWOLE(EnumPokemonType.BUG, EnumPokemonType.FIGHTING),
	PHEROMOSA(EnumPokemonType.BUG, EnumPokemonType.FIGHTING),
	XURKITREE(EnumPokemonType.ELECTRIC),
	CELESTEELA(EnumPokemonType.STEEL, EnumPokemonType.FLYING),
	KARTANA(EnumPokemonType.GRASS, EnumPokemonType.STEEL),
	GUZZLORD(EnumPokemonType.DARK, EnumPokemonType.DRAGON),
	NECROZMA(EnumPokemonType.PSYCHIC), // forms
	MAGEARNA(EnumPokemonType.STEEL, EnumPokemonType.FAIRY), // forms
	MARSHADOW(EnumPokemonType.FIGHTING, EnumPokemonType.GHOST),
	POIPOLE(EnumPokemonType.POISON),
	NAGANADEL(EnumPokemonType.POISON, EnumPokemonType.DRAGON),
	STAKATAKA(EnumPokemonType.ROCK, EnumPokemonType.STEEL),
	BLACEPHALON(EnumPokemonType.FIRE, EnumPokemonType.GHOST),
	ZERAORA(EnumPokemonType.ELECTRIC),
	// MELTAN (gen 7?)
	MELTAN(EnumPokemonType.STEEL),
	MELMETAL(EnumPokemonType.STEEL),;

	private String name;
	private EnumPokemonType type1;
	private EnumPokemonType type2;
	private FormSet forms;
	private EnumGender gender;
	private EnumPokemon[] family;

	EnumPokemon(EnumPokemonType type1, EnumPokemon... family) {
		this(type1, (EnumPokemonType) null, family);
	}

	EnumPokemon(EnumPokemonType type1, String name, EnumPokemon... family) {
		this(type1, (EnumPokemonType) null, name, family);
	}

	EnumPokemon(EnumPokemonType type1, String name, EnumGender gender, EnumPokemon... family) {
		this(type1, (EnumPokemonType) null, name, gender, family);
	}

	EnumPokemon(EnumPokemonType type1, FormSet formSet, EnumPokemon... family) {
		this(type1, (EnumPokemonType) null, formSet, family);
	}

	EnumPokemon(EnumPokemonType type1, FormSet formSet, EnumGender gender, EnumPokemon... family) {
		this(type1, (EnumPokemonType) null, formSet, gender, family);
	}

	EnumPokemon(EnumPokemonType type1, String name, FormSet formSet, EnumPokemon... family) {
		this(type1, (EnumPokemonType) null, name, formSet, family);
	}

	EnumPokemon(EnumPokemonType type1, String name, FormSet formSet, EnumGender gender,
			EnumPokemon... family) {
		this(type1, (EnumPokemonType) null, name, formSet, family);
		this.gender = gender;
	}

	EnumPokemon(EnumPokemonType type1, EnumGender gender, EnumPokemon... family) {
		this(type1, (EnumPokemonType) null, gender, family);
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, EnumPokemon... family) {
		name = this.name();
		name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase();
		this.type1 = type1;
		this.type2 = type2;
		this.gender = EnumGender.EITHER;
		this.family = family;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, String name, EnumPokemon... family) {
		this.name = name;
		this.type1 = type1;
		this.type2 = type2;
		this.gender = EnumGender.EITHER;
		this.family = family;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, String name, EnumGender gender,
			EnumPokemon... family) {
		this(type1, type2, name);
		this.gender = gender;
		this.family = family;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, FormSet formSet, EnumPokemon... family) {
		this(type1, type2);
		this.forms = formSet;
		this.gender = EnumGender.EITHER;
		this.family = family;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, FormSet formSet, EnumGender gender,
			EnumPokemon... family) {
		this(type1, type2, formSet);
		this.gender = gender;
		this.family = family;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, String name, FormSet formSet,
			EnumPokemon... family) {
		this(type1, type2, name);
		this.forms = formSet;
		this.gender = EnumGender.EITHER;
		this.family = family;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, String name, FormSet formSet,
			EnumGender gender, EnumPokemon... family) {
		this(type1, type2, name, formSet);
		this.gender = gender;
		this.family = family;
	}

	EnumPokemon(EnumPokemonType type1, EnumPokemonType type2, EnumGender gender, EnumPokemon... family) {
		this(type1, type2);
		this.gender = gender;
		this.family = family;
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

	public Form getDefaultForm() {
		if (this.forms == null)
			return null;
		return this.forms.getDefaultForm();
	}

	public EnumGender getGenderPossible() {
		return this.gender;
	}

	/**
	 * @deprecated Not functional yet
	 */
	@Deprecated
	public EnumPokemon[] getFamily() {
		return this.family;
	}

	public int getId() {
		return ordinal() + 1;
	}

	// TODO: new gen, update trade exceptions
	public boolean isTradable() {
		switch (this) {
		case MELTAN:
		case MELMETAL:
			return true;
		default:
			return !isMythical();
		}
	}

	// TODO: new gen, update legends
	public boolean isLegendary() {
		switch (this) {
		// gen 1
		case ARTICUNO:
		case ZAPDOS:
		case MOLTRES:
		case MEWTWO:
			return true;
		// gen 2
		case RAIKOU:
		case ENTEI:
		case SUICUNE:
		case HO_OH:
		case LUGIA:
			return true;
		// gen 3
		case REGICE:
		case REGISTEEL:
		case REGIROCK:
		case LATIAS:
		case LATIOS:
		case GROUDON:
		case KYOGRE:
		case RAYQUAZA:
			return true;
		// gen 4
		case AZELF:
		case MESPRIT:
		case UXIE:
		case DIALGA:
		case PALKIA:
		case GIRATINA:
		case CRESSELIA:
		case HEATRAN:
		case REGIGIGAS:
			return true;
		// gen 5
		case COBALION:
		case TERRAKION:
		case VIRIZION:
		case TORNADUS:
		case THUNDURUS:
		case LANDORUS:
		case ZEKROM:
		case RESHIRAM:
		case KYUREM:
			return true;
		// gen 6
		case XERNEAS:
		case YVELTAL:
		case ZYGARDE:
			return true;
		// gen 7
		case TYPE_NULL:
		case SILVALLY:
		case TAPU_KOKO:
		case TAPU_LELE:
		case TAPU_BULU:
		case TAPU_FINI:
		case COSMOG:
		case COSMOEM:
		case SOLGALEO:
		case LUNALA:
		case NECROZMA:
			return true;
		default:
			return false;
		}
	}

	// TODO: new gen, update mythics
	public boolean isMythical() {
		switch (this) {
		// gen 1
		case MEW:
			return true;
		// gen 2
		case CELEBI:
			return true;
		// gen 3
		case JIRACHI:
		case DEOXYS:
			return true;
		// gen 4
		case MANAPHY:
		case PHIONE:
		case DARKRAI:
		case SHAYMIN:
		case ARCEUS:
			return true;
		// gen 5
		case VICTINI:
		case KELDEO:
		case MELOETTA:
		case GENESECT:
			return true;
		// gen 6
		case DIANCIE:
		case HOOPA:
		case VOLCANION:
			return true;
		// gen 7
		case MAGEARNA:
		case MARSHADOW:
		case ZERAORA:
		case MELTAN:
		case MELMETAL:
			return true;
		default:
			return false;
		}
	}

	// TODO: new gen, update max num
	public int getGeneration() {
		int id = this.getId();
		if (id < 0)
			return -1;
		else if (id <= 151)
			return 1;
		else if (id <= 251)
			return 2;
		else if (id <= 386)
			return 3;
		else if (id <= 493)
			return 4;
		else if (id <= 649)
			return 5;
		else if (id <= 721)
			return 6;
		else if (id <= 809)
			return 7;
		return -1;
	}

	// TODO: new gen, update starters
	public boolean isStarter() {
		switch (this) {
		// gen 1
		case BULBASAUR:
		case IVYSAUR:
		case VENUSAUR:
		case CHARMANDER:
		case CHARMELEON:
		case CHARIZARD:
		case SQUIRTLE:
		case WARTORTLE:
		case BLASTOISE:
			return true;
		// gen 2
		case CHIKORITA:
		case BAYLEEF:
		case MEGANIUM:
		case CYNDAQUIL:
		case QUILAVA:
		case TYPHLOSION:
		case TOTODILE:
		case CROCONAW:
		case FERALIGATR:
			return true;
		// gen 3
		case TREECKO:
		case GROVYLE:
		case SCEPTILE:
		case TORCHIC:
		case COMBUSKEN:
		case BLAZIKEN:
		case MUDKIP:
		case MARSHTOMP:
		case SWAMPERT:
			return true;
		// gen 4
		case TURTWIG:
		case GROTLE:
		case TORTERRA:
		case CHIMCHAR:
		case MONFERNO:
		case INFERNAPE:
		case PIPLUP:
		case PRINPLUP:
		case EMPOLEON:
			return true;
		// gen 5
		case SNIVY:
		case SERVINE:
		case SERPERIOR:
		case TEPIG:
		case PIGNITE:
		case EMBOAR:
		case OSHAWOTT:
		case DEWOTT:
		case SAMUROTT:
			return true;
		// gen 6
		case CHESPIN:
		case QUILLADIN:
		case CHESNAUGHT:
		case FENNEKIN:
		case BRAIXEN:
		case DELPHOX:
		case FROAKIE:
		case FROGADIER:
		case GRENINJA:
			return true;
		// gen 7
		case ROWLET:
		case DARTRIX:
		case DECIDUEYE:
		case LITTEN:
		case TORRACAT:
		case INCINEROAR:
		case POPPLIO:
		case BRIONNE:
		case PRIMARINA:
			return true;
		default:
			return false;
		}
	}

	public boolean isBaby() {
		switch (this) {
		// gen 2
		case PICHU:
		case CLEFFA:
		case IGGLYBUFF:
		case TOGEPI:
		case TYROGUE:
		case SMOOCHUM:
		case ELEKID:
		case MAGBY:
			return true;
		// gen 3
		case AZURILL:
		case WYNAUT:
			return true;
		// gen 4
		case BUDEW:
		case CHINGLING:
		case BONSLY:
		case MIME_JR:
		case HAPPINY:
		case MUNCHLAX:
		case RIOLU:
		case MANTYKE:
			return true;
		default:
			return false;
		}
	}

	public String getArtwork(int formId) {
		switch (this) {
		case WORMADAM:
		case ROTOM:
		case ARCEUS:
			return ImageHelper.getOfficalArtwork(this);
		default:
			return ImageHelper.getOfficalArtwork(this, formId);
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
		try {
			int id = Integer.parseInt(name);
			return getPokemon(id);
		} catch (NumberFormatException e) {}
		for (EnumPokemon p : values())
			if (p.name.equalsIgnoreCase(name))
				return p;
		return null;
	}

	private static CachedData<List<EnumPokemon>> AVAILABLE;
	private static CachedData<List<EnumPokemon>> SHINYABLE;

	private static final String SILPH_DEX = "https://thesilphroad.com/catalog";
	private static final Pattern POKEMON_GENERAL_PATTERN = Pattern
			.compile("<div class=\"pokemonOption (sighted|notSighted).*?</div>");
	private static final Pattern DEX_NUM_PATTERN = Pattern.compile("<span>#[0-9]{0,3}</span>");

	private static void checkCaches() {
		if (AVAILABLE == null || AVAILABLE.hasPassed(86400000L) || SHINYABLE == null
				|| SHINYABLE.hasPassed(86400000L)) { // 1 day
			AVAILABLE = new CachedData<>(new LinkedList<>());
			SHINYABLE = new CachedData<>(new LinkedList<>());

			try {
				URL url = new URL(SILPH_DEX);
				URLConnection conn = url.openConnection();
				conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String html = "";
				String line = null;
				while ((line = in.readLine()) != null)
					html += line;
				Matcher generalMatcher = POKEMON_GENERAL_PATTERN.matcher(html);
				while (generalMatcher.find()) {
					String match = generalMatcher.group();
					if (match.contains("data-released=\"1\"")) {
						Matcher dexNumMatcher = DEX_NUM_PATTERN.matcher(match);
						if (dexNumMatcher.find()) {
							String dexMatch = dexNumMatcher.group();
							EnumPokemon pokemon = EnumPokemon.getPokemon(
									Integer.parseInt(dexMatch.substring(7, dexMatch.length() - 7)));
							if (pokemon == null)
								continue;
							AVAILABLE.getValue().add(pokemon);
							if (match.contains("data-shiny-released=\"1\""))
								SHINYABLE.getValue().add(pokemon);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@ClearCache("pokemon")
	public static void dumpCache() {
		AVAILABLE = null;
		SHINYABLE = null;
	}

	public boolean isAvailable() {
		checkCaches();
		return AVAILABLE.getValue().contains(this);
	}

	public boolean isShinyable() {
		checkCaches();
		return SHINYABLE.getValue().contains(this);
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
			int genderMult = p.getGenderPossible() == EnumGender.EITHER ? 2 : 1;
			int shinyMult = p.isShinyable() ? 2 : 1;
			space += ((p.getFormSet() == null ? 1 : p.getFormSet().getNumForms()) * genderMult
					* shinyMult);
		}
		System.out.println("You need " + space + " bag space to hold all forms");

		// for (EnumPokemon p : EnumPokemon.values())
		// if (FormManager.isShinyable(p.getId()))
		// System.out.println(p + " is shinyable");
	}
}
