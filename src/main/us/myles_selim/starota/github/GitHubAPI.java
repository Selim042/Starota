package us.myles_selim.starota.github;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;

import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.utils.StarotaConstants;

public class GitHubAPI {

	private static final Gson GSON = new Gson();

	private static final String API_URL = "https://api.github.com/";

	private static CachedData<GitHubContributor[]> CONTRIBUTORS;

	public static GitHubContributor[] getContributors(String user, String repo) {
		// 24 hrs
		if (CONTRIBUTORS != null && !CONTRIBUTORS.hasPassed(86400000L))
			return CONTRIBUTORS.getValue();
		try {
			URL url = new URL(API_URL + "repos/" + user + "/" + repo + "/contributors");
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String input = "";
			String line = null;
			while ((line = in.readLine()) != null)
				input += line;
			CONTRIBUTORS = new CachedData<>(GSON.fromJson(input, GitHubContributor[].class));
			return CONTRIBUTORS.getValue();
		} catch (IOException e) {
			e.printStackTrace();
			return new GitHubContributor[0];
		}
	}

}
