package us.myles_selim.starota.forms;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.forms.FormSet.DefaultFormSet;
import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;
import us.myles_selim.starota.misc.utils.StarotaConstants;

public class FormManager {

	private static final Gson GSON = new Gson();
	private static final String FORMS_URL = "https://raw.githubusercontent.com/Selim042/Misc-PoGo-Stuffs/master/forms.json";

	private static CachedData<HashMap<EnumPokemon, FormSet>> FORMS;

	private static void checkCaches() {
		if (FORMS != null && FORMS.hasPassed(3600000 * 12)) // 12 hrs
			return;
		try {
			URL url = new URL(FORMS_URL);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			FORMS = new CachedData<>(GSON.fromJson(new InputStreamReader(conn.getInputStream()),
					new TypeToken<Map<EnumPokemon, FormSet>>() { /* */ }.getType()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static FormSet getForms(EnumPokemon pokemon) {
		if (pokemon == null)
			return null;
		checkCaches();
		if (FORMS.getValue().containsKey(pokemon))
			return FORMS.getValue().get(pokemon);
		FormSet set = new DefaultFormSet(pokemon);
		FORMS.getValue().put(pokemon, set);
		return FORMS.getValue().get(pokemon);
	}

	@ClearCache("forms")
	public static void dumpCache() {
		FORMS = null;
	}

	// public static void main(String... args) {
	// Gson gson = new GsonBuilder().setPrettyPrinting().create();
	// PrintStream file;
	// try {
	// file = new PrintStream(new File("FormManager.json"));
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// return;
	// }
	// Map<String, FormSet> allForms = new HashMap<>();
	// for (EnumPokemonOld pokemon : EnumPokemonOld.values()) {
	// if (pokemon.getFormSet() == null)
	// continue;
	// EnumPokemon newPokemon = pokemon.getNewPokemon();
	// FormSetOld oldSet = pokemon.getFormSet();
	// FormSetOld.Form defaultForm = pokemon.getDefaultForm();
	// Form newForm = new Form();
	// FormSet newSet = new FormSet();
	//
	// newForm.setName(defaultForm.toString());
	// newForm.setGoHubFormId(defaultForm.getGoHubId(newPokemon));
	// newForm.setGoHubFormName(defaultForm.getGoHubFormName(newPokemon));
	// newForm.setImage(ImageHelper.getOfficalArtwork(newPokemon, defaultForm));
	//
	// if (ImageHelper.getOfficalArtwork(newPokemon).equals(newForm.getImage()))
	// newForm.setImage(null);
	// if (defaultForm.getType1(newPokemon) != pokemon.getType1())
	// newForm.setType1(defaultForm.getType1(newPokemon));
	// if (defaultForm.getType2(newPokemon) != pokemon.getType2())
	// newForm.setType2(defaultForm.getType2(newPokemon));
	// newForm.setShinyable(defaultForm.canBeShiny(newPokemon));
	//
	// newSet.setDefaultForm(newForm);
	//
	// List<Form> forms = new ArrayList<>();
	// for (FormSetOld.Form oldForm : oldSet.getForms()) {
	// if (oldForm.equals(defaultForm))
	// continue;
	// newForm = new Form();
	//
	// newForm.setName(oldForm.toString());
	// newForm.setGoHubFormId(oldForm.getGoHubId(newPokemon));
	// newForm.setGoHubFormName(oldForm.getGoHubFormName(newPokemon));
	// newForm.setImage(ImageHelper.getOfficalArtwork(newPokemon, oldForm));
	//
	// if (ImageHelper.getOfficalArtwork(newPokemon).equals(newForm.getImage()))
	// newForm.setImage(null);
	// if (oldForm.getType1(newPokemon) != pokemon.getType1())
	// newForm.setType1(oldForm.getType1(newPokemon));
	// if (oldForm.getType2(newPokemon) != pokemon.getType2())
	// newForm.setType2(oldForm.getType2(newPokemon));
	// newForm.setShinyable(oldForm.canBeShiny(newPokemon));
	//
	// forms.add(newForm);
	// }
	// newSet.setOtherForms(forms.toArray(new Form[0]));
	// allForms.put(pokemon.name(), newSet);
	// }
	// file.println(gson.toJson(allForms));
	// file.close();
	// }

}
