package us.myles_selim.starota.trading;

import java.time.Instant;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.ImageHelper;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.embed_converter.annotations.EmbedFooterText;
import us.myles_selim.starota.embed_converter.annotations.EmbedTitle;
import us.myles_selim.starota.enums.EnumGender;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.wrappers.StarotaServer;

@EmbedFooterText("Trade posted")
@EmbedTitle("Profile for %getIdString%:")
public class TradeboardPost extends DataType<TradeboardPost> {

	private int id;
	private boolean lookingFor;
	private long owner;
	private EnumPokemon pokemon;
	private Form form;
	private boolean shiny;
	private long timePosted;
	private EnumGender gender;
	private boolean legacy;

	public TradeboardPost() {}

	protected TradeboardPost(int id, boolean lookingFor, long owner, PokemonInstance instance) {
		this(id, lookingFor, owner, instance.getPokemon(), instance.getForm(), instance.getShiny(),
				instance.getGender(), instance.isLegacy());
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon) {
		this(id, lookingFor, owner, pokemon, null, false, EnumGender.EITHER, false);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon,
			EnumGender gender) {
		this(id, lookingFor, owner, pokemon, null, false, gender, false);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon,
			boolean shiny) {
		this(id, lookingFor, owner, pokemon, null, shiny, EnumGender.EITHER, false);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon, boolean shiny,
			EnumGender gender) {
		this(id, lookingFor, owner, pokemon, null, shiny, gender, false);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon, Form form) {
		this(id, lookingFor, owner, pokemon, form, false, EnumGender.EITHER, false);
	}

