package us.myles_selim.starota.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sx.blah.discord.handle.obj.IGuild;

public class StarotaModule {

	private static final List<StarotaModule> MODULES = new ArrayList<>();
	private static final Map<IGuild, List<StarotaModule>> DISABLED_MODULES = new HashMap<>();

	public static void registerModule(StarotaModule module) {
		if (!MODULES.contains(module))
			MODULES.add(module);
	}

	public static boolean enableModule(IGuild server, StarotaModule module) {
		if (!DISABLED_MODULES.containsKey(server))
			return false;
		List<StarotaModule> modules = DISABLED_MODULES.get(server);
		if (!modules.contains(module))
			return false;
		return modules.remove(module);
	}

	public static boolean disableModule(IGuild server, StarotaModule module) {
		if (!DISABLED_MODULES.containsKey(server))
			DISABLED_MODULES.put(server, new ArrayList<>());
		List<StarotaModule> modules = DISABLED_MODULES.get(server);
		if (modules.contains(module))
			return false;
		return modules.add(module);
	}

	public static boolean isModuleEnabled(IGuild server, StarotaModule module) {
		if (!DISABLED_MODULES.containsKey(server))
			return true;
		List<StarotaModule> modules = DISABLED_MODULES.get(server);
		return !modules.contains(module);
	}

	public static List<StarotaModule> getEnabledModules(IGuild server) {
		if (!DISABLED_MODULES.containsKey(server))
			return Collections.unmodifiableList(MODULES);
		List<StarotaModule> modules = new ArrayList<>(MODULES);
		modules.removeAll(DISABLED_MODULES.get(server));
		return Collections.unmodifiableList(modules);
	}

	public static List<StarotaModule> getDisabledModules(IGuild server) {
		if (!DISABLED_MODULES.containsKey(server))
			return Collections.emptyList();
		return Collections.unmodifiableList(DISABLED_MODULES.get(server));
	}

	public static boolean isCategoryEnabled(IGuild server, String category) {
		if (!DISABLED_MODULES.containsKey(server))
			return true;
		List<StarotaModule> modules = DISABLED_MODULES.get(server);
		for (StarotaModule m : modules)
			if (category.equalsIgnoreCase(m.commandCategory))
				return false;
		return true;
	}

	private final String name;
	private final String commandCategory;
	

	public StarotaModule(String name) {
		this(name, null);
	}

	public StarotaModule(String name, String commandCategory) {
		this.name = name;
		this.commandCategory = commandCategory;
	}

	public String getName() {
		return this.name;
	}

	public String getCommandCategory() {
		return this.commandCategory;
	}

}
