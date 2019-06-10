package us.myles_selim.starota.commands.settings;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.commands.settings.types.ServerSetting;
import us.myles_selim.starota.misc.data_types.BotServer;
import us.myles_selim.starota.misc.utils.IValueSetCallback;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.wrappers.StarotaServer;

public class SettingSet implements Iterable<Setting<?>> {

	private IValueSetCallback setCallback;

	private final Map<String, Setting<?>> settings;

	public SettingSet() {
		this.settings = new HashMap<>();
	}

	public SettingSet(SettingSet settings) {
		this.settings = new HashMap<>();
		for (Setting<?> setting : settings)
			this.settings.put(setting.getName(), (Setting<?>) setting.clone());
	}

	public SettingSet(StarotaServer server, SettingSet settings) {
		this.settings = new HashMap<>();
		for (Setting<?> setting : settings)
			if (setting instanceof ServerSetting)
				this.settings.put(setting.getName(), ((ServerSetting<?>) setting).clone(server));
			else
				this.settings.put(setting.getName(), setting.clone());
	}

	public SettingSet setWriteCallback(IValueSetCallback callback) {
		this.setCallback = callback;
		return this;
	}

	public <V> EnumReturnSetStatus addSetting(Setting<V> setting) {
		if (settings.containsKey(setting.getName()))
			return EnumReturnSetStatus.NOT_SET;
		settings.put(setting.getName(), setting);
		if (setCallback != null) {
			setCallback.onSet();
			setting.setWriteCallback(() -> {
				if (setCallback != null)
					setCallback.onSet();
			});
		}
		return EnumReturnSetStatus.SUCCESS;
	}

	public <V> EnumReturnSetStatus setSetting(String name, String value) {
		Setting<?> setting = MiscUtils.getValueIgnoreCase(settings, name);
		if (setting == null)
			return EnumReturnSetStatus.NOT_FOUND;
		if (setting.setValue(value)) {
			if (setCallback != null)
				setCallback.onSet();
			return EnumReturnSetStatus.SUCCESS;
		} else
			return EnumReturnSetStatus.NOT_SET;
	}

	public <V> EnumReturnSetStatus setSetting(String name, V value) {
		Setting<?> setting = MiscUtils.getValueIgnoreCase(settings, name);
		if (setting == null)
			return EnumReturnSetStatus.NOT_FOUND;
		if (!setting.getType().isInstance(value))
			return EnumReturnSetStatus.NOT_SET;
		if (setting.setValue(value)) {
			if (setCallback != null)
				setCallback.onSet();
			return EnumReturnSetStatus.SUCCESS;
		} else
			return EnumReturnSetStatus.NOT_SET;
	}

	@SuppressWarnings("unchecked")
	public <V> V getSetting(String name) {
		Setting<?> setting = MiscUtils.getValueIgnoreCase(settings, name);
		if (setting != null)
			return (V) setting.getValue();
		return null;
	}

	public boolean hasSetting(String name) {
		return MiscUtils.getValueIgnoreCase(settings, name) != null;
	}

	public boolean isEmpty(String name) {
		Setting<?> setting = getSetting(name);
		if (setting == null)
			return true;
		return setting.getValue().equals(setting.getEmptyValue());
	}

	public SettingSet setServer(BotServer server) {
		iterator().forEachRemaining((Setting<?> setting) -> {
			if (setting instanceof ServerSetting)
				this.settings.put(setting.getName(), ((ServerSetting<?>) setting).clone(server));
			else
				this.settings.put(setting.getName(), setting.clone());
		});
		if (setCallback != null)
			setCallback.onSet();
		return this;
	}

	@Override
	public Iterator<Setting<?>> iterator() {
		return new SettingSetIterator();
	}

	private class SettingSetIterator implements Iterator<Setting<?>> {

		private Iterator<String> keyIterator = settings.keySet().iterator();

		@Override
		public boolean hasNext() {
			return keyIterator.hasNext();
		}

		@Override
		public Setting<?> next() {
			return settings.get(keyIterator.next());
		}

	}

	public enum EnumReturnSetStatus {
		NOT_FOUND,
		SUCCESS,
		NOT_SET;
	}

	public abstract static class DataTypeSettingSet extends DataType<SettingSet> {

		private final SettingSet value;

		public DataTypeSettingSet() {
			this.value = getDefaultSettings();
		}

		public DataTypeSettingSet(@Nonnull SettingSet settings) {
			this.value = settings;
		}

		@Nonnull
		protected abstract SettingSet getDefaultSettings();

		@Override
		public SettingSet getValue() {
			return this.value;
		}

		@Override
		protected void setValueInternal(SettingSet value) {
			this.value.setWriteCallback(() -> setValue(getValue()));
			for (Setting<?> setting : value)
				this.value.setSetting(setting.getName(), setting.getValue());
		}

		@Override
		protected void setValueObject(Object value) {
			if (this.acceptsValue(value))
				setValue((SettingSet) value);
		}

		@Override
		public Class<?>[] accepts() {
			return new Class[] { SettingSet.class };
		}

		@Override
		public void toBytes(Storage stor) {
			stor.writeInt(value.settings.size());
			for (Entry<String, Setting<?>> e : value.settings.entrySet()) {
				stor.writeString(e.getKey());
				e.getValue().toBytes(stor);
			}
		}

		@Override
		public void fromBytes(Storage stor) {
			int size = stor.readInt();
			for (int i = 0; i < size; i++) {
				String settingName = stor.readString();
				if (!value.settings.containsKey(settingName))
					continue;
				Setting<?> setting = value.settings.get(settingName);
				setting.fromBytes(stor);
			}
		}

	}

}
