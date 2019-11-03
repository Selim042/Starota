package us.myles_selim.starota.commands.credits;

import java.util.HashSet;

public class CreditSet extends HashSet<Creditable> implements Creditable {

	private static final long serialVersionUID = -8624445929154089456L;

	private EnumCreditType type;

	public CreditSet(EnumCreditType type) {
		this.type = type;
	}

	@Override
	public EnumCreditType getType() {
		return type;
	}

}
