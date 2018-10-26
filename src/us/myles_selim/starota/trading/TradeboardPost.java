package us.myles_selim.starota.trading;

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

	public TradeboardPost() {}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon) {
		this(id, lookingFor, owner, pokemon, null, false);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon,
			boolean shiny) {
		this(id, lookingFor, owner, pokemon, null, shiny);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon, Form form) {
		this(id, lookingFor, owner, pokemon, form, false);
	}

	protected TradeboardPost(int id, boolean lookingFor, long owner, EnumPokemon pokemon, Form form,
			boolean shiny) {
		this.id = id;
		this.lookingFor = lookingFor;
		this.owner = owner;
		this.pokemon = pokemon;
		this.form = form;
		this.shiny = shiny;
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
				|| FormManager.isShinyable(this.pokemon);
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
		stor.writeBoolean(this.shiny);
	}

	@Override
	public void fromBytes(Storage stor) {
		this.id = stor.readInt();
		this.lookingFor = stor.readBoolean();
		this.owner = stor.readLong();
		this.pokemon = EnumPokemon.getPokemon(stor.readInt());
		int form = stor.readInt();
		if (form != -1 && this.pokemon.getFormSet() != null)
			this.pokemon.getFormSet().getForms().get(form);
		this.shiny = stor.readBoolean();
	}

}
