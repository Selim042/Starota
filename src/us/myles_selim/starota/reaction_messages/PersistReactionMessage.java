package us.myles_selim.starota.reaction_messages;

import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;

public abstract class PersistReactionMessage extends ReactionMessage {

	public abstract void toBytes(Storage stor);

	public abstract void fromBytes(Storage stor);

	public static class DataTypePReactionMessage extends DataType<PersistReactionMessage> {

		private PersistReactionMessage value;

		@Override
		public PersistReactionMessage getValue() {
			return this.value;
		}

		@Override
		public void setValue(PersistReactionMessage value) {
			this.value = value;
		}

		@Override
		protected void setValueObject(Object value) {
			if (this.acceptsValue(value))
				this.value = (PersistReactionMessage) value;
		}

		@Override
		public Class<?>[] accepts() {
			return new Class[] { PersistReactionMessage.class };
		}

		@Override
		public void toBytes(Storage stor) {
			stor.writeBoolean(this.value == null);
			if (this.value != null) {
				stor.writeString(this.value.getClass().getName());
				this.value.toBytes(stor);
			}
		}

		@Override
		public void fromBytes(Storage stor) {
			boolean isNull = stor.readBoolean();
			if (!isNull) {
				try {
					Object obj = Class.forName(stor.readString()).newInstance();
					if (!(obj instanceof PersistReactionMessage)) {
						System.out.println(obj.getClass().getName()
								+ " is no longer a peristent message, was it previously?");
						return;
					}
					this.value = (PersistReactionMessage) obj;
					this.value.fromBytes(stor);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
