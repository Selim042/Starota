package us.myles_selim.starota.misc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterHelper {

	private static final Properties PROPERTIES = new Properties();
	private static final Twitter TWITTER;

	static {
		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ConfigurationBuilder conf = new ConfigurationBuilder();
		conf.setOAuthConsumerKey(PROPERTIES.getProperty("twitter_key"));
		conf.setOAuthConsumerSecret(PROPERTIES.getProperty("twitter_secret"));
		conf.setOAuthAccessToken(PROPERTIES.getProperty("twitter_token"));
		conf.setOAuthAccessTokenSecret(PROPERTIES.getProperty("twitter_token_secret"));
		conf.setTweetModeExtended(true);
		conf.setDebugEnabled(true);
		Configuration builtConf = conf.build();
		TwitterFactory tf = new TwitterFactory(builtConf);
		// TwitterStreamFactory tsf = new TwitterStreamFactory(builtConf);
		TWITTER = tf.getInstance();
		// TwitterStream stream = tsf.getInstance();
		// stream.addListener(new StatusListener() {
		//
		// @Override
		// public void onException(Exception arg0) {
		// arg0.printStackTrace();
		// }
		//
		// @Override
		// public void onDeletionNotice(StatusDeletionNotice arg0) {}
		//
		// @Override
		// public void onScrubGeo(long arg0, long arg1) {}
		//
		// @Override
		// public void onStallWarning(StallWarning arg0) {}
		//
		// @Override
		// public void onStatus(Status arg0) {
		// if (arg0.getUser().getScreenName().matches(".*Sel.*")
		// || arg0.getUser().getScreenName().matches(".*koalac.*"))
		// System.out.println(arg0.getUser().getName() + " (@" +
		// arg0.getUser().getScreenName()
		// + ")" + ": " + arg0.getText());
		// try {
		// if (arg0.getUser().getScreenName().equals("koalaclumsy")
		// || arg0.getUser().getScreenName().equals("Selim_042")) {
		// TWITTER.createFavorite(arg0.getId());
		// System.out.println(arg0.getUser().getName() + " (@"
		// + arg0.getUser().getScreenName() + ")" + ": " + arg0.getText());
		// // Toolkit.getDefaultToolkit().beep();
		// }
		// } catch (TwitterException e) {
		// e.printStackTrace();
		// }
		// // System.out.println(arg0.getUser().getName() + " (@" +
		// // arg0.getUser().getScreenName()
		// // + ")" + ": " + arg0.getText());
		// }
		//
		// @Override
		// public void onTrackLimitationNotice(int arg0) {}
		//
		// });
	}

	public static void sendTweet(String tweet) {
		try {
			Status status = null;
			for (String t : splitIntoTweets(tweet)) {
				if (status == null)
					status = TWITTER.updateStatus(t);
				else {
					StatusUpdate newStatus = new StatusUpdate(t);
					newStatus.inReplyToStatusId(status.getId());
					status = TWITTER.updateStatus(newStatus);
				}
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public static void sendDirectMessage(String username, String message) {
		try {
			TWITTER.sendDirectMessage(username, message);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private static String[] splitIntoTweets(String tweet) {
		if (tweet.length() <= 280)
			return new String[] { tweet };
		String[] lines = tweet.split("\n");
		int lineIndex = 0;
		int tweetIndex = 1;
		List<String> ret = new ArrayList<>();
		StringBuilder t = new StringBuilder();
		while (lineIndex < lines.length) {
			while (lineIndex < lines.length && t.length() + lines[lineIndex].length() < 250)
				t.append(lines[lineIndex++] + "\n");
			t.append(String.format("\n(%d/", tweetIndex++));
			ret.add(t.toString());
			t = new StringBuilder();
		}
		for (int i = 0; i < ret.size(); i++)
			ret.set(i, ret.get(i) + (tweetIndex - 1) + ")");
		return ret.toArray(new String[0]);
	}

	public static void main(String... args) {
		String bigMessage = "Duplicate Changelog, Testing:\n\nChangelog for v2.9.0\r\n"
				+ "Public changes:\r\n" + " + Brand new, searchable, enhanced event system\r\n"
				+ " * Fix the 'e' in Pokemon in more places\r\n"
				+ " + Mentioning Starota now works as a command prefix\r\n"
				+ " + Add \"vote\" command\r\n"
				+ " * Fix Pokedex cutting off moves for Pokemon with large movesets, like Mew\r\n"
				+ " * Fixes updateProfile having trouble when setting alt account data\r\n"
				+ " * Starota will now automatically update trainer level if it can find a Silph Road card with higher level\r\n"
				+ " * Remove emoji from egg hatch tier command\r\n"
				+ " * Correct types for Zapdos and Moltres\r\n"
				+ " * Raid embeds now display nickname and Discord username/discriminator\r\n" + "\r\n"
				+ "Administrative changes:\r\n"
				+ " + Added system to message all server owners to easily inform of new features\r\n"
				+ " + Brand new settings system, pulls the changelog channel command into here";
		sendTweet(bigMessage);
	}

}
