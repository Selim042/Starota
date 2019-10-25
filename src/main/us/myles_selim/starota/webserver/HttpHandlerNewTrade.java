package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import us.myles_selim.starota.enums.EnumGender;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.forms.Form;
import us.myles_selim.starota.forms.FormSet;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.webserver.WebServer.Cookie;

@SuppressWarnings("restriction")
public class HttpHandlerNewTrade implements HttpHandler {

	private static final String DROPDOWN_GROUP = "<optgroup label=\"{NAME}\">{OPTIONS}</optgroup>";
	private static final String DROPDOWN = "<option value=\"{ID}\">{NAME}</option>";
	private static final String DROPDOWN_SELECTED = "<option value=\"{ID}\" selected>{NAME}</option>";

	@Override
	public void handle(HttpExchange ex) throws IOException {
		try {
			if (!WebServer.isLoggedIn(ex)) {
				// WebServer.return404(ex, "Please " +
				// WebServer.getLoginHTML(ex, "login"));
				WebServer.redirect(ex, "/login");
				return;
			}
			Cookie tokenCookie = WebServer.getCookies(ex).get("token");
			Cookie serverCookie = WebServer.getCookies(ex).get("current_server");
			if (serverCookie == null) {
				WebServer.returnServer404(ex, "Please select a server");
				return;
			}
			Map<String, String> get = WebServer.getGET(ex);
			int selected;
			if (get.containsKey("pokemon")) {
				try {
					selected = Integer.parseInt(get.get("pokemon"));
				} catch (NumberFormatException e) {
					selected = 1;
				}
			} else
				selected = 1;

			InputStream file = WebServer.getResourceFile("http/new_trade.html");
			BufferedReader profTempFile = new BufferedReader(new InputStreamReader(file));
			StringBuilder tempBuilder = new StringBuilder();
			profTempFile.lines().forEach(tempBuilder::append);
			profTempFile.close();
			String temp = tempBuilder.toString();

			// fill stuff
			temp = WebServer.fillBaseStuff(ex, tokenCookie.value, temp);
			temp = fillFormInfo(temp, selected, get.containsKey("form") ? get.get("form") : null);

			WebServer.setContentType(ex, "http/new_trade.html");
			OutputStream response = ex.getResponseBody();
			byte[] out = temp.getBytes("UTF-8");
			ex.sendResponseHeaders(200, out.length);
			response.write(out);
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
			WebServer.returnError(ex, e);
		}
	}

	private String fillFormInfo(String temp, int selectedPokeI, String selectedFormS) {
		EnumPokemon selectedPokemon = EnumPokemon.getPokemon(selectedPokeI);
		if (selectedPokemon == null || !selectedPokemon.getData().isTradable())
			selectedPokemon = EnumPokemon.BULBASAUR;
		Form selectedForm;
		FormSet set = selectedPokemon.getData().getFormSet();
		if (set == null)
			selectedForm = null;
		else
			selectedForm = set.getForm(selectedFormS);
		if (selectedForm == null)
			selectedForm = selectedPokemon.getData().getDefaultForm();

		// Pokemon
		StringBuilder fullPokemonOptions = new StringBuilder();
		StringBuilder pokemonOptions = new StringBuilder();
		int prevGen = 1;
		for (EnumPokemon pokemon : EnumPokemon.values()) {
			if (!SilphRoadData.isAvailable(pokemon) || !pokemon.getData().isTradable())
				continue;
			if (pokemon.getData().getGeneration() != prevGen) {
				fullPokemonOptions
						.append(DROPDOWN_GROUP.replaceAll("\\{NAME\\}", "Generation " + prevGen)
								.replaceAll("\\{OPTIONS\\}", pokemonOptions.toString()));
				pokemonOptions = new StringBuilder();
				prevGen = pokemon.getData().getGeneration();
			}
			String pokemonOption;
			if (selectedPokemon.getData().getId() != pokemon.getData().getId())
				pokemonOption = DROPDOWN.replaceAll("\\{ID\\}",
						Integer.toString(pokemon.getData().getId()));
			else
				pokemonOption = DROPDOWN_SELECTED.replaceAll("\\{ID\\}",
						Integer.toString(pokemon.getData().getId()));
			pokemonOption = pokemonOption.replaceAll("\\{NAME\\}", pokemon.getData().getName());

			pokemonOptions.append(pokemonOption);
		}
		fullPokemonOptions.append(DROPDOWN_GROUP.replaceAll("\\{NAME\\}", "Generation " + prevGen)
				.replaceAll("\\{OPTIONS\\}", pokemonOptions.toString()));
		temp = temp.replaceAll("\\{POKEMON_OPTIONS\\}", fullPokemonOptions.toString());

		// Forms
		StringBuilder formOptions = new StringBuilder();
		FormSet forms = selectedPokemon.getData().getFormSet();
		if (forms != null) {
			for (Form f : forms) {
				if (f.equals(selectedForm))
					formOptions.append(DROPDOWN_SELECTED.replaceAll("\\{ID\\}", f.toString())
							.replaceAll("\\{NAME\\}", f.getName()));
				else
					formOptions.append(DROPDOWN.replaceAll("\\{ID\\}", f.toString())
							.replaceAll("\\{NAME\\}", f.getName()));
			}
			temp = temp.replaceAll("\\{FORM_OPTIONS\\}", formOptions.toString());
		} else {
			temp = temp.replaceAll("\\{FORM_OPTIONS\\}",
					DROPDOWN.replaceAll("\\{ID\\}", "-1").replaceAll("\\{NAME\\}", "-"));
		}

		// Genders
		StringBuilder genderOptions = new StringBuilder();
		EnumGender genders = selectedPokemon.getData().getGenderPossible();
		switch (genders) {
		case EITHER:
			genderOptions.append(DROPDOWN.replaceAll("\\{ID\\}", Integer.toString(2))
					.replaceAll("\\{NAME\\}", "Male"));
			genderOptions.append(DROPDOWN.replaceAll("\\{ID\\}", Integer.toString(3))
					.replaceAll("\\{NAME\\}", "Female"));
			genderOptions.append(DROPDOWN.replaceAll("\\{ID\\}", Integer.toString(1))
					.replaceAll("\\{NAME\\}", "Male/female"));
			break;
		case FEMALE:
			genderOptions.append(DROPDOWN.replaceAll("\\{ID\\}", Integer.toString(3))
					.replaceAll("\\{NAME\\}", "Female"));
			break;
		case MALE:
			genderOptions.append(DROPDOWN.replaceAll("\\{ID\\}", Integer.toString(2))
					.replaceAll("\\{NAME\\}", "Male"));
			break;
		case UNKNOWN:
			genderOptions.append(DROPDOWN.replaceAll("\\{ID\\}", Integer.toString(0))
					.replaceAll("\\{NAME\\}", "Unknown"));
			break;
		}
		temp = temp.replaceAll("\\{GENDER_OPTIONS\\}", genderOptions.toString());

		// Shiny
		if (selectedForm == null)
			temp = temp.replaceAll("\\{SHINY_DISABLE\\}",
					SilphRoadData.isShinyable(selectedPokemon) ? "" : "disabled");
		else
			temp = temp.replaceAll("\\{SHINY_DISABLE\\}", selectedForm.isShinyable() ? "" : "disabled");

		return temp;
	}

}
