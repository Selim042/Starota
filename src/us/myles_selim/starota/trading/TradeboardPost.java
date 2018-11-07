package us.myles_selim.starota.trading;

import java.time.Instant;

import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class TradeboardPost extends DataType<TradeboardPost> {

	private int id;
	private boolean lookingFor;
	private long owner;
	private EnumPokemon pokemon;
	private Form form;
	private boolean shiny;
	private long timePosted;
	private EnumGenderPossible gender;

	public TradeboardPost() {}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon) {
		this(id, lookingFor, owner, pokemon, null, false, EnumGenderPossible.EITHER);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon,
			EnumGenderPossible gender) {
		this(id, lookingFor, owner, pokemon, null, false, gender);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon,
			boolean shiny) {
		this(id, lookingFor, owner, pokemon, null, shiny, EnumGenderPossible.EITHER);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon, boolean shiny,
			EnumGenderPossible gender) {
		this(id, lookingFor, owner, pokemon, null, shiny, gender);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon, Form form) {
		this(id, lookingFor, owner, pokemon, form, false, EnumGenderPossible.EITHER);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon, Form form,
			EnumGenderPossible gender) {
		this(id, lookingFor, owner, pokemon, form, false, gender);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon, Form form,
			boolean shiny, EnumGenderPossible gender) {
		this.id = id;
		this.lookingFor = lookingFor;
		this.owner = owner;
		this.pokemon = pokemon;
		this.form = form;
		this.shiny = shiny;
		this.timePosted = System.currentTimeMillis() / 1000;
		this.gender = gender;
	}

	public int getId() {
		return this.id;
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

	public EnumGenderPossible getGender() {
		return this.gender;
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
		// TODO: Fix this
		this.gender = EnumGenderPossible.values()[stor.readInt()];
		// this.gender = EnumGenderPossible.EITHER;
	}

}
