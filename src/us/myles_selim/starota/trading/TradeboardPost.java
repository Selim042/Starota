package us.myles_selim.starota.trading;

import java.time.Instant;

import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.embed_converter.annotations.EmbedFooterText;
import us.myles_selim.starota.embed_converter.annotations.EmbedTitle;
import us.myles_selim.starota.trading.forms.FormSet.Form;

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

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon, Form form,
			EnumGender gender) {
		this(id, lookingFor, owner, pokemon, form, false, gender, false);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon, Form form,
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
		stor.writeInt(this.gender.ordinal());
		stor.writeBoolean(this.legacy);
	}

	@Override
	public void fromBytes(Storage stor) {
		this.id = stor.readInt();
		this.lookingFor = stor.readBoolean();
		this.owner = stor.readLong();
		this.pokemon = EnumPokemon.getPokemon(stor.readInt());
		int form = stor.readInt();
		if (form != -1 && this.pokemon.getFormSet() != null)
			this.form = this.pokemon.getFormSet().getForms().get(form);
		this.shiny = stor.readBoolean();
		this.timePosted = stor.readLong();
		if (this.timePosted <= 0 || this.timePosted >= System.currentTimeMillis() / 1000)
			this.timePosted = System.currentTimeMillis() / 1000;
		this.gender = EnumGender.values()[stor.readInt()];
		this.legacy = stor.readBoolean();
	}

}
