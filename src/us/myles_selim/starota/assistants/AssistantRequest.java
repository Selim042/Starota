package us.myles_selim.starota.assistants;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.TwitterHelper;

public class AssistantRequest {

	private static long TWITTER_REPORT_TIME = 0;

	public static <T> T request(IAssistantRequest<T> request) {
		T ret = null;
		boolean executed = false;
		boolean hasNoAssistants = StarotaAssistants.getClients().isEmpty();
		if (hasNoAssistants && Starota.IS_DEV)
			return RequestBuffer.request(() -> request.request(Starota.getClient())).get();
		if (hasNoAssistants)
			throw new IllegalArgumentException("there are no assistants defined");
		for (IDiscordClient client : StarotaAssistants.getClients()) {
			try {
				ret = request.request(client);
				executed = true;
				break;
			} catch (RateLimitException e) {}
		}
		if (!executed) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
			ret = request(request);
			// half hour
			if (System.currentTimeMillis() - TWITTER_REPORT_TIME > 1800000) {
				TwitterHelper.sendDirectMessage("Selim_042",
						"The assistants are all rate limited, " + "please add another.");
				TWITTER_REPORT_TIME = System.currentTimeMillis();
			}
		}
		return ret;
	}

	public static void request(IVoidAssistantRequest request) {
		request((IDiscordClient client) -> {
			request.doRequest(client);
			return null;
		});
	}

	@FunctionalInterface
	public interface IAssistantRequest<T> {

		T request(IDiscordClient client);

	}

	@FunctionalInterface
	public interface IVoidAssistantRequest {

		void doRequest(IDiscordClient client);

	}

}
