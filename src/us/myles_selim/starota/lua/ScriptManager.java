package us.myles_selim.starota.lua;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.compiler.CompileException;
import org.squiddev.cobalt.compiler.LoadState;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IMessage.Attachment;
import us.myles_selim.starota.lua.conversion.ConversionHandler;

public class ScriptManager {

	public static final int FOLDER_SIZE_LIMIT = 10000000;
	public static final String SIZE_LIMIT_S = "1 megabyte";
	public static final File SCRIPT_FOLDER = new File("starotaData", "lua");
	public static final String LUA_EXENSION = ".lua";
	public static final FilenameFilter LUA_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.toLowerCase().endsWith(LUA_EXENSION);
		}
	};

	public static boolean saveScript(IGuild server, String name, Attachment attach) {
		if (attach == null)
			return false;
		String contents = getAttachmentContents(attach.getUrl());
		File folder = new File(SCRIPT_FOLDER, server.getStringID());
		long folderSize = folderSize(folder.toPath());
		if (folderSize + contents.getBytes().length >= FOLDER_SIZE_LIMIT)
			return false;
		try {
			if (!folder.exists())
				folder.mkdirs();
			File file = new File(folder, name);
			FileWriter writer = new FileWriter(file);
			writer.write(contents);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean removeScript(IGuild server, String name) {
		File folder = new File(SCRIPT_FOLDER, server.getStringID());
		File script = new File(folder, name);
		// if (script.exists() && !script.isDirectory())
		try {
			Files.delete(script.toPath());
			return true;
		} catch (IOException e) {
			System.out.println("Attempting to remove " + name + " script for " + server.getName());
			e.printStackTrace();
			return false;
		}
		// return false;
	}

	public static boolean executeCommandScript(IGuild server, String name, IMessage message,
			IChannel channel) throws LuaError, IOException, CompileException {
		return executeCommandScript(server, name, message, channel, new String[0]);
	}

	public static boolean executeCommandScript(IGuild server, String name, IMessage message,
			IChannel channel, String[] args) throws LuaError, IOException, CompileException {
		File folder = new File(SCRIPT_FOLDER, server.getStringID());
		LuaState state = LuaUtils.getState(server);
		// state.stdout = System.out;
		LuaTable _G = state.getMainThread().getfenv();
		// try {
		LuaValue[] argsA = new LuaValue[args.length + 2];
		argsA[0] = ConversionHandler.convertToLua(state, message);
		argsA[1] = ConversionHandler.convertToLua(state, channel);
		for (int i = 0; i < args.length; i++)
			argsA[i + 2] = ValueFactory.valueOf(args[i]);
		FileInputStream scriptFile = new FileInputStream(
				new File(folder, "commands" + File.separator + name + LUA_EXENSION));
		LoadState.load(state, scriptFile, "@" + name, _G).invoke(state, ValueFactory.varargsOf(argsA));
		scriptFile.close();
		return true;
		// } catch (LuaError e) {
		// channel.sendMessage("An error was encountered when executing your )
		// return true;
		// } catch (CompileException | IOException e) {
		// e.printStackTrace();
		// return false;
		// }
	}

	public static boolean executeEventScript(IGuild server) {
		return executeEventScript(LuaUtils.getState(server), server);
	}

	public static boolean executeEventScript(LuaState state, IGuild server) {
		File folder = new File(SCRIPT_FOLDER, server.getStringID());
		// state.stdout = System.out;
		LuaTable _G = state.getMainThread().getfenv();
		try {
			File file = new File(folder, "eventHandler" + LUA_EXENSION);
			if (file.isDirectory() || !file.exists())
				return false;
			FileInputStream scriptFile = new FileInputStream(file);
			LoadState.load(state, scriptFile, "@eventHandler", _G).call(state);
			scriptFile.close();
			return true;
		} catch (LuaError | CompileException | IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static List<String> getCommandScripts(IGuild server) {
		List<String> ret = new ArrayList<>();
		File folder = new File(SCRIPT_FOLDER, server.getStringID() + File.separator + "commands");
		for (File f : folder.listFiles(LUA_FILTER)) {
			String name = f.getName();
			ret.add(name.substring(0, name.length() - LUA_EXENSION.length()));
		}
		return Collections.unmodifiableList(ret);
	}

	private static String getAttachmentContents(String url) {
		try {
			URL urlL = new URL(url);
			URLConnection connection = urlL.openConnection();
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) "
							+ "Chrome/23.0.1271.95 Safari/537.11");
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				response.append(inputLine + '\n');
			in.close();
			return response.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static long folderSize(Path path) {

		final AtomicLong size = new AtomicLong(0);

		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

					size.addAndGet(attrs.size());
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) {

					System.out.println("skipped: " + file + " (" + exc + ")");
					// Skip folders that can't be traversed
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

					if (exc != null)
						System.out.println("had trouble traversing: " + dir + " (" + exc + ")");
					// Ignore errors traversing a folder
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw new AssertionError(
					"walkFileTree will not throw IOException if the FileVisitor does not");
		}

		return size.get();
	}

}
