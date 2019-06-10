package us.myles_selim.starota.enums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
	BULBASAUR(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	IVYSAUR(EnumPokemonStage.MIDDLE, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	VENUSAUR(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	CHARMANDER(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	CHARMELEON(EnumPokemonStage.MIDDLE, EnumPokemonType.FIRE),
	CHARIZARD(EnumPokemonStage.FINAL, EnumPokemonType.FIRE, EnumPokemonType.FLYING),
	SQUIRTLE(EnumPokemonStage.BASE, EnumPokemonType.WATER, FormSetSquirtleSquad.FORM_SET),
	WARTORTLE(EnumPokemonStage.MIDDLE, EnumPokemonType.WATER, FormSetSquirtleSquad.FORM_SET),
	BLASTOISE(EnumPokemonStage.FINAL, EnumPokemonType.WATER, FormSetSquirtleSquad.FORM_SET),
	CATERPIE(EnumPokemonStage.BASE, EnumPokemonType.BUG),
	METAPOD(EnumPokemonStage.MIDDLE, EnumPokemonType.BUG),
	BUTTERFREE(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.FLYING),
	WEEDLE(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.POISON),
	KAKUNA(EnumPokemonStage.MIDDLE, EnumPokemonType.BUG, EnumPokemonType.POISON),
	BEEDRILL(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.POISON),
	PIDGEY(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	PIDGEOTTO(EnumPokemonStage.MIDDLE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	PIDGEOT(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	RATTATA(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, FormSetAlolan.FORM_SET),
	RATICATE(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, FormSetAlolan.FORM_SET),
	SPEAROW(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	FEAROW(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	EKANS(EnumPokemonStage.BASE, EnumPokemonType.POISON),
	ARBOK(EnumPokemonStage.FINAL, EnumPokemonType.POISON),
	PIKACHU(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC, FormSetPikachuHat.FORM_SET),
	RAICHU(EnumPokemonStage.FINAL, EnumPokemonType.ELECTRIC, FormSetRaichuHat.FORM_SET),
	SANDSHREW(EnumPokemonStage.BASE, EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	SANDSLASH(EnumPokemonStage.FINAL, EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	NIDORAN_F(EnumPokemonStage.BASE, EnumPokemonType.POISON, "Nidoran♀", EnumGender.FEMALE),
	NIDORINA(EnumPokemonStage.MIDDLE, EnumPokemonType.POISON, EnumGender.FEMALE),
	NIDOQUEEN(EnumPokemonStage.FINAL, EnumPokemonType.POISON, EnumPokemonType.GROUND, EnumGender.FEMALE),
	NIDORAN_M(EnumPokemonStage.BASE, EnumPokemonType.POISON, "Nidoran♂", EnumGender.MALE),
	NIDORINO(EnumPokemonStage.MIDDLE, EnumPokemonType.POISON, EnumGender.MALE),
	NIDOKING(EnumPokemonStage.FINAL, EnumPokemonType.POISON, EnumPokemonType.GROUND, EnumGender.MALE),
	CLEFAIRY(EnumPokemonStage.BASE, EnumPokemonType.FAIRY),
	CLEFABLE(EnumPokemonStage.FINAL, EnumPokemonType.FAIRY),
	VULPIX(EnumPokemonStage.BASE, EnumPokemonType.FIRE, FormSetAlolan.FORM_SET),
	NINETALES(EnumPokemonStage.FINAL, EnumPokemonType.FIRE, FormSetAlolan.FORM_SET),
	JIGGLYPUFF(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FAIRY),
	WIGGLYTUFF(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.FAIRY),
	ZUBAT(EnumPokemonStage.BASE, EnumPokemonType.POISON, EnumPokemonType.FLYING),
	GOLBAT(EnumPokemonStage.MIDDLE, EnumPokemonType.POISON, EnumPokemonType.FLYING),
	ODDISH(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	GLOOM(EnumPokemonStage.MIDDLE, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	VILEPLUME(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	PARAS(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.GRASS),
	PARASECT(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.GRASS),
	VENONAT(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.POISON),
	VENOMOTH(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.POISON),
	DIGLETT(EnumPokemonStage.BASE, EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	DUGTRIO(EnumPokemonStage.FINAL, EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	MEOWTH(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, FormSetAlolan.FORM_SET),
	PERSIAN(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, FormSetAlolan.FORM_SET),
	PSYDUCK(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	GOLDUCK(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	MANKEY(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING),
	PRIMEAPE(EnumPokemonStage.FINAL, EnumPokemonType.FIGHTING),
	GROWLITHE(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	ARCANINE(EnumPokemonStage.FINAL, EnumPokemonType.FIRE),
	POLIWAG(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	POLIWHIRL(EnumPokemonStage.MIDDLE, EnumPokemonType.WATER),
	POLIWRATH(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.FIGHTING),
	ABRA(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC),
	KADABRA(EnumPokemonStage.MIDDLE, EnumPokemonType.PSYCHIC),
	ALAKAZAM(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC),
	MACHOP(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING),
	MACHOKE(EnumPokemonStage.MIDDLE, EnumPokemonType.FIGHTING),
	MACHAMP(EnumPokemonStage.FINAL, EnumPokemonType.FIGHTING),
	BELLSPROUT(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	WEEPINBELL(EnumPokemonStage.MIDDLE, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	VICTREEBEL(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	TENTACOOL(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.POISON),
	TENACRUEL(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.POISON),
	GEODUDE(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	GRAVELER(	EnumPokemonStage.MIDDLE,
				EnumPokemonType.ROCK,
				EnumPokemonType.GROUND,
				FormSetAlolan.FORM_SET),
	GOLEM(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	PONYTA(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	RAPIDASH(EnumPokemonStage.FINAL, EnumPokemonType.FIRE),
	SLOWPOKE(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.PSYCHIC),
	SLOWBRO(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.PSYCHIC),
	MAGNEMITE(	EnumPokemonStage.BASE,
				EnumPokemonType.ELECTRIC,
				EnumPokemonType.STEEL,
				EnumGender.UNKNOWN),
	MAGNETON(	EnumPokemonStage.MIDDLE,
				EnumPokemonType.ELECTRIC,
				EnumPokemonType.STEEL,
				EnumGender.UNKNOWN),
	FARFETCHD(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING, "Farfetch'd"),
	DODUO(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	DODRIO(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	SEEL(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	DEWGONG(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.ICE),
	GRIMER(EnumPokemonStage.BASE, EnumPokemonType.POISON, FormSetAlolan.FORM_SET),
	MUK(EnumPokemonStage.FINAL, EnumPokemonType.POISON, FormSetAlolan.FORM_SET),
	SHELLDER(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	CLOYSTER(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.ICE),
	GASTLY(EnumPokemonStage.BASE, EnumPokemonType.GHOST, EnumPokemonType.POISON),
	HAUNTER(EnumPokemonStage.MIDDLE, EnumPokemonType.GHOST, EnumPokemonType.POISON),
	GENGAR(EnumPokemonStage.FINAL, EnumPokemonType.GHOST, EnumPokemonType.POISON),
	ONIX(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	DROWZEE(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC),
	HYPNO(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC),
	KRABBY(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	KINGLER(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	VOLTORB(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC, EnumGender.UNKNOWN),
	ELECTRODE(EnumPokemonStage.FINAL, EnumPokemonType.ELECTRIC, EnumGender.UNKNOWN),
	EXEGGCUTE(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.PSYCHIC),
	EXEGGUTOR(	EnumPokemonStage.FINAL,
				EnumPokemonType.GRASS,
				EnumPokemonType.PSYCHIC,
				FormSetAlolan.FORM_SET),
	CUBONE(EnumPokemonStage.BASE, EnumPokemonType.GROUND),
	MAROWAK(EnumPokemonStage.FINAL, EnumPokemonType.GROUND, FormSetAlolan.FORM_SET),
	HITMONLEE(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING, EnumGender.MALE),
	HITMONCHAN(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING, EnumGender.MALE),
	LICKITUNG(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	KOFFING(EnumPokemonStage.BASE, EnumPokemonType.POISON),
	WEEZING(EnumPokemonStage.FINAL, EnumPokemonType.POISON),
	RHYHORN(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	RHYDON(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	CHANSEY(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumGender.FEMALE),
	TANGELA(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	KANGASKHAN(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumGender.FEMALE),
	HORSEA(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	SEADRA(EnumPokemonStage.MIDDLE, EnumPokemonType.WATER),
	GOLDEEN(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	SEAKING(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	STARYU(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumGender.UNKNOWN),
	STARMIE(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	MR_MIME(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC, "Mr. Mime"),
	SCYTHER(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.FLYING),
	JYNX(EnumPokemonStage.BASE, EnumPokemonType.ICE, EnumPokemonType.PSYCHIC, EnumGender.FEMALE),
	ELECTABUZZ(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC),
	MAGMAR(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	PINSIR(EnumPokemonStage.BASE, EnumPokemonType.BUG),
	TAUROS(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumGender.MALE),
	MAGIKARP(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	GYARADOS(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.FLYING),
	LAPRAS(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.ICE),
	DITTO(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	EEVEE(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, FormSetEevee.FORM_SET),
	VAPOREON(EnumPokemonStage.FINAL, EnumPokemonType.WATER, FormSetEevee.FORM_SET),
	JOLTEON(EnumPokemonStage.FINAL, EnumPokemonType.ELECTRIC, FormSetEevee.FORM_SET),
	FLAREON(EnumPokemonStage.FINAL, EnumPokemonType.FIRE, FormSetEevee.FORM_SET),
	PORYGON(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumGender.UNKNOWN),
	OMANYTE(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.WATER),
	OMASTAR(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.WATER),
	KABUTO(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.WATER),
	KABUTOPS(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.WATER),
	AERODACTYL(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.FLYING),
	SNORLAX(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	ARTICUNO(EnumPokemonStage.LEGEND, EnumPokemonType.ICE, EnumPokemonType.FLYING, EnumGender.UNKNOWN),
	ZAPDOS(	EnumPokemonStage.LEGEND,
			EnumPokemonType.ELECTRIC,
			EnumPokemonType.FLYING,
			EnumGender.UNKNOWN),
	MOLTRES(EnumPokemonStage.LEGEND, EnumPokemonType.FIRE, EnumPokemonType.FLYING, EnumGender.UNKNOWN),
	DRATINI(EnumPokemonStage.BASE, EnumPokemonType.DRAGON),
	DRAGONAIR(EnumPokemonStage.MIDDLE, EnumPokemonType.DRAGON),
	DRAGONITE(EnumPokemonStage.FINAL, EnumPokemonType.DRAGON, EnumPokemonType.FLYING),
	MEWTWO(EnumPokemonStage.LEGEND, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	MEW(EnumPokemonStage.MYTHIC, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	// GEN 2
	CHIKORITA(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	BAYLEEF(EnumPokemonStage.MIDDLE, EnumPokemonType.GRASS),
	MEGANIUM(EnumPokemonStage.FINAL, EnumPokemonType.GRASS),
	CYNDAQUIL(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	QUILAVA(EnumPokemonStage.MIDDLE, EnumPokemonType.FIRE),
	TYPHLOSION(EnumPokemonStage.FINAL, EnumPokemonType.FIRE),
	TOTODILE(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	CROCONAW(EnumPokemonStage.MIDDLE, EnumPokemonType.WATER),
	FERALIGATR(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	SENTRET(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	FURRET(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	HOOTHOOT(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	NOCTOWL(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	LEDYBA(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.FLYING),
	LEDIAN(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.FLYING),
	SPINARAK(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.POISON),
	ARIADOS(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.POISON),
	CROBAT(EnumPokemonStage.FINAL, EnumPokemonType.POISON, EnumPokemonType.FLYING),
	CHINCHOU(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.ELECTRIC),
	LANTURN(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.ELECTRIC),
	PICHU(EnumPokemonStage.BABY, EnumPokemonType.ELECTRIC, FormSetPichuHat.FORM_SET),
	CLEFFA(EnumPokemonStage.BABY, EnumPokemonType.FAIRY),
	IGGLYBUFF(EnumPokemonStage.BABY, EnumPokemonType.NORMAL, EnumPokemonType.FAIRY),
	TOGEPI(EnumPokemonStage.BABY, EnumPokemonType.FAIRY),
	TOGETIC(EnumPokemonStage.BASE, EnumPokemonType.FAIRY, EnumPokemonType.FLYING),
	NATU(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING),
	XATU(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING),
	MAREEP(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC),
	FLAAFFY(EnumPokemonStage.MIDDLE, EnumPokemonType.ELECTRIC),
	AMPHAROS(EnumPokemonStage.FINAL, EnumPokemonType.ELECTRIC),
	BELLOSSOM(EnumPokemonStage.FINAL, EnumPokemonType.GRASS),
	MARILL(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.FAIRY),
	AZUMARILL(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.FAIRY),
	SUDOWOODO(EnumPokemonStage.BASE, EnumPokemonType.ROCK),
	POLITOED(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	HOPPIP(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.FLYING),
	SKIPLOOM(EnumPokemonStage.MIDDLE, EnumPokemonType.GRASS, EnumPokemonType.FLYING),
	JUMPLUFF(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.FLYING),
	AIPOM(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	SUNKERN(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	SUNFLORA(EnumPokemonStage.FINAL, EnumPokemonType.GRASS),
	YANMA(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.FLYING),
	WOOPER(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.GROUND),
	QUAGSIRE(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.GROUND),
	ESPEON(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC, FormSetEevee.FORM_SET),
	UMBREON(EnumPokemonStage.FINAL, EnumPokemonType.DARK, FormSetEevee.FORM_SET),
	MURKROW(EnumPokemonStage.BASE, EnumPokemonType.DARK, EnumPokemonType.FLYING),
	SLOWKING(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.PSYCHIC),
	MISDREAVUS(EnumPokemonStage.BASE, EnumPokemonType.GHOST),
	UNOWN(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC, FormSetUnown.FORM_SET, EnumGender.UNKNOWN),
	WOBBUFFET(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC),
	GIRAFARIG(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC),
	PINECO(EnumPokemonStage.BASE, EnumPokemonType.BUG),
	FORRETRESS(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.STEEL),
	DUNSPARCE(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	GLIGAR(EnumPokemonStage.BASE, EnumPokemonType.GROUND, EnumPokemonType.FLYING),
	STEELIX(EnumPokemonStage.FINAL, EnumPokemonType.STEEL, EnumPokemonType.GROUND),
	SNUBBULL(EnumPokemonStage.BASE, EnumPokemonType.FAIRY),
	GRANBULL(EnumPokemonStage.FINAL, EnumPokemonType.FAIRY),
	QUILFISH(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.POISON),
	SCIZOR(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.STEEL),
	SHUCKLE(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.ROCK),
	HERACROSS(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.FIGHTING),
	SNEASEL(EnumPokemonStage.BASE, EnumPokemonType.DARK, EnumPokemonType.ICE),
	TEDDIURSA(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	URSARING(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	SLUGMA(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	MAGCARGO(EnumPokemonStage.FINAL, EnumPokemonType.FIRE, EnumPokemonType.ROCK),
	SWINUB(EnumPokemonStage.BASE, EnumPokemonType.ICE, EnumPokemonType.GROUND),
	PILOSWINE(EnumPokemonStage.MIDDLE, EnumPokemonType.ICE, EnumPokemonType.GROUND),
	CORSOLA(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.ROCK),
	REMORAID(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	OCTILLERY(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	DELIBIRD(EnumPokemonStage.BASE, EnumPokemonType.ICE, EnumPokemonType.FLYING),
	MANTINE(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.FLYING),
	SKARMORY(EnumPokemonStage.BASE, EnumPokemonType.STEEL, EnumPokemonType.FLYING),
	HOUNDOUR(EnumPokemonStage.BASE, EnumPokemonType.DARK, EnumPokemonType.FIRE),
	HOUNDOOM(EnumPokemonStage.FINAL, EnumPokemonType.DARK, EnumPokemonType.FIRE),
	KINGDRA(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.DRAGON),
	PHANPY(EnumPokemonStage.BASE, EnumPokemonType.GROUND),
	DONPHAN(EnumPokemonStage.FINAL, EnumPokemonType.GROUND),
	PORYGON2(EnumPokemonStage.MIDDLE, EnumPokemonType.NORMAL, EnumGender.UNKNOWN),
	STANTLER(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	SMEARGLE(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	TYROGUE(EnumPokemonStage.BABY, EnumPokemonType.FIGHTING, EnumGender.MALE),
	HITMONTOP(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING, EnumGender.MALE),
	SMOOCHUM(EnumPokemonStage.BABY, EnumPokemonType.ICE, EnumPokemonType.PSYCHIC, EnumGender.FEMALE),
	ELEKID(EnumPokemonStage.BABY, EnumPokemonType.ELECTRIC),
	MAGBY(EnumPokemonStage.BABY, EnumPokemonType.FIRE),
	MILTANK(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumGender.FEMALE),
	BLISSEY(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumGender.FEMALE),
	RAIKOU(EnumPokemonStage.LEGEND, EnumPokemonType.ELECTRIC, EnumGender.UNKNOWN),
	ENTEI(EnumPokemonStage.LEGEND, EnumPokemonType.FIRE, EnumGender.UNKNOWN),
	SUICUNE(EnumPokemonStage.LEGEND, EnumPokemonType.WATER, EnumGender.UNKNOWN),
	LARVITAR(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	PUPITAR(EnumPokemonStage.MIDDLE, EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	TYRANITAR(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.DARK),
	LUGIA(EnumPokemonStage.LEGEND, EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING, EnumGender.UNKNOWN),
	HO_OH(	EnumPokemonStage.LEGEND,
			EnumPokemonType.WATER,
			EnumPokemonType.FLYING,
			"Ho-Oh",
			EnumGender.UNKNOWN),
	CELEBI(EnumPokemonStage.MYTHIC, EnumPokemonType.PSYCHIC, EnumPokemonType.GRASS),
	// GEN 3
	TREECKO(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	GROVYLE(EnumPokemonStage.MIDDLE, EnumPokemonType.GRASS),
	SCEPTILE(EnumPokemonStage.FINAL, EnumPokemonType.GRASS),
	TORCHIC(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	COMBUSKEN(EnumPokemonStage.MIDDLE, EnumPokemonType.FIRE, EnumPokemonType.FIGHTING),
	BLAZIKEN(EnumPokemonStage.FINAL, EnumPokemonType.FIRE, EnumPokemonType.FIGHTING),
	MUDKIP(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	MARSHTOMP(EnumPokemonStage.MIDDLE, EnumPokemonType.WATER, EnumPokemonType.GROUND),
	SWAMPERT(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.GROUND),
	POOCHYENA(EnumPokemonStage.BASE, EnumPokemonType.DARK),
	MIGHTYENA(EnumPokemonStage.FINAL, EnumPokemonType.DARK),
	ZIGZAGOON(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	LINOONE(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	WURMPLE(EnumPokemonStage.BASE, EnumPokemonType.BUG),
	SILCOON(EnumPokemonStage.MIDDLE, EnumPokemonType.BUG),
	BEAUTIFLY(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.FLYING),
	CASCOON(EnumPokemonStage.MIDDLE, EnumPokemonType.BUG),
	DUSTOX(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.POISON),
	LOTAD(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.GRASS),
	LOMBRE(EnumPokemonStage.MIDDLE, EnumPokemonType.WATER, EnumPokemonType.GRASS),
	LUDICOLO(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.GRASS),
	SEEDOT(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	NUZLEAF(EnumPokemonStage.MIDDLE, EnumPokemonType.GRASS, EnumPokemonType.DARK),
	SHIFTRY(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.DARK),
	TAILLOW(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	SWELLOW(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	WINGULL(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.FLYING),
	PELIPPER(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.FLYING),
	RALTS(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC, EnumPokemonType.FAIRY),
	KIRLIA(EnumPokemonStage.MIDDLE, EnumPokemonType.PSYCHIC, EnumPokemonType.FAIRY),
	GARDEVOIR(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC, EnumPokemonType.FAIRY),
	SURSKIT(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.WATER),
	MASQUERAIN(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.FLYING),
	SHROOMISH(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	BRELOOM(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.FIGHTING),
	SLAKOTH(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	VIGOROTH(EnumPokemonStage.MIDDLE, EnumPokemonType.NORMAL),
	SLAKING(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	NINCADA(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.GROUND),
	NINJASK(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.FLYING),
	SHEDINJA(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.GHOST, EnumGender.UNKNOWN),
	WHISMUR(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	LOUDRED(EnumPokemonStage.MIDDLE, EnumPokemonType.NORMAL),
	EXPLOUD(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	MAKUHITA(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING),
	HARIYAMA(EnumPokemonStage.FINAL, EnumPokemonType.FIGHTING),
	AZURILL(EnumPokemonStage.BABY, EnumPokemonType.NORMAL, EnumPokemonType.FAIRY),
	NOSEPASS(EnumPokemonStage.BASE, EnumPokemonType.ROCK),
	SKITTY(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	DELCATTY(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	SABLEYE(EnumPokemonStage.BASE, EnumPokemonType.DARK, EnumPokemonType.GHOST),
	MAWILE(EnumPokemonStage.BASE, EnumPokemonType.STEEL, EnumPokemonType.FAIRY),
	ARON(EnumPokemonStage.BASE, EnumPokemonType.STEEL, EnumPokemonType.ROCK),
	LAIRON(EnumPokemonStage.MIDDLE, EnumPokemonType.STEEL, EnumPokemonType.ROCK),
	AGGRON(EnumPokemonStage.FINAL, EnumPokemonType.STEEL, EnumPokemonType.ROCK),
	MEDITITE(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING, EnumPokemonType.PSYCHIC),
	MEDICHAM(EnumPokemonStage.FINAL, EnumPokemonType.FIGHTING, EnumPokemonType.PSYCHIC),
	ELECTRIKE(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC),
	MANECTRIC(EnumPokemonStage.FINAL, EnumPokemonType.ELECTRIC),
	PLUSLE(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC),
	MINUN(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC),
	VOLBEAT(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumGender.MALE),
	ILLUMISE(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumGender.FEMALE),
	ROSELIA(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	GULPIN(EnumPokemonStage.BASE, EnumPokemonType.POISON),
	SWALOT(EnumPokemonStage.FINAL, EnumPokemonType.POISON),
	CARVANHA(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.DARK),
	SHARPEDO(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.DARK),
	WAILMER(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	WAILORD(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	NUMEL(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	CAMERUPT(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.GROUND),
	TORKOAL(EnumPokemonStage.BASE, EnumPokemonType.ROCK),
	SPOINK(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC),
	GRUMPIG(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC),
	SPINDA(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, FormSetSpinda.FORM_SET),
	TRAPINCH(EnumPokemonStage.BASE, EnumPokemonType.GROUND),
	VIBRAVA(EnumPokemonStage.MIDDLE, EnumPokemonType.GROUND, EnumPokemonType.DRAGON),
	FLYGON(EnumPokemonStage.FINAL, EnumPokemonType.GROUND, EnumPokemonType.DRAGON),
	CACNEA(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	CACTURNE(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.DARK),
	SWABLU(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	ALTARIA(EnumPokemonStage.FINAL, EnumPokemonType.DRAGON, EnumPokemonType.FLYING),
	ZANGOOSE(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	SEVIPER(EnumPokemonStage.BASE, EnumPokemonType.POISON),
	LUNATONE(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	SOLROCK(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	BARBOACH(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.GROUND),
	WHISCASH(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.GROUND),
	CORPHISH(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	CRAWDAUNT(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.DARK),
	BALTOY(EnumPokemonStage.BASE, EnumPokemonType.GROUND, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	CLAYDOL(EnumPokemonStage.FINAL, EnumPokemonType.GROUND, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	LILEEP(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.GRASS),
	CRADILY(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.GRASS),
	ANORITH(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.BUG),
	ARMALDO(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.BUG),
	FEEBAS(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	MILOTIC(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	CASTFORM(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, FormSetCastform.FORM_SET),
	KECLEON(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	SHUPPET(EnumPokemonStage.BASE, EnumPokemonType.GHOST),
	BANETTE(EnumPokemonStage.FINAL, EnumPokemonType.GHOST),
	DUSKULL(EnumPokemonStage.BASE, EnumPokemonType.GHOST),
	DUSCLOPS(EnumPokemonStage.MIDDLE, EnumPokemonType.GHOST),
	TROPIUS(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.FLYING),
	CHIMECHO(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC),
	ABSOL(EnumPokemonStage.BASE, EnumPokemonType.DARK),
	WYNAUT(EnumPokemonStage.BABY, EnumPokemonType.PSYCHIC),
	SNORUNT(EnumPokemonStage.BASE, EnumPokemonType.ICE),
	GLALIE(EnumPokemonStage.FINAL, EnumPokemonType.ICE),
	SPHEAL(EnumPokemonStage.BASE, EnumPokemonType.ICE, EnumPokemonType.WATER),
	SEALEO(EnumPokemonStage.MIDDLE, EnumPokemonType.ICE, EnumPokemonType.WATER),
	WALREIN(EnumPokemonStage.FINAL, EnumPokemonType.ICE, EnumPokemonType.WATER),
	CLAMPERL(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	HUNTAIL(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	GOREBYSS(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	RELICANTH(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.ROCK),
	LUVDISC(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	BAGON(EnumPokemonStage.BASE, EnumPokemonType.DRAGON),
	SHELGON(EnumPokemonStage.MIDDLE, EnumPokemonType.DRAGON),
	SALAMENCE(EnumPokemonStage.FINAL, EnumPokemonType.DRAGON, EnumPokemonType.FLYING),
	BELDUM(EnumPokemonStage.BASE, EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	METANG(EnumPokemonStage.MIDDLE, EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	METAGROSS(	EnumPokemonStage.FINAL,
				EnumPokemonType.STEEL,
				EnumPokemonType.PSYCHIC,
				EnumGender.UNKNOWN),
	REGIROCK(EnumPokemonStage.LEGEND, EnumPokemonType.ROCK, EnumGender.UNKNOWN),
	REGICE(EnumPokemonStage.LEGEND, EnumPokemonType.ICE, EnumGender.UNKNOWN),
	REGISTEEL(EnumPokemonStage.LEGEND, EnumPokemonType.STEEL, EnumGender.UNKNOWN),
	LATIAS(EnumPokemonStage.LEGEND, EnumPokemonType.DRAGON, EnumPokemonType.PSYCHIC, EnumGender.FEMALE),
	LATIOS(EnumPokemonStage.LEGEND, EnumPokemonType.DRAGON, EnumPokemonType.PSYCHIC, EnumGender.MALE),
	KYOGRE(EnumPokemonStage.LEGEND, EnumPokemonType.WATER, EnumGender.UNKNOWN),
	GROUDON(EnumPokemonStage.LEGEND, EnumPokemonType.GROUND, EnumGender.UNKNOWN),
	RAYQUAZA(	EnumPokemonStage.LEGEND,
				EnumPokemonType.DRAGON,
				EnumPokemonType.FLYING,
				EnumGender.UNKNOWN),
	JIRACHI(EnumPokemonStage.MYTHIC, EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	DEOXYS(EnumPokemonStage.MYTHIC, EnumPokemonType.PSYCHIC, FormSetDeoxys.FORM_SET, EnumGender.UNKNOWN),
	// GEN 4
	TURTWIG(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	GROTLE(EnumPokemonStage.MIDDLE, EnumPokemonType.GRASS),
	TORTERRA(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.GROUND),
	CHIMCHAR(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	MONFERNO(EnumPokemonStage.MIDDLE, EnumPokemonType.FIRE, EnumPokemonType.FIGHTING),
	INFERNAPE(EnumPokemonStage.FINAL, EnumPokemonType.FIRE, EnumPokemonType.FIGHTING),
	PIPLUP(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	PRINPLUP(EnumPokemonStage.MIDDLE, EnumPokemonType.WATER),
	EMPOLEON(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.STEEL),
	STARLY(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	STARAVIA(EnumPokemonStage.MIDDLE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	STARAPTOR(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	BIDOOF(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	BIBAREL(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.WATER),
	KRICKETOT(EnumPokemonStage.BASE, EnumPokemonType.BUG),
	KRICKETUNE(EnumPokemonStage.FINAL, EnumPokemonType.BUG),
	SHINX(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC),
	LUXIO(EnumPokemonStage.MIDDLE, EnumPokemonType.ELECTRIC),
	LUXRAY(EnumPokemonStage.FINAL, EnumPokemonType.ELECTRIC),
	BUDEW(EnumPokemonStage.BABY, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	ROSERADE(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	CRANIDOS(EnumPokemonStage.BASE, EnumPokemonType.ROCK),
	RAMPARDOS(EnumPokemonStage.FINAL, EnumPokemonType.ROCK),
	SHIELDON(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.STEEL),
	BASTIODON(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.STEEL),
	BURMY(EnumPokemonStage.BASE, EnumPokemonType.BUG, FormSetBurmyFamily.FORM_SET),
	WORMADAM(	EnumPokemonStage.FINAL,
				EnumPokemonType.BUG,
				EnumPokemonType.GRASS,
				FormSetBurmyFamily.FORM_SET,
				EnumGender.FEMALE),
	MOTHIM(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.FLYING, EnumGender.MALE),
	COMBEE(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.FLYING),
	VESPIQUEN(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.FLYING, EnumGender.FEMALE),
	PACHIRISU(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC),
	BUIZEL(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	FLOATZEL(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	CHERUBI(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	CHERRIM(EnumPokemonStage.FINAL, EnumPokemonType.GRASS), // TODO: Add form if
															// necessary later
	SHELLOS(EnumPokemonStage.BASE, EnumPokemonType.WATER, FormSetShellosFamily.FORM_SET),
	GASTRODON(	EnumPokemonStage.FINAL,
				EnumPokemonType.WATER,
				EnumPokemonType.GROUND,
				FormSetShellosFamily.FORM_SET),
	AMBIPOM(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	DRIFLOON(EnumPokemonStage.BASE, EnumPokemonType.GHOST, EnumPokemonType.FLYING),
	DRIFBLIM(EnumPokemonStage.FINAL, EnumPokemonType.GHOST, EnumPokemonType.FLYING),
	BUNEARY(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	LOPUNNY(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	MISMAGIUS(EnumPokemonStage.FINAL, EnumPokemonType.GHOST),
	HONCHKROW(EnumPokemonStage.FINAL, EnumPokemonType.DARK, EnumPokemonType.FLYING),
	GLAMEOW(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	PURUGLY(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	CHINGLING(EnumPokemonStage.BABY, EnumPokemonType.PSYCHIC),
	STUNKY(EnumPokemonStage.BASE, EnumPokemonType.POISON, EnumPokemonType.DARK),
	SKUNTANK(EnumPokemonStage.FINAL, EnumPokemonType.POISON, EnumPokemonType.DARK),
	BRONZOR(EnumPokemonStage.BASE, EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	BRONZONG(EnumPokemonStage.FINAL, EnumPokemonType.STEEL, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	BONSLY(EnumPokemonStage.BABY, EnumPokemonType.ROCK),
	MIME_JR(EnumPokemonStage.BABY, EnumPokemonType.PSYCHIC, EnumPokemonType.FAIRY, "Mime Jr."),
	HAPPINY(EnumPokemonStage.BABY, EnumPokemonType.NORMAL, EnumGender.FEMALE),
	CHATOT(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	SPIRITOMB(EnumPokemonStage.BASE, EnumPokemonType.GHOST, EnumPokemonType.DARK),
	GIBLE(EnumPokemonStage.BASE, EnumPokemonType.DRAGON, EnumPokemonType.GROUND),
	GABITE(EnumPokemonStage.MIDDLE, EnumPokemonType.DRAGON, EnumPokemonType.GROUND),
	GARCHOMP(EnumPokemonStage.FINAL, EnumPokemonType.DRAGON, EnumPokemonType.GROUND),
	MUNCHLAX(EnumPokemonStage.BABY, EnumPokemonType.NORMAL),
	RIOLU(EnumPokemonStage.BABY, EnumPokemonType.FIGHTING),
	LUCARIO(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING, EnumPokemonType.STEEL),
	HIPPOPOTAS(EnumPokemonStage.BASE, EnumPokemonType.GROUND),
	HIPPOWDON(EnumPokemonStage.FINAL, EnumPokemonType.GROUND),
	SKORUPI(EnumPokemonStage.BASE, EnumPokemonType.POISON, EnumPokemonType.BUG),
	DRAPION(EnumPokemonStage.FINAL, EnumPokemonType.POISON, EnumPokemonType.DARK),
	CROAGUNK(EnumPokemonStage.BASE, EnumPokemonType.POISON, EnumPokemonType.FIGHTING),
	TOXICROAK(EnumPokemonStage.FINAL, EnumPokemonType.POISON, EnumPokemonType.FIGHTING),
	CARNIVINE(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	FINNEON(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	LUMINEON(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	MANTYKE(EnumPokemonStage.BABY, EnumPokemonType.WATER, EnumPokemonType.FLYING),
	SNOVER(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.ICE),
	ABOMASNOW(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.ICE),
	WEAVILE(EnumPokemonStage.FINAL, EnumPokemonType.DARK, EnumPokemonType.ICE),
	MAGNEZONE(	EnumPokemonStage.FINAL,
				EnumPokemonType.ELECTRIC,
				EnumPokemonType.STEEL,
				EnumGender.UNKNOWN),
	LICKILICKY(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	RHYPERIOR(EnumPokemonStage.FINAL, EnumPokemonType.GROUND, EnumPokemonType.ROCK),
	TANGROWTH(EnumPokemonStage.FINAL, EnumPokemonType.GRASS),
	ELECTIVIRE(EnumPokemonStage.FINAL, EnumPokemonType.ELECTRIC),
	MAGMORTAR(EnumPokemonStage.FINAL, EnumPokemonType.FIRE),
	TOGEKISS(EnumPokemonStage.FINAL, EnumPokemonType.FAIRY, EnumPokemonType.FLYING),
	YANMEGA(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.FLYING),
	LEAFEON(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, FormSetEevee.FORM_SET),
	GLACEON(EnumPokemonStage.FINAL, EnumPokemonType.ICE, FormSetEevee.FORM_SET),
	GLISCOR(EnumPokemonStage.FINAL, EnumPokemonType.GROUND, EnumPokemonType.FLYING),
	MAMOSWINE(EnumPokemonStage.FINAL, EnumPokemonType.ICE, EnumPokemonType.GROUND),
	PORYGON_Z(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, "Porygon-Z", EnumGender.UNKNOWN),
	GALLADE(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC, EnumPokemonType.FIGHTING, EnumGender.MALE),
	PROBOPASS(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.STEEL),
	DUSKNOIR(EnumPokemonStage.FINAL, EnumPokemonType.GHOST),
	FROSLASS(EnumPokemonStage.FINAL, EnumPokemonType.ICE, EnumPokemonType.GHOST, EnumGender.FEMALE),
	ROTOM(	EnumPokemonStage.BASE,
			EnumPokemonType.ELECTRIC,
			EnumPokemonType.GHOST,
			FormSetRotom.FORM_SET,
			EnumGender.UNKNOWN),
	UXIE(EnumPokemonStage.LEGEND, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	MESPRIT(EnumPokemonStage.LEGEND, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	AZELF(EnumPokemonStage.LEGEND, EnumPokemonType.PSYCHIC, EnumGender.UNKNOWN),
	DIALGA(EnumPokemonStage.LEGEND, EnumPokemonType.STEEL, EnumPokemonType.DRAGON, EnumGender.UNKNOWN),
	PALKIA(EnumPokemonStage.LEGEND, EnumPokemonType.WATER, EnumPokemonType.DRAGON, EnumGender.UNKNOWN),
	HEATRAN(EnumPokemonStage.LEGEND, EnumPokemonType.FIRE, EnumPokemonType.STEEL),
	REGIGIGAS(EnumPokemonStage.LEGEND, EnumPokemonType.NORMAL, EnumGender.UNKNOWN),
	GIRATINA(	EnumPokemonStage.LEGEND,
				EnumPokemonType.GHOST,
				EnumPokemonType.DRAGON,
				FormSetGiratina.FORM_SET,
				EnumGender.UNKNOWN),
	CRESSELIA(EnumPokemonStage.LEGEND, EnumPokemonType.PSYCHIC, EnumGender.FEMALE),
	PHIONE(EnumPokemonStage.MYTHIC, EnumPokemonType.WATER, EnumGender.UNKNOWN),
	MANAPHY(EnumPokemonStage.MYTHIC, EnumPokemonType.WATER, EnumGender.UNKNOWN),
	DARKRAI(EnumPokemonStage.MYTHIC, EnumPokemonType.DARK, EnumGender.UNKNOWN),
	SHAYMIN(EnumPokemonStage.MYTHIC, EnumPokemonType.GRASS, EnumGender.UNKNOWN),
	ARCEUS(EnumPokemonStage.MYTHIC, EnumPokemonType.NORMAL, FormSetArceus.FORM_SET, EnumGender.UNKNOWN),
	// TODO: GEN 5 genders, forms
	VICTINI(EnumPokemonStage.MYTHIC, EnumPokemonType.PSYCHIC, EnumPokemonType.FIRE),
	SNIVY(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	SERVINE(EnumPokemonStage.MIDDLE, EnumPokemonType.GRASS),
	SERPERIOR(EnumPokemonStage.FINAL, EnumPokemonType.GRASS),
	TEPIG(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	PIGNITE(EnumPokemonStage.MIDDLE, EnumPokemonType.FIRE, EnumPokemonType.FIGHTING),
	EMBOAR(EnumPokemonStage.FINAL, EnumPokemonType.FIRE, EnumPokemonType.FIGHTING),
	OSHAWOTT(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	DEWOTT(EnumPokemonStage.MIDDLE, EnumPokemonType.WATER),
	SAMUROTT(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	PARTAR(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	WATCHOG(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	LILLIPUP(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	HERDIER(EnumPokemonStage.MIDDLE, EnumPokemonType.NORMAL),
	STOUTLAND(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	PURRLOIN(EnumPokemonStage.BASE, EnumPokemonType.DARK),
	LIEPARD(EnumPokemonStage.FINAL, EnumPokemonType.DARK),
	PANSAGE(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	SIMISAGE(EnumPokemonStage.FINAL, EnumPokemonType.GRASS),
	PANSEAR(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	SIMISEAR(EnumPokemonStage.FINAL, EnumPokemonType.FIRE),
	PANPOUR(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	SIMIPOUR(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	MUNNA(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC),
	MASHARNA(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC),
	PIDOVE(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	TRAQUILL(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	UNFEZANT(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	BLITZLE(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC),
	ZEBSTRIKA(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC),
	ROGGENROLA(EnumPokemonStage.BASE, EnumPokemonType.ROCK),
	BOLDORE(EnumPokemonStage.MIDDLE, EnumPokemonType.ROCK),
	GIGALITH(EnumPokemonStage.FINAL, EnumPokemonType.ROCK),
	WOOBAT(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING),
	SWOOBAT(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING),
	DRILBUR(EnumPokemonStage.BASE, EnumPokemonType.GROUND),
	EXCADRILL(EnumPokemonStage.FINAL, EnumPokemonType.GROUND, EnumPokemonType.STEEL),
	AUDINO(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	TIMBURR(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING),
	GURDURR(EnumPokemonStage.MIDDLE, EnumPokemonType.FIGHTING),
	CONKELDURR(EnumPokemonStage.FINAL, EnumPokemonType.FIGHTING),
	TYMPOLE(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	PALPITOAD(EnumPokemonStage.MIDDLE, EnumPokemonType.WATER, EnumPokemonType.GROUND),
	SEISMITOAD(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.GROUND),
	THROH(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING),
	SAWK(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING),
	SEWADDLE(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.GRASS),
	SWADLOON(EnumPokemonStage.MIDDLE, EnumPokemonType.BUG, EnumPokemonType.GRASS),
	LEAVANNY(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.GRASS),
	VENIPEDE(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.POISON),
	WHIRLIPEDE(EnumPokemonStage.MIDDLE, EnumPokemonType.BUG, EnumPokemonType.POISON),
	SCOLIPEDE(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.POISON),
	COTTONEE(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.FAIRY),
	WHIMSICOTT(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.FAIRY),
	PETILIL(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	LILLIGANT(EnumPokemonStage.FINAL, EnumPokemonType.GRASS),
	BASCULIN(EnumPokemonStage.BASE, EnumPokemonType.WATER), // forms
	SANDILE(EnumPokemonStage.BASE, EnumPokemonType.GROUND, EnumPokemonType.DARK),
	KROKOROK(EnumPokemonStage.MIDDLE, EnumPokemonType.GROUND, EnumPokemonType.DARK),
	KROOKODILE(EnumPokemonStage.FINAL, EnumPokemonType.GROUND, EnumPokemonType.DARK),
	DARUMAKA(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	DARMANITAN(EnumPokemonStage.FINAL, EnumPokemonType.FIRE), // forms
	MARACTUS(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	DWEBBLE(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.ROCK),
	CRUSTLE(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.ROCK),
	SCRAGGY(EnumPokemonStage.BASE, EnumPokemonType.DARK, EnumPokemonType.FIGHTING),
	SCRAFTY(EnumPokemonStage.FINAL, EnumPokemonType.DARK, EnumPokemonType.FIGHTING),
	SIGILYPH(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC, EnumPokemonType.FLYING),
	YANMASK(EnumPokemonStage.BASE, EnumPokemonType.GHOST),
	COFAGRIGUS(EnumPokemonStage.FINAL, EnumPokemonType.GHOST),
	TIRTOUGA(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.ROCK),
	CARRACOSTA(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.ROCK),
	ARCHEN(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.FLYING),
	ARCHEOPS(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.FLYING),
	TRUBBISH(EnumPokemonStage.BASE, EnumPokemonType.POISON),
	GARBODOR(EnumPokemonStage.FINAL, EnumPokemonType.POISON),
	ZORUA(EnumPokemonStage.BASE, EnumPokemonType.DARK),
	ZOROARK(EnumPokemonStage.FINAL, EnumPokemonType.DARK),
	MINCCINO(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	CINCCINO(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	GOTHITA(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC),
	GOTHORITA(EnumPokemonStage.MIDDLE, EnumPokemonType.PSYCHIC),
	GOTHITELLE(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC),
	SOLOSIS(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC),
	DUOSION(EnumPokemonStage.MIDDLE, EnumPokemonType.PSYCHIC),
	REUNICLUS(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC),
	DUCKLETT(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.FLYING),
	SWANNA(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.FLYING),
	VANILLITE(EnumPokemonStage.BASE, EnumPokemonType.ICE),
	VANILLISH(EnumPokemonStage.MIDDLE, EnumPokemonType.ICE),
	VANILLUXE(EnumPokemonStage.FINAL, EnumPokemonType.ICE),
	DEERLING(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.GRASS), // forms
	SAWSBUCK(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.GRASS), // forms
	EMOLGA(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC, EnumPokemonType.FLYING),
	KARRABLAST(EnumPokemonStage.BASE, EnumPokemonType.BUG),
	ESCAVALIER(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.STEEL),
	FOONGUS(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	AMOONGUSS(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.POISON),
	FRILLISH(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.GHOST),
	JELLICENT(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.GHOST),
	ALOMOMOLA(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	JOLTIK(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.ELECTRIC),
	GALVANTULA(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.ELECTRIC),
	FERROSEED(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.STEEL),
	FERROTHORN(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.STEEL),
	KLINK(EnumPokemonStage.BASE, EnumPokemonType.STEEL),
	KLANG(EnumPokemonStage.MIDDLE, EnumPokemonType.STEEL),
	KLINKLANG(EnumPokemonStage.FINAL, EnumPokemonType.STEEL),
	TYNAMO(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC),
	EELETRIK(EnumPokemonStage.MIDDLE, EnumPokemonType.ELECTRIC),
	EELEKTROSS(EnumPokemonStage.FINAL, EnumPokemonType.ELECTRIC),
	ELGYEM(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC),
	BEHEEYM(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC),
	LITWICK(EnumPokemonStage.BASE, EnumPokemonType.GHOST, EnumPokemonType.FIRE),
	LAMPENT(EnumPokemonStage.MIDDLE, EnumPokemonType.GHOST, EnumPokemonType.FIRE),
	CHANDELURE(EnumPokemonStage.FINAL, EnumPokemonType.GHOST, EnumPokemonType.FIRE),
	AXEW(EnumPokemonStage.BASE, EnumPokemonType.DRAGON),
	FRAXURE(EnumPokemonStage.MIDDLE, EnumPokemonType.DRAGON),
	HAXORUS(EnumPokemonStage.FINAL, EnumPokemonType.DRAGON),
	CUBCHOO(EnumPokemonStage.BASE, EnumPokemonType.ICE),
	BEARTIC(EnumPokemonStage.FINAL, EnumPokemonType.ICE),
	CRYOGONAL(EnumPokemonStage.BASE, EnumPokemonType.ICE),
	SHELMET(EnumPokemonStage.BASE, EnumPokemonType.BUG),
	ACCELGOR(EnumPokemonStage.FINAL, EnumPokemonType.BUG),
	STUNFISK(EnumPokemonStage.BASE, EnumPokemonType.GROUND, EnumPokemonType.ELECTRIC),
	MIENFOO(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING),
	MIENSHAO(EnumPokemonStage.FINAL, EnumPokemonType.FIGHTING),
	DRUDDIGON(EnumPokemonStage.BASE, EnumPokemonType.DRAGON),
	GOLETT(EnumPokemonStage.BASE, EnumPokemonType.GROUND, EnumPokemonType.GHOST),
	GOLURK(EnumPokemonStage.FINAL, EnumPokemonType.GROUND, EnumPokemonType.GHOST),
	PAWNIARD(EnumPokemonStage.BASE, EnumPokemonType.DARK, EnumPokemonType.STEEL),
	BISHARP(EnumPokemonStage.FINAL, EnumPokemonType.DARK, EnumPokemonType.STEEL),
	BOUFFALANT(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	RUFFLET(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	BRAVIARY(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	VULLABY(EnumPokemonStage.BASE, EnumPokemonType.DARK, EnumPokemonType.FLYING),
	MANDIBUZZ(EnumPokemonStage.FINAL, EnumPokemonType.DARK, EnumPokemonType.FLYING),
	HEATMOR(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	DURANT(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.STEEL),
	DEINO(EnumPokemonStage.BASE, EnumPokemonType.DARK, EnumPokemonType.DRAGON),
	ZWEILOUS(EnumPokemonStage.MIDDLE, EnumPokemonType.DARK, EnumPokemonType.DRAGON),
	HYDREIGON(EnumPokemonStage.FINAL, EnumPokemonType.DARK, EnumPokemonType.DRAGON),
	LARVESTA(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.FIRE),
	VOLCARONA(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.FIRE),
	COBALION(EnumPokemonStage.LEGEND, EnumPokemonType.STEEL, EnumPokemonType.FIGHTING),
	TERRAKION(EnumPokemonStage.LEGEND, EnumPokemonType.ROCK, EnumPokemonType.FIGHTING),
	VIRIZION(EnumPokemonStage.LEGEND, EnumPokemonType.GRASS, EnumPokemonType.FIGHTING),
	TORNADUS(EnumPokemonStage.LEGEND, EnumPokemonType.FLYING), // forms
	THUNDURUS(EnumPokemonStage.LEGEND, EnumPokemonType.ELECTRIC, EnumPokemonType.FLYING), // forms
	RESHIRAM(EnumPokemonStage.LEGEND, EnumPokemonType.DRAGON, EnumPokemonType.FIRE),
	ZEKROM(EnumPokemonStage.LEGEND, EnumPokemonType.DRAGON, EnumPokemonType.ELECTRIC),
	LANDORUS(EnumPokemonStage.LEGEND, EnumPokemonType.GROUND, EnumPokemonType.FLYING), // forms
	KYUREM(EnumPokemonStage.LEGEND, EnumPokemonType.DRAGON, EnumPokemonType.ICE),
	KELDEO(EnumPokemonStage.MYTHIC, EnumPokemonType.WATER, EnumPokemonType.FIGHTING),
	MELOETTA(EnumPokemonStage.MYTHIC, EnumPokemonType.NORMAL, EnumPokemonType.PSYCHIC), // forms
	GENESECT(EnumPokemonStage.MYTHIC, EnumPokemonType.BUG, EnumPokemonType.STEEL),
	// TODO: GEN 6 genders, forms
	CHESPIN(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	QUILLADIN(EnumPokemonStage.MIDDLE, EnumPokemonType.GRASS),
	CHESNAUGHT(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.FIGHTING),
	FENNEKIN(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	BRAIXEN(EnumPokemonStage.MIDDLE, EnumPokemonType.FIRE),
	DELPHOX(EnumPokemonStage.FINAL, EnumPokemonType.FIRE),
	FROAKIE(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	FROGADIER(EnumPokemonStage.MIDDLE, EnumPokemonType.WATER),
	GRENINJA(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.DARK),
	BUNNELBY(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	DIGGERSBY(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.GROUND),
	FLETCHLING(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	FLETCHINDER(EnumPokemonStage.MIDDLE, EnumPokemonType.FIRE, EnumPokemonType.FLYING),
	TALONFLAME(EnumPokemonStage.FINAL, EnumPokemonType.FIRE, EnumPokemonType.FLYING),
	SCATTERBUG(EnumPokemonStage.BASE, EnumPokemonType.BUG),
	SPEWPA(EnumPokemonStage.FINAL, EnumPokemonType.BUG),
	VIVILLON(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.FLYING),
	LITEO(EnumPokemonStage.BASE, EnumPokemonType.FIRE, EnumPokemonType.NORMAL),
	PYROAR(EnumPokemonStage.FINAL, EnumPokemonType.FIRE, EnumPokemonType.NORMAL),
	FLABEBE(EnumPokemonStage.BASE, EnumPokemonType.FAIRY, "Flabébé"),
	FLOETTE(EnumPokemonStage.MIDDLE, EnumPokemonType.FAIRY),
	FLORGES(EnumPokemonStage.FINAL, EnumPokemonType.FAIRY),
	SKIDDO(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	GOGOAT(EnumPokemonStage.FINAL, EnumPokemonType.GRASS),
	PANCHAM(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING),
	PANGORO(EnumPokemonStage.FINAL, EnumPokemonType.FIGHTING, EnumPokemonType.DARK),
	FURFROU(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	ESPURR(EnumPokemonStage.BASE, EnumPokemonType.PSYCHIC),
	MEOWSTIC(EnumPokemonStage.FINAL, EnumPokemonType.PSYCHIC),
	HONEDGE(EnumPokemonStage.BASE, EnumPokemonType.STEEL, EnumPokemonType.GHOST),
	DOUBLATE(EnumPokemonStage.MIDDLE, EnumPokemonType.STEEL, EnumPokemonType.GHOST),
	AEGISLASH(EnumPokemonStage.FINAL, EnumPokemonType.STEEL, EnumPokemonType.GHOST),
	SPRITZEE(EnumPokemonStage.BASE, EnumPokemonType.FAIRY),
	AROMATISSE(EnumPokemonStage.FINAL, EnumPokemonType.FAIRY),
	SWIRLIX(EnumPokemonStage.BASE, EnumPokemonType.FAIRY),
	SLURPUFF(EnumPokemonStage.FINAL, EnumPokemonType.FAIRY),
	INKAY(EnumPokemonStage.BASE, EnumPokemonType.DARK, EnumPokemonType.PSYCHIC),
	MALMAR(EnumPokemonStage.FINAL, EnumPokemonType.DARK, EnumPokemonType.PSYCHIC),
	BINACLE(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.WATER),
	BARBARACLE(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.WATER),
	SKRELP(EnumPokemonStage.BASE, EnumPokemonType.POISON, EnumPokemonType.WATER),
	DRAGALGE(EnumPokemonStage.FINAL, EnumPokemonType.POISON, EnumPokemonType.DRAGON),
	CLAUNCHER(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	CLAWITZER(EnumPokemonStage.FINAL, EnumPokemonType.WATER),
	HELIOPTILE(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC, EnumPokemonType.NORMAL),
	HELIOLISK(EnumPokemonStage.FINAL, EnumPokemonType.ELECTRIC, EnumPokemonType.NORMAL),
	TYRUNT(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.DRAGON),
	TYRANTRUM(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.DRAGON),
	AMAURA(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.ICE),
	AURORUS(EnumPokemonStage.FINAL, EnumPokemonType.ROCK, EnumPokemonType.ICE),
	SYLVEON(EnumPokemonStage.FINAL, EnumPokemonType.FAIRY, FormSetEevee.FORM_SET),
	HAWLUNCHA(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING, EnumPokemonType.FLYING),
	DEDENNE(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC, EnumPokemonType.FAIRY),
	CARBINK(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.FAIRY),
	GOOMY(EnumPokemonStage.BASE, EnumPokemonType.DRAGON),
	SLIGGOO(EnumPokemonStage.MIDDLE, EnumPokemonType.DRAGON),
	GOODRA(EnumPokemonStage.FINAL, EnumPokemonType.DRAGON),
	KLEFKI(EnumPokemonStage.BASE, EnumPokemonType.STEEL, EnumPokemonType.FAIRY),
	PHANTUMP(EnumPokemonStage.BASE, EnumPokemonType.GHOST, EnumPokemonType.GRASS),
	TREVENANT(EnumPokemonStage.FINAL, EnumPokemonType.GHOST, EnumPokemonType.GRASS),
	PUMPKABOO(EnumPokemonStage.BASE, EnumPokemonType.GHOST, EnumPokemonType.GRASS), // forms
	GOURGEIST(EnumPokemonStage.FINAL, EnumPokemonType.GHOST, EnumPokemonType.GRASS), // forms
	BERGMITE(EnumPokemonStage.BASE, EnumPokemonType.ICE),
	AVALUGG(EnumPokemonStage.FINAL, EnumPokemonType.ICE),
	NOIBAT(EnumPokemonStage.BASE, EnumPokemonType.FLYING, EnumPokemonType.DRAGON),
	NOIVERN(EnumPokemonStage.FINAL, EnumPokemonType.FLYING, EnumPokemonType.DRAGON),
	XERNEAS(EnumPokemonStage.LEGEND, EnumPokemonType.FAIRY),
	YVELTAL(EnumPokemonStage.LEGEND, EnumPokemonType.DARK, EnumPokemonType.FLYING),
	ZYGARDE(EnumPokemonStage.LEGEND, EnumPokemonType.DRAGON, EnumPokemonType.GROUND),
	DIANCIE(EnumPokemonStage.MYTHIC, EnumPokemonType.ROCK, EnumPokemonType.FAIRY),
	HOOPA(EnumPokemonStage.MYTHIC, EnumPokemonType.PSYCHIC, EnumPokemonType.GHOST), // forms
	VOLCANION(EnumPokemonStage.MYTHIC, EnumPokemonType.FIRE, EnumPokemonType.WATER),
	// TODO: GEN 7 genders, forms
	ROWLET(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.FLYING),
	DARTRIX(EnumPokemonStage.MIDDLE, EnumPokemonType.GRASS, EnumPokemonType.FLYING),
	DECIDUEYE(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.GHOST),
	LITTEN(EnumPokemonStage.BASE, EnumPokemonType.FIRE),
	TORRACAT(EnumPokemonStage.MIDDLE, EnumPokemonType.FIRE),
	INCINEROAR(EnumPokemonStage.FINAL, EnumPokemonType.FIRE, EnumPokemonType.DARK),
	POPPLIO(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	BRIONNE(EnumPokemonStage.MIDDLE, EnumPokemonType.WATER),
	PRIMARINA(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.FAIRY),
	PIKIPEK(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	TRUMBEAK(EnumPokemonStage.MIDDLE, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	TOUCANNON(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.FLYING),
	YUNGOOS(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	GUMSHOOS(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL),
	GRUBBIN(EnumPokemonStage.BASE, EnumPokemonType.BUG),
	CHARJABUG(EnumPokemonStage.MIDDLE, EnumPokemonType.BUG, EnumPokemonType.ELECTRIC),
	VIKAVOLT(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.ELECTRIC),
	CRABRAWLER(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING),
	CRABOMINABLE(EnumPokemonStage.FINAL, EnumPokemonType.FIGHTING, EnumPokemonType.ICE),
	ORICORIO(EnumPokemonStage.BASE, EnumPokemonType.FIRE, EnumPokemonType.FLYING), // forms
	CUTIEFLY(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.FAIRY),
	RIBOMBEE(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.FAIRY),
	ROCKRUFF(EnumPokemonStage.BASE, EnumPokemonType.ROCK),
	LYCANROC(EnumPokemonStage.FINAL, EnumPokemonType.ROCK), // forms
	WISHIWASHI(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	MAREANIE(EnumPokemonStage.BASE, EnumPokemonType.POISON, EnumPokemonType.WATER),
	TOXAPEX(EnumPokemonStage.FINAL, EnumPokemonType.POISON, EnumPokemonType.WATER),
	MUDBRAY(EnumPokemonStage.BASE, EnumPokemonType.GROUND),
	MUDSDALE(EnumPokemonStage.FINAL, EnumPokemonType.GROUND),
	DEWPIDER(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.BUG),
	ARAQUANID(EnumPokemonStage.FINAL, EnumPokemonType.WATER, EnumPokemonType.BUG),
	FORMANTIS(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	LURANTIS(EnumPokemonStage.FINAL, EnumPokemonType.GRASS),
	MORELULL(EnumPokemonStage.BASE, EnumPokemonType.GRASS, EnumPokemonType.FAIRY),
	SHIINOTIC(EnumPokemonStage.FINAL, EnumPokemonType.GRASS, EnumPokemonType.FAIRY),
	SALANDIT(EnumPokemonStage.BASE, EnumPokemonType.POISON, EnumPokemonType.FIRE),
	SALAZZLE(EnumPokemonStage.FINAL, EnumPokemonType.POISON, EnumPokemonType.FIRE),
	STUFFUL(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.FIGHTING),
	BEWEAR(EnumPokemonStage.FINAL, EnumPokemonType.NORMAL, EnumPokemonType.FIGHTING),
	BOUNSWEET(EnumPokemonStage.BASE, EnumPokemonType.GRASS),
	STEENEE(EnumPokemonStage.MIDDLE, EnumPokemonType.GRASS),
	TSAREENA(EnumPokemonStage.FINAL, EnumPokemonType.GRASS),
	COMFEY(EnumPokemonStage.BASE, EnumPokemonType.FAIRY),
	ORANGURU(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.PSYCHIC),
	PASSIMIAN(EnumPokemonStage.BASE, EnumPokemonType.FIGHTING),
	WIMPOD(EnumPokemonStage.BASE, EnumPokemonType.BUG, EnumPokemonType.WATER),
	GOLISOPOD(EnumPokemonStage.FINAL, EnumPokemonType.BUG, EnumPokemonType.WATER),
	SANDYGAST(EnumPokemonStage.BASE, EnumPokemonType.GHOST, EnumPokemonType.GROUND),
	PALOSSAND(EnumPokemonStage.FINAL, EnumPokemonType.GHOST, EnumPokemonType.GROUND),
	PYUKUMUKU(EnumPokemonStage.BASE, EnumPokemonType.WATER),
	TYPE_NULL(EnumPokemonStage.LEGEND, EnumPokemonType.NORMAL, "Type: Null"),
	SILVALLY(EnumPokemonStage.LEGEND, EnumPokemonType.NORMAL),
	MINIOR(EnumPokemonStage.BASE, EnumPokemonType.ROCK, EnumPokemonType.FLYING),
	KOMALA(EnumPokemonStage.BASE, EnumPokemonType.NORMAL),
	TURTONATOR(EnumPokemonStage.BASE, EnumPokemonType.FIRE, EnumPokemonType.DRAGON),
	TOGEDEMARU(EnumPokemonStage.BASE, EnumPokemonType.ELECTRIC, EnumPokemonType.STEEL),
	MIMIKYU(EnumPokemonStage.BASE, EnumPokemonType.GHOST, EnumPokemonType.FAIRY),
	BRUXISH(EnumPokemonStage.BASE, EnumPokemonType.WATER, EnumPokemonType.PSYCHIC),
	DRAMPA(EnumPokemonStage.BASE, EnumPokemonType.NORMAL, EnumPokemonType.DRAGON),
	DHELMISE(EnumPokemonStage.BASE, EnumPokemonType.GHOST, EnumPokemonType.GRASS),
	JANGMO_O(EnumPokemonStage.BASE, EnumPokemonType.DRAGON, "Jangmo-o"),
	HAKAMO_O(EnumPokemonStage.MIDDLE, EnumPokemonType.DRAGON, "Hakamo-o"),
	KOMMO_O(EnumPokemonStage.FINAL, EnumPokemonType.DRAGON, "Kommo-o"),
	TAPU_KOKO(EnumPokemonStage.LEGEND, EnumPokemonType.ELECTRIC, EnumPokemonType.FAIRY, "Tapu Koko"),
	TAPU_LELE(EnumPokemonStage.LEGEND, EnumPokemonType.PSYCHIC, EnumPokemonType.FAIRY, "Tapu Lele"),
	TAPU_BULU(EnumPokemonStage.LEGEND, EnumPokemonType.GRASS, EnumPokemonType.FAIRY, "Tapu Bulu"),
	TAPU_FINI(EnumPokemonStage.LEGEND, EnumPokemonType.WATER, EnumPokemonType.FAIRY, "Tapu Fini"),
	COSMOG(EnumPokemonStage.LEGEND, EnumPokemonType.PSYCHIC),
	COSMOEM(EnumPokemonStage.LEGEND, EnumPokemonType.PSYCHIC),
	SOLGALEO(EnumPokemonStage.LEGEND, EnumPokemonType.PSYCHIC, EnumPokemonType.STEEL),
	LUNALA(EnumPokemonStage.LEGEND, EnumPokemonType.PSYCHIC, EnumPokemonType.GHOST),
	NIHILEGO(EnumPokemonStage.LEGEND, EnumPokemonType.ROCK, EnumPokemonType.POISON),
	BUZZWOLE(EnumPokemonStage.LEGEND, EnumPokemonType.BUG, EnumPokemonType.FIGHTING),
	PHEROMOSA(EnumPokemonStage.LEGEND, EnumPokemonType.BUG, EnumPokemonType.FIGHTING),
	XURKITREE(EnumPokemonStage.LEGEND, EnumPokemonType.ELECTRIC),
	CELESTEELA(EnumPokemonStage.LEGEND, EnumPokemonType.STEEL, EnumPokemonType.FLYING),
	KARTANA(EnumPokemonStage.LEGEND, EnumPokemonType.GRASS, EnumPokemonType.STEEL),
	GUZZLORD(EnumPokemonStage.LEGEND, EnumPokemonType.DARK, EnumPokemonType.DRAGON),
	NECROZMA(EnumPokemonStage.LEGEND, EnumPokemonType.PSYCHIC), // forms
	MAGEARNA(EnumPokemonStage.MYTHIC, EnumPokemonType.STEEL, EnumPokemonType.FAIRY), // forms
	MARSHADOW(EnumPokemonStage.MYTHIC, EnumPokemonType.FIGHTING, EnumPokemonType.GHOST),
	POIPOLE(EnumPokemonStage.LEGEND, EnumPokemonType.POISON),
	NAGANADEL(EnumPokemonStage.LEGEND, EnumPokemonType.POISON, EnumPokemonType.DRAGON),
	STAKATAKA(EnumPokemonStage.LEGEND, EnumPokemonType.ROCK, EnumPokemonType.STEEL),
	BLACEPHALON(EnumPokemonStage.MYTHIC, EnumPokemonType.FIRE, EnumPokemonType.GHOST),
	ZERAORA(EnumPokemonStage.MYTHIC, EnumPokemonType.ELECTRIC),
	// MELTAN (gen 7?)
	MELTAN(EnumPokemonStage.MYTHIC, EnumPokemonType.STEEL),
	MELMETAL(EnumPokemonStage.MYTHIC, EnumPokemonType.STEEL),;

	private String name;
	private EnumPokemonStage stage;
	private EnumPokemonType type1;
	private EnumPokemonType type2;
	private FormSet forms;
	private EnumGender gender;
	private EnumPokemon[] family;

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, EnumPokemon... family) {
		this(stage, type1, (EnumPokemonType) null, family);
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, String name, EnumPokemon... family) {
		this(stage, type1, (EnumPokemonType) null, name, family);
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, String name, EnumGender gender,
			EnumPokemon... family) {
		this(stage, type1, (EnumPokemonType) null, name, gender, family);
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, FormSet formSet, EnumPokemon... family) {
		this(stage, type1, (EnumPokemonType) null, formSet, family);
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, FormSet formSet, EnumGender gender,
			EnumPokemon... family) {
		this(stage, type1, (EnumPokemonType) null, formSet, gender, family);
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, String name, FormSet formSet,
			EnumPokemon... family) {
		this(stage, type1, (EnumPokemonType) null, name, formSet, family);
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, String name, FormSet formSet,
			EnumGender gender, EnumPokemon... family) {
		this(stage, type1, (EnumPokemonType) null, name, formSet, family);
		this.gender = gender;
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, EnumGender gender,
			EnumPokemon... family) {
		this(stage, type1, (EnumPokemonType) null, gender, family);
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, EnumPokemonType type2,
			EnumPokemon... family) {
		name = this.name();
		name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase();
		this.stage = stage;
		this.type1 = type1;
		this.type2 = type2;
		this.gender = EnumGender.EITHER;
		this.family = family;
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, EnumPokemonType type2, String name,
			EnumPokemon... family) {
		this.name = name;
		this.stage = stage;
		this.type1 = type1;
		this.type2 = type2;
		this.gender = EnumGender.EITHER;
		this.family = family;
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, EnumPokemonType type2, String name,
			EnumGender gender, EnumPokemon... family) {
		this(stage, type1, type2, name);
		this.gender = gender;
		this.family = family;
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, EnumPokemonType type2, FormSet formSet,
			EnumPokemon... family) {
		this(stage, type1, type2);
		this.forms = formSet;
		this.gender = EnumGender.EITHER;
		this.family = family;
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, EnumPokemonType type2, FormSet formSet,
			EnumGender gender, EnumPokemon... family) {
		this(stage, type1, type2, formSet);
		this.gender = gender;
		this.family = family;
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, EnumPokemonType type2, String name,
			FormSet formSet, EnumPokemon... family) {
		this(stage, type1, type2, name);
		this.forms = formSet;
		this.gender = EnumGender.EITHER;
		this.family = family;
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, EnumPokemonType type2, String name,
			FormSet formSet, EnumGender gender, EnumPokemon... family) {
		this(stage, type1, type2, name, formSet);
		this.gender = gender;
		this.family = family;
	}

	EnumPokemon(EnumPokemonStage stage, EnumPokemonType type1, EnumPokemonType type2, EnumGender gender,
			EnumPokemon... family) {
		this(stage, type1, type2);
		this.gender = gender;
		this.family = family;
	}

	public String getName() {
		return this.name;
	}

	public EnumPokemonStage getStage() {
		return this.stage;
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

	public boolean isLegendary() {
		return stage == EnumPokemonStage.LEGEND;
	}

	public boolean isMythical() {
		return stage == EnumPokemonStage.MYTHIC;
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
		return stage == EnumPokemonStage.BABY;
	}

	// TODO: update when new evolutions come out
	public boolean evolvesWithItem() {
		switch (this) {
		// gen 1
		case GLOOM:
		case POLIWHIRL:
		case SLOWPOKE:
		case ONIX:
		case LICKITUNG:
		case RHYDON:
		case TANGELA:
		case SEADRA:
		case SCYTHER:
		case ELECTABUZZ:
		case MAGMAR:
		case PORYGON:
			return true;
		// gen 2
		case TOGETIC:
		case AIPOM:
		case SUNKERN:
		case YANMA:
		case MURKROW:
		case MISDREAVUS:
		case GLIGAR:
		case SNEASEL:
		case PORYGON2:
			return true;
		// gen 3
		case KIRLIA:
		case ROSELIA:
		case DUSCLOPS:
		case SNORUNT:
		case MAMOSWINE:
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

	public static EnumPokemon getRandomPokemon(Random rand) {
		checkCaches();
		List<EnumPokemon> avail = AVAILABLE.getValue();
		return avail.get(rand.nextInt(avail.size()));
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
