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
		return isModuleEnabledShallow(server, module) && areDepsEnabled(server, module);
	}

	public static List<StarotaModule> getEnabledModules(IGuild server) {
		List<StarotaModule> modules = new ArrayList<>();
		for (StarotaModule m : MODULES)
			if (isModuleEnabled(server, m))
				modules.add(m);
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

	private static boolean areDepsEnabled(IGuild server, StarotaModule module) {
		for (StarotaModule d : getNestedDeps(server, module))
			if (!isModuleEnabledShallow(server, d))
				return false;
		return true;
	}

	private static boolean isModuleEnabledShallow(IGuild server, StarotaModule module) {
		if (!DISABLED_MODULES.containsKey(server))
			return true;
		List<StarotaModule> modules = DISABLED_MODULES.get(server);
		return !modules.contains(module);
	}

	private static List<StarotaModule> getNestedDeps(IGuild server, StarotaModule module) {
		List<StarotaModule> deps = new ArrayList<>();
		for (StarotaModule d : module.dependencies) {
			if (deps.contains(d))
				continue;
			deps.add(d);
		}
		return deps;
	}

	private final String name;
	private final String commandCategory;
	private final StarotaModule[] dependencies;

	public StarotaModule(String name) {
		this(name, null);
	}

	public StarotaModule(String name, String commandCategory) {
		this(name, commandCategory, new StarotaModule[] {});
	}

	public StarotaModule(String name, String commandCategory, StarotaModule... dependencies) {
		this.name = name;
		this.commandCategory = commandCategory;
		this.dependencies = dependencies;
	}

	public String getName() {
		return this.name;
	}

	public String getCommandCategory() {
		return this.commandCategory;
	}

	public StarotaModule[] getDependencies() {
		return this.dependencies;
	}

}