	public TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon, Form form,
			EnumGender gender) {
		this(id, lookingFor, owner, pokemon, form, false, gender, false);
	}

	public TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon, Form form,
			boolean shiny, EnumGender gender, boolean legacy) {
		this.id = id;
		this.lookingFor = lookingFor;
		this.owner = owner;
		this.pokemon = pokemon;
		this.form = form;
		this.shiny = shiny;
		this.timePosted = System.currentTimeMillis() / 1000;
		this.gender = gender;
		this.legacy = legacy;
	}

	public int getId() {
		return this.id;
	}

	public String getIdString() {
		return String.format("%04d", this.id);
	}

	public boolean isLookingFor() {
		return this.lookingFor;
	}

	public long getOwner() {
		return this.owner;
	}

	public EnumPokemon getPokemon() {
		return this.pokemon;
	}

	public Form getForm() {
		return this.form;
	}

	public boolean isShiny() {
		return this.shiny && ((this.form != null && this.form.canBeShiny(pokemon)))
				|| (this.shiny && FormManager.isShinyable(this.pokemon));
	}

	public Instant getTimePosted() {
		if (this.timePosted == 0)
			this.timePosted = System.currentTimeMillis() / 1000;
		return Instant.ofEpochSecond(this.timePosted);
	}

	public EnumGender getGender() {
		return this.gender;
	}

	public boolean isLegacy() {
		return this.legacy;
	}

	public EmbedObject getPostEmbed(StarotaServer server) {
		return getPostEmbed(server, true);
	}

	public EmbedObject getPostEmbed(StarotaServer server, boolean includeUsage) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Tradeboard Post #" + String.format("%04d", this.getId()) + "\n\n");
		builder.appendField("Trade Type:",
				"Poster " + (this.isLookingFor() ? "is looking for" : "currently has"), false);
		IUser user = Starota.getUser(this.getOwner());
		if (user != null) {
			String nickname = user.getNicknameForGuild(server.getDiscordGuild());
			if (nickname != null)
				builder.appendField("Discord User:",
						nickname + " (_" + user.getName() + "#" + user.getDiscriminator() + "_)", true);
			else
				builder.appendField("Discord User:", user.getName() + "#" + user.getDiscriminator(),
						true);
		}
		PlayerProfile profile = server.getProfile(user);
		if (profile != null && profile.getTrainerCode() != -1)
			builder.appendField("Trainer Code:", profile.getTrainerCodeString(), true);

		EnumPokemon pokemon = this.getPokemon();
		Form form = this.getForm();
		if (form == null && pokemon.getFormSet() != null)
			form = pokemon.getDefaultForm();
		// builder.withThumbnail(ImageHelper.getPokeAPISprite(pokemon, form,
		// isShiny()));
		builder.withThumbnail(ImageHelper.getOfficalArtwork(pokemon, form));

		builder.withAuthorIcon(user.getAvatarURL());
		builder.withAuthorName(user.getDisplayName(server.getDiscordGuild()));
		if (form != null)
			builder.withColor(form.getType1(pokemon).getColor());
		else
			builder.withColor(pokemon.getType1().getColor());

		builder.appendField("Pokemon:", pokemon.getName(), false);
		if (form != null)
			builder.appendField("Form:", form.toString(), true);
		if (FormManager.isShinyable(pokemon) || (form != null && form.canBeShiny(pokemon))) {
			String isShinyS = Boolean.toString(this.isShiny());
			builder.appendField("Shiny:",
					Character.toUpperCase(isShinyS.charAt(0)) + isShinyS.substring(1), true);
		}
		builder.appendField("Gender:", this.getGender().toString(), true);
		String isLegacyS = Boolean.toString(this.isLegacy());
		builder.appendField("Legacy:",
				Character.toUpperCase(isLegacyS.charAt(0)) + isLegacyS.substring(1), true);

		if (includeUsage)
			builder.appendField("Reaction Usage:", "To let the poster know you are interested, press "
					+ TradeboardReactionMessage.CONFIRM_EMOJI + ".\n" + "If you are the poster, press "
					+ TradeboardReactionMessage.DELETE_EMOJI + " to remove the post.", false);

		builder.withFooterText("Trade posted");
		builder.withTimestamp(this.getTimePosted());
		return builder.build();
	}

	@Override
	public TradeboardPost getValue() {
		return this;
	}

	@Override
	public void setValue(TradeboardPost value) {
		if (value == null)
			return;
		this.id = value.id;
		this.lookingFor = value.lookingFor;
		this.owner = value.owner;
		this.pokemon = value.pokemon;
		this.form = value.form;
		this.shiny = value.shiny;
		this.timePosted = value.timePosted;
		this.legacy = value.legacy;
	}

	@Override
	protected void setValueObject(Object value) {
		if (value instanceof TradeboardPost)
			setValue((TradeboardPost) value);
	}

	@Override
	public Class<?>[] accepts() {
		return new Class[] { TradeboardPost.class };
	}

	@Override
	public void toBytes(Storage stor) {
		stor.writeInt(this.id);
		stor.writeBoolean(this.lookingFor);
		stor.writeLong(this.owner);
		stor.writeInt(this.pokemon.getId());
		if (this.pokemon.getFormSet() != null)
			stor.writeInt(this.pokemon.getFormSet().getForms().indexOf(this.form));
		else
			stor.writeInt(-1);
		stor.writeBoolean(this.shiny);
		stor.writeLong(this.timePosted);
		if (this.gender != null)
			stor.writeInt(this.gender.ordinal());
		else
			stor.writeInt(-1);
		stor.writeBoolean(this.legacy);
	}

	@Override
	public void fromBytes(Storage stor) {
		this.id = stor.readInt();
		this.lookingFor = stor.readBoolean();
		this.owner = stor.readLong();
		int pokemonId = stor.readInt();
		this.pokemon = EnumPokemon.getPokemon(pokemonId);
		int form = stor.readInt();
		if (form != -1 && this.pokemon.getFormSet() != null)
			this.form = this.pokemon.getFormSet().getForms().get(form);
		this.shiny = stor.readBoolean();
		this.timePosted = stor.readLong();
		if (this.timePosted <= 0 || this.timePosted >= System.currentTimeMillis() / 1000)
			this.timePosted = System.currentTimeMillis() / 1000;
		int genderOrdinal = stor.readInt();
		if (genderOrdinal != -1)
			this.gender = EnumGender.values()[genderOrdinal];
		this.legacy = stor.readBoolean();
	}

}
