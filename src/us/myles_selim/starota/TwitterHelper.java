package us.myles_selim.starota;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
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
		TwitterStreamFactory tsf = new TwitterStreamFactory(builtConf);
		TWITTER = tf.getInstance();
		TwitterStream stream = tsf.getInstance();
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
			TWITTER.updateStatus(tweet);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) {}

}
