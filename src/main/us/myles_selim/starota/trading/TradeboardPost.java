package us.myles_selim.starota.trading;

import java.time.Instant;
import java.util.function.Consumer;

import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumDonorPerm;
import us.myles_selim.starota.enums.EnumGender;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.forms.Form;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.wrappers.StarotaServer;

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

	public Snowflake getOwnerSnowflake() {
		return Snowflake.of(this.owner);
	}

	public EnumPokemon getPokemon() {
		return this.pokemon;
	}

	public Form getForm() {
		return this.form;
	}

	public boolean isShiny() {
		return this.shiny && ((this.form != null && this.form.isShinyable()))
				|| (this.shiny && SilphRoadData.isShinyable(pokemon));
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

	public Consumer<? super EmbedCreateSpec> getPostEmbed(StarotaServer server) {
		return getPostEmbed(server, true);
	}

	public Consumer<? super EmbedCreateSpec> getPostEmbed(StarotaServer server, boolean includeUsage) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Tradeboard Post #" + String.format("%04d", this.getId()) + "\n\n");
		if (server.getVoteRewards().contains(EnumDonorPerm.HTTP))
			builder.withUrl(Starota.getStarotaURL(this)).withAuthorUrl(Starota.getStarotaURL(this));
		builder.appendField("Trade Type:",
				"Poster " + (this.isLookingFor() ? "is looking for" : "currently has"), false);
		Member user = server.getDiscordGuild().getMemberById(Snowflake.of(this.getOwner())).block();
		if (user != null) {
			String nickname = user.getDisplayName();
			if (nickname != null)
				builder.appendField("Discord User:",
						nickname + " (_" + user.getUsername() + "#" + user.getDiscriminator() + "_)",
						true);
			else
				builder.appendField("Discord User:", user.getUsername() + "#" + user.getDiscriminator(),
						true);
		}
		PlayerProfile profile = server.getProfile(user);
		if (profile != null && profile.getTrainerCode() != -1)
			builder.appendField("Trainer Code:", profile.getTrainerCodeString(), true);

		EnumPokemon pokemon = this.getPokemon();
		Form form = this.getForm();
		if (form == null && pokemon.getData().getFormSet() != null)
			form = pokemon.getData().getDefaultForm();
		// builder.withThumbnail(ImageHelper.getPokeAPISprite(pokemon, form,
		// isShiny()));
		builder.withThumbnail(ImageHelper.getOfficalArtwork(pokemon, form));

		builder.withAuthorIcon(user.getAvatarUrl());
		builder.withAuthorName(user.getDisplayName());
		if (form != null)
			builder.withColor(form.getType1().getColor());
		else
			builder.withColor(pokemon.getData().getType1().getColor());

		builder.appendField("Pokemon:", pokemon.getData().getName(), false);
		if (!pokemon.getData().getFormSet().isDefaultOnly())
			builder.appendField("Form:", form.getName(), true);
		if (SilphRoadData.isShinyable(pokemon) || (form != null && form.isShinyable())) {
			String isShinyS = Boolean.toString(this.isShiny());
			builder.appendField("Shiny:",
					Character.toUpperCase(isShinyS.charAt(0)) + isShinyS.substring(1), true);
		}
		EnumGender gender = this.getGender();
		if (gender == null)
			gender = this.pokemon.getData().getGenderPossible();
		builder.appendField("Gender:", gender.toString(), true);
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
	protected void setValueInternal(TradeboardPost value) {
		if (value == null)
			return;
		this.id = value.id;
		this.lookingFor = value.lookingFor;
		this.owner = value.owner;
		this.pokemon = value.pokemon;
		this.form = value.form;
		this.shiny = value.shiny;
		this.timePosted = value.timePosted;
		this.gender = value.gender;
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
		stor.writeInt(this.pokemon.getData().getId());
		if (this.pokemon.getData().getFormSet() != null)
			stor.writeInt(this.pokemon.getData().getFormSet().getFormId(this.form));
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
		if (form != -1 && this.pokemon.getData().getFormSet() != null)
			this.form = this.pokemon.getData().getFormSet().getFormById(form);
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
