package us.myles_selim.starota.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.data_types.DataTypeString;
import us.myles_selim.starota.debug_server.DebugServer;
import us.myles_selim.starota.wrappers.StarotaServer;

public class StarotaModule {

	private final String name;
	private final String commandCategory;
	private String description;
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

	public StarotaModule setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getDescription() {
		return this.description;
	}

	public StarotaModule[] getDependencies() {
		return this.dependencies;
	}

	public static final String MODULE_KEY = "disabledModules";

	private static final List<StarotaModule> MODULES = new ArrayList<>();

	@SuppressWarnings("unchecked")
	private static List<StarotaModule> getDisabledModulesRaw(StarotaServer server) {
		if (server == null || !server.hasDataKey(MODULE_KEY, EBList.class))
			return new ArrayList<>();
		List<StarotaModule> modules = new ArrayList<>();
		for (String name : ((EBList<String>) server.getDataValue(MODULE_KEY)).values()) {
			StarotaModule module = getModule(name);
			if (module != null)
				modules.add(module);
		}
		return modules;
	}

	private static boolean disableModuleRaw(StarotaServer server, StarotaModule module) {
		if (server == null || !server.hasDataKey(MODULE_KEY, EBList.class))
			server.setDataValue(MODULE_KEY, new EBList<>(new DataTypeString()));
		@SuppressWarnings("unchecked")
		EBList<String> modules = (EBList<String>) server.getDataValue(MODULE_KEY);
		if (modules.containsWrapped(module.name))
			return false;
		modules.addWrapped(module.name);
		server.setDataValue(MODULE_KEY, modules);
		return true;
	}

	private static boolean enableModuleRaw(StarotaServer server, StarotaModule module) {
		if (server == null || !server.hasDataKey(MODULE_KEY))
			return false;
		@SuppressWarnings("unchecked")
		EBList<String> modules = (EBList<String>) server.getDataValue(MODULE_KEY);
		if (!modules.containsWrapped(module.name))
			return false;
		modules.removeWrapped(module.name);
		server.setDataValue(MODULE_KEY, modules);
		return true;
	}

	public static void registerModule(StarotaModule module) {
		if (!MODULES.contains(module))
			MODULES.add(module);
	}

	public static boolean enableModule(StarotaServer server, StarotaModule module) {
		return enableModuleRaw(server, module);
	}

	public static boolean disableModule(StarotaServer server, StarotaModule module) {
		return disableModuleRaw(server, module);
	}

	public static boolean isModuleEnabled(StarotaServer server, StarotaModule module) {
		return isModuleEnabledShallow(server, module) && areDepsEnabled(server, module);
	}

	public static List<StarotaModule> getEnabledModules(StarotaServer server) {
		List<StarotaModule> modules = new ArrayList<>();
		for (StarotaModule m : MODULES)
			if (isModuleEnabled(server, m))
				modules.add(m);
		return Collections.unmodifiableList(modules);
	}

	public static List<StarotaModule> getDisabledModules(StarotaServer server) {
		return Collections.unmodifiableList(getDisabledModulesRaw(server));
	}

	public static boolean isCategoryEnabled(StarotaServer server, String category) {
		if (server == null || !server.hasDataKey(MODULE_KEY, EBList.class))
			return true;
		List<StarotaModule> modules = getDisabledModulesRaw(server);
		for (StarotaModule m : modules)
			if (category.equalsIgnoreCase(m.commandCategory))
				return false;
		return true;
	}

	public static StarotaModule getModule(String name) {
		for (StarotaModule m : MODULES)
			if (m.name.equalsIgnoreCase(name)
					|| (m.commandCategory != null && m.commandCategory.equalsIgnoreCase(name)))
				return m;
		return null;
	}

	public static List<StarotaModule> getAllModules() {
		return Collections.unmodifiableList(MODULES);
	}

	private static boolean areDepsEnabled(StarotaServer server, StarotaModule module) {
		for (StarotaModule d : getNestedDeps(server, module))
			if (!isModuleEnabledShallow(server, d))
				return false;
		return true;
	}

	private static boolean isModuleEnabledShallow(StarotaServer server, StarotaModule module) {
		if (server == null || !server.hasDataKey(MODULE_KEY, EBList.class))
			return true;
		List<StarotaModule> modules = getDisabledModulesRaw(server);
		return !modules.contains(module);
	}

	private static List<StarotaModule> getNestedDeps(StarotaServer server, StarotaModule module) {
		List<StarotaModule> deps = new ArrayList<>();
		for (StarotaModule d : module.dependencies) {
			if (deps.contains(d))
				continue;
			deps.add(d);
		}
		return deps;
	}

}
